package com.java_app.demo.currency.impl;

import static java.util.Objects.requireNonNull;

import com.java_app.demo.currency.*;
import com.java_app.demo.currency.dto.ExchangeDto;
import com.java_app.demo.location.LocationService;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {
  private static final String BASE_URL_TO_EXCHANGE_RATE =
      "https://v1.apiplugin.io/v1/currency/VqZWGJXn/rates";
  private final ExchangeRepository exchangeRepository;
  private final RestTemplate restTemplate;
  private final LocationService locationService;

  public ExchangeDto getExchangeRate(String ip, @NotBlank String target)
      throws HttpClientErrorException {
    String source = locationService.getUserLocationByIp(ip).getCurrency();
    Optional<Exchange> optionalExchange = exchangeRepository.findBySourceAndTarget(source, target);
    if (optionalExchange.isPresent()
        && LocalDateTime.now().isBefore(optionalExchange.get().getExpirationDate())) {
      Exchange result = optionalExchange.get();
      return new ExchangeDto(source, target, result.getAmount());
    } else {
      if (target.length() != 3) {
        throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
      }
      CurrencyApiResponse response =
          requireNonNull(
              restTemplate.getForObject(
                  BASE_URL_TO_EXCHANGE_RATE
                      + "?source="
                      + source.toUpperCase()
                      + "&target="
                      + target.toUpperCase(),
                  CurrencyApiResponse.class));
      if (response.getError() != null) {
        throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
      }
      Exchange exchange =
          Exchange.builder()
              .source(source)
              .target(target)
              .amount(response.getRates().get(target))
              .expirationDate(LocalDateTime.now().plusDays(30))
              .build();
      exchangeRepository.save(exchange);
      return new ExchangeDto(source, target, exchange.getAmount());
    }
  }
}

package com.java_app.demo.currency.impl;

import static java.util.Objects.requireNonNull;

import com.java_app.demo.advice.exceptions.BadRequestException;
import com.java_app.demo.advice.exceptions.InternalServerErrorException;
import com.java_app.demo.currency.*;
import com.java_app.demo.currency.dto.ExchangeDto;
import com.java_app.demo.location.LocationService;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {
  private static final String BASE_URL_TO_EXCHANGE_RATE =
      "https://v1.apiplugin.io/v1/currency/VqZWGJXn/rates";
  private final ExchangeRepository exchangeRepository;
  private final RestTemplate restTemplate;
  private final LocationService locationService;

  public ExchangeDto getExchangeRate(String ip, String target)
      throws BadRequestException, InternalServerErrorException {
    String source = locationService.getUserLocationByIp(ip).getCurrency();
    Optional<Exchange> optionalExchange = exchangeRepository.findBySourceAndTarget(source, target);
    if (optionalExchange.isPresent()) {
      if (LocalDateTime.now().isBefore(optionalExchange.get().getExpirationDate())) {
        Exchange result = optionalExchange.get();
        return new ExchangeDto(source, target, result.getAmount());
      }
      exchangeRepository.delete(optionalExchange.orElse(null));
    }

    if (target.length() != 3) {
      throw new BadRequestException("The request doesn't respect the 3 letters format.");
    }
    CurrencyApiResponse response;
    try {
      response =
          requireNonNull(
              restTemplate.getForObject(
                  BASE_URL_TO_EXCHANGE_RATE
                      + "?source="
                      + source.toUpperCase()
                      + "&target="
                      + target.toUpperCase(),
                  CurrencyApiResponse.class));
    } catch (Exception e) {
      throw new InternalServerErrorException(
          String.format(
              "An internal error occurred while trying to call the exchange rate API: %s",
              e.getMessage()));
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

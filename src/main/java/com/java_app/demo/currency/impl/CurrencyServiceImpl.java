package com.java_app.demo.currency.impl;

import static com.java_app.demo.location.impl.LocationServiceImpl.BASE_URL_TO_LOCATION_API;
import static com.java_app.demo.location.impl.LocationServiceImpl.FIELDS_REQUIRED_AS_PARAMETERS;
import static java.util.Objects.requireNonNull;

import com.java_app.demo.currency.*;
import com.java_app.demo.currency.dto.ExchangeDto;
import com.java_app.demo.location.LocationDto;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {
  private static final String BASE_URL_TO_EXCHANGE_RATE =
      "https://v1.apiplugin.io/v1/currency/VqZWGJXn/rates";
  private final ExchangeRepository exchangeRepository;

  public CustomTransferCurrency getExchangeRate(String ip, @NotBlank String target) {
    RestTemplate restTemplate = new RestTemplate();
    LocalDateTime today = LocalDateTime.now();
    InetAddressValidator validator = InetAddressValidator.getInstance();

    if (!validator.isValid(ip)) {
      return new CustomTransferCurrency(null, HttpStatus.BAD_REQUEST);
    }
    LocationDto locationDto;
    try {
      locationDto =
          requireNonNull(
              restTemplate.getForObject(
                  BASE_URL_TO_LOCATION_API + ip + FIELDS_REQUIRED_AS_PARAMETERS,
                  LocationDto.class));
    } catch (Exception e) {
      return new CustomTransferCurrency(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    String source = locationDto.getCurrency();
    Optional<Exchange> optional_exchange = exchangeRepository.findBySourceAndTarget(source, target);
    if (optional_exchange.isPresent()
        && today.isBefore(optional_exchange.get().getExpirationDate())) {
      Exchange result = optional_exchange.get();
      ExchangeDto exchangeDto = new ExchangeDto(source, target, result.getAmount());
      return new CustomTransferCurrency(exchangeDto, HttpStatus.OK);
    } else {
      if (target.length() != 3) {
        return new CustomTransferCurrency(null, HttpStatus.BAD_REQUEST);
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
        return new CustomTransferCurrency(null, HttpStatus.NOT_FOUND);
      }
      LocalDateTime expirationTime = LocalDateTime.now().plusDays(30);
      Exchange exchange =
          Exchange.builder()
              .source(source)
              .target(target)
              .amount(response.getRates().get(target))
              .expirationDate(expirationTime)
              .build();
      exchangeRepository.save(exchange);
      ExchangeDto exchangeDto = new ExchangeDto(source, target, exchange.getAmount());
      return new CustomTransferCurrency(exchangeDto, HttpStatus.OK);
    }
  }
}

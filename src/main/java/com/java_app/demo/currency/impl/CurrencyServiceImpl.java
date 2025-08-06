package com.java_app.demo.currency.impl;

import static java.util.Objects.requireNonNull;

import com.java_app.demo.location.LocationDto;
import com.java_app.demo.currency.CurrencyApiResponse;
import com.java_app.demo.currency.CurrencyService;
import com.java_app.demo.currency.Exchange;
import com.java_app.demo.currency.ExchangeRepository;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {
  public static final String FIELDS =
      "?fields=status,message,country,countryCode,region,regionName,city,zip,lat,lon,timezone,currency,isp,org,as,query";
  public static final String URL = "http://ip-api.com/json/";
  private final RestTemplate restTemplate = new RestTemplate();
  private static final String BASE_URL = "https://v1.apiplugin.io/v1/currency/VqZWGJXn/rates";
  private final ExchangeRepository exchangeRepository;
  static InetAddressValidator validator = InetAddressValidator.getInstance();
  private final Date today = new Date();

  public ResponseEntity<Map<String, Double>> getCurrencies(String ip, @NotBlank String target) {

    if (!validator.isValid(ip)) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    LocationDto locationDto;
    try {
      locationDto = requireNonNull(restTemplate.getForObject(URL + ip + FIELDS, LocationDto.class));
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    String source = locationDto.getCurrency();
    if (exchangeRepository.existsBySourceAndTarget(source, target)
        && today
            .toInstant()
            .isBefore(
                exchangeRepository
                    .findBySourceAndTarget(source, target)
                    .getExpirationDate()
                    .toInstant())) {
      Exchange result = exchangeRepository.findBySourceAndTarget(source, target);
      Map<String, Double> map = Map.of(source, result.getAmount());
      return new ResponseEntity<>(map, HttpStatus.OK);
    } else {
      if (target.length() != 3) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
      CurrencyApiResponse response =
          requireNonNull(
              restTemplate.getForObject(
                  BASE_URL + "?source=" + source.toUpperCase() + "&target=" + target.toUpperCase(),
                  CurrencyApiResponse.class));
      Map<String, Double> rates = response.getRates();
      if (response.getError() != null) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
      Date ExpirationTime = new Date(LocalDate.now().plusDays(30).toEpochDay());
      Exchange exchange = new Exchange(source, target, rates.get(target), ExpirationTime);
      exchangeRepository.save(exchange);
      return new ResponseEntity<>(rates, HttpStatus.OK);
    }
  }
}

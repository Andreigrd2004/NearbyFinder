package com.java_app.demo.api;

import com.java_app.demo.currency.CustomTransferCurrency;
import com.java_app.demo.currency.dto.ExchangeDto;
import com.java_app.demo.location.CustomTransferLocation;
import com.java_app.demo.location.LocationDto;
import com.java_app.demo.location.LocationService;
import com.java_app.demo.currency.CurrencyService;
import com.java_app.demo.news.CustomTransferNews;
import com.java_app.demo.news.NewsService;
import com.java_app.demo.news.dto.NewsDto;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class AllInformationApiController {

  private final LocationService locationService;
  private final NewsService newsService;
  private final CurrencyService currencyService;

  @GetMapping("/location")
  public ResponseEntity<LocationDto> getLocation(@RequestParam("ip") String ip) {
    CustomTransferLocation transfer = locationService.getUserLocationByIp(ip);
    return new ResponseEntity<>(transfer.getLocationDto(), transfer.getHttpStatus());
  }

  @GetMapping("/news")
  public ResponseEntity<List<NewsDto>> getNews(@RequestParam("ip") String ip) {
    CustomTransferNews transfer = newsService.getNewspaper(ip);
    return new ResponseEntity<>(transfer.getNews(), transfer.getStatus());
  }

  @GetMapping("/exchangerate")
  public ResponseEntity<ExchangeDto> getExchange(
      @RequestParam("target") @NotBlank String source, @RequestParam("ip") String ip) {
    CustomTransferCurrency transfer = currencyService.getExchangeRate(ip, source);
    return new ResponseEntity<>(transfer.getCurrency(), transfer.getStatus());
  }
}

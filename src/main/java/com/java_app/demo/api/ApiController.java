package com.java_app.demo.api;

import com.java_app.demo.location.LocationService;
import com.java_app.demo.currency.CurrencyService;
import com.java_app.demo.news.NewsService;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Validated
public class ApiController {

  LocationService locationService;
  NewsService newsService;
  CurrencyService currencyService;

  @GetMapping("/location")
  public ResponseEntity<?> getLocation(@RequestParam("ip") String ip) {
    return locationService.getUserLocationByIp(ip);
  }

  @GetMapping("/news")
  public ResponseEntity<?> getNews(@RequestParam("ip") String ip) {
    return newsService.getCountry(ip);
  }

  @GetMapping("/exchangerate")
  public ResponseEntity<?> getExchange(
      @RequestParam("target") @NotBlank String source, @RequestParam("ip") String ip) {
    return currencyService.getCurrencies(ip, source);
  }
}

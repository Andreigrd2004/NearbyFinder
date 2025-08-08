package com.java_app.demo.api;

import com.java_app.demo.currency.CurrencyService;
import com.java_app.demo.currency.dto.ExchangeDto;
import com.java_app.demo.location.LocationDto;
import com.java_app.demo.location.LocationService;
import com.java_app.demo.news.NewsService;
import com.java_app.demo.news.dto.NewsDto;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class AllInformationApiController {

  private final LocationService locationService;
  private final NewsService newsService;
  private final CurrencyService currencyService;

  @GetMapping("/location")
  public LocationDto getLocation(@RequestParam("ip") String ip) {
    return locationService.getUserLocationByIp(ip);
  }

  @GetMapping("/news")
  public List<NewsDto> getNews(@RequestParam("ip") String ip) {
    return newsService.getNewspaper(ip);
  }

  @GetMapping("/exchangerate")
  public ExchangeDto getExchange(
      @RequestParam("target") @NotBlank String source, @RequestParam("ip") String ip) {
    return currencyService.getExchangeRate(ip, source);
  }
}

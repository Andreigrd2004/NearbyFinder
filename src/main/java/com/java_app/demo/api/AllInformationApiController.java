package com.java_app.demo.api;

import com.java_app.demo.currency.dto.ExchangeDto;
import com.java_app.demo.location.dto.LocationDto;
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

  private final InformationRetriever informationRetriever;

  @GetMapping("/location")
  public LocationDto getLocation(@RequestParam("ip") @NotBlank String ip) {
    return informationRetriever.getLocationByIp(ip);
  }

  @GetMapping("/news")
  public List<NewsDto> getNews(@RequestParam("ip") @NotBlank String ip) {
    return informationRetriever.getNewsForToday(ip);
  }

  @GetMapping("/exchangerate")
  public ExchangeDto getExchange(
      @RequestParam("target") @NotBlank String target, @RequestParam("ip") @NotBlank String ip) {
    return informationRetriever.getExchangeRateFromSourceToTarget(ip, target);
  }
}

package com.java_app.demo.api;

import com.java_app.demo.currency.CurrencyService;
import com.java_app.demo.currency.dto.ExchangeDto;
import com.java_app.demo.location.LocationDto;
import com.java_app.demo.location.LocationService;
import com.java_app.demo.news.NewsService;
import com.java_app.demo.news.dto.NewsDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InformationRetrieverImpl implements InformationRetriever {
  private final CurrencyService currencyService;
  private final LocationService locationService;
  private final NewsService newsService;

  @Override
  public LocationDto getLocationByIp(String ip) {
    return locationService.getUserLocationByIp(ip);
  }

  @Override
  public List<NewsDto> getNewsForToday(String ip) {
    return newsService.getNewspaper(ip);
  }

  @Override
  public ExchangeDto getExchangeRateFromSourceToTarget(String ip, String target) {
    return currencyService.getExchangeRate(ip, target);
  }
}

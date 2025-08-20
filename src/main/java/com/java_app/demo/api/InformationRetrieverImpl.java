package com.java_app.demo.api;

import com.java_app.demo.currency.CurrencyService;
import com.java_app.demo.currency.dto.ExchangeDto;
import com.java_app.demo.location.LocationService;
import com.java_app.demo.location.dto.LocationDto;
import com.java_app.demo.news.NewsService;
import com.java_app.demo.news.dto.NewsDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InformationRetrieverImpl implements InformationRetriever {
  private final CurrencyService currencyService;
  private final LocationService locationService;
  private final NewsService newsService;

  @Override
  @Cacheable(value = "locations", key = "#ip")
  public LocationDto getLocationByIp(String ip) {
    return locationService.getUserLocationByIp(ip);
  }

  @Override
  @Cacheable(value = "news", key = "#ip")
  public List<NewsDto> getNewsForToday(String ip) {
    return newsService.getNewspaper(ip);
  }

  @Override
  @Cacheable(value = "rates", key = "#root.target.getExchangeRateKeyCacheName(#ip, #target)")
  public ExchangeDto getExchangeRateFromSourceToTarget(String ip, String target) {
    return currencyService.getExchangeRate(ip, target);
  }

  public String getExchangeRateKeyCacheName(String ip, String target) {
    String sourceCurrency = locationService.getUserLocationByIp(ip).getCurrency();
    return sourceCurrency + target;
  }
}

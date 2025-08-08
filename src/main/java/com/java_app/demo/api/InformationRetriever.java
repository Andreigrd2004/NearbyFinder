package com.java_app.demo.api;

import com.java_app.demo.currency.dto.ExchangeDto;
import com.java_app.demo.location.LocationDto;
import com.java_app.demo.news.dto.NewsDto;
import java.util.List;

public interface InformationRetriever {
  LocationDto getLocationByIp(String ip);

  List<NewsDto> getNewsForToday(String ip);

  ExchangeDto getExchangeRateFromSourceToTarget(String ip, String target);
}

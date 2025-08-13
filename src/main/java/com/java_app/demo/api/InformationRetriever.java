package com.java_app.demo.api;

import com.java_app.demo.currency.dto.ExchangeDto;
import com.java_app.demo.location.dto.KeyDto;
import com.java_app.demo.news.dto.NewsDto;
import java.util.List;

public interface InformationRetriever {
  KeyDto getLocationByIp(String ip);

  List<NewsDto> getNewsForToday(String ip);

  ExchangeDto getExchangeRateFromSourceToTarget(String ip, String target);
}

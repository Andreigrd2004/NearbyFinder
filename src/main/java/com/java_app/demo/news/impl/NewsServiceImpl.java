package com.java_app.demo.news.impl;

import static java.util.Objects.requireNonNull;

import com.java_app.demo.location.CountryRepository;
import com.java_app.demo.location.LocationDto;
import com.java_app.demo.location.LocationService;
import com.java_app.demo.news.NewsApiResponse;
import com.java_app.demo.news.NewsService;
import com.java_app.demo.news.dto.NewsDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
  final String BASE_URL_TO_NEWS_API = "https://newsdata.io/api/1/latest?";
  final String API_KEY = "pub_b8cdd8692a1142e09ded627d92e3ffbc";
  private final CountryRepository countryRepository;
  private final LocationService locationService;
  private final RestTemplate restTemplate;

  public List<NewsDto> getNewspaper(String ip) throws HttpClientErrorException {

    LocationDto locationDto = locationService.getUserLocationByIp(ip);

    String country_code = countryRepository.findCountryByName(locationDto.getCountry()).getCode();
    NewsApiResponse news =
        requireNonNull(
            restTemplate.getForObject(
                BASE_URL_TO_NEWS_API
                    + "country="
                    + country_code.toLowerCase()
                    + "&apikey="
                    + API_KEY,
                NewsApiResponse.class));
    if (news.getStatus().equals("error")) {
      throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return news.getResults();
  }
}

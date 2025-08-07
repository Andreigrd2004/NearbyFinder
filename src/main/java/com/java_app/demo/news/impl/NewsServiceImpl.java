package com.java_app.demo.news.impl;

import static com.java_app.demo.location.impl.LocationServiceImpl.BASE_URL_TO_LOCATION_API;
import static com.java_app.demo.location.impl.LocationServiceImpl.FIELDS_REQUIRED_AS_PARAMETERS;
import static java.util.Objects.requireNonNull;

import com.java_app.demo.location.LocationDto;
import com.java_app.demo.location.CountryRepository;
import com.java_app.demo.news.CustomTransferNews;
import com.java_app.demo.news.NewsApiResponse;
import com.java_app.demo.news.NewsService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
  final String BASE_URL_TO_NEWS_API = "https://newsdata.io/api/1/latest?";
  final String API_KEY = "pub_b8cdd8692a1142e09ded627d92e3ffbc";
  private final CountryRepository countryRepository;

  public CustomTransferNews getNewspaper(String ip) {
    RestTemplate restTemplate = new RestTemplate();
    InetAddressValidator validator = InetAddressValidator.getInstance();
    if (!validator.isValid(ip)) {
      return new CustomTransferNews(null, HttpStatus.BAD_REQUEST);
    }
    LocationDto locationDto;
    try {
      locationDto = requireNonNull(restTemplate.getForObject(BASE_URL_TO_LOCATION_API + ip + FIELDS_REQUIRED_AS_PARAMETERS, LocationDto.class));
    } catch (Exception e) {
      return new CustomTransferNews(null, HttpStatus.BAD_REQUEST);
    }
    String country_code = countryRepository.findByName(locationDto.getCountry()).getCountry_code();
    NewsApiResponse news =
        requireNonNull(restTemplate.getForObject(
            BASE_URL_TO_NEWS_API + "country=" + country_code.toLowerCase() + "&apikey=" + API_KEY,
            NewsApiResponse.class));
    if (news.getStatus().equals("error")) {
      return new CustomTransferNews(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new CustomTransferNews(news.getResults(), HttpStatus.OK);
  }
}

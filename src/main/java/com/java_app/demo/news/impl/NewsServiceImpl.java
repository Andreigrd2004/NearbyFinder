package com.java_app.demo.news.impl;

import static com.java_app.demo.currency.impl.CurrencyServiceImpl.FIELDS;
import static com.java_app.demo.currency.impl.CurrencyServiceImpl.URL;
import static java.util.Objects.requireNonNull;

import com.java_app.demo.location.LocationRepository;
import com.java_app.demo.location.LocationDto;
import com.java_app.demo.news.NewsApiResponse;
import com.java_app.demo.news.NewsService;
import com.java_app.demo.news.dto.NewDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
  private final RestTemplate restTemplate = new RestTemplate();
  final String BASE_URL = "https://newsdata.io/api/1/latest?";
  final String API_KEY = "pub_b8cdd8692a1142e09ded627d92e3ffbc";
  static InetAddressValidator validator = InetAddressValidator.getInstance();
  private final LocationRepository locationRepository;

  public ResponseEntity<List<NewDto>> getCountry(String ip) {

    if (!validator.isValid(ip)) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    LocationDto locationDto;
    try {
      locationDto = requireNonNull(restTemplate.getForObject(URL + ip + FIELDS, LocationDto.class));
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    String country_code = locationRepository.findByName(locationDto.getCountry()).getCountry_code();
    NewsApiResponse news =
        restTemplate.getForObject(
            BASE_URL + "country=" + country_code.toLowerCase() + "&apikey=" + API_KEY,
            NewsApiResponse.class);
    assert news != null;
    if (news.getStatus().equals("error")) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(news.getResults(), HttpStatus.OK);
  }
}

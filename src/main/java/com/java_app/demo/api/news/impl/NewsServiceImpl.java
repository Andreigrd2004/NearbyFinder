package com.java_app.demo.api.news.impl;

import java.util.List;

import com.java_app.demo.api.news.NewsApiResponse;
import com.java_app.demo.api.news.NewsService;
import com.java_app.demo.api.news.dto.NewDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NewsServiceImpl implements NewsService {
  private final RestTemplate restTemplate = new RestTemplate();
  final String BASE_URL = "https://newsdata.io/api/1/latest?";
  final String API_KEY = "pub_b8cdd8692a1142e09ded627d92e3ffbc";

  public ResponseEntity<List<NewDto>> getCountry(String country_symbol) {

    if (country_symbol.length() != 2) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    NewsApiResponse news =
        restTemplate.getForObject(
            BASE_URL + "country=" + country_symbol.toLowerCase() + "&apikey=" + API_KEY, NewsApiResponse.class);
    assert news != null;
    if (news.getStatus().equals("error")) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(news.getResults(), HttpStatus.OK);
  }
}

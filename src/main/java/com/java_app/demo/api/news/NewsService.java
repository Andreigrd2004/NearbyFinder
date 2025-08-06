package com.java_app.demo.api.news;

import com.java_app.demo.api.news.dto.NewDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface NewsService {
    ResponseEntity<List<NewDto>> getCountry(String country_symbol);
}

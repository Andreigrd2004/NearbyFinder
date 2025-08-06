package com.java_app.demo.news;

import com.java_app.demo.news.dto.NewDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface NewsService {
    ResponseEntity<List<NewDto>> getCountry(String ip);
}

package com.java_app.demo.news;

import com.java_app.demo.news.dto.NewsDto;
import java.util.List;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class NewsApiResponse {
  private String status;
  private List<NewsDto> results;
}

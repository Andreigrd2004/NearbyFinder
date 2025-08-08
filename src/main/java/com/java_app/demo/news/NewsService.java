package com.java_app.demo.news;

import com.java_app.demo.news.dto.NewsDto;

import java.util.List;

public interface NewsService {
    List<NewsDto> getNewspaper(String ip);
}

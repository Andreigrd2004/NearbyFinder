package com.java_app.demo.api.news;

import com.java_app.demo.api.news.dto.NewDto;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class NewsApiResponse {
    private String status;
    private List<NewDto> results;
}


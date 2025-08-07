package com.java_app.demo.news;

import com.java_app.demo.news.dto.NewsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@AllArgsConstructor
public class CustomTransferNews {
    private List<NewsDto> news;
    private HttpStatus status;
}

package com.java_app.demo.news.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class NewsDto implements Serializable {
  @NotBlank private String title;

  @NotBlank private String content;

  @NotBlank private String link;
}

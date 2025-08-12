package com.java_app.demo.apikey.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyDto {
  @NotBlank
  private String value;

  @NotBlank
  private String name;
}

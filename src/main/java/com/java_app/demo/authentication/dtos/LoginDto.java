package com.java_app.demo.authentication.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

  @NotBlank
  private String Username;

  @Size(min = 4, max = 20)
  private String password;
}

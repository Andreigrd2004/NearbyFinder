package com.java_app.demo.user.dtos;

import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
  private Integer id;

  private String email;

  private String username;

  private Boolean enabled;

  private Boolean accountNonExpired;

  private Set<String> roles = new HashSet<>();
}

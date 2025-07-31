package com.java_app.demo.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

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

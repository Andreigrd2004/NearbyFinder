package com.java_app.demo.user.dtos;

import java.util.HashSet;
import java.util.Set;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
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

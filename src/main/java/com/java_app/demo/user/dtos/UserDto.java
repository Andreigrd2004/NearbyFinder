package com.java_app.demo.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
  @NotBlank private Integer id;

  @Email private String email;

  @NotBlank private String username;

  private Boolean enabled;

  private Boolean accountNonExpired;

  @NotBlank private Set<String> roles = new HashSet<>();
}

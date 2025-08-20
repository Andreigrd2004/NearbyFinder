package com.java_app.demo.location.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto implements Serializable {

  @NotBlank private String query;
  @NotBlank private String status;
  @NotBlank private String country;
  @NotBlank private String regionName;
  @NotBlank private String city;
  @NotBlank private String zip;
  @NotBlank private String lat;
  @NotBlank private String lon;
  @NotBlank private String isp;
  @NotBlank private String currency;
}

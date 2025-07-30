package com.java_app.demo.country;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {
  private String query;
  private String status;
  private String country;
  private String regionName;
  private String city;
  private String zip;
  private String lat;
  private String lon;
  private String isp;
}

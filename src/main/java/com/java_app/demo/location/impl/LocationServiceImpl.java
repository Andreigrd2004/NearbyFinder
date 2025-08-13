package com.java_app.demo.location.impl;

import com.java_app.demo.advice.exceptions.BadRequestException;
import com.java_app.demo.advice.exceptions.InternalServerErrorException;
import com.java_app.demo.location.dto.KeyDto;
import com.java_app.demo.location.LocationService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

  public static final String FIELDS_REQUIRED_AS_PARAMETERS =
      "?fields=status,message,country,countryCode,region,regionName,city,zip,lat,lon,timezone,currency,isp,org,as,query";
  public static final String BASE_URL_TO_LOCATION_API = "http://ip-api.com/json/";
  private final RestTemplate restTemplate;

  @Override
  public KeyDto getUserLocationByIp(String ip) throws BadRequestException {
    if (!InetAddressValidator.getInstance().isValid(ip)) {
      throw new BadRequestException(
          "The request doesn't match the format of the IP address.");
    }
    try {
      return restTemplate.getForObject(
          BASE_URL_TO_LOCATION_API + ip + FIELDS_REQUIRED_AS_PARAMETERS, KeyDto.class);
    } catch (Exception e) {
      throw new InternalServerErrorException(
              "An internal error occurred while trying to call the location API.");
    }
  }
}

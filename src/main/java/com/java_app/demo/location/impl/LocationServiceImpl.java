package com.java_app.demo.location.impl;

import com.java_app.demo.location.CustomTransferLocation;
import com.java_app.demo.location.LocationDto;
import com.java_app.demo.location.LocationService;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LocationServiceImpl implements LocationService {

    public static final String FIELDS_REQUIRED_AS_PARAMETERS =
      "?fields=status,message,country,countryCode,region,regionName,city,zip,lat,lon,timezone,currency,isp,org,as,query";
  public static final String BASE_URL_TO_LOCATION_API = "http://ip-api.com/json/";

  @Override
  public CustomTransferLocation getUserLocationByIp(String ip) {
    RestTemplate restTemplate = new RestTemplate();
    InetAddressValidator validator = InetAddressValidator.getInstance();
    if (!validator.isValid(ip)) {
      return new CustomTransferLocation("", HttpStatus.BAD_REQUEST);
    }
    try {
      LocationDto locationDto = restTemplate.getForObject(BASE_URL_TO_LOCATION_API + ip + FIELDS_REQUIRED_AS_PARAMETERS, LocationDto.class);
      return new CustomTransferLocation(locationDto, HttpStatus.OK);
    } catch (Exception e) {
      return new CustomTransferLocation("", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}

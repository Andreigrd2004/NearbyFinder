package com.java_app.demo.services;

import static com.java_app.demo.location.impl.LocationServiceImpl.BASE_URL_TO_LOCATION_API;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.java_app.demo.location.LocationDto;
import com.java_app.demo.location.impl.LocationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

  public static final String FIELDS_REQUIRED_AS_PARAMETERS =
      "?fields=status,message,country,countryCode,region,regionName,city,zip,lat,lon,timezone,currency,isp,org,as,query";
  @Mock private RestTemplate restTemplate;

  @InjectMocks private LocationServiceImpl countryService;

  @Test
  void testGetUserLocationByIp_validIP_returnsLocation() {

    String ip = "8.8.8.8";

    LocationDto mockLocation = new LocationDto();
    mockLocation.setCountry("United States");
    mockLocation.setCity("Mountain View");
    when(restTemplate.getForObject(
            BASE_URL_TO_LOCATION_API + ip + FIELDS_REQUIRED_AS_PARAMETERS, LocationDto.class))
        .thenReturn(mockLocation);

    LocationDto response = countryService.getUserLocationByIp(ip);

    assertInstanceOf(LocationDto.class, response);
    assertEquals("United States", response.getCountry());
    assertEquals("Mountain View", response.getCity());
  }
}

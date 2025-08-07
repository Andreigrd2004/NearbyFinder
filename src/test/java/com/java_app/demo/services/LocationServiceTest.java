package com.java_app.demo.services;

import com.java_app.demo.location.LocationDto;
import com.java_app.demo.location.impl.LocationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

    public static final String FIELDS_REQUIRED_AS_PARAMETERS = "?fields=status,message,country,countryCode,region,regionName,city,zip,lat,lon,timezone,currency,isp,org,as,query";
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private LocationServiceImpl countryService;

    @Test
    void testGetUserLocationByIp_validIP_returnsLocation() {

        String ip = "8.8.8.8";
        String url = "http://ip-api.com/json/" + ip + FIELDS_REQUIRED_AS_PARAMETERS;

        LocationDto mockLocation = new LocationDto();
        mockLocation.setCountry("United States");
        mockLocation.setCity("Mountain View");

        when(restTemplate.getForObject(url, LocationDto.class)).thenReturn(mockLocation);

        ResponseEntity<?> response =  new ResponseEntity<>(countryService.getUserLocationByIp(ip).getLocationDto(), countryService.getUserLocationByIp(ip).getHttpStatus()) ;

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(LocationDto.class, response.getBody());
        LocationDto location = (LocationDto) response.getBody();
        assertEquals("United States", location.getCountry());
        assertEquals("Mountain View", location.getCity());

    }


}

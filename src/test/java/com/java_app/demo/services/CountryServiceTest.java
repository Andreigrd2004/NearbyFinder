package com.java_app.demo.services;

import com.java_app.demo.country.CountryService;
import com.java_app.demo.country.LocationDto;
import com.java_app.demo.country.impl.CountryServiceImpl;
import org.apache.commons.validator.routines.InetAddressValidator;
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
public class CountryServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CountryServiceImpl countryService;

    @Test
    void testGetUserLocationByIp_validIP_returnsLocation() {

        String ip = "8.8.8.8";
        String url = "http://ip-api.com/json/" + ip;

        LocationDto mockLocation = new LocationDto();
        mockLocation.setCountry("United States");
        mockLocation.setCity("Mountain View");

        when(restTemplate.getForObject(url, LocationDto.class)).thenReturn(mockLocation);

        ResponseEntity<?> response =  countryService.getUserLocationByIp(ip);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(LocationDto.class, response.getBody());
        LocationDto location = (LocationDto) response.getBody();
        assertEquals("United States", location.getCountry());
        assertEquals("Mountain View", location.getCity());

    }


}

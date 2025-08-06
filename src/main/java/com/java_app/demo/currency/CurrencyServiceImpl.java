package com.java_app.demo.currency;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Service
public class CurrencyServiceImpl implements CurrencyService {
    @Setter
    @Getter
    private RestTemplate restTemplate = new RestTemplate();
    private final String BASE_URL = "https://v1.apiplugin.io/v1/currency/VqZWGJXn/rates";

    public ResponseEntity<Map<String, Double>> getCurrencies(String source) {
        if(source.length() != 3){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        CurrencyApiResponse response = restTemplate.getForObject(BASE_URL + "?source=" + source.toUpperCase(), CurrencyApiResponse.class);
        assert response != null;
        if(response.getError() != null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response.getRates(), HttpStatus.OK);
    }
}

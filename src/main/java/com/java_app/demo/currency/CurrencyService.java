package com.java_app.demo.currency;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface CurrencyService {

    ResponseEntity<Map<String, Double>> getCurrencies(String source);
}

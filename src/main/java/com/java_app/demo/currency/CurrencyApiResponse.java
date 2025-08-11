package com.java_app.demo.currency;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CurrencyApiResponse {
  String error;
  Map<String, Double> rates;
}

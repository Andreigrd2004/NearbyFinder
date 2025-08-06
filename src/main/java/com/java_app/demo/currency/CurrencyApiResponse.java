package com.java_app.demo.currency;

import lombok.Data;
import lombok.Getter;

import java.util.Map;

@Data
@Getter
public class CurrencyApiResponse {
  String error;
  Map<String, Double> rates;
}

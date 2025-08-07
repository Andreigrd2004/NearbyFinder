package com.java_app.demo.currency;

import com.java_app.demo.currency.dto.ExchangeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class CustomTransferCurrency {
  private ExchangeDto currency;
  private HttpStatus status;
}

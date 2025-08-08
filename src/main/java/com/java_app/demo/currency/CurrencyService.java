package com.java_app.demo.currency;

import com.java_app.demo.currency.dto.ExchangeDto;

public interface CurrencyService {

    ExchangeDto getExchangeRate(String ip, String source);
}

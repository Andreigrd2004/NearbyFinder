package com.java_app.demo.currency;

public interface CurrencyService {

    CustomTransferCurrency getExchangeRate(String ip, String source);
}

package com.java_app.demo.currency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExchangeDto {
    private String source;
    private String target;
    private Double amount;
}

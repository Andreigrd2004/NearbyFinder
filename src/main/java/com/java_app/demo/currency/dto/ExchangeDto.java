package com.java_app.demo.currency.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExchangeDto {
    @NotBlank
    private String source;

    @NotBlank
    private String target;

    @NotBlank
    private Double amount;
}

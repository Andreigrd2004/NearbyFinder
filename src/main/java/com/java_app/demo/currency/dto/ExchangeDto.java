package com.java_app.demo.currency.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeDto implements Serializable {
    @NotBlank
    private String source;

    @NotBlank
    private String target;

    @NotBlank
    private Double amount;
}

package com.java_app.demo.advice.response;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
public class ErrorResponse {

    private final int statusCode;
    private final String message;
}

package com.example.ewallet.dtos.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ConfirmationTokenResponse {
    private String message;
    private HttpStatus statusCode;
}

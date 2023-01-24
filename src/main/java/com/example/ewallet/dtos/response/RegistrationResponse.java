package com.example.ewallet.dtos.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class RegistrationResponse {
    private HttpStatus status;
    private String token;
}

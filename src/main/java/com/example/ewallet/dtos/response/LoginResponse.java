package com.example.ewallet.dtos.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class LoginResponse {
    private  String message;
    private HttpStatus statusCode;
}

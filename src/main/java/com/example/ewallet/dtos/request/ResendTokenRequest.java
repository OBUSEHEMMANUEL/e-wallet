package com.example.ewallet.dtos.request;

import lombok.Data;

@Data
public class ResendTokenRequest {
    private String emailAddress;
    private String password;
}

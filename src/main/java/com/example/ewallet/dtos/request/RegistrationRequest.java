package com.example.ewallet.dtos.request;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;
}

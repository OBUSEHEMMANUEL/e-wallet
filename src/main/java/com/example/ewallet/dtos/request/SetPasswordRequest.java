package com.example.ewallet.dtos.request;

import lombok.Data;

@Data
public class SetPasswordRequest {
    private String emailAddress;
    private String newPassword;
}
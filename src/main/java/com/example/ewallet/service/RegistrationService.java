package com.example.ewallet.service;

import com.example.ewallet.dtos.request.ConfirmTokenRequest;
import com.example.ewallet.dtos.request.RegistrationRequest;
import com.example.ewallet.dtos.response.RegistrationResponse;

public interface RegistrationService {
    RegistrationResponse register(RegistrationRequest registrationRequest);
    String confirmationToken(ConfirmTokenRequest confirmationToken);
}

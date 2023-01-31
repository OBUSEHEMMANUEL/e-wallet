package com.example.ewallet.service;

import com.example.ewallet.dtos.request.ConfirmTokenRequest;
import com.example.ewallet.dtos.request.RegistrationRequest;
import com.example.ewallet.dtos.request.ResendTokenRequest;
import com.example.ewallet.dtos.request.SetPasswordRequest;
import com.example.ewallet.dtos.response.RegistrationResponse;
import jakarta.mail.MessagingException;

public interface RegistrationService {
    RegistrationResponse register(RegistrationRequest registrationRequest) throws MessagingException;
    String confirmToken(ConfirmTokenRequest confirmationToken);
    String resendToken(ResendTokenRequest request) throws MessagingException;

    String setPassword(SetPasswordRequest passwordRequest);
}

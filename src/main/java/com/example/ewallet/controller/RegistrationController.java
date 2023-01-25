package com.example.ewallet.controller;

import com.example.ewallet.dtos.request.ConfirmTokenRequest;
import com.example.ewallet.dtos.request.RegistrationRequest;
import com.example.ewallet.dtos.request.ResendTokenRequest;
import com.example.ewallet.dtos.request.SetPasswordRequest;
import com.example.ewallet.service.RegistrationService;
import com.example.ewallet.utils.ApiResponse;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@RestController
@RequestMapping(path = "/api/v1/registration")
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

    @PostMapping("register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegistrationRequest registrationRequest, HttpServletRequest httpServletRequest) throws MessagingException {
        ApiResponse response = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(registrationService.register(registrationRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/confirm")
    public ResponseEntity<?> confirmToken(@RequestBody ConfirmTokenRequest confirmTokenRequest, HttpServletRequest httpServletRequest) {

        ApiResponse apiResponse = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(registrationService.confirmToken(confirmTokenRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }
    @PostMapping("forgottenPassword")
    public ResponseEntity<ApiResponse> forgottenPassword(@RequestBody ResendTokenRequest request,
                                                         HttpServletRequest httpServletRequest) throws MessagingException {
        ApiResponse response = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(registrationService.ResendToken(request))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/setPassword")
    public ResponseEntity<ApiResponse> setPassword(@RequestBody SetPasswordRequest passwordRequest,
                                                   HttpServletRequest httpServletRequest){
        ApiResponse apiResponse = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(registrationService.setPassword(passwordRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }
}

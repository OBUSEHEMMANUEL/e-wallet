package com.example.ewallet.controller;

import com.example.ewallet.dtos.request.ChangePasswordRequest;
import com.example.ewallet.dtos.request.LoginRequest;
import com.example.ewallet.dtos.request.RegistrationRequest;
import com.example.ewallet.service.UserService;
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
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request, HttpServletRequest httpServletRequest) throws MessagingException {
        ApiResponse response = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(userService.login(request))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("changePassword")
    public ResponseEntity<ApiResponse> ChangePassword (@RequestBody ChangePasswordRequest request, HttpServletRequest httpServletRequest) throws MessagingException {

        ApiResponse response = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(userService.changePassword(request))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

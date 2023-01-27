package com.example.ewallet.controller;

import com.example.ewallet.dtos.request.*;
import com.example.ewallet.service.UserService;
import com.example.ewallet.utils.ApiResponse;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.ZonedDateTime;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

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
    @PostMapping("addCard")
    public ResponseEntity<ApiResponse> addCard(@RequestBody AddCardRequest addCardRequest, HttpServletRequest httpServletRequest){
        ApiResponse response = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(userService.addCard(addCardRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("deleteCard")
    public ResponseEntity<ApiResponse> deleteCard(@RequestBody DeleteCardRequest deleteCardRequest,
                                                  HttpServletRequest httpServletRequest){
        ApiResponse response = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(userService.deleteCard(deleteCardRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    @PatchMapping("updateCard")
    public ResponseEntity<ApiResponse> updateCard(@RequestBody UpdateCardRequest updateCardRequest,
                                                  HttpServletRequest httpServletRequest){
        ApiResponse response = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(userService.updateCard(updateCardRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    @GetMapping("viewUserCard/{id}")
    public ResponseEntity<ApiResponse> viewUserCard(@PathVariable String id,
                                                  HttpServletRequest httpServletRequest){
        ApiResponse response = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(userService.findUserCards(id))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/kyc")
    public ResponseEntity<ApiResponse> doKyc(@RequestBody KycRequest kycRequest, HttpServletRequest httpServletRequest){
        ApiResponse apiResponse = ApiResponse.builder()
                .data(userService.doKyc(kycRequest))
                .isSuccessful(true)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .timeStamp(ZonedDateTime.now())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    @PostMapping("/updateKyc")
    public ResponseEntity<ApiResponse> updateKyc(@RequestBody KycUpdateRequest kycUpdateRequest, HttpServletRequest httpServletRequest){
        ApiResponse apiResponse = ApiResponse.builder()
                .data(userService.updateKyc(kycUpdateRequest))
                .isSuccessful(true)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .timeStamp(ZonedDateTime.now())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/verifyAccount")
    public ResponseEntity<ApiResponse> verifyAccount(@RequestBody AccountVerificatonRequest verificatonRequest, HttpServletRequest httpServletRequest) throws IOException {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(userService.verifyRecieversAccount(verificatonRequest))
                .isSuccessful(true)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .timeStamp(ZonedDateTime.now())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }



}

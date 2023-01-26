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


}

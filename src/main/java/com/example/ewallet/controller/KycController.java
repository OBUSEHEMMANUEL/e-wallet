package com.example.ewallet.controller;


import com.example.ewallet.dtos.request.KycRequest;
import com.example.ewallet.dtos.request.KycUpdateRequest;
import com.example.ewallet.service.KycService;
import com.example.ewallet.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api/v1/kyc")
public class KycController {

    @Autowired
    private KycService kycService;

    @PostMapping("/startProcess")
    public ResponseEntity<ApiResponse> doKyc(@RequestBody KycRequest kycRequest, HttpServletRequest httpServletRequest){
        ApiResponse apiResponse = ApiResponse.builder()
                .data(kycService.doKyc(kycRequest))
                .isSuccessful(true)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .timeStamp(ZonedDateTime.now())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    @PostMapping("/update")
    public ResponseEntity<ApiResponse> updateKyc(@RequestBody KycUpdateRequest kycUpdateRequest, HttpServletRequest httpServletRequest){
        ApiResponse apiResponse = ApiResponse.builder()
                .data(kycService.updateDocument(kycUpdateRequest))
                .isSuccessful(true)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .timeStamp(ZonedDateTime.now())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


}

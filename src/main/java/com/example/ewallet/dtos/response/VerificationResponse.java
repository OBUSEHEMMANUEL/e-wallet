package com.example.ewallet.dtos.response;


import com.example.ewallet.data.models.RecipientAccountData;
import lombok.Data;

@Data
public class VerificationResponse{
    private String status;
    private String message;
    private RecipientAccountData data;

}

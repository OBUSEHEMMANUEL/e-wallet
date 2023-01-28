package com.example.ewallet.dtos.response;

import com.example.ewallet.data.models.RecipientData;
import lombok.Data;

@Data
public class CreateRecipientResponse {
    private String status;
    private String message;
    private RecipientData data;
}

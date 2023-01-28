package com.example.ewallet.dtos.response;

import com.example.ewallet.data.models.CardData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardValidationResponse {
    private String status;
    private String message;
    private CardData data;
}
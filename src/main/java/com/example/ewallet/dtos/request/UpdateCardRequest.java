package com.example.ewallet.dtos.request;

import com.example.ewallet.data.models.Card;
import lombok.Data;

@Data
public class UpdateCardRequest {
    private String cardId;
    private String cardNo;
    private String cardName;
    private String cardCvv;
    private String expiryDate;
    private String userId;
}

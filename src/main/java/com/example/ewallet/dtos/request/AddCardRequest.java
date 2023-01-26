package com.example.ewallet.dtos.request;

import com.example.ewallet.data.models.Card;
import lombok.Data;

@Data
public class AddCardRequest {
    private Card card;
    private String userId;
}

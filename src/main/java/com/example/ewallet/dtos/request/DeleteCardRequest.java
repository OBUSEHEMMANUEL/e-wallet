package com.example.ewallet.dtos.request;

import lombok.Data;

@Data
public class DeleteCardRequest {
    private String cardId;
    private String userId;
}

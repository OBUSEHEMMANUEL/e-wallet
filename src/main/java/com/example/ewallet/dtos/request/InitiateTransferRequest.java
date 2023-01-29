package com.example.ewallet.dtos.request;

import lombok.Data;

@Data
public class InitiateTransferRequest {
    private String source;
    private String amount;
    private String reference;
    private String recipient;
    private String reason;
}

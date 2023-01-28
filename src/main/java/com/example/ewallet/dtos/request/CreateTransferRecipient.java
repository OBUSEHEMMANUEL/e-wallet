package com.example.ewallet.dtos.request;

import lombok.Data;

@Data
public class CreateTransferRecipient {
    private String type;
    private String name;
    private String account_number;
    private String bank_code;
    private String currency;
}

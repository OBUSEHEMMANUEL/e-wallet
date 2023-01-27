package com.example.ewallet.dtos.request;

import lombok.Data;

@Data
public class AccountVerificatonRequest {
    private String accountNo;
    private String bankCode;
}

package com.example.ewallet.dtos.request;

import lombok.Builder;
import lombok.Data;

@Data
public class AccountVerificationRequest {
    private String accountNo;
    private String bankCode;
}

package com.example.ewallet.dtos.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountVerificationRequest {
    private String accountNo;
    private String bankCode;
}

package com.example.ewallet.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RecipientAccountData {
    private String account_number;
    private String account_name;
    private String bank_id;

}

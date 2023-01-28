package com.example.ewallet.data.models;

import lombok.Data;

@Data
public class Details {
    private String authorization_code;
    private String account_number;
    private String account_name;
    private String bank_code;
    private String bank_name;
}

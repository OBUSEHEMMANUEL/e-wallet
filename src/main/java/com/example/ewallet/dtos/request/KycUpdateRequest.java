package com.example.ewallet.dtos.request;

import com.example.ewallet.data.models.CardType;
import lombok.Data;

@Data
public class KycUpdateRequest {
    private String userId;
    private String kycId;
    private String cardNo;
    private String password;
    private String CardName;
    private String expireDate;
    private int cvv;
    private String nextOfKinFullName;
    private String emailAddress;
    private String phoneNumber;
    private String relationship;
    private String homeAddress;
    private CardType cardType;
}

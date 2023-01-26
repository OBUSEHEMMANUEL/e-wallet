package com.example.ewallet.dtos.request;

import com.example.ewallet.data.models.Card;
import com.example.ewallet.data.models.CardType;
import com.example.ewallet.data.models.NextOfKin;
import lombok.Data;

@Data
public class KycUpdateRequest {
    private String userId;
    private String kycId;
    private String password;
    private NextOfKin nextOfKin;
    private String homeAddress;
    private Card card;
    private CardType cardType;
}

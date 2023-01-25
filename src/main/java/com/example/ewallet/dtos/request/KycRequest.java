package com.example.ewallet.dtos.request;

import com.example.ewallet.data.models.CardType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;


@Data
public class KycRequest {
    private String bvn;
    private String cardNo;
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

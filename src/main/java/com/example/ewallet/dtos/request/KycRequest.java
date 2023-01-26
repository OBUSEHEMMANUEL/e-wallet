package com.example.ewallet.dtos.request;

import com.example.ewallet.data.models.Card;
import com.example.ewallet.data.models.CardType;
import com.example.ewallet.data.models.NextOfKin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;


@Data
public class KycRequest {
    private String userId;
    private String bvn;
    private Card card;
    private NextOfKin nextOfKin;
    private String homeAddress;
    private CardType cardType;
}

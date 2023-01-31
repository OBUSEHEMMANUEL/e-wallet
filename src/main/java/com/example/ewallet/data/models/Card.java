package com.example.ewallet.data.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Card {
    @Id
    private String cardId;
    private String userId;
    @NotBlank(message = "Wrong card number format")
    @Pattern(regexp = "\\d{16}")
    private String cardNo;
    @NotBlank(message = "Card name cannot be blank")
    private String CardName;
    @NotBlank
    private String expireDate;
    @NotBlank(message = "Wrong cvv format")
    @Pattern(regexp = "\\d{3}")
    private String cvv;
}

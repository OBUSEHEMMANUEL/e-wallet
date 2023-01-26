package com.example.ewallet.data.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class Card {
    @NotBlank(message = "Wrong card number format")
    @Pattern(regexp = "\\d{16}")
    private String cardNo;
    @NotBlank(message = "Card name cannot be blank")
    private String CardName;
    @NotBlank
    private String expireDate;
    @NotBlank(message = "Wrong cvv format")
    @Pattern(regexp = "\\d{3}")
    private int cvv;
}

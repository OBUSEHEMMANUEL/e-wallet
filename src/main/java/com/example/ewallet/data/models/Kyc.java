package com.example.ewallet.data.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;


@Data
@Builder
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Kyc {

    @NotBlank
    private String userId;
    @Id
    private String id;
    @NotBlank(message = "Bvn is required")
    @Pattern(regexp = "\\d{11}")
    private String bvn;
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
    @NotBlank(message = "Name cannot be blank")
    private String nextOfKinFullName;
    @NotBlank(message = "Wrong email format")
    private String emailAddress;
    @NotBlank(message = "Wrong phone number format")
    @Pattern(regexp = "\\d{11}")
    private String phoneNumber;
    @NotBlank
    private String relationship;
    @NotBlank(message = "Address field is required")
    @Pattern(regexp = "^[a-zA-Z0-9\\\\s,'-]*$")
    private String homeAddress;
    @NotBlank(message = "Select card type")
    private CardType cardType;
}

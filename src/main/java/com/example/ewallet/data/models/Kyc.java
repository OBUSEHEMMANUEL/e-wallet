package com.example.ewallet.data.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Kyc {

    @Id
    private Long bvn;
    @NotBlank(message = "Wrong card number format")
    @Pattern(regexp = "\\d{10}")
    private Long cardNo;
    @NotBlank(message = "Card name cannot be blank")
    private Long CardName;
    @NotBlank
    private LocalDateTime expireDate;
    @NotBlank(message = "Wrong cvv format")
    @Pattern(regexp = "\\d{3}")
    private int cvv;
    private String nextOfFullName;
    private String emailAddress;
    private String phoneNumber;
    private String relationship;
}

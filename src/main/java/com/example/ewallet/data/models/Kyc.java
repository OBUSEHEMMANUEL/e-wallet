package com.example.ewallet.data.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    @NotBlank(message = "Bvn is required")
    @Pattern(regexp = "\\d{11}")
    private String bvn;
    @NotBlank(message = "Address field is required")
    @Pattern(regexp = "^[a-zA-Z0-9\\\\s,'-]*$")
    private String homeAddress;
    private NextOfKin nextOfKin;
    @NotBlank(message = "Select card type")
    private CardType cardType;
}

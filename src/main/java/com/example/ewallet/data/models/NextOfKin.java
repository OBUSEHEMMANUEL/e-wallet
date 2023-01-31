package com.example.ewallet.data.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Set;

@Data
public class NextOfKin {
    @NotBlank(message = "Name cannot be blank")
    private String nextOfKinFullName;
    @NotBlank(message = "Wrong email format")
    private String emailAddress;
    @NotBlank(message = "Wrong phone number format")
    @Pattern(regexp = "\\d{11}")
    private String phoneNumber;
    @NotBlank
    private String relationship;
}

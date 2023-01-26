package com.example.ewallet.data.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String id;
    @NotNull(message = "This field is required")
    @NotEmpty(message = "This field is required")
    private String firstName;
    @NotNull(message = "This field is required")
    @NotEmpty(message = "This field is required")
    private String lastName;
    @Email(message="This field requires a valid email address")
    private String emailAddress;
    private String password;
    private boolean isDisabled = true;
    private boolean isCompletedKyc;
    @DBRef
    private Set<Card> userCards = new HashSet<>();
}

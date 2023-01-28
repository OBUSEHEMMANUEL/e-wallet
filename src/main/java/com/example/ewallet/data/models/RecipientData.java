package com.example.ewallet.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class RecipientData {
    private String active;
    private String createdAt;
    private String currency;
    private String description;
    private String domain;
    private String email;
    private String id;
    private String integration;
    private String metadata;
    private String name;
    private String recipient_code;
    private String type;
    private String updatedAt;
    private boolean is_deleted;
    private boolean isDeleted;
    private Details details;
}

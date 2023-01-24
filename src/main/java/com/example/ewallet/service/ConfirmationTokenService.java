package com.example.ewallet.service;

import com.example.ewallet.data.models.ConfirmationToken;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ConfirmationTokenService {
    void saveConfirmationToken(ConfirmationToken confirmationToken);
    Optional<ConfirmationToken> getConfirmationToken(String token);
    void deleteExpiredToken();
    void setConfirmed(String token);
}

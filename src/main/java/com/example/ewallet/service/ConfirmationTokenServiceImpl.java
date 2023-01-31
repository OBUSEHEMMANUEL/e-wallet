package com.example.ewallet.service;

import com.example.ewallet.data.models.ConfirmationToken;
import com.example.ewallet.data.repository.ConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService{
@Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;


//    public ConfirmationTokenServiceImpl(ConfirmationTokenRepository confirmationTokenRepository){
//        this.confirmationTokenRepository = confirmationTokenRepository;
//    }

    public void saveConfirmationToken(ConfirmationToken confirmationToken){
        confirmationTokenRepository.save(confirmationToken);
    }

    @Override
    public ConfirmationToken findTokenByUserId(String id){
        return confirmationTokenRepository.findByUserId(id);
    }
    public Optional<ConfirmationToken> getConfirmationToken(String token){
        return confirmationTokenRepository.findByToken(token);
    }
    public  void deleteExpiredToken(){
        confirmationTokenRepository.deleteConfirmationTokensByExpiredAtBefore(LocalDateTime.now());
    }
    public  void setConfirmed(String token ){
        confirmationTokenRepository.confirmAt(LocalDateTime.now(),token);
    }
}

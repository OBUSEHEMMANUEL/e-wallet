package com.example.ewallet.service;

import com.example.ewallet.data.models.Kyc;
import com.example.ewallet.data.models.User;
import com.example.ewallet.data.repository.KycRepository;
import com.example.ewallet.dtos.request.KycRequest;
import com.example.ewallet.dtos.request.KycUpdateRequest;
import com.example.ewallet.dtos.response.KycResponse;
import com.example.ewallet.dtos.response.KycUpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class KycServiceImpl implements KycService{

    @Autowired
    private KycRepository kycRepository;

    @Override
    public void saveKyc(Kyc kyc) {
        kycRepository.save(kyc);
    }

    @Override
    public Optional<Kyc> findKyc(String id) {
        return kycRepository.findById(id);
    }
}

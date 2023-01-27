package com.example.ewallet.service;

import com.example.ewallet.data.models.Kyc;
import com.example.ewallet.dtos.request.KycRequest;
import com.example.ewallet.dtos.request.KycUpdateRequest;
import com.example.ewallet.dtos.response.KycResponse;
import com.example.ewallet.dtos.response.KycUpdateResponse;

import java.util.Optional;


public interface KycService {
   void saveKyc(Kyc kyc);

   Optional<Kyc> findKyc(String id);

}

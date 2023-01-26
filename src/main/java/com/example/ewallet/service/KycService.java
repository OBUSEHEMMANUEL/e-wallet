package com.example.ewallet.service;

import com.example.ewallet.data.models.User;
import com.example.ewallet.dtos.request.KycRequest;
import com.example.ewallet.dtos.request.KycUpdateRequest;
import com.example.ewallet.dtos.response.KycResponse;
import com.example.ewallet.dtos.response.KycUpdateResponse;

import java.util.List;
import java.util.Optional;


public interface KycService {
   KycResponse doKyc(KycRequest kycRequest);
   KycUpdateResponse updateKycDetails(KycUpdateRequest kycUpdateRequest);

}

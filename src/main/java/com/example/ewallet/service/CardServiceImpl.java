package com.example.ewallet.service;

import com.example.ewallet.data.models.Card;
import com.example.ewallet.data.repository.CardRepository;
import com.example.ewallet.dtos.request.AddCardRequest;
import com.example.ewallet.dtos.response.*;
import com.example.ewallet.utils.InvalidCreditCardNumberException;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.ResponseBody;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService{

    private CardRepository cardRepository;

    private final String SECRET_KEY = System.getenv("YOUR_SECRET_KEY");

    public CardServiceImpl(CardRepository cardRepository){
        this.cardRepository = cardRepository;
    }
    @Override
    public void addCard(Card card) {
        cardRepository.save(card);
    }

    @Override
    public Optional<Card> findCard(String cardNo) {
        return cardRepository.findByCardNo(cardNo);
    }

    @Override
    public Card findByCardId(String cardId) {
        return cardRepository.findByCardId(cardId);
    }
    @Override
    public void deleteCard(String id) {
        cardRepository.deleteById(id);
    }

    @Override
    public CardValidationResponse validateCreditCard(AddCardRequest addCardRequest) throws IOException {
        OkHttpClient httpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.paystack.co/decision/bin/"
                        + addCardRequest.getCard().getCardNo().substring(0, 6))
                .get()
                .addHeader("Authorization", "Bearer " + SECRET_KEY)
                .build();
        try (ResponseBody response = httpClient.newCall(request).execute().body()) {
            Gson gson = new Gson();
            CardValidationResponse cardValidationResponse = gson.fromJson(response.string(), CardValidationResponse.class);

            if (Objects.equals(cardValidationResponse.getData().getCard_type(), "")) throw new InvalidCreditCardNumberException("Invalid card");

            return cardValidationResponse;
        }
    }

}

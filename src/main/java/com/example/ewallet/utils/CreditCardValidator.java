package com.example.ewallet.utils;

import com.example.ewallet.data.models.Card;

public class CreditCardValidator {
    public static long sumOfDoubleEvenDigit(Card card) {
        long sum = 0;
        StringBuilder value = new StringBuilder();

        for (int i = card.getCardNo().length() -2; i >= 0; i-=2){
            value.append(card.getCardNo().charAt(i));
            long result = Long.parseLong(String.valueOf(value)) * 2;
            sum += getResult(result);
        }
        return sum;
    }

    private static long getResult(long result){
        if (result < 9) return result;
        return addResult(result);
    }

    private static long addResult(long result){
        long decimal = result/10;
        long modulo = result%10;
        return decimal + modulo;
    }

    public static long sumOfOddPlaceDigit(Card card) {
        long sum = 0;
        StringBuilder value = new StringBuilder();
        for (int i = card.getCardNo().length() - 1; i >= 0; i-=2){
            value.append(card.getCardNo().charAt(i));
            long result = Long.parseLong(String.valueOf(value));
            sum += result;
        }
        return sum;
    }

    public static long add(Card card){
        return sumOfDoubleEvenDigit(card) + sumOfOddPlaceDigit(card);
    }


    public static boolean isValidCreditCard(Card card) {
        if (add(card) % 10 == 0) return true;
        throw new InvalidCreditCardNumberException("Credit card is invalid");
    }
}

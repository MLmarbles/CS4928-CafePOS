package com.example.Package;

import com.example.domain.Order;

public final class CardPayment implements PaymentStrategy {
    private final String cardNumber;

public CardPayment(String cardNumber) { 
    this.cardNumber = cardNumber;
 }

    @Override
    public void pay(Order order) {
        // mask card and print payment confirmation
        int length = cardNumber.length();
        StringBuilder sb = new StringBuilder(cardNumber);
        sb.replace(0, length - 4, "****");


        System.out.println("[Card] Customer paid " + order.totalWithTax(10) + "EUR with card " + sb);
    }
}
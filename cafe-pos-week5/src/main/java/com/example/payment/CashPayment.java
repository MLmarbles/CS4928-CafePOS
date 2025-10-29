package com.example.payment;

import com.example.common.Money;
import com.example.domain.Order;

public final class CashPayment implements PaymentStrategy {
    @Override
    public void pay(Order order) {
        System.out.println("[Cash] Customer paid " +
                order.totalWithTax(10) + " EUR");
    }

    @Override
    public void pay(Money money) {
        System.out.println("[Cash] Customer paid " +
                money + " EUR");
    }
}
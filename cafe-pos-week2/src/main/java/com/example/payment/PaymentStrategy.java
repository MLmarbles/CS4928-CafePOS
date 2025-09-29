package com.example.payment;

import com.example.domain.Order;

public interface PaymentStrategy {
    void pay(Order order);
}

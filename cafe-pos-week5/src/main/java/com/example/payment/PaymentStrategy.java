package com.example.payment;

import com.example.common.Money;
import com.example.domain.Order;

    public interface PaymentStrategy {
        void pay(Order order);
        void pay(Money money);
    }


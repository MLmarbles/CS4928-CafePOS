package com.example.Package;

import com.example.domain.Order;

    public interface PaymentStrategy {
        void pay(Order order);
    }


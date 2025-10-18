package com.example.pricing;

import com.example.common.Money;

public interface DiscountPolicy {
Money discountOf(Money subtotal);
}
package com.example.pricing;

import com.example.common.Money;

public final class NoDiscount implements DiscountPolicy {
@Override public Money discountOf(Money subtotal) { return
Money.zero(); }
}
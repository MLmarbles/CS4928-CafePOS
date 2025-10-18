package com.example.pricing;

import com.example.common.Money;

public final class FixedCouponDiscount implements DiscountPolicy {
private final Money amount;
public FixedCouponDiscount(Money amount) { this.amount = amount; }
@Override public Money discountOf(Money subtotal) {
// cap at subtotal
if (amount.asBigDecimal().compareTo(subtotal.asBigDecimal()) > 0)
return subtotal;
return amount;
}
}
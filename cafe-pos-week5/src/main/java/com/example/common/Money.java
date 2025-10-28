package com.example.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class Money implements Comparable<Money> {
    private BigDecimal amount;

    public static Money of(double value) {
        return new Money(BigDecimal.valueOf(value));
    }

    public static Money of(BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("BigDecimal value cannot be null");
        }
        return new Money(value);
    }
    public BigDecimal amount() {
        return amount;
    }

    public static Money zero() {
        return new Money(BigDecimal.ZERO);
    }

    private Money(BigDecimal a) {
        if (a == null)
            throw new IllegalArgumentException("amount required");
        this.amount = a.setScale(2, RoundingMode.HALF_UP);
    }

    public Money add(Money other) {
        return new Money(this.amount.add(other.amount));
    }

    public Money multiply(int qty) {
        return new Money(this.amount.multiply(BigDecimal.valueOf(qty)));
    }

    public Money percentage(int percent) {
        if (percent < 0) {
            throw new IllegalArgumentException("Percentage cannot be negative");
        }
        BigDecimal percentageAmount = this.amount
                .multiply(BigDecimal.valueOf(percent))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        return new Money(percentageAmount);
    }
    // equals, hashCode, toString, etc.

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Money money = (Money) o;
        return amount.compareTo(money.amount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    @Override
    public String toString() {
        return amount.toString();
    }

    @Override
    public int compareTo(Money other) {
        if (other == null)
            throw new IllegalArgumentException("Money to compare cannot be null");
        return this.amount.compareTo(other.amount);
    }

    public BigDecimal asBigDecimal() {
        return this.amount;
    }
}
package com.example.decorator;

import com.example.common.Money;

// Introduce a new interface for pricing
public interface Priced {
    Money price();}

    // Make SimpleProduct implement Priced (price() == basePrice())
    // Make all decorators implement Priced (price() == basePrice() + surcharges)
    // In LineItem:

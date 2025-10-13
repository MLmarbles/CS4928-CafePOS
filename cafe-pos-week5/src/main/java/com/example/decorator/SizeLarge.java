package com.example.decorator;

import com.example.common.Money;
import com.example.common.Priced;
import com.example.common.Product;

public final class SizeLarge extends ProductDecorator implements Priced {
    private static final Money SURCHARGE = Money.of(0.70);

    public SizeLarge(Product base) {
        super(base);
    }

    @Override
    public String name() {
        return base.name() + "(Large)";
    }

    public Money price() {
        return (base instanceof Priced p ? p.price() : base.basePrice()).add(SURCHARGE);
    }
}

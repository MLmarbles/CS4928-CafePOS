package com.example.decorator;

import com.example.common.Money;
import com.example.common.Priced;
import com.example.common.Product;

public abstract class ProductDecorator implements Product{
    protected final Product base;

    protected ProductDecorator(Product base) {
        if (base == null) throw new
                IllegalArgumentException("base product required");
        this.base = base;
    }

    @Override
    public String id() {
        return base.id();
    }

    @Override
    public Money basePrice() {
        return
                base.basePrice();
    }
}
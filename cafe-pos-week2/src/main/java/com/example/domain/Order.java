package com.example.domain;

import java.util.ArrayList;
import java.util.List;

import com.example.Package.PaymentStrategy;
import com.example.common.Money;

public final class Order {
    private final long id;
    private final List<LineItem> items = new ArrayList<>();

    public Order(long id) {
        this.id = id;
    }

    public void addItem(LineItem li) {
        if (li == null) {
            throw new IllegalArgumentException("LineItem cannot be null");
        }
        items.add(li);
    }

    public Money subtotal() {
        return items.stream().map(LineItem::lineTotal).reduce(Money.zero(), Money::add);
    }

    public Money taxAtPercent(int percent) {
        return subtotal().percentage(percent);
    }

    public Money totalWithTax(int percent) {
        return subtotal().add(taxAtPercent(percent));
    }

    public long id() {
        return id;
    }

    public List<LineItem> items() {
        return items;
    }

    public void pay(PaymentStrategy strategy) {
        if (strategy == null)
            throw new IllegalArgumentException("strategy required");
        strategy.pay(this);
    }

}
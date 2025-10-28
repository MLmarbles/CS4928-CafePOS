package com.example.domain;

import java.util.ArrayList;
import java.util.List;

import com.example.common.Money;
import com.example.payment.PaymentStrategy;

public final class Order {

    private final long id;
    private final List<LineItem> items = new ArrayList<>();
    private final List<OrderObserver> observers = new ArrayList<>();

    public Order(long id) {
        this.id = id;
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
    public void removeLast() {
        if (!items.isEmpty()) {
            items.removeLast();
            notifyObservers("itemRemoved");
        }
    }

    public long id() {
        return id;
    }

    public List<LineItem> items() {
        return items;
    }

    public void register(OrderObserver o) {
        if (o != null && !observers.contains(o)) {
            observers.add(o);
        }
    }

    public void unregister(OrderObserver o) {
        observers.remove(o);
    }

// 2 Publish events
    private void notifyObservers(String eventType) {
        for (OrderObserver observer : observers) {
            observer.updated(this, eventType);
        }
    }

// 3 Hook notifications into existing behaviors
    public void addItem(LineItem li) {
        if (li == null) {
            throw new IllegalArgumentException("LineItem cannot be null");
        }
        items.add(li);
        notifyObservers("itemAdded");
    }

    public void pay(PaymentStrategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("strategy required");
        }
        strategy.pay(this);
        notifyObservers("paid");
    }

    public void markReady() {
        notifyObservers("ready");
    }
}

package com.example.app.events;

public record OrderPaid(long orderId) implements OrderEvent { }

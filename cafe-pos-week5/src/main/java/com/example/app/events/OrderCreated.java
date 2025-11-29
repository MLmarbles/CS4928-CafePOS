package com.example.app.events;

public record OrderCreated(long orderId) implements OrderEvent { }

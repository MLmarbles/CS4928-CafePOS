package com.example.app.events;



// com/cafepos/app/events/OrderEvent.java
public sealed interface OrderEvent permits OrderCreated, OrderPaid { }


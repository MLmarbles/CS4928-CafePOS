package com.example.domain;

public interface OrderObserver {
 void updated(Order order, String eventType);
}
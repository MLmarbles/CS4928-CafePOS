package com.example.demo;

import com.example.catalog.Catalog;
import com.example.catalog.InMemoryCatalog;
import com.example.common.Money;
import com.example.common.SimpleProduct;
import com.example.domain.CustomerNotifier;
import com.example.domain.DeliveryDesk;
import com.example.domain.KitchenDisplay;
import com.example.domain.LineItem;
import com.example.domain.Order;
import com.example.domain.OrderIds;
import com.example.payment.CashPayment;

public final class Week4Demo {
 public static void main(String[] args) {
 Catalog catalog = new InMemoryCatalog();
 catalog.add(new SimpleProduct("P-ESP", "Espresso",Money.of(2.50)));
 Order order = new Order(OrderIds.next());
 order.register(new KitchenDisplay());
 order.register(new DeliveryDesk());
 order.register(new CustomerNotifier());
 order.addItem(new LineItem(catalog.findById("P-ESP").orElseThrow(), 1));
 order.pay(new CashPayment());
 order.markReady();
 }
}
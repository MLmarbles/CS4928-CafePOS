package com.example.demo;

import com.example.catalog.Catalog;
import com.example.catalog.InMemoryCatalog;
import com.example.common.Money;
import com.example.common.SimpleProduct;
import com.example.domain.LineItem;
import com.example.domain.Order;
import com.example.domain.OrderIds;

public final class Week2Demo {
public static void main(String[] args) {
Catalog catalog = new InMemoryCatalog();
catalog.add(new SimpleProduct("P-ESP", "Espresso",Money.of(2.50)));
catalog.add(new SimpleProduct("P-CCK", "ChocolateCookie", Money.of(3.50)));
Order order = new Order(OrderIds.next());
order.addItem(new LineItem(catalog.findById("P-ESP").orElseThrow(), 2));
order.addItem(new LineItem(catalog.findById("P-CCK").orElseThrow(), 1));
int taxPct = 10;
System.out.println("Order #" + order.id());
System.out.println("Items: " + order.items().size());
System.out.println("Subtotal: " + order.subtotal());
System.out.println("Tax (" + taxPct + "%): " +
order.taxAtPercent(taxPct));
System.out.println("Total: " +
order.totalWithTax(taxPct));
}
}
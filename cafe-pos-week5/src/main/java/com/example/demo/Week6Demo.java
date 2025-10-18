package com.example.demo;

import com.example.domain.Order;
import com.example.factory.ProductFactory;
import com.example.payment.PaymentStrategy;
import com.example.pricing.FixedRateTaxPolicy;
import com.example.pricing.LoyaltyPercentDiscount;
import com.example.pricing.PricingService;
import com.example.pricing.ReceiptPrinter;
import com.example.smelly.CheckoutService;
import com.example.smelly.OrderManagerGod;

public final class Week6Demo {
    public static void main(String[] args) {
        // Old behavior
        String oldReceipt = OrderManagerGod.process("LAT+L", 2, "CARD",
                "LOYAL5", false);
        // New behavior with equivalent result
        var pricing = new PricingService(new LoyaltyPercentDiscount(5),
                new FixedRateTaxPolicy(10));
        var printer = new ReceiptPrinter();
        var checkout = new CheckoutService(new ProductFactory(), pricing,
                printer, 10);
        Order dummyOrder = new Order(0);
        PaymentStrategy noopPayment = o -> {};
        String newReceipt = checkout.checkout("LAT+L", 2, dummyOrder, noopPayment);
        System.out.println("Old Receipt:\n" + oldReceipt);
        System.out.println("\nNew Receipt:\n" + newReceipt);
        System.out.println("\nMatch: " + oldReceipt.equals(newReceipt));
    }
}
package com.example;

import com.example.common.Money;
import com.example.domain.*;
import com.example.factory.ProductFactory;
import com.example.payment.PaymentStrategy;
import com.example.pricing.DiscountPolicy;
import com.example.pricing.FixedCouponDiscount;
import com.example.pricing.FixedRateTaxPolicy;
import com.example.pricing.LoyaltyPercentDiscount;
import com.example.pricing.NoDiscount;
import com.example.pricing.PricingService;
import com.example.pricing.ReceiptPrinter;
import com.example.smelly.CheckoutService;
import com.example.smelly.OrderManagerGod;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("--------Week 6 CLI Comparison Tool--------------");

        System.out.print("Enter product code (e.g., LAT+L): ");
        String code = sc.nextLine().trim();

        System.out.print("Enter quantity: ");
        int qty = sc.nextInt();
        sc.nextLine();

        System.out.println("\nSelect discount option:");
        System.out.println("1. No Discount");
        System.out.println("2. Loyalty 5% (LOYAL5)");
        System.out.println("3. Coupon 1 euro off (COUPON1)");
        System.out.print("Choice: ");
        int discountChoice = sc.nextInt();
        sc.nextLine();

        String discountCode;
        int loyaltyPercent = 0;
        DiscountPolicy discountPolicy;

        switch (discountChoice) {
            case 2 -> {
                discountCode = "LOYAL5";
                discountPolicy = new LoyaltyPercentDiscount(5);
            }
            case 3 -> {
                discountCode = "COUPON1";
                discountPolicy = new FixedCouponDiscount(Money.of(1.00));
            }
            default -> {
                discountCode = "";
                discountPolicy = new NoDiscount();
            }
        }

        System.out.println("\nSelect payment method:");
        System.out.println("1. CARD");
        System.out.println("2. CASH");
        System.out.println("3. WALLET");
        System.out.print("Choice: ");
        int payChoice = sc.nextInt();
        sc.nextLine();

        String paymentType = switch (payChoice) {
            case 2 -> "CASH";
            case 3 -> "WALLET";
            default -> "CARD";
        };

        // Old behavior
        String oldReceipt = OrderManagerGod.process(code, qty, paymentType, discountCode, false);

        // New behavior with equivalent result
        var pricing = new PricingService(discountPolicy, new FixedRateTaxPolicy(10));
        var printer = new ReceiptPrinter();
        var checkout = new CheckoutService(new ProductFactory(), pricing, printer, 10);
        String newReceipt = checkout.checkout(code, qty);

        System.out.println("\n------------OLD RECEIPT-------------");
        System.out.println(oldReceipt);
        System.out.println("------------------------------------");

        System.out.println("\n----------------NEW RECEIPT----------------------");
        System.out.println(newReceipt);
        System.out.println("---------------------------------------------------------");

        boolean match = oldReceipt.equals(newReceipt);
        System.out.println("\nMatch: " + (match ? "YES" : "NO"));
    }
}
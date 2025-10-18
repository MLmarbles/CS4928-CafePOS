package com.example.smelly;

import com.example.common.Money;
import com.example.common.Product;
import com.example.factory.ProductFactory;
import com.example.pricing.DiscountPolicy;
import com.example.pricing.FixedCouponDiscount;
import com.example.pricing.LoyaltyPercentDiscount;
import com.example.pricing.NoDiscount;

public class OrderManagerGod {
    public static int TAX_PERCENT = 10;
    public static String LAST_DISCOUNT_CODE = null;

    public static String process(String recipe, int qty, String paymentType, String discountCode,
            boolean printReceipt) {
        ProductFactory factory = new ProductFactory();
        Product product = factory.create(recipe);

        // God Class & Long Method: One method performs creation, pricing, discounting,
        // tax, and printing
        Money unitPrice;

        try {
            var priced = product instanceof com.example.common.Priced p
                    ? p.price()
                    : product.basePrice();
            unitPrice = priced;
        } catch (Exception e) {
            unitPrice = product.basePrice();
        }

        if (qty <= 0)
            qty = 1;
        Money subtotal = unitPrice.multiply(qty);

        DiscountPolicy discountPolicy = switch (discountCode == null ? "" : discountCode.toUpperCase()) {
            case "LOYAL5" -> new LoyaltyPercentDiscount(5);
            case "COUPON1" -> new FixedCouponDiscount(Money.of(1.00));
            default -> new NoDiscount();
        };

        Money discount = discountPolicy.discountOf(subtotal);

        // Duplicated Logic: BigDecimal subtraction inline
        Money discounted = Money.of(subtotal.asBigDecimal().subtract(discount.asBigDecimal()));
        if (discounted.asBigDecimal().signum() < 0)
            discounted = Money.zero();

        // Feature Envy / Shotgun Surgery risk: tax inline
        var tax = Money.of(discounted.asBigDecimal()
                .multiply(java.math.BigDecimal.valueOf(TAX_PERCENT)) // Global state
                .divide(java.math.BigDecimal.valueOf(100)));
        var total = discounted.add(tax);

        // God Class / Long Method: payment I/O inline
        if (paymentType != null) {
            if (paymentType.equalsIgnoreCase("CASH")) {
                System.out.println("[Cash] Customer paid " + total + " EUR");
            } else if (paymentType.equalsIgnoreCase("CARD")) {
                System.out.println("[Card] Customer paid " + total + " EUR with card ****1234");
            } else if (paymentType.equalsIgnoreCase("WALLET")) {
                System.out.println("[Wallet] Customer paid " + total + " EUR via wallet user-wallet-789");
            } else {
                System.out.println("[UnknownPayment] " + total);
            }
        }

        StringBuilder receipt = new StringBuilder();
        receipt.append("Order (").append(recipe).append(") x").append(qty).append("\n");
        receipt.append("Subtotal: ").append(subtotal).append("\n");

        if (discount.asBigDecimal().signum() > 0) {
            receipt.append("Discount: -").append(discount).append("\n");
        }

        receipt.append("Tax (").append(TAX_PERCENT).append("%): ").append(tax).append("\n");
        receipt.append("Total: ").append(total);

        String out = receipt.toString();
        if (printReceipt) {
            System.out.println(out);
        }

        return out;
    }

}
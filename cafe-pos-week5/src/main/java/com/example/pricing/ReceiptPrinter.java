package com.example.pricing;

import com.example.domain.LineItem;
import com.example.domain.Order;

public final class ReceiptPrinter {
    public String format(String recipe, int qty,
            PricingService.PricingResult pr, int taxPercent) {
        StringBuilder receipt = new StringBuilder();
        receipt.append("Order (").append(recipe).append(") x").append(qty).append("\n"); // add space
        receipt.append("Subtotal: ").append(pr.subtotal()).append("\n");
        if (pr.discount().asBigDecimal().signum() > 0) {
            receipt.append("Discount: -").append(pr.discount()).append("\n");
        }
        receipt.append("Tax (").append(taxPercent).append("%): ").append(pr.tax()).append("\n"); // add space
        receipt.append("Total: ").append(pr.total());
        return receipt.toString();
    }

    public void printReceipt(Order order,
            PricingService.PricingResult pr, int taxPercent) {
        System.out.println("\n--- Order Summary ---");
        for (LineItem li : order.items()) {
            System.out.println(" - " + li.product().name() + " x"
                    + li.quantity() + " = " + li.lineTotal());
        }
        System.out.println("Subtotal:  $" + pr.subtotal());
        System.out.println("Discount: -$" + pr.discount());
        System.out.println("Tax:       $" + pr.tax());
        System.out.println("Total:     $" + pr.total());
        System.out.println("Thank you for your purchase at the Meowcafe!");
    }
}

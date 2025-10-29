package com.example.smelly;

import java.util.ArrayList;
import java.util.List;

import com.example.common.Money;
import com.example.common.Product;
import com.example.domain.LineItem;
import com.example.domain.Order;
import com.example.factory.ProductFactory;
import com.example.payment.PaymentStrategy;
import com.example.pricing.PricingService;
import com.example.pricing.ReceiptPrinter;

public final class CheckoutService {

    private final ProductFactory factory;
    private final PricingService pricing;
    private final ReceiptPrinter printer;
    private final int taxPercent;

    public CheckoutService(PricingService pricing,
            ReceiptPrinter printer, int taxPercent) {
        this.pricing = pricing;
        this.printer = printer;
        this.taxPercent = taxPercent;
        factory = new ProductFactory();
    }

    public CheckoutService(ProductFactory factory, PricingService pricing,
            ReceiptPrinter printer, int taxPercent) {
        this.factory = factory;
        this.pricing = pricing;
        this.printer = printer;
        this.taxPercent = taxPercent;
    }

    public String checkout(String recipe, int qty) {
        Product product = factory.create(recipe);
        if (qty <= 0)
            qty = 1;
        Money unit = (product instanceof com.example.common.Priced p)
                ? p.price()
                : product.basePrice();
        Money subtotal = unit.multiply(qty);
        var result = pricing.price(subtotal);
        return printer.format(recipe, qty, result, taxPercent);
    }

    public void checkout(Order order, PaymentStrategy payment_method) {
        List<LineItem> lineItems = order.items();
        Money lineSubtotal = Money.zero();
        for (LineItem i : lineItems) {
            lineSubtotal = lineSubtotal.add(i.lineTotal());
        }
        var result = pricing.price(lineSubtotal);
        printer.printReceipt(order, result, taxPercent);
        order.pay(payment_method, result.total());
    }
}

package com.example.smelly;

import com.example.common.Money;
import com.example.common.Product;
import com.example.domain.Order;
import com.example.factory.ProductFactory;
import com.example.payment.PaymentStrategy;
import com.example.pricing.PricingService;
import com.example.pricing.PricingService.PricingResult;
import com.example.pricing.ReceiptPrinter;

public final class CheckoutService {

    private final ProductFactory factory;
    private final PricingService pricing;
    private final ReceiptPrinter printer;
    private final int taxPercent;

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
}

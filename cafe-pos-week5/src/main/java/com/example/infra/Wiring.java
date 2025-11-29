package com.example.infra;

import com.example.app.CheckoutService;
import com.example.domain.OrderRepository;
import com.example.pricing.FixedRateTaxPolicy;
import com.example.pricing.LoyaltyPercentDiscount;
import com.example.pricing.PricingService;

public final class Wiring {
    public static record Components(OrderRepository repo, PricingService
    pricing, CheckoutService checkout) {
    }

    public static Components createDefault() {
        OrderRepository repo = new InMemoryOrderRepository();
        PricingService pricing = new PricingService(new
                LoyaltyPercentDiscount(5), new FixedRateTaxPolicy(10));
        CheckoutService checkout = new CheckoutService(repo, pricing);
        return new Components(repo, pricing, checkout);
    }
}
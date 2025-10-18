package com.example.smelly;

import com.example.common.Money;
import com.example.common.Product;
import com.example.domain.LineItem;
import com.example.domain.Order;
import com.example.domain.OrderIds;
import com.example.factory.ProductFactory;
import com.example.payment.CardPayment;
import com.example.payment.CashPayment;
import com.example.payment.PaymentStrategy;
import com.example.payment.WalletPayment;
import com.example.pricing.DiscountPolicy;
import com.example.pricing.FixedCouponDiscount;
import com.example.pricing.FixedRateTaxPolicy;
import com.example.pricing.LoyaltyPercentDiscount;
import com.example.pricing.NoDiscount;
import com.example.pricing.PricingService;
import com.example.pricing.ReceiptPrinter;
import com.example.pricing.TaxPolicy;

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

        TaxPolicy taxPolicy = new FixedRateTaxPolicy(10);
        Money tax = taxPolicy.taxOn(discounted);
        var total = discounted.add(tax);

        PaymentStrategy payment = switch (paymentType == null ? "" : paymentType.toUpperCase()) {
            case "CASH" -> new CashPayment();
            case "CARD" -> new CardPayment("1234"); // supply dummy card for now
            case "WALLET" -> new WalletPayment("user-wallet-789"); // dummy wallet id
            default -> order -> System.out.println("[UnknownPayment] " + order.totalWithTax(10));
        };

        Order dummyOrder = new Order(OrderIds.next());
        dummyOrder.addItem(new LineItem(product, qty)); 

        payment.pay(dummyOrder);

        ReceiptPrinter printer = new ReceiptPrinter();
        String receiptText = printer.format(recipe, qty,
                new PricingService.PricingResult(subtotal, discount, tax, total), 10);

        if (printReceipt)
            System.out.println(receiptText);
        return receiptText;

    }

}
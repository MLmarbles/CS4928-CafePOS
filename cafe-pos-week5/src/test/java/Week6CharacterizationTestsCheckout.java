import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.common.Money;
import com.example.domain.Order;
import com.example.factory.ProductFactory;
import com.example.payment.PaymentStrategy;
import com.example.pricing.*;
import com.example.smelly.CheckoutService;

import static org.junit.jupiter.api.Assertions.*;

public class Week6CharacterizationTestsCheckout {

    private CheckoutService checkoutService;
    private ProductFactory factory;
    private ReceiptPrinter printer;

    @BeforeEach
    void setup() {
        factory = new ProductFactory();
        printer = new ReceiptPrinter();
    }

    @Test
    void no_discount_cash_payment() {
        PricingService pricing = new PricingService(new NoDiscount(), new FixedRateTaxPolicy(10));
        checkoutService = new CheckoutService(factory, pricing, printer, 10);

        Order order = new Order(1);
        PaymentStrategy payment = o -> {};
        String receipt = checkoutService.checkout("ESP+SHOT+OAT", 1, order, payment);

        assertTrue(receipt.startsWith("Order (ESP+SHOT+OAT) x1"));
        assertTrue(receipt.contains("Subtotal: 3.80"));
        assertTrue(receipt.contains("Tax (10%): 0.38"));
        assertTrue(receipt.contains("Total: 4.18"));
    }

    @Test
    void loyalty_discount_card_payment() {
        PricingService pricing = new PricingService(new LoyaltyPercentDiscount(5), new FixedRateTaxPolicy(10));
        checkoutService = new CheckoutService(factory, pricing, printer, 10);

        Order order = new Order(2);
        PaymentStrategy payment = o -> {};
        String receipt = checkoutService.checkout("LAT+L", 2, order, payment);

        assertTrue(receipt.contains("Subtotal: 7.80"));
        assertTrue(receipt.contains("Discount: -0.39"));
        assertTrue(receipt.contains("Tax (10%): 0.74"));
        assertTrue(receipt.contains("Total: 8.15"));
    }

    @Test
    void coupon_fixed_amount_and_qty_clamp() {
        PricingService pricing = new PricingService(new FixedCouponDiscount(Money.of(1.00)), new FixedRateTaxPolicy(10));
        checkoutService = new CheckoutService(factory, pricing, printer, 10);

        Order order = new Order(3);
        PaymentStrategy payment = o -> {};
        String receipt = checkoutService.checkout("ESP+SHOT", 0, order, payment);

        assertTrue(receipt.contains("Order (ESP+SHOT) x1"));
        assertTrue(receipt.contains("Subtotal: 3.30"));
        assertTrue(receipt.contains("Discount: -1.00"));
        assertTrue(receipt.contains("Tax (10%): 0.23"));
        assertTrue(receipt.contains("Total: 2.53"));
    }
}

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.example.common.Money;
import com.example.pricing.DiscountPolicy;
import com.example.pricing.FixedCouponDiscount;
import com.example.pricing.LoyaltyPercentDiscount;
import com.example.pricing.NoDiscount;

public class DiscountPolicyTest {

    @Test void loyalty_discount_5_percent() {
        DiscountPolicy d = new LoyaltyPercentDiscount(5);
        Money subtotal = Money.of(7.80);
        assertEquals(Money.of(0.39), d.discountOf(subtotal));
    }

    @Test void fixed_coupon_discount_1_euro() {
        DiscountPolicy d = new FixedCouponDiscount(Money.of(1.00));
        Money subtotal = Money.of(3.30);
        assertEquals(Money.of(1.00), d.discountOf(subtotal));
    }

    @Test void no_discount() {
        DiscountPolicy d = new NoDiscount();
        Money subtotal = Money.of(7.80);
        assertEquals(Money.zero(), d.discountOf(subtotal));
    }
}
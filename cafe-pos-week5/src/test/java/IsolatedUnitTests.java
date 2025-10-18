import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.example.common.Money;
import com.example.pricing.DiscountPolicy;
import com.example.pricing.FixedCouponDiscount;
import com.example.pricing.LoyaltyPercentDiscount;
import com.example.pricing.NoDiscount;

public class IsolatedUnitTests {

    @Test
    void noDiscount_returnsZero() {
        DiscountPolicy noDiscount = new NoDiscount();
        Money subtotal = Money.of(BigDecimal.valueOf(10.00));
        Money discount = noDiscount.discountOf(subtotal);
        assertEquals(Money.zero(), discount, "NoDiscount should return zero");
    }

    @Test
    void loyaltyPercentDiscount_correctCalculation() {
        DiscountPolicy loyalty = new LoyaltyPercentDiscount(5);
        Money subtotal = Money.of(BigDecimal.valueOf(20.00));
        Money discount = loyalty.discountOf(subtotal);
        assertEquals(Money.of(BigDecimal.valueOf(1.00)), discount,
                "5% of 20.00 should be 1.00");
    }

    @Test
    void fixedCouponDiscount_cappedAtSubtotal() {
        DiscountPolicy coupon = new FixedCouponDiscount(Money.of(5.00));
        Money subtotal = Money.of(BigDecimal.valueOf(10.00));
        Money discount = coupon.discountOf(subtotal);
        assertEquals(Money.of(5.00), discount, "Fixed coupon discount should be 5.00");

        subtotal = Money.of(BigDecimal.valueOf(3.00));
        discount = coupon.discountOf(subtotal);
        assertEquals(Money.of(3.00), discount, "Discount should be capped at subtotal");
    }
}

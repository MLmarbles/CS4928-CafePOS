import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.example.smelly.OrderManagerGod;

public class DiscountPolicyCharacterizationTests {

    @Test
    void noDiscount_unknownCode() {
        String receipt = OrderManagerGod.process("ESP+SHOT+OAT", 1, "CASH", "UNKNOWN", false);

        // Should not have Discount line
        assertFalse(receipt.contains("Discount:"), "Receipt should not contain Discount for unknown code");

        assertTrue(receipt.contains("Subtotal: 3.80"));
        assertTrue(receipt.contains("Tax (10%): 0.38"));
        assertTrue(receipt.contains("Total: 4.18"));
    }

    @Test
    void loyaltyDiscount_loyal5() {
        String receipt = OrderManagerGod.process("LAT+L", 2, "CARD", "LOYAL5", false);

        // Should include Discount line
        assertTrue(receipt.contains("Discount: -0.39"), "Receipt should include loyalty discount");

        assertTrue(receipt.contains("Subtotal: 7.80"));
        assertTrue(receipt.contains("Tax (10%): 0.74"));
        assertTrue(receipt.contains("Total: 8.15"));
    }

    @Test
    void fixedCoupon_discount() {
        String receipt = OrderManagerGod.process("ESP+SHOT", 1, "WALLET", "COUPON1", false);

        // Should include Discount line
        assertTrue(receipt.contains("Discount: -1.00"), "Receipt should include fixed coupon discount");

        assertTrue(receipt.contains("Subtotal: 3.30"));
        assertTrue(receipt.contains("Tax (10%): 0.23"));
        assertTrue(receipt.contains("Total: 2.53"));
    }
}

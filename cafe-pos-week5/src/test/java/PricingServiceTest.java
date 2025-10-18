import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.example.common.Money;
import com.example.pricing.FixedRateTaxPolicy;
import com.example.pricing.LoyaltyPercentDiscount;
import com.example.pricing.PricingService;
import com.example.pricing.TaxPolicy;

public class PricingServiceTest {

    @Test void fixed_rate_tax_10_percent() {
        TaxPolicy t = new FixedRateTaxPolicy(10);
        assertEquals(Money.of(0.74), t.taxOn(Money.of(7.41)));
    }

    @Test void pricing_pipeline() {
        PricingService pricing = new PricingService(
            new LoyaltyPercentDiscount(5),
            new FixedRateTaxPolicy(10)
        );

        Money subtotal = Money.of(7.80);
        PricingService.PricingResult pr = pricing.price(subtotal);

        assertEquals(Money.of(0.39), pr.discount());
        assertEquals(Money.of(7.41), 
            Money.of(pr.subtotal().asBigDecimal().subtract(pr.discount().asBigDecimal()))
        );
        assertEquals(Money.of(0.74), pr.tax());
        assertEquals(Money.of(8.15), pr.total());
    }
}

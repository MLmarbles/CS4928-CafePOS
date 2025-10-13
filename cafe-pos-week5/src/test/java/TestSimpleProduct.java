import com.example.common.Money;
import com.example.common.SimpleProduct;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestSimpleProduct {

    @Test
    void testConstructorAndGetters() {
        Money price = Money.of(100.00);
        SimpleProduct product = new SimpleProduct("1", "Test Product", price);

        assertEquals("1", product.id());
        assertEquals("Test Product", product.name());
        assertEquals(price, product.basePrice());
    }
}
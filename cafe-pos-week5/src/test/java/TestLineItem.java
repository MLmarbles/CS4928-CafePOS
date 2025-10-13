import com.example.common.Money;
import com.example.common.Product;
import com.example.common.SimpleProduct;
import com.example.domain.LineItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestLineItem{

    @Test
    void testConstructorValidArguments() {
        Product product = new SimpleProduct("1", "Test Product", Money.of(50.00));
        LineItem lineItem = new LineItem(product, 2);

        assertEquals(product, lineItem.product());
        assertEquals(2, lineItem.quantity());
    }

    @Test
    void testConstructorThrowsExceptionForNullProduct() {
        assertThrows(IllegalArgumentException.class, () -> new LineItem(null, 2));
    }

    @Test
    void testConstructorThrowsExceptionForNonPositiveQuantity() {
        Product product = new SimpleProduct("1", "Test Product", Money.of(50.00));
        assertThrows(IllegalArgumentException.class, () -> new LineItem(product, 0));
        assertThrows(IllegalArgumentException.class, () -> new LineItem(product, -1));
    }

    @Test
    void testLineTotal() {
        Product product = new SimpleProduct("1", "Test Product", Money.of(50.00));
        LineItem lineItem = new LineItem(product, 3);

        Money expectedTotal = Money.of(150.00); // 50.00 * 3
        assertEquals(expectedTotal, lineItem.lineTotal());
    }
}
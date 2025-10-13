import com.example.common.Money;
import com.example.common.Product;
import com.example.common.SimpleProduct;
import com.example.domain.LineItem;
import com.example.domain.Order;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestOrder {

    @Test
    void testAddItem() {
        Order order = new Order(1L);
        Product product = new SimpleProduct("1", "Test Product", Money.of(50.00));
        LineItem item = new LineItem(product, 2);
        order.addItem(item);

        List<LineItem> items = order.items();
        assertEquals(1, items.size());
        assertEquals(item, items.get(0));
    }

    @Test
    void testSubtotal() {
        Order order = new Order(1L);
        Product product1 = new SimpleProduct("1", "Product 1", Money.of(100.00));
        Product product2 = new SimpleProduct("2", "Product 2", Money.of(50.00));
        order.addItem(new LineItem(product1, 2)); // 200
        order.addItem(new LineItem(product2, 1)); // 50

        Money subtotal = order.subtotal();
        assertEquals(Money.of(250.00), subtotal);
    }

    @Test
    void testTaxAtPercent() {
        Order order = new Order(1L);
        Product product = new SimpleProduct("1", "Test Product", Money.of(100.00));
        order.addItem(new LineItem(product, 2)); // 200

        Money tax = order.taxAtPercent(10); // 10% of 200
        assertEquals(Money.of(20.00), tax);
    }

    @Test
    void testTotalWithTax() {
        Order order = new Order(1L);
        Product product = new SimpleProduct("1", "Test Product", Money.of(100.00));
        order.addItem(new LineItem(product, 2)); // 200

        Money total = order.totalWithTax(10); // 200 + 10% tax
        assertEquals(Money.of(220.00), total);
    }

    @Test
    void testAddItemThrowsExceptionForNull() {
        Order order = new Order(1L);
        assertThrows(IllegalArgumentException.class, () -> order.addItem(null));
    }
}
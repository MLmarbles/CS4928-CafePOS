import com.example.catalog.InMemoryCatalog;
import com.example.common.Product;
import com.example.common.Money;
import com.example.common.SimpleProduct;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TestCatalog{
    @Test
    public void testAddAndFindById() {
        InMemoryCatalog catalog = new InMemoryCatalog();
        Product product = new SimpleProduct("1", "Test Product", Money.of(10.00));

        catalog.add(product);
        Optional<Product> foundProduct = catalog.findById("1");

        assertTrue(foundProduct.isPresent());
        assertEquals(product, foundProduct.get());
    }

    @Test
    public void testFindByIdNotFound() {
        InMemoryCatalog catalog = new InMemoryCatalog();

        Optional<Product> foundProduct = catalog.findById("nonexistent");

        assertFalse(foundProduct.isPresent());
    }

    @Test
    public void testAddThrowsExceptionForNullProduct() {
        InMemoryCatalog catalog = new InMemoryCatalog();

        assertThrows(IllegalArgumentException.class, () -> catalog.add(null));
    }
}
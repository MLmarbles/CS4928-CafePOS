import com.example.common.Money;
import com.example.common.Priced;
import com.example.common.SimpleProduct;
import com.example.decorator.*;
import com.example.domain.LineItem;
import com.example.domain.Order;
import com.example.domain.OrderIds;
import com.example.factory.ProductFactory;
import org.junit.jupiter.api.Test;

public class FactoryManualTest {
    @Test
    public void test_factory_manual(){
        var viaFactory = new ProductFactory().create("ESP+SHOT+OAT+L");
        var viaManual = new SizeLarge(new OatMilk(new ExtraShot(new SimpleProduct("P-ESP","Espresso", Money.of(2.50)))));

        assert(viaFactory.name().equals(viaManual.name()));
        assert(((Priced)viaFactory).price().equals(((Priced)viaManual).price()));

        Order factoryOrder = new Order(OrderIds.next());
        factoryOrder.addItem(new LineItem(viaFactory, 1));

        Order manualOrder = new Order(OrderIds.next());
        manualOrder.addItem(new LineItem(viaManual, 1));

        assert(factoryOrder.subtotal().equals(manualOrder.subtotal()));
    }
}

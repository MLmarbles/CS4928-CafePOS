import com.example.common.Money;
import com.example.common.SimpleProduct;
import com.example.domain.LineItem;
import com.example.domain.Order;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

final class TestOrderObserver {

    @Test
    void singleObserverReceives_itemAdded_event() {
        SimpleProduct product = new SimpleProduct("A", "Espresso", Money.of(2.50));
        Order order = new Order(1);
        List<String> events = new ArrayList<>();
        order.register((o, evt) -> events.add(evt));

        order.addItem(new LineItem(product, 1));

        assertTrue(events.contains("itemAdded"),
                "Observer should receive 'itemAdded' when an item is added.");
    }

    @Test
    void multipleObserversReceive_ready_event() {
        SimpleProduct product = new SimpleProduct("B", "Latte", Money.of(3.00));
        Order order = new Order(2);
        order.addItem(new LineItem(product, 1));

        List<String> observer1Events = new ArrayList<>();
        List<String> observer2Events = new ArrayList<>();

        order.register((o, evt) -> observer1Events.add(evt));
        order.register((o, evt) -> observer2Events.add(evt));

        order.markReady();
        assertTrue(observer1Events.contains("ready"),
                "Observer 1 should receive 'ready' event.");
        assertTrue(observer2Events.contains("ready"),
                "Observer 2 should receive 'ready' event.");
    }
}

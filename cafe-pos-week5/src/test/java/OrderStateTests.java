
import org.junit.jupiter.api.Test;

import com.example.state.OrderFSM;

import static org.junit.jupiter.api.Assertions.*;

public class OrderStateTests {
    @Test void happy_path_delivery() {
        OrderFSM fsm = new OrderFSM();
        assertEquals("NEW", fsm.status());
        fsm.pay();
        assertEquals("PREPARING", fsm.status());
        fsm.markReady();
        assertEquals("READY", fsm.status());
        fsm.deliver();
        assertEquals("DELIVERED", fsm.status());
    }
}

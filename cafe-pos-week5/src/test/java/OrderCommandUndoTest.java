
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class OrderCommandUndoTest {
    interface Command {
        void execute();

        void undo();
    }

    static class FakeOrder {
        private int items = 0;

        void addItem() {
            items++;
        }

        void removeLastItem() {
            if (items > 0) items--;
        }

        int itemCount() {
            return items;
        }
    }

    static class AddItemCommand implements Command {
        private final FakeOrder order;

        AddItemCommand(FakeOrder order) {
            this.order = order;
        }

        @Override
        public void execute() {
            order.addItem();
        }

        @Override
        public void undo() {
            order.removeLastItem();
        }
    }

    @Test
    void add_then_undo_changes_item_count() {
        FakeOrder order = new FakeOrder();
        AddItemCommand cmd = new AddItemCommand(order);

        assertEquals(0, order.itemCount());
        cmd.execute();
        assertEquals(1, order.itemCount());
        cmd.undo();
        assertEquals(0, order.itemCount());
    }
}
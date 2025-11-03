import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.example.command.Command;
import org.junit.jupiter.api.Test;

public class MacroCommandTest {

    static class FakeOrder {
        private int items = 0;
        void addItem() { items++; }
        void removeLastItem() { if (items > 0) items--; }
        int itemCount() { return items; }
    }

    static class AddItemCommand implements Command {
        private final FakeOrder order;
        AddItemCommand(FakeOrder order) { this.order = order; }
        @Override public void execute() { order.addItem(); }
        @Override public void undo() { order.removeLastItem(); }
    }

    static class MacroCommand implements Command {
        private final List<Command> commands;
        MacroCommand(List<Command> commands) { this.commands = new ArrayList<>(commands); }
        @Override public void execute() { commands.forEach(Command::execute); }
        @Override public void undo() {
            List<Command> rev = new ArrayList<>(commands);
            Collections.reverse(rev);
            rev.forEach(Command::undo);
        }
    }

    @Test
    void macro_undo_is_reverse_and_reverts_one_add() {
        FakeOrder order = new FakeOrder();
        AddItemCommand a1 = new AddItemCommand(order);
        AddItemCommand a2 = new AddItemCommand(order);

        List<Command> list = List.of(a1, a2);
        MacroCommand macro = new MacroCommand(list);

        macro.execute();
        assertEquals(2, order.itemCount(), "two adds executed");
        a2.undo();
        assertEquals(1, order.itemCount(), "one add reverted (reverse-order)");
    }
}
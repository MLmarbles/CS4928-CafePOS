import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PrinterAdapterTest {

    // Legacy printer accepts byte[] payloads
    static abstract class LegacyPrinter {
        abstract void printBytes(byte[] payload);
    }

    static class StringPrinterAdapter {
        private final LegacyPrinter legacy;

        StringPrinterAdapter(LegacyPrinter legacy) {
            this.legacy = legacy;
        }

        void print(String s) {
            byte[] payload = s.getBytes();
            legacy.printBytes(payload);
        }
    }

    @Test
    void adapter_converts_string_to_bytes_and_forwards() {
        class CapturingPrinter extends LegacyPrinter {
            int lastLength = 0;

            @Override
            void printBytes(byte[] payload) {
                lastLength = payload == null ? 0 : payload.length;
            }
        }

        CapturingPrinter captured = new CapturingPrinter();
        StringPrinterAdapter adapter = new StringPrinterAdapter(captured);

        adapter.print("ABC");
        assertTrue(captured.lastLength >= 3, "payload length should be at least 3 bytes for \"ABC\"");
    }
}

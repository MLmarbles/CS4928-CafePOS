import org.junit.jupiter.api.Test;

// Example adapter test idea (pseudo-JUnit)
class FakeLegacy extends vendor.legacy.LegacyThermalPrinter {
    int lastLen = -1;

    @Override
    public void legacyPrint(byte[] payload) {
        lastLen =
                payload.length;
    }

    @Test
    void test_adapter_converts_text_to_bytes() {
        var fake = new FakeLegacy();
        com.example.printing.Printer p = new
                com.example.printing.LegacyPrinterAdapter(fake);
        p.print("ABC");
        org.junit.jupiter.api.Assertions.assertTrue(fake.lastLen >= 3);
    }
}
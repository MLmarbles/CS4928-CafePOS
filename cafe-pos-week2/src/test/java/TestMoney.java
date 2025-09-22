import com.example.common.Money;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestMoney{
    @Test
    void testOf() {
        Money money = Money.of(100.123);
        assertEquals("100.12", money.toString());
    }

    @Test
    void testZero() {
        Money zero = Money.zero();
        assertEquals("0.00", zero.toString());
    }

    @Test
    void testAdd() {
        Money money1 = Money.of(50.25);
        Money money2 = Money.of(49.75);
        Money result = money1.add(money2);
        assertEquals("100.00", result.toString());
    }

    @Test
    void testMultiply() {
        Money money = Money.of(20.50);
        Money result = money.multiply(3);
        assertEquals("61.50", result.toString());
    }

    @Test
    void testPercentage() {
        Money money = Money.of(200.00);
        Money result = money.percentage(15);
        assertEquals("30.00", result.toString());
    }

    @Test
    void testPercentageThrowsExceptionForNegativePercent() {
        Money money = Money.of(100.00);
        assertThrows(IllegalArgumentException.class, () -> money.percentage(-10));
    }

    @Test
    void testEqualsAndHashCode() {
        Money money1 = Money.of(100.00);
        Money money2 = Money.of(100.00);
        Money money3 = Money.of(50.00);

        assertEquals(money1, money2);
        assertNotEquals(money1, money3);
        assertEquals(money1.hashCode(), money2.hashCode());
        assertNotEquals(money1.hashCode(), money3.hashCode());
    }

    @Test
    void testCompareTo() {
        Money money1 = Money.of(100.00);
        Money money2 = Money.of(50.00);
        Money money3 = Money.of(100.00);

        assertTrue(money1.compareTo(money2) > 0);
        assertTrue(money2.compareTo(money1) < 0);
        assertEquals(0, money1.compareTo(money3));
    }

    @Test
    void testCompareToThrowsExceptionForNull() {
        Money money = Money.of(100.00);
        assertThrows(IllegalArgumentException.class, () -> money.compareTo(null));
    }

    @Test
    void testToString() {
        Money money = Money.of(123.456);
        assertEquals("123.46", money.toString());
    }
}
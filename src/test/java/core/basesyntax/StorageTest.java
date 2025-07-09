package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.data.Storage;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StorageTest {
    @BeforeEach
    public void setUp() {
        Storage.getAssortment().clear(); // обнуляем перед каждым тестом
    }

    @Test
    public void getAssortment_shouldReturnSameStorage() {
        Map<String, Integer> first = Storage.getAssortment();
        Map<String, Integer> second = Storage.getAssortment();
        assertSame(first, second, "Should return same storage");
    }

    @Test
    public void getAssortment_shouldBeEmpty() {
        assertTrue(Storage.getAssortment().isEmpty(), "Assortment must be empty");
    }

    @Test
    public void getAssortment_canPutValue() {
        Storage.getAssortment().put("banana", 10);
        assertEquals(10, Storage.getAssortment().get("banana"));
    }
}

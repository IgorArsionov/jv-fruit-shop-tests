package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.data.Storage;
import core.basesyntax.handlers.impl.SupplyOperation;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    @BeforeEach
    public void setUp() {
        Storage.getAssortment().clear();
    }

    @Test
    public void supplyHandler_shouldIncreaseQuantity() {
        Storage.getAssortment().put("banana", 20);
        OperationHandler handler = new SupplyOperation();
        FruitTransaction tx = new FruitTransaction("banana",
                30, FruitTransaction.Operation.SUPPLY);
        handler.apply(tx);
        assertEquals(50, Storage.getAssortment().get("banana"));
    }

    @Test
    public void supplyHandler_negativeQuantityTrowsOk() {
        Storage.getAssortment().put("banana", 10);
        OperationHandler handler = new SupplyOperation();
        FruitTransaction tx = new FruitTransaction("banana",
                -5, FruitTransaction.Operation.SUPPLY);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> handler.apply(tx));
        assertEquals("Quantity cannot be negative: -5", exception.getMessage(),
                "Expected trow 'Quantity cannot be negative: -5'");
    }
}

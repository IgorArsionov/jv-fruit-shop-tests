package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.data.Storage;
import core.basesyntax.handlers.impl.ReturnOperation;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {

    @BeforeEach
    public void setUp() {
        Storage.getAssortment().clear();
    }

    @Test
    public void returnHandler_shouldIncreaseQuantity() {
        Storage.getAssortment().put("banana", 30);
        OperationHandler handler = new ReturnOperation();
        FruitTransaction tx = new FruitTransaction("banana",
                10, FruitTransaction.Operation.RETURN);
        handler.apply(tx);
        assertEquals(40, Storage.getAssortment().get("banana"));
    }

    @Test
    public void returnHandler_negativeQuantityTrowsOk() {
        Storage.getAssortment().put("banana", 10);
        OperationHandler handler = new ReturnOperation();
        FruitTransaction tx = new FruitTransaction("banana",
                -3, FruitTransaction.Operation.RETURN);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> handler.apply(tx));
        assertEquals("Quantity cannot be negative: -3", exception.getMessage(),
                "Expected trow 'Quantity cannot be negative: -3'");
    }
}

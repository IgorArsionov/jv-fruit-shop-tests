package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.data.Storage;
import core.basesyntax.handlers.impl.BalanceHandler;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceHandlerTest {

    @BeforeEach
    public void setUp() {
        Storage.getAssortment().clear();
    }

    @Test
    public void balance_shouldAddQuantity() {
        OperationHandler handler = new BalanceHandler();
        FruitTransaction tx = new FruitTransaction("apple",
                50, FruitTransaction.Operation.BALANCE);
        handler.apply(tx);
        assertEquals(50, Storage.getAssortment().get("apple"));
    }

    @Test
    public void supplyHandler_negativeQuantityTrowsOk() {
        Storage.getAssortment().put("banana", 50);
        OperationHandler handler = new BalanceHandler();
        FruitTransaction tx = new FruitTransaction("banana",
                -1, FruitTransaction.Operation.BALANCE);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> handler.apply(tx));
        assertEquals("Quantity cannot be negative: -1", exception.getMessage(),
                "Expected trow 'Quantity cannot be negative: -1'");
    }
}

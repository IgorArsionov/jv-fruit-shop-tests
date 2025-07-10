package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.data.Storage;
import core.basesyntax.handlers.impl.PurchaseOperation;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    private OperationHandler handler;

    @BeforeEach
    public void setUp() {
        Storage.getAssortment().clear();
    }

    @Test
    public void purchaseHandler_shouldDecreaseQuantity() {
        Storage.getAssortment().put("banana", 100);
        handler = new PurchaseOperation();
        FruitTransaction tx = new FruitTransaction("banana",
                15, FruitTransaction.Operation.PURCHASE);
        handler.apply(tx);
        assertEquals(85, Storage.getAssortment().get("banana"));
    }

    @Test
    public void purchaseHandler_fruitNotInStorage_notOk() {
        handler = new PurchaseOperation();
        FruitTransaction tx = new FruitTransaction("banana",
                10, FruitTransaction.Operation.PURCHASE);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> handler.apply(tx));
        assertEquals("Fruit 'banana' not found in storage!", exception.getMessage());
    }

    @Test
    public void purchaseHandler_shouldThrow_NotEnoughQuantity() {
        Storage.getAssortment().put("banana", 1);
        handler = new PurchaseOperation();
        FruitTransaction tx = new FruitTransaction("banana",
                10, FruitTransaction.Operation.PURCHASE);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> handler.apply(tx));
        assertEquals("Not enough 'banana' in storage! Only 1 left.", exception.getMessage());
    }

    @Test
    public void purchaseHandler_shouldThrow_NegativeQuantity() {
        Storage.getAssortment().put("banana", 10);
        handler = new PurchaseOperation();
        FruitTransaction tx = new FruitTransaction("banana",
                -10, FruitTransaction.Operation.PURCHASE);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> handler.apply(tx));
        assertEquals("Negative quantity is not allowed: -10", exception.getMessage());
    }
}

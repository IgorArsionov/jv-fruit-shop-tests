package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.data.Storage;
import core.basesyntax.handlers.OperationHandler;
import core.basesyntax.handlers.impl.BalanceHandler;
import core.basesyntax.handlers.impl.PurchaseOperation;
import core.basesyntax.handlers.impl.ReturnOperation;
import core.basesyntax.handlers.impl.SupplyOperation;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationHandlerTest {
    private static OperationHandler handler;

    @BeforeEach
    public void setUp() {
        Storage.getAssortment().clear();
    }

    @Test
    public void balance_shouldAddQuantity() {
        handler = new BalanceHandler();
        FruitTransaction tx = new FruitTransaction("apple",
                50, FruitTransaction.Operation.BALANCE);
        handler.apply(tx);
        assertEquals(50, Storage.getAssortment().get("apple"));
    }

    @Test
    public void supplyHandler_shouldIncreaseQuantity() {
        Storage.getAssortment().put("banana", 20);
        handler = new SupplyOperation();
        FruitTransaction tx = new FruitTransaction("banana",
                30, FruitTransaction.Operation.SUPPLY);
        handler.apply(tx);
        assertEquals(50, Storage.getAssortment().get("banana"));
    }

    @Test
    public void returnHandler_shouldIncreaseQuantity() {
        Storage.getAssortment().put("banana", 30);
        handler = new ReturnOperation();
        FruitTransaction tx = new FruitTransaction("banana",
                10, FruitTransaction.Operation.RETURN);
        handler.apply(tx);
        assertEquals(40, Storage.getAssortment().get("banana"));
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
    public void purchaseHandler_shouldThrow_FruitNotInStorage() {
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

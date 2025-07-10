package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.handlers.impl.OperationStrategyImpl;
import core.basesyntax.handlers.impl.PurchaseOperation;
import core.basesyntax.handlers.impl.ReturnOperation;
import core.basesyntax.handlers.impl.SupplyOperation;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyTest {
    private OperationStrategy strategy;

    @BeforeEach
    public void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        strategy = new OperationStrategyImpl(handlers);
    }

    @Test
    public void strategy_IllegalArgument_shouldBeThrow() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> strategy.get(FruitTransaction.Operation.BALANCE));
        assertEquals("No handler found for operation: BALANCE", exception.getMessage());
    }

    @Test
    public void strategy_OperationOk() {
        OperationHandler expectedHandler = new PurchaseOperation();
        OperationHandler actualHandler = strategy.get(FruitTransaction.Operation.PURCHASE);
        assertEquals(expectedHandler.getClass(), actualHandler.getClass(),
                "Expected correct handler for PURCHASE operation");
    }
}

package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.data.Storage;
import core.basesyntax.handlers.OperationHandler;
import core.basesyntax.handlers.OperationStrategy;
import core.basesyntax.handlers.impl.BalanceHandler;
import core.basesyntax.handlers.impl.OperationStrategyImpl;
import core.basesyntax.handlers.impl.PurchaseOperation;
import core.basesyntax.handlers.impl.ReturnOperation;
import core.basesyntax.handlers.impl.SupplyOperation;
import core.basesyntax.handlers.service.ShopService;
import core.basesyntax.handlers.service.impl.ShopServiceImpl;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceTest {
    private static ShopService service;

    @BeforeAll
    public static void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());

        OperationStrategy strategy = new OperationStrategyImpl(handlers);
        service = new ShopServiceImpl(strategy);

    }

    @BeforeEach
    public void clear() {
        Storage.getAssortment().clear();
    }

    @Test
    public void shopService_Ok() {
        List<FruitTransaction> fruitTransactions = List.of(
                new FruitTransaction("banana", 20, FruitTransaction.Operation.BALANCE),
                new FruitTransaction("banana", 30, FruitTransaction.Operation.SUPPLY),
                new FruitTransaction("banana", 10, FruitTransaction.Operation.PURCHASE),
                new FruitTransaction("banana", 5, FruitTransaction.Operation.RETURN)
        );
        service.process(fruitTransactions);
        Map<String, Integer> expected = Map.of("banana", 45);
        assertEquals(expected, Storage.getAssortment());
    }

    @Test
    public void shopService_EmptyOk() {
        List<FruitTransaction> fruitTransactions = List.of();
        service.process(fruitTransactions);
        Map<String, Integer> assortmentExp = Storage.getAssortment();
        assertTrue(assortmentExp.isEmpty(), "Must be empty");
    }
}


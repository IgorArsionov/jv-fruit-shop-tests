package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.data.Storage;
import core.basesyntax.handlers.DataConverter;
import core.basesyntax.handlers.OperationHandler;
import core.basesyntax.handlers.OperationStrategy;
import core.basesyntax.handlers.filehandlers.FileReader;
import core.basesyntax.handlers.filehandlers.impl.FileReaderImpl;
import core.basesyntax.handlers.impl.BalanceHandler;
import core.basesyntax.handlers.impl.DataConverterImpl;
import core.basesyntax.handlers.impl.OperationStrategyImpl;
import core.basesyntax.handlers.impl.PurchaseOperation;
import core.basesyntax.handlers.impl.ReturnOperation;
import core.basesyntax.handlers.impl.SupplyOperation;
import core.basesyntax.handlers.service.ShopService;
import core.basesyntax.handlers.service.impl.ShopServiceImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceTest {
    private static ShopService service;
    private static List<FruitTransaction> fruitTransactions;
    private static List<FruitTransaction> fruitTransactionsExpected;
    private static FileReader fileReader;
    private static DataConverter converter;
    private static String data = "src/main/resources/reportToReadTest.csv";
    private static String dataEmpty = "src/main/resources/emptyData.csv";

    @BeforeAll
    public static void setUp() {
        fileReader = new FileReaderImpl();
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());

        OperationStrategy strategy = new OperationStrategyImpl(handlers);
        service = new ShopServiceImpl(strategy);
        converter = new DataConverterImpl();

        List<String> resultReaderExpected = fileReader.read(data);
        fruitTransactionsExpected = converter.convert(resultReaderExpected);
    }

    @BeforeEach
    public void clear() {
        Storage.getAssortment().clear();
    }

    @Test
    public void shopService_Ok() {
        fruitTransactions = converter.convert(fileReader.read(data));
        service.process(fruitTransactions);
        Map<String, Integer> assortmentRes = Storage.getAssortment();

        service.process(fruitTransactionsExpected);
        Map<String, Integer> assortmentExp = Storage.getAssortment();

        assertEquals(assortmentExp, assortmentRes,
                "Result does not match the expectation. It should be: "
                        + assortmentExp + ". But it was: " + assortmentRes);
    }

    @Test
    public void shopService_EmptyOk() {
        fruitTransactions = converter.convert(fileReader.read(dataEmpty));
        service.process(fruitTransactions);
        Map<String, Integer> assortmentExp = Storage.getAssortment();
        assertTrue(assortmentExp.isEmpty(), "Must be empty");
    }
}


package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.handlers.DataConverter;
import core.basesyntax.handlers.OperationHandler;
import core.basesyntax.handlers.OperationStrategy;
import core.basesyntax.handlers.ReportGenerator;
import core.basesyntax.handlers.filehandlers.FileReader;
import core.basesyntax.handlers.filehandlers.impl.FileReaderImpl;
import core.basesyntax.handlers.impl.BalanceHandler;
import core.basesyntax.handlers.impl.DataConverterImpl;
import core.basesyntax.handlers.impl.OperationStrategyImpl;
import core.basesyntax.handlers.impl.PurchaseOperation;
import core.basesyntax.handlers.impl.ReportGeneratorImpl;
import core.basesyntax.handlers.impl.ReturnOperation;
import core.basesyntax.handlers.impl.SupplyOperation;
import core.basesyntax.handlers.service.ShopService;
import core.basesyntax.handlers.service.impl.ShopServiceImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReportGeneratorTest {
    private static ReportGenerator generator;
    private static DataConverter converter;
    private static FileReader fileReader;
    private static ShopService service;
    private static List<String> resultReaderExpected;
    private static List<FruitTransaction> fruitTransactions;
    private static String data = "src/main/resources/reportToReadTest.csv";

    @BeforeAll
    public static void setUp() {
        generator = new ReportGeneratorImpl();
        converter = new DataConverterImpl();
        fileReader = new FileReaderImpl();

        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());

        OperationStrategy strategy = new OperationStrategyImpl(handlers);
        service = new ShopServiceImpl(strategy);

        resultReaderExpected = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50",
                "b,pineapple,30",
                "s,pineapple,200",
                "p,pineapple,10",
                "r,pineapple,3",
                "p,pineapple,5",
                "p,pineapple,4",
                "b,cucumber,5");
    }

    @Test
    public void reportGenerator_Ok() {
        fruitTransactions = converter.convert(fileReader.read(data));
        service.process(fruitTransactions);
        List<String> resultingReport = generator.getReport();

        fruitTransactions = converter.convert(resultReaderExpected);
        service.process(fruitTransactions);
        List<String> resultingReportExp = generator.getReport();

        assertEquals(resultingReportExp, resultingReport,
                "Expected: " + resultingReportExp + ". But was: " + resultingReport);
    }
}


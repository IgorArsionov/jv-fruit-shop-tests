package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.data.Storage;
import core.basesyntax.handlers.DataConverter;
import core.basesyntax.handlers.OperationHandler;
import core.basesyntax.handlers.OperationStrategy;
import core.basesyntax.handlers.ReportGenerator;
import core.basesyntax.handlers.filehandlers.FileReader;
import core.basesyntax.handlers.filehandlers.FileWriter;
import core.basesyntax.handlers.filehandlers.impl.FileReaderImpl;
import core.basesyntax.handlers.filehandlers.impl.FileWriterImpl;
import core.basesyntax.handlers.impl.BalanceHandler;
import core.basesyntax.handlers.impl.DataConverterImpl;
import core.basesyntax.handlers.impl.OperationStrategyImpl;
import core.basesyntax.handlers.impl.PurchaseOperation;
import core.basesyntax.handlers.impl.ReportGeneratorImpl;
import core.basesyntax.handlers.impl.ReturnOperation;
import core.basesyntax.handlers.impl.SupplyOperation;
import core.basesyntax.handlers.service.ShopService;
import core.basesyntax.handlers.service.impl.ShopServiceImpl;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class HelloWorldTest {
    private static String data;
    private static String dataEmpty;
    private static String dataNonExist;
    private static String dataOutput;
    private static List<String> result;
    private static List<String> resultExpected;
    private static List<String> reportOutExpected;
    private static FileReader fileReader;
    private static Map<FruitTransaction.Operation, OperationHandler> handlers;
    private static OperationStrategy strategy;
    private static DataConverter converter;
    private static List<FruitTransaction> fruitTransactions;
    private static List<FruitTransaction> fruitTransactionsExpected;
    private static ShopService service;
    private static ReportGenerator generator;

    @BeforeAll
    public static void setUp() {
        resultExpected = List.of(
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
                "p,pineapple,4"
        );
        reportOutExpected = List.of("fruit,quantity",
                "banana,152",
                "apple,90",
                "pineapple,214");
        data = "reportToReadTest.csv";
        dataEmpty = "emptyData.csv";
        dataNonExist = "nonExistData.csv";
        dataOutput = "reportToReadTestOut.csv";
        fileReader = new FileReaderImpl();
        handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        strategy = new OperationStrategyImpl(handlers);
        converter = new DataConverterImpl();
        fruitTransactionsExpected = converter.convert(resultExpected);
        service = new ShopServiceImpl(strategy);
        generator = new ReportGeneratorImpl();
    }

    @Test
    public void nonExistFile() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fileReader.read("nonExistData.csv");
        });
        assertEquals("Errors reading file: " + dataNonExist, exception.getMessage());
    }

    @Test
    public void emptyData() {
        result = fileReader.read(dataEmpty);
        assertNotNull(result, "Result not be null");
        assertTrue(result.isEmpty(), "Empty list expected");
    }

    @Test
    public void dataResult_Ok() {
        result = fileReader.read(data);
        assertFalse(result.isEmpty(), "List must not be empty");
        assertEquals(resultExpected, result, "Should match expected data");
        for (String line : result) {
            assertEquals(3, line.split(",").length,
                    "Each line should have 3 columns");
        }
    }

    @Test
    public void converter_Ok() {
        fruitTransactions = setListFruit(data);
        assertEquals(fruitTransactionsExpected, fruitTransactions,
                "Result does not match the expectation. It should be: "
                        + fruitTransactionsExpected + ". But it was: " + fruitTransactions);
    }

    @Test
    public void shopService_Ok() {
        fruitTransactions = setListFruit(data);

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
        fruitTransactions = setListFruit(dataEmpty);

        service.process(fruitTransactions);
        Map<String, Integer> assortmentExp = Storage.getAssortment();
        assertTrue(assortmentExp.isEmpty(), "Must be empty");
    }

    @Test
    public void reportGenerator_Ok() {
        fruitTransactions = setListFruit(data);
        service.process(fruitTransactions);
        List<String> resultingReport = generator.getReport();

        fruitTransactions = converter.convert(resultExpected);
        service.process(fruitTransactions);
        List<String> resultingReportExp = generator.getReport();

        assertEquals(resultingReportExp, resultingReport,
                "Expected: " + resultingReportExp
                + ". But was: " + resultingReport);
    }

    @Test
    public void writerFile_Exist() {
        File fileExp = setFile(data);
        assertTrue(fileExp.exists(), "File '" + dataOutput + "' does not exist");
    }

    @Test
    public void writerFile_NotEmpty() {
        File file = setFile(data);
        List<String> resultReportFromFile = readFile(file);
        assertEquals(reportOutExpected, resultReportFromFile,
                "Result does not match the expectation. It should be: "
                        + reportOutExpected
                        + "But was: " + resultReportFromFile);
    }

    @Test
    public void fruitNotInStorage_ThrowException() {
        FruitTransaction tx = new FruitTransaction("banana",
                5,
                FruitTransaction.Operation.PURCHASE);
        OperationHandler purchaseOperation = new PurchaseOperation();
        assertThrows(IllegalArgumentException.class, () -> purchaseOperation.apply(tx),
                "Expected exception when fruit not found in storage");
    }

    private List<FruitTransaction> setListFruit(String data) {
        result = fileReader.read(data);
        return converter.convert(result);
    }

    private File setFile(String data) {
        fruitTransactions = setListFruit(data);
        service.process(fruitTransactions);
        List<String> resultingReport = generator.getReport();
        FileWriter writer = new FileWriterImpl();
        writer.write(resultingReport, dataOutput);
        return new File(dataOutput);
    }

    private List<String> readFile(File file) {
        List<String> resultReport = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                resultReport.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("File not read");
        }
        return resultReport;
    }

}

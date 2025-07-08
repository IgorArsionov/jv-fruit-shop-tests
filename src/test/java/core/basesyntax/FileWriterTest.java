package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

public class FileWriterTest {
    private static ReportGenerator generator;
    private static FileWriter writer;
    private static FileReader fileReader;
    private static DataConverter converter;
    private static ShopService service;
    private static String data = "src/main/resources/reportToReadTest.csv";
    private static String dataOutput = "src/main/resources/reportToReadTestOut.csv";
    private static List<String> reportOutExpected;

    @BeforeAll
    public static void setUp() {
        generator = new ReportGeneratorImpl();
        writer = new FileWriterImpl();
        fileReader = new FileReaderImpl();
        converter = new DataConverterImpl();

        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());

        OperationStrategy strategy = new OperationStrategyImpl(handlers);
        service = new ShopServiceImpl(strategy);

        reportOutExpected = List.of("fruit,quantity",
                "banana,152", "apple,90", "pineapple,214", "cucumber,5");
    }

    @Test
    public void writerFile_Exist() {
        File file = setFile(data);
        assertTrue(file.exists(), "File '" + dataOutput + "' does not exist");
    }

    @Test
    public void writerFile_WriteOk() {
        File file = setFile(data);
        List<String> resultReportFromFile = readFile(file);
        assertEquals(reportOutExpected, resultReportFromFile,
                "Result does not match the expectation. It should be: "
                        + reportOutExpected + "But was: " + resultReportFromFile);
    }

    private File setFile(String data) {
        List<FruitTransaction> fruitTransactions = converter.convert(fileReader.read(data));
        service.process(fruitTransactions);
        List<String> resultingReport = generator.getReport();
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


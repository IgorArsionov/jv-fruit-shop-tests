package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.data.Storage;
import core.basesyntax.handlers.ReportGenerator;
import core.basesyntax.handlers.impl.ReportGeneratorImpl;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReportGeneratorTest {
    private static ReportGenerator generator;
    private static final Map<String, Integer> STORAGE = Storage.getAssortment();
    private static List<String> resultExpected;

    @BeforeAll
    public static void setUp() {
        generator = new ReportGeneratorImpl();
        STORAGE.put("banana", 20);
        STORAGE.put("cucumber", 15);
        resultExpected = List.of("fruit,quantity",
                "banana,20",
                "cucumber,15");
    }

    @Test
    public void reportGenerator_Ok() {
        List<String> resultingReport = generator.getReport();

        assertEquals(resultExpected, resultingReport,
                "Expected: " + resultExpected + ". But was: " + resultingReport);
    }
}


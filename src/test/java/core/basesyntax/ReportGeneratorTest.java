package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.data.Storage;
import core.basesyntax.handlers.ReportGenerator;
import core.basesyntax.handlers.impl.ReportGeneratorImpl;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorTest {
    private static final Map<String, Integer> STORAGE = Storage.getAssortment();
    private ReportGenerator generator;
    private List<String> resultExpected;

    @BeforeEach
    public void setUp() {
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


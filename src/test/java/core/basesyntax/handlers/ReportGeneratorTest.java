package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.data.Storage;
import core.basesyntax.handlers.impl.ReportGeneratorImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorTest {
    private ReportGenerator generator;
    private List<String> resultExpected;

    @BeforeEach
    public void setUp() {
        generator = new ReportGeneratorImpl();
        Storage.getAssortment().put("banana", 20);
        Storage.getAssortment().put("cucumber", 15);
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


package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.handlers.DataConverter;
import core.basesyntax.handlers.impl.DataConverterImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DataConverterTest {
    private static DataConverter converter;
    private static List<String> resultReaderExpected;
    private static List<FruitTransaction> fruitTransactionsExpected;

    @BeforeAll
    public static void setUp() {
        converter = new DataConverterImpl();
        resultReaderExpected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,cucumber,5"
        );
        fruitTransactionsExpected = converter.convert(resultReaderExpected);
    }

    @Test
    public void converter_Ok() {
        List<FruitTransaction> result = converter.convert(resultReaderExpected);
        assertEquals(fruitTransactionsExpected, result,
                "Result does not match the expectation. It should be: "
                        + fruitTransactionsExpected + ". But it was: " + result);
    }
}


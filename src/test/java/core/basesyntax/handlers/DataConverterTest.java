package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.handlers.impl.DataConverterImpl;
import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConverterTest {
    private DataConverter converter;
    private List<String> resultReaderExpected;
    private List<FruitTransaction> fruitTransactionsExpected;

    @BeforeEach
    public void setUp() {
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

    @Test
    public void converter_NotNull() {
        List<String> empty = List.of();
        List<FruitTransaction> result = converter.convert(empty);
        assertNotNull(result, "Converter should not return null for empty input");
        assertTrue(result.isEmpty(), "Result list should be empty when input is empty");
    }

    @Test
    public void converter_NotThrow() {
        List<String> empty = List.of();
        assertDoesNotThrow(() -> converter.convert(empty));
    }

    @Test
    public void converter_ThrowOk() {
        List<String> input = List.of("type,fruit,quantity", "x,banana,10");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            converter.convert(input);
        });
        assertEquals("Unknown operation code: x", exception.getMessage());
    }

    @Test
    void dataConverter_shouldThrow_whenLineHasTooFewFields() {
        List<String> input = List.of("type,fruit,quantity", "b,banana");
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> converter.convert(input));
    }
}


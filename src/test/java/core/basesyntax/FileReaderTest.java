package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.handlers.filehandlers.FileReader;
import core.basesyntax.handlers.filehandlers.impl.FileReaderImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileReaderTest {
    private static FileReader fileReader;
    private static String dataEmpty = "src/main/resources/emptyData.csv";
    private static String dataNonExist = "src/main/resources/nonExistData.csv";

    @BeforeAll
    public static void init() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void nonExistData() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fileReader.read(dataNonExist);
        });
        assertEquals("Errors reading file: " + dataNonExist, exception.getMessage());
    }

    @Test
    public void emptyData() {
        List<String> resultReader = fileReader.read(dataEmpty);
        assertNotNull(resultReader, "Result not be null");
        assertTrue(resultReader.isEmpty(), "Empty list expected");
    }
}


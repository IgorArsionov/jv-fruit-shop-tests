package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.handlers.filehandlers.FileReader;
import core.basesyntax.handlers.filehandlers.impl.FileReaderImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileReaderTest {
    private static final String dataEmpty = "src/main/resources/emptyData.csv";
    private static final String dataNonExist = "src/main/resources/nonExistData.csv";
    private FileReader fileReader;

    @BeforeEach
    public void init() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void file_Not_Exist() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fileReader.read(dataNonExist);
        });
        assertEquals("Errors reading file: " + dataNonExist, exception.getMessage());
    }

    @Test
    public void empty_Data() {
        List<String> resultReader = fileReader.read(dataEmpty);
        assertNotNull(resultReader, "Result not be null");
        assertTrue(resultReader.isEmpty(), "Empty list expected");
    }
}


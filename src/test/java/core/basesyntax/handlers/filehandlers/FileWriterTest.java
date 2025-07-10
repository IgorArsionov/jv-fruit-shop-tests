package core.basesyntax.handlers.filehandlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.handlers.filehandlers.impl.FileWriterImpl;
import java.io.File;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileWriterTest {
    private static final String OUTPUT_FILE = "src/main/resources/reportToReadTestOut.csv";
    private FileWriter writer;
    private List<String> simpleData;
    private String data;

    @BeforeEach
    public void setUp() {
        writer = new FileWriterImpl();
        simpleData = List.of("fruit,quantity",
                "banana,152", "apple,90", "pineapple,214", "cucumber,5");
        data = String.join(System.lineSeparator(), simpleData);
    }

    @Test
    public void writerFile_Exist() {
        File file = new File(OUTPUT_FILE);
        writer.write(data, OUTPUT_FILE);
        assertTrue(file.exists(), "File '" + OUTPUT_FILE + "' does not exist");
    }

    @Test
    public void writerFile_WriteOk() {
        FileWriter writer = new FileWriterImpl();
        String directoryPath = "src/main/resources";
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            writer.write(data, directoryPath);
        });
        assertEquals("Errors create and write file: " + directoryPath,
                exception.getMessage());
    }
}


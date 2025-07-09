package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.handlers.filehandlers.FileWriter;
import core.basesyntax.handlers.filehandlers.impl.FileWriterImpl;
import java.io.File;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileWriterTest {
    private static final String OUTPUT_FILE = "src/main/resources/reportToReadTestOut.csv";
    private static FileWriter writer;
    private static List<String> simpleData;
    private static String data;

    @BeforeAll
    public static void setUp() {
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
        assertThrows(RuntimeException.class, () -> {
            writer.write(data, directoryPath);
        });
    }
}


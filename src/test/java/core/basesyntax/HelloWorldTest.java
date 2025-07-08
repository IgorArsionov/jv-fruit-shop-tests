package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

public class HelloWorldTest {

    @Test
    void mainMethod_shouldNotThrowException() {
        assertDoesNotThrow(() -> HelloWorld.main(new String[]{}));
    }
}

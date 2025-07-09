package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

public class FruitTransactionTest {

    @Test
    void fruitTransaction_creationAndGetters_Ok() {
        FruitTransaction transaction = new FruitTransaction("banana",
                10, FruitTransaction.Operation.SUPPLY);
        assertEquals("banana", transaction.getFruit());
        assertEquals(10, transaction.getQuantity());
        assertEquals(FruitTransaction.Operation.SUPPLY, transaction.getOperation());
    }

    @Test
    void fruitTransaction_setters_Ok() {
        FruitTransaction transaction = new FruitTransaction("apple",
                5, FruitTransaction.Operation.BALANCE);
        transaction.setFruit("orange");
        transaction.setQuantity(100);
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);

        assertEquals("orange", transaction.getFruit());
        assertEquals(100, transaction.getQuantity());
        assertEquals(FruitTransaction.Operation.PURCHASE, transaction.getOperation());
    }

    @Test
    void fruitTransaction_equalsAndHashCode_Ok() {
        FruitTransaction a = new FruitTransaction("apple",
                10, FruitTransaction.Operation.BALANCE);
        FruitTransaction b = new FruitTransaction("apple",
                10, FruitTransaction.Operation.BALANCE);
        FruitTransaction c = new FruitTransaction("apple",
                15, FruitTransaction.Operation.BALANCE);

        assertEquals(a, b);
        assertNotEquals(a, c);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void operation_fromCode_ReturnCorrectEnum() {
        assertEquals(FruitTransaction.Operation.BALANCE, FruitTransaction.Operation.fromCode("b"));
        assertEquals(FruitTransaction.Operation.SUPPLY, FruitTransaction.Operation.fromCode("s"));
        assertEquals(FruitTransaction.Operation.PURCHASE, FruitTransaction.Operation.fromCode("p"));
        assertEquals(FruitTransaction.Operation.RETURN, FruitTransaction.Operation.fromCode("r"));
    }

    @Test
    void operation_fromCode_invalid_shouldThrow() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            FruitTransaction.Operation.fromCode("x");
        });
        assertEquals("Unknown operation code: x", exception.getMessage());
    }

    @Test
    void operation_getCode_returnsValueOk() {
        assertEquals("b", FruitTransaction.Operation.BALANCE.getCode());
        assertEquals("s", FruitTransaction.Operation.SUPPLY.getCode());
        assertEquals("p", FruitTransaction.Operation.PURCHASE.getCode());
        assertEquals("r", FruitTransaction.Operation.RETURN.getCode());
    }

}

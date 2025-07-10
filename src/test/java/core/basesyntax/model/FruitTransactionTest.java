package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class FruitTransactionTest {

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
    void equals_nullOrDifferentType_shouldReturnFalse() {
        FruitTransaction tx = new FruitTransaction("apple",
                10, FruitTransaction.Operation.BALANCE);

        assertNotEquals(tx, null);
        assertNotEquals(tx, "not a transaction");
    }

    @Test
    void operation_fromCode_ReturnCorrectEnum() {
        assertEquals(FruitTransaction.Operation.BALANCE,
                FruitTransaction.Operation.fromCode("b"));
        assertEquals(FruitTransaction.Operation.SUPPLY,
                FruitTransaction.Operation.fromCode("s"));
        assertEquals(FruitTransaction.Operation.PURCHASE,
                FruitTransaction.Operation.fromCode("p"));
        assertEquals(FruitTransaction.Operation.RETURN,
                FruitTransaction.Operation.fromCode("r"));
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

    @Test
    void equals_nullFruitHandledCorrectly() {
        FruitTransaction tx1 = new FruitTransaction(null,
                10, FruitTransaction.Operation.BALANCE);
        FruitTransaction tx2 = new FruitTransaction(null,
                10, FruitTransaction.Operation.BALANCE);

        assertEquals(tx1, tx2);
        assertEquals(tx1.hashCode(), tx2.hashCode());
    }

    @Test
    void setters_shouldAffectEquality() {
        FruitTransaction tx1 = new FruitTransaction("apple",
                10, FruitTransaction.Operation.BALANCE);
        FruitTransaction tx2 = new FruitTransaction("apple",
                10, FruitTransaction.Operation.BALANCE);
        tx2.setFruit("banana");
        assertNotEquals(tx1, tx2);
    }

}

package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

public class FruitTransactionTest {

    @Test
    void fruitTransaction_equalsAndHashCode_Ok() {
        FruitTransaction a = new FruitTransaction("apple", 10, FruitTransaction.Operation.BALANCE);
        FruitTransaction b = new FruitTransaction("apple", 10, FruitTransaction.Operation.BALANCE);
        FruitTransaction c = new FruitTransaction("apple", 15, FruitTransaction.Operation.BALANCE);

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

}

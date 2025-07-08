package core.basesyntax.handlers.impl;

import core.basesyntax.FruitTransaction;
import core.basesyntax.handlers.FruitPars;

public class FruitParsImpl implements FruitPars {
    @Override
    public FruitTransaction.Operation parse(String value) {
        for (FruitTransaction.Operation op : FruitTransaction.Operation.values()) {
            if (op.getCode().equals(value)) {
                return op;
            }
        }
        throw new RuntimeException("Unknown operation: " + value);
    }
}

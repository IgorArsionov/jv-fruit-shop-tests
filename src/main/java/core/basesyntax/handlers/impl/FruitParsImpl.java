package core.basesyntax.handlers.impl;

import core.basesyntax.FruitTransaction;
import core.basesyntax.handlers.FruitPars;

public class FruitParsImpl implements FruitPars {
    @Override
    public FruitTransaction.Operation parse(String value) {
        return FruitTransaction.Operation.fromCode(value);
    }
}

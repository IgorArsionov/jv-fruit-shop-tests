package core.basesyntax.handlers;

import core.basesyntax.FruitTransaction;

public interface FruitPars {
    FruitTransaction.Operation parse(String value);
}

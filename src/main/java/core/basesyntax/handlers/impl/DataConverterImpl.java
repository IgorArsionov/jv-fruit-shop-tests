package core.basesyntax.handlers.impl;

import core.basesyntax.FruitTransaction;
import core.basesyntax.handlers.DataConverter;
import core.basesyntax.handlers.FruitPars;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    private static final String SPLIT = ",";

    @Override
    public List<FruitTransaction> convert(List<String> value) {
        return value.stream()
                .map(v -> v.split(SPLIT))
                .map(v -> new FruitTransaction(v[1],
                        Integer.parseInt(v[2]),
                        FruitPars.parse(v[0])))
                .toList();
    }
}

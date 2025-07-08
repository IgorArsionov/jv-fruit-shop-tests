package core.basesyntax.handlers.impl;

import core.basesyntax.FruitTransaction;
import core.basesyntax.handlers.DataConverter;
import core.basesyntax.handlers.FruitPars;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    private static final String SPLIT = ",";
    private static final int FRUIT_OPERATION = 0;
    private static final int FRUIT_NAME = 1;
    private static final int FRUIT_QUANTITY = 2;
    private static final int DELETE_COLUM = 0;
    private static FruitPars fruitPars;

    public DataConverterImpl() {
        fruitPars = new FruitParsImpl();
    }

    @Override
    public List<FruitTransaction> convert(List<String> inputReport) {
        List<String> inputReportConvert = new ArrayList<>(inputReport);
        if (!inputReportConvert.isEmpty()) {
            inputReportConvert.remove(DELETE_COLUM);
        }
        return inputReportConvert.stream()
                .map(string -> string.split(SPLIT))
                .map(report -> new FruitTransaction(report[FRUIT_NAME],
                        Integer.parseInt(report[FRUIT_QUANTITY]),
                        fruitPars.parse(report[FRUIT_OPERATION])))
                .toList();
    }
}

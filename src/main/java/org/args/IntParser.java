package org.args;

import java.util.List;
import java.util.function.Function;

class IntParser implements OptionParser {
    Function<String, Object> valueParser = Integer::parseInt;

    public IntParser(Function<String, Object> valueParser) {
        this.valueParser = valueParser;
    }

    public static OptionParser createIntParser() {
        return new IntParser(Integer::parseInt);
    }

    @Override
    public Object parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        String value = arguments.get(index + 1);

        return valueParser.apply(value);
    }

}

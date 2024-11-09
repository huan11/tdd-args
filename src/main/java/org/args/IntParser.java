package org.args;

import java.util.List;
import java.util.function.Function;

class IntParser implements OptionParser {
    Function<String, Object> valueParser = value1 -> Integer.parseInt(value1);

    @Override
    public Object parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        String value = arguments.get(index + 1);
        return parseValue(value);
    }

    protected Object parseValue(String value) {

        return valueParser.apply(value);
    }
}

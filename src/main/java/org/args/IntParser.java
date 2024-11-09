package org.args;

import java.util.List;

class IntParser implements OptionParser {
    @Override
    public Object parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        String value = arguments.get(index + 1);
        return parseValue(value);
    }

    protected static int parseValue(String value) {
        return Integer.parseInt(value);
    }
}

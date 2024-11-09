package org.args;

import java.util.List;

class StringParser implements OptionParser {
    @Override
    public Object parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        String value = arguments.get(index + 1);
        return parseValue(value);
    }

    private static String parseValue(String value) {
        return String.valueOf(value);
    }
}

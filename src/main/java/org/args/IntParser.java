package org.args;

import java.util.List;

class IntParser implements OptionParser {
    @Override
    public Object parse(List<String> arguments, Option option) {
        // First query the index of the flag ,then get the value and ensure it is an integer
        int index = arguments.indexOf("-" + option.value());
        String value = arguments.get(index + 1);
        return Integer.parseInt(value);
    }
}

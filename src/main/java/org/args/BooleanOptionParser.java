package org.args;

import java.util.List;

class BooleanOptionParser implements OptionParser {
    @Override
    public Object parse(List<String> arguments, Option option) {
        // If the flag is present, the value is true, otherwise false
        int index = arguments.indexOf("-" + option.value());
        if (!arguments.get(index + 1).startsWith("-")) throw new TooManyArgumentsException(option.value());
        return index != -1;
    }
}

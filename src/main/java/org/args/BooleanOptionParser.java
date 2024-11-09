package org.args;

import java.util.List;

class BooleanOptionParser implements OptionParser {
    @Override
    public Object parse(List<String> arguments, Option option) {
        // If the flag is present, the value is true, otherwise false
        return arguments.contains("-" + option.value());
    }
}

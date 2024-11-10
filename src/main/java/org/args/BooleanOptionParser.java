package org.args;

import java.util.List;

class BooleanOptionParser implements OptionParser<Boolean> {
    @Override
    public Boolean parse(List<String> arguments, Option option) {
        // If the flag is present, the value is true, otherwise false
        int index = arguments.indexOf("-" + option.value());
        // 考虑越界的问题
        if (index + 1 < arguments.size() &&
                !arguments.get(index + 1).startsWith("-")) throw new TooManyArgumentsException(option.value());
        return index != -1;
    }
}

package org.args;

import org.args.exceptions.TooManyArgumentsException;

import java.util.List;

class BooleanOptionParser implements OptionParser<Boolean> {
    @Override
    public Boolean parse(List<String> arguments, Option option) {
        // If the flag is present, the value is true, otherwise false
        int index = arguments.indexOf("-" + option.value());
        if (index == -1) return false;

        List<String> values = SingValueOptionParser.getValuesBetweenCurrentAndNextFlag(arguments, index);

        // 考虑越界的问题
        if (values.size() > 0) throw new TooManyArgumentsException(option.value());
        return true;
    }
}

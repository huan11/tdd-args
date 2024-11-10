package org.args;

import java.util.List;

class BooleanOptionParser implements OptionParser<Boolean> {
    @Override
    public Boolean parse(List<String> arguments, Option option) {
        return SingValueOptionParser.values(arguments, option, 0)
                .map(it -> true).orElse(false);
    }
}

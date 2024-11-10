package org.args;

import java.util.List;

import static org.args.SingValueOptionParser.values;

class BooleanOptionParser implements OptionParser<Boolean> {
    @Override
    public Boolean parse(List<String> arguments, Option option) {
        return values(arguments, option, 0)
                .map(it -> true).orElse(false);
    }
}

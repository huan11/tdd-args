package org.args;

import java.util.List;
import java.util.function.Function;

class StringParser extends IntParser {
    public StringParser() {
        super(String::valueOf);
    }

    @Override
    protected  String parseValue(String value) {
        return String.valueOf(value);
    }
}

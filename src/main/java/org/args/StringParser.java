package org.args;

import java.util.List;
import java.util.function.Function;

class StringParser extends IntParser {
    private StringParser() {
        super(String::valueOf);
    }

    public static StringParser createStringParser() {
        return new StringParser();
    }
}

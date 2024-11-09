package org.args;

import java.util.List;

class StringParser extends IntParser {
    protected static String parseValue(String value) {
        return String.valueOf(value);
    }
}

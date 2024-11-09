package org.args;

import java.util.List;

class StringParser extends IntParser {
    @Override
    protected  String parseValue(String value) {
        return String.valueOf(value);
    }
}

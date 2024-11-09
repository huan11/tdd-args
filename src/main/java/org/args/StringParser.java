package org.args;

class StringParser extends IntParser {
    private StringParser() {
        super(String::valueOf);
    }

    public static OptionParser createStringParser() {
        return new IntParser(String::valueOf);
    }
}

package org.args;

class StringParser extends IntParser {
    public static OptionParser createStringParser() {
        return new IntParser(String::valueOf);
    }
}

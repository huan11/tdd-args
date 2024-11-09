package org.args;

public class TooManyArgumentsException extends RuntimeException {
    private final String option;

    public TooManyArgumentsException(String option) {
        super("Too many arguments for option: " + option);
        this.option = option;
    }

    public String getOption() {
        return option;
    }
}

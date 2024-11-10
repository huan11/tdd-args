package org.args;

public class InsufficientArgumentsException extends RuntimeException {
    private final String option;

    public InsufficientArgumentsException(String option) {
        super("Insufficient Argument for option: " + option);
        this.option = option;
    }

    public String getOption() {
        return option;
    }
}

package org.args;

public class IllegalOptionException extends RuntimeException {
    private final String parameter;

    public IllegalOptionException(String parameter) {
        super("Insufficient Argument for option: " + parameter);
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }
}
package org.args;

import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BooleanOptionParserTest {
    // Sad path
    @Test
    public void should_not_accept_extra_argument_for_boolean_option() {
        // Assert
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
            // Act
            new BooleanOptionParser().parse(asList("-l", "t"), option("l"));
        });

        // Assert
        assertEquals("l", e.getOption());
    }

    @Test
    public void should_not_accept_extra_arguments_for_boolean_option() {
        // Assert
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
            // Act
            new BooleanOptionParser().parse(asList("-l", "t", "f"), option("l"));
        });

        // Assert
        assertEquals("l", e.getOption());
    }


    static Option option(String value) {
        return new Option() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return Option.class;
            }

            @Override
            public String value() {
                return value;
            }
        };
    }
}
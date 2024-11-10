package org.args;

import org.args.exceptions.TooManyArgumentsException;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BooleanOptionParserTest {

    @Test // Sad path
    public void should_not_accept_extra_argument_for_boolean_option() {
        // Assert
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
            // Act
            OptionParsers.bool().parse(asList("-l", "t"), option("l"));
        });

        // Assert
        assertEquals("l", e.getOption());
    }


    @Test // Default value
    public void should_set_default_value_to_false_if_not_present() {
        assertFalse(OptionParsers.bool().parse(asList(), option("l")));
    }


    @Test // Happy path
    public void should_set_value_to_true_if_present() {
        assertTrue(OptionParsers.bool().parse(asList("-l"), option("l")));
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
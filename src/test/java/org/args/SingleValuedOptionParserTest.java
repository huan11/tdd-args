package org.args;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static java.util.Arrays.asList;
import static org.args.BooleanOptionParserTest.option;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SingleValuedOptionParserTest {
    @Test
    public void should_not_accept_extra_argument_for_single_valued_option() {
        // Assert
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
            // Act
            SingValueOptionParser.createSingValueOptionParser(Integer::parseInt, 0).parse(asList("-p", "8080", "8081"), option("p"));
        });

        // Assert
        assertEquals("p", e.getOption());
    }

    @ParameterizedTest
    @ValueSource(strings = {"-p -l", "-p"})
    public void should_not_accept_insufficient_argument_for_single_valued_option(String arguments) {
        // Assert
        InsufficientArgumentsException e = assertThrows(InsufficientArgumentsException.class, () -> {
            // Act
            SingValueOptionParser.createSingValueOptionParser(Integer::parseInt, 0).parse(asList(arguments.split(" ")), option("p"));
        });

        // Assert
        assertEquals("p", e.getOption());
    }


    @Test
    public void should_set_default_value_to_zero_for_int_option() {
        assertEquals(0, SingValueOptionParser.createSingValueOptionParser(Integer::parseInt, 0).parse(asList(), option("p")));
    }
}
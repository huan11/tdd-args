package org.args;

import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.args.BooleanOptionParserTest.option;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SingleValuedOptionParserTest {
    @Test
    public void should_not_accept_extra_argument_for_single_valued_option() {
        // Assert
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
            // Act
            new SingValueOptionParser<>(Integer::parseInt).parse(asList("-p", "8080", "8081"), option("p"));
        });

        // Assert
        assertEquals("p", e.getOption());
    }

    @Test
    public void should_not_accept_insufficient_argument_for_single_valued_option() {
        // Assert
        InsufficientArgumentsException e = assertThrows(InsufficientArgumentsException.class, () -> {
            // Act
            new SingValueOptionParser<>(Integer::parseInt).parse(asList("-p"), option("p"));
        });

        // Assert
        assertEquals("p", e.getOption());
    }

}
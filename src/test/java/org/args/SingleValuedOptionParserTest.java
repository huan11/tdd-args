package org.args;

import org.args.exceptions.InsufficientArgumentsException;
import org.args.exceptions.TooManyArgumentsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.function.Function;

import static java.util.Arrays.asList;
import static org.args.BooleanOptionParserTest.option;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SingleValuedOptionParserTest {
    @Test //Sad path
    public void should_not_accept_extra_argument_for_single_valued_option() {
        // Assert
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
            // Act
            SingValueOptionParser.unary(0, Integer::parseInt).parse(asList("-p", "8080", "8081"), option("p"));
        });

        // Assert
        assertEquals("p", e.getOption());
    }

    @ParameterizedTest // Sad path
    @ValueSource(strings = {"-p -l", "-p"})
    public void should_not_accept_insufficient_argument_for_single_valued_option(String arguments) {
        // Assert
        InsufficientArgumentsException e = assertThrows(InsufficientArgumentsException.class, () -> {
            // Act
            SingValueOptionParser.unary(0, Integer::parseInt).parse(asList(arguments.split(" ")), option("p"));
        });

        // Assert
        assertEquals("p", e.getOption());
    }


    @Test // Default value
    public void should_set_default_value_to_zero_for_int_option() {
        // 任意的实现
        Function<String, Object> whatever = (it) -> null;
        // 任意的 default value
        Object defaultValue = new Object();
        // 意图更加明显，无论你是啥，保证最终的 defaultValue 一致，和 任何具体的 parser 实现whatever 没有关系
        assertEquals(defaultValue, SingValueOptionParser.unary(defaultValue, whatever).parse(asList(), option("p")));
    }

    @Test // Happy path
    public void should_parse_value_if_flag_present() {
        Object parsed = new Object();
        Function<String, Object> parse = (it) -> parsed;
        Object whatever = new Object();
        assertEquals(parsed, SingValueOptionParser.unary(whatever, parse).parse(asList("-p", "8080"), option("p")));
    }
}
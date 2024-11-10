package org.args;

import org.args.exceptions.IllegalValueException;
import org.args.exceptions.InsufficientArgumentsException;
import org.args.exceptions.TooManyArgumentsException;
import org.junit.Assert;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.annotation.Annotation;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

public class OptionParsersTest {
    @Nested
    class UnaryOptionParser {
        @Test //Sad path
        public void should_not_accept_extra_argument_for_single_valued_option() {
            // Assert
            TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
                // Act
                OptionParsers.unary(0, Integer::parseInt).parse(asList("-p", "8080", "8081"), option("p"));
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
                OptionParsers.unary(0, Integer::parseInt).parse(asList(arguments.split(" ")), option("p"));
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
            assertEquals(defaultValue, OptionParsers.unary(defaultValue, whatever).parse(asList(), option("p")));
        }

        @Test // Happy path
        public void should_parse_value_if_flag_present() {
            Object parsed = new Object();
            Function<String, Object> parse = (it) -> parsed;
            Object whatever = new Object();
            assertEquals(parsed, OptionParsers.unary(whatever, parse).parse(asList("-p", "8080"), option("p")));
        }

        @Test // Sad path - Illegal value format
        public void should_raise_exception_if_illegal_value_format() {
            // Arrange: Create a dummy parser that throws an exception for any input
            Object whatever = new Object();
            Function<String, Object> parse = (it) -> {
                throw new RuntimeException("Invalid value format");
            };

            // Act & Assert: Expect an IllegalValueException when parsing
            IllegalValueException e = assertThrows(IllegalValueException.class, () ->
                    OptionParsers.unary(whatever, parse).parse(asList("-p", "8080"), option("p"))
            );

            // Verify that the exception contains the correct option and value information
            assertEquals("p", e.getOption());
            assertEquals("8080", e.getValue());
        }
    }


    @Nested
    class BooleanOptionParser {

        @Test // Sad path
        public void should_not_accept_extra_argument_for_boolean_option() {
            // Assert
            TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
                // Act
                OptionParsers.bool().parse(asList("-l", "t"), option("l"));
            });

            // Assert
            Assert.assertEquals("l", e.getOption());
        }


        @Test // Default value
        public void should_set_default_value_to_false_if_not_present() {
            assertFalse(OptionParsers.bool().parse(asList(), option("l")));
        }


        @Test // Happy path
        public void should_set_value_to_true_if_present() {
            assertTrue(OptionParsers.bool().parse(asList("-l"), option("l")));
        }
    }

    @Nested
    class ListOptionParser {
        @Test
        public void should_parse_list_value() {
            String[] value = OptionParsers.list(String[]::new, String::valueOf).parse(asList("-g", "this", "is"), option("g"));
            assertArrayEquals(new String[]{"this", "is"}, value);
        }

        @Test
        public void should_use_empty_array_as_default_value() {
            String[] value = OptionParsers.list(String[]::new, String::valueOf).parse(asList(), option("g"));
            assertEquals(0, value.length);
        }

        //TODO: -d a throw exception
        @Test
        public void should_throw_exception_if_value_parser_cant_parse_value() {
            Function<String, String> parser = (it) -> {
                throw new RuntimeException();
            };
            IllegalValueException e = assertThrows(IllegalValueException.class, () ->
                    OptionParsers.list(String[]::new, parser).parse(asList("-g", "this", "is"), option("g")));

            assertEquals("g", e.getOption());
            assertEquals("this", e.getValue());
        }
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
package org.args;

import org.args.exceptions.InsufficientArgumentsException;
import org.args.exceptions.TooManyArgumentsException;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;

class SingValueOptionParser<T> implements OptionParser<T> {
    Function<String, T> valueParser;
    T defaultValue;

    private SingValueOptionParser(T defaultValue, Function<String, T> valueParser) {
        this.defaultValue = defaultValue;
        this.valueParser = valueParser;
    }

    public static OptionParser<Boolean> bool() {
        return ((arguments, option) ->
                values(arguments, option, 0).map(it -> true).orElse(false));
    }

    public static <T> OptionParser<T> createSingValueOptionParser(T defaultValue, Function<String, T> valueParser) {
        return new SingValueOptionParser<T>(defaultValue, valueParser);
    }


    @Override
    public T parse(List<String> arguments, Option option) {

        return getT(arguments, option, valueParser, defaultValue);
    }

    private static <T> T getT(List<String> arguments, Option option, Function<String, T> valueParser, T defaultValue) {
        return values(arguments, option, 1).map(it -> parseValue(it.get(0), valueParser)).orElse(defaultValue);
    }

    private static Optional<List<String>> values(List<String> arguments, Option option, int expectedSize) {
        int index = arguments.indexOf("-" + option.value());
        if (index == -1) {
            return Optional.empty();
        }

        List<String> values = getValuesBetweenCurrentAndNextFlag(arguments, index);


        if (values.size() < expectedSize) throw new InsufficientArgumentsException(option.value());
        if (values.size() > expectedSize) throw new TooManyArgumentsException(option.value());

        return Optional.of(values);
    }

    private static <T> T parseValue(String value, Function<String, T> valueParser) {
        return valueParser.apply(value);
    }

    private static List<String> getValuesBetweenCurrentAndNextFlag(List<String> arguments, int index) {

        return arguments.subList(index + 1, IntStream.range(index + 1, arguments.size())
                .filter(it -> arguments.get(it).startsWith("-"))
                .findFirst().orElse(arguments.size()));
    }

}

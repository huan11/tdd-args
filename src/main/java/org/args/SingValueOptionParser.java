package org.args;

import org.args.exceptions.InsufficientArgumentsException;
import org.args.exceptions.TooManyArgumentsException;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

class SingValueOptionParser<T> implements OptionParser<T> {
    Function<String, T> valueParser;
    T defaultValue;

    public SingValueOptionParser(T defaultValue, Function<String, T> valueParser) {
        this.defaultValue = defaultValue;
        this.valueParser = valueParser;
    }


    @Override
    public T parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        if (index == -1) return defaultValue;

        List<String> values = getValuesBetweenCurrentAndNextFlag(arguments, index);

        if (values.size() < 1) throw new InsufficientArgumentsException(option.value());
        if (values.size() > 1) throw new TooManyArgumentsException(option.value());
        return valueParser.apply(values.get(0));
    }

    private static List<String> getValuesBetweenCurrentAndNextFlag(List<String> arguments, int index) {

        return arguments.subList(index + 1, IntStream.range(index + 1, arguments.size())
                .filter(it -> arguments.get(it).startsWith("-"))
                .findFirst().orElse(arguments.size()));
    }

}

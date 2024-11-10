package org.args;

import org.args.exceptions.IllegalOptionException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Args {

    public static <T> T parse(Class<T> optionsClass, String... args) {
        try {
            List<String> arguments = Arrays.asList(args);
            // get first constructor（eg:for boolean record, it shld be public BooleanOption(@Option("l")boolean logging){ ）
            Constructor<?> constructor = optionsClass.getDeclaredConstructors()[0];

            Object[] values = Arrays.stream(constructor.getParameters()).map((it) -> parseOption(it, arguments)).toArray();

            return (T) constructor.newInstance(values);
        } catch (IllegalOptionException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static Object parseOption(Parameter parameter, List<String> arguments) {
        if (!parameter.isAnnotationPresent(Option.class)) throw new IllegalOptionException(parameter.getName());
        return PARSERS.get(parameter.getType()).parse(arguments, parameter.getAnnotation(Option.class));
    }

    private static Map<Class<?>, OptionParser> PARSERS = Map.of(
            boolean.class, BooleanOptionParser.bool(),
            int.class, new SingValueOptionParser<>(0, Integer::parseInt),
            String.class, new SingValueOptionParser<>("", String::valueOf)
    );

}

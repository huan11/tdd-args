package org.args;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Args {

    public static <T> T parse(Class<T> optionsClass, String... args){
        try{
            List<String> arguments = Arrays.asList(args);
            // get first constructor（eg:for boolean record, it shld be public BooleanOption(@Option("l")boolean logging){ ）
            Constructor<?> constructor = optionsClass.getDeclaredConstructors()[0];

            Object[] values = Arrays.stream(constructor.getParameters()).map((it) -> parseOption(it, arguments)).toArray();

            return (T) constructor.newInstance(values);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    private static Object parseOption(Parameter parameter, List<String> arguments) {
        // get annotation of first parameter(eg: @Option("l"))
        return getOptionParser(parameter.getType()).parse(arguments, parameter.getAnnotation(Option.class));
    }

    private static Map<Class<?>, OptionParser> PARSERS = Map.of(
            boolean.class, new BooleanParser(),
            int.class, new IntParser(),
            String.class, new StringParser()
    );
    private static OptionParser getOptionParser(Class<?> type) {
        return PARSERS.get(type);
    }

    interface OptionParser{
        Object parse(List<String> arguments, Option option);
    }

    static class StringParser implements OptionParser{
        @Override
        public Object parse(List<String> arguments, Option option) {
            int index = arguments.indexOf("-" + option.value());
            return arguments.get(index + 1);
        }
    }

    static class IntParser implements OptionParser{
        @Override
        public Object parse(List<String> arguments, Option option) {
            // First query the index of the flag ,then get the value and ensure it is an integer
            int index = arguments.indexOf("-" + option.value());
            return Integer.parseInt(arguments.get(index + 1));
        }
    }

    static class BooleanParser implements OptionParser{
        @Override
        public Object parse(List<String> arguments, Option option) {
            // If the flag is present, the value is true, otherwise false
            return arguments.contains("-" + option.value());
        }
    }
}

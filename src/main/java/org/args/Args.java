package org.args;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

public class Args {

    public static <T> T parse(Class<T> optionsClass, String... args){
        try{
            List<String> arguments = Arrays.asList(args);
            // get first constructor（eg:for boolean record, it shld be public BooleanOption(@Option("l")boolean logging){ ）
            Constructor<?> constructor = optionsClass.getDeclaredConstructors()[0];

            // get first parameter of constructor (eg: @Option("l")boolean logging)
            Parameter parameter = constructor.getParameters()[0];

            Object value = parseOption(parameter, arguments);
            return (T) constructor.newInstance(value);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    private static Object parseOption(Parameter parameter, List<String> arguments) {
        Object value = null;
        // get annotation of first parameter(eg: @Option("l"))
        Option option = parameter.getAnnotation(Option.class);


        if (parameter.getType() == boolean.class){
            // If the flag is present, the value is true, otherwise false
            value = arguments.contains("-" + option.value());
        }
        if (parameter.getType() == int.class) {
            // First query the index of the flag ,then get the value and ensure it is an integer
            int index = arguments.indexOf("-" + option.value());
            value = Integer.parseInt(arguments.get(index + 1));
        }
        if (parameter.getType() == String.class){
            int index = arguments.indexOf("-" + option.value());
            value = arguments.get(index + 1);
        }
        return value;
    }
}

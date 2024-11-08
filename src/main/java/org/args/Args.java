package org.args;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

public class Args {

    public static <T> T parse(Class<T> optionsClass, String... args){
        try{
            // get first constructor（eg:for boolean record, it shld be public BooleanOption(@Option("l")boolean logging){ ）
            Constructor<?> constructor = optionsClass.getDeclaredConstructors()[0];
            // get first parameter of constructor (eg: @Option("l")boolean logging)
            Parameter parameter = constructor.getParameters()[0];
            // get annotation of first parameter(eg: @Option("l"))
            Option option = parameter.getAnnotation(Option.class);

            List<String> arguments = Arrays.asList(args);

            boolean value = arguments.contains("-" + option.value());
            return (T) constructor.newInstance(value);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}

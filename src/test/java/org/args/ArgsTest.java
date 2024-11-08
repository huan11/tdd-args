package org.args;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import static org.junit.jupiter.api.Assertions.*;


public class ArgsTest {
    // Single Option:
    static record BooleanOption(@Option("l")boolean logging){}
    @Test
    public void should_set_boolean_option_to_true_if_flag_present() {
        BooleanOption options = Args.parse(BooleanOption.class, "-l");
        assertTrue(options.logging());
    }
    @Test
    public void should_set_boolean_option_to_false_if_flag_not_present() {
        BooleanOption options = Args.parse(BooleanOption.class);
        assertFalse(options.logging());
    }

    static record IntOption(@Option("p") int port){}
    @Test
    public void should_parse_int_as_option_value(){
        // execute
        IntOption option = Args.parse(IntOption.class, "-p", "8080");
        // assert option value shld be equal to 8080
        assertEquals(8080, option.port());
    }

    static record StringOption(@Option("d") String directory){}
    @Test
    public void should_parse_string_as_option_value(){
        // execute
        StringOption option = Args.parse(StringOption.class, "-d", "/usr/logs");
        // assert
        assertEquals("/usr/logs", option.directory());
    }

    static record MultiOptions(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {
    }
    @Test
    public void should_parse_multi_options(){
        MultiOptions options = Args.parse(MultiOptions.class, "-l", "-p", "8080", "-d", "/use/logs");
        assertTrue(options.logging());
        assertEquals(8080, options.port());
        assertEquals("/use/logs", options.directory());
    }
    // sad path:
    // TODO:      - bool -l t / -l t f
    // TODO:      - int -p / -p 8080 8081
    // TODO:      - string -d / -d /usr/logs /usr/vars

    // default value
    // TODO:      - bool : false
    // TODO:      - int : 0
    // TODO:      - string ""


    // example2 `-g this is a list -d 1 2 -3 5` GPT 生成
    @Test
    @Disabled
    public void should_example2() {
        // 解析输入命令行参数
        ListOption option = Args.parse(ListOption.class, "-g", "this", "is", "a", "list", "-d", "1", "2", "-3", "5");

        // 断言 group 字段解析正确
        assertEquals(new String[]{"this", "is", "a", "list"}, option.group());

        // 断言 decimals 字段解析正确
        assertEquals(new int[]{1, 2, -3, 5}, option.decimals());
    }



    static record ListOption(@Option("-g") String[] group, @Option("-d") int[] decimals){}
}

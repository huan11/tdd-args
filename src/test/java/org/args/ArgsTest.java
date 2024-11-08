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

    // TODO:      - Integer -p 8080
    // TODO:      - String -d /usr/logs
    // TODO:      - multi options: -l -p 8080 -d /usr/logs

    // sad path:
    // TODO:      - bool -l t / -l t f
    // TODO:      - int -p / -p 8080 8081
    // TODO:      - string -d / -d /usr/logs /usr/vars

    // default value
    // TODO:      - bool : false
    // TODO:      - int : 0
    // TODO:      - string ""

    // example1 `-l -p 8080 -d /usr/logs`
    @Test
    @Disabled
    public void should_example1(){
        Options options = Args.parse(Options.class, "-l", "-p", "8080", "-d", "/use/logs");
        assertTrue(options.logging());
        assertEquals(8080, options.port());
        assertEquals("/use/logs", options.directory());
    }

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


    static record Options(@Option("-l") boolean logging, @Option("-p") int port, @Option("-d") String directory) {
    }
    static record ListOption(@Option("-g") String[] group, @Option("-d") int[] decimals){}
}

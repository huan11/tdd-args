package org.args;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ArgsTest {
    static record MultiOptions(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {
    }

    @Test
    public void should_parse_multi_options() {
        MultiOptions options = Args.parse(MultiOptions.class, "-l", "-p", "8080", "-d", "/use/logs");
        assertTrue(options.logging());
        assertEquals(8080, options.port());
        assertEquals("/use/logs", options.directory());
    }

    // Options without port annotation
    static record OptionsWithoutAnnotation(@Option("l") boolean logging, int port, @Option("d") String directory) {
    }

    @Test
    //should throw illegal option exception if annotation not present.
    public void should_throw_illegal_option_exception_if_annotation_not_present() {
        IllegalOptionException e = assertThrows(IllegalOptionException.class, () -> {
            Args.parse(OptionsWithoutAnnotation.class, "-l", "-p", "8080", "-d", "/use/logs");
        });

        assertEquals("port", e.getParameter());
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


    static record ListOption(@Option("-g") String[] group, @Option("-d") int[] decimals) {
    }
}

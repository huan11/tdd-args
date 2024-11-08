package example;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArgsTest {
    // example1 `-l -p 8080 -d /usr/logs`
    @Test
    public void should_example1(){
        Options options = Args.parse(Options.class, "-l", "-p", "8080", "-d", "/use/logs");
        assertTrue(options.logging());
        assertEquals(8080, options.port());
        assertEquals("/use/logs", options.directory());
    }

    // example2 `-g this is a list -d 1 2 -3 5` GPT 生成
    @Test
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

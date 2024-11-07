package example;

import org.junit.Test;

public class ArgsTest {
    @Test
    public void should() {
        Options options = Args.parse(Options.class, "-l", "-p", "8080", "-d", "/use/logs");
        // Show how to use
        options.logging();
        options.port();
        options.directory();
    }

    static record Options(@Option("-l") boolean logging, @Option("-p") int port, @Option("-d") String directory) {
    }
}

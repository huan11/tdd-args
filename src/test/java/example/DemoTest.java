package example;

import org.example.Demo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DemoTest {

    @Test
    public void testAdd() {
        int result = Demo.add(2, 3);
        assertEquals(5, result, "The add method should correctly add two integers");
    }
}

import cicd.Main;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {
    @Test
    void testMain() {
        Main main = new Main();
        assertEquals("Hello world!", main.getHello());
    }
}

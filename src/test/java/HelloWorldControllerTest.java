import cicd.HelloWorldController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloWorldControllerTest {
    private final HelloWorldController controller = new HelloWorldController();

    @Test
    void testHello() {
        String result = controller.hello();
        assertEquals("Hello world!", result);
    }
}

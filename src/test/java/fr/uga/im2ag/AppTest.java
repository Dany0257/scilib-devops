package fr.uga.im2ag;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    void testMain() {
        App.main(new String[] {});
        // App.main is a demonstration, we just verify it runs without exception
        assertTrue(true);
    }
}

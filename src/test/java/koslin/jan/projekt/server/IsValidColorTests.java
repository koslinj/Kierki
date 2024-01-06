package koslin.jan.projekt.server;

import koslin.jan.projekt.Rule;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IsValidColorTests {

    @Test
    public void testIsValidColor1() {
        boolean valid = GameLogic.isValidColor("2_of_clubs.png", true, "", false);

        boolean expected = true;
        assertEquals(expected, valid, "Card correctness calculated wrong!");
    }

    @Test
    public void testIsValidColor2() {
        boolean valid = GameLogic.isValidColor("2_of_clubs.png", false, "", false);

        boolean expected = true;
        assertEquals(expected, valid, "Card correctness calculated wrong!");
    }

    @Test
    public void testIsValidColor3() {
        boolean valid = GameLogic.isValidColor("2_of_hearts.png", false, "", false);

        boolean expected = true;
        assertEquals(expected, valid, "Card correctness calculated wrong!");
    }

    @Test
    public void testIsValidColor4() {
        boolean valid = GameLogic.isValidColor("2_of_hearts.png", true, "", false);

        boolean expected = false;
        assertEquals(expected, valid, "Card correctness calculated wrong!");
    }

}
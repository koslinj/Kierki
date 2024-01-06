package koslin.jan.projekt.server;

import koslin.jan.projekt.Deck;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static koslin.jan.projekt.server.Server.NUMBER_OF_PLAYERS;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IsValidColorDetailedTests {

    @Test
    public void testIsValidColor1() {
        List<String> cards = List.of("2_of_clubs.png", "2_of_hearts.png");
        int round = 5;
        String actualColor = "";

        boolean hasActualColor = cards.stream().anyMatch(card -> Deck.colorFromCard(card).equals(actualColor));
        int countOtherThanHearts = (int) cards.stream().filter(card -> !Deck.colorFromCard(card).equals("hearts")).count();
        boolean hideHearts = List.of(2, 5, 7).contains(round) && countOtherThanHearts > 0 && actualColor.equals("");

        boolean valid = GameLogic.isValidColor("2_of_hearts.png", hideHearts, actualColor, hasActualColor);

        boolean expected = false;
        assertEquals(expected, valid, "Card correctness calculated wrong!");
    }

    @Test
    public void testIsValidColor2() {
        List<String> cards = List.of("3_of_hearts.png", "2_of_hearts.png");
        int round = 5;
        String actualColor = "";

        boolean hasActualColor = cards.stream().anyMatch(card -> Deck.colorFromCard(card).equals(actualColor));
        int countOtherThanHearts = (int) cards.stream().filter(card -> !Deck.colorFromCard(card).equals("hearts")).count();
        boolean hideHearts = List.of(2, 5, 7).contains(round) && countOtherThanHearts > 0 && actualColor.equals("");

        boolean valid = GameLogic.isValidColor("2_of_hearts.png", hideHearts, actualColor, hasActualColor);

        boolean expected = true;
        assertEquals(expected, valid, "Card correctness calculated wrong!");
    }

    @Test
    public void testIsValidColor3() {
        List<String> cards = List.of("2_of_clubs.png", "2_of_hearts.png");
        int round = 5;
        String actualColor = "hearts";

        boolean hasActualColor = cards.stream().anyMatch(card -> Deck.colorFromCard(card).equals(actualColor));
        int countOtherThanHearts = (int) cards.stream().filter(card -> !Deck.colorFromCard(card).equals("hearts")).count();
        boolean hideHearts = List.of(2, 5, 7).contains(round) && countOtherThanHearts > 0 && actualColor.equals("");

        boolean valid = GameLogic.isValidColor("2_of_hearts.png", hideHearts, actualColor, hasActualColor);

        boolean expected = true;
        assertEquals(expected, valid, "Card correctness calculated wrong!");
    }

    @Test
    public void testIsValidColor4() {
        List<String> cards = List.of("2_of_clubs.png", "2_of_hearts.png");
        int round = 5;
        String actualColor = "hearts";

        boolean hasActualColor = cards.stream().anyMatch(card -> Deck.colorFromCard(card).equals(actualColor));
        int countOtherThanHearts = (int) cards.stream().filter(card -> !Deck.colorFromCard(card).equals("hearts")).count();
        boolean hideHearts = List.of(2, 5, 7).contains(round) && countOtherThanHearts > 0 && actualColor.equals("");

        boolean valid = GameLogic.isValidColor("2_of_clubs.png", hideHearts, actualColor, hasActualColor);

        boolean expected = false;
        assertEquals(expected, valid, "Card correctness calculated wrong!");
    }

}
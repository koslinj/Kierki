package koslin.jan.projekt.server;

import koslin.jan.projekt.Rule;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatePointsTests {

    @Test
    public void testCalculatePointsRound1() {
        // Set up test data
        Rule rule = Server.rulesForRounds.get(1);
        HashMap<Integer, String> cardsInGame = new HashMap<>();
        cardsInGame.put(1, "6_of_hearts.png");
        cardsInGame.put(2, "3_of_hearts.png");
        cardsInGame.put(3, "ace_of_hearts.png");
        cardsInGame.put(4, "5_of_clubs.png");
        int lewaNumber = 1;

        // Call the method to be tested
        int actualPoints = GameLogic.calculatePoints(rule, cardsInGame, lewaNumber);

        // Perform assertions to verify the correctness of the method
        int expectedPoints = -20;
        assertEquals(expectedPoints, actualPoints, "Points were not calculated correctly");
    }

    @Test
    public void testCalculatePointsRound2() {
        // Set up test data
        Rule rule = Server.rulesForRounds.get(2);
        HashMap<Integer, String> cardsInGame = new HashMap<>();
        cardsInGame.put(1, "6_of_hearts.png");
        cardsInGame.put(2, "3_of_hearts.png");
        cardsInGame.put(3, "ace_of_hearts.png");
        cardsInGame.put(4, "5_of_clubs.png");
        int lewaNumber = 1;

        // Call the method to be tested
        int actualPoints = GameLogic.calculatePoints(rule, cardsInGame, lewaNumber);

        // Perform assertions to verify the correctness of the method
        int expectedPoints = -60;
        assertEquals(expectedPoints, actualPoints, "Points were not calculated correctly");
    }

    @Test
    public void testCalculatePointsRound3() {
        // Set up test data
        Rule rule = Server.rulesForRounds.get(3);
        HashMap<Integer, String> cardsInGame = new HashMap<>();
        cardsInGame.put(1, "6_of_hearts.png");
        cardsInGame.put(2, "queen_of_hearts.png");
        cardsInGame.put(3, "ace_of_hearts.png");
        cardsInGame.put(4, "queen_of_clubs.png");
        int lewaNumber = 1;

        // Call the method to be tested
        int actualPoints = GameLogic.calculatePoints(rule, cardsInGame, lewaNumber);

        // Perform assertions to verify the correctness of the method
        int expectedPoints = -120;
        assertEquals(expectedPoints, actualPoints, "Points were not calculated correctly");
    }

    @Test
    public void testCalculatePointsRound4v1() {
        // Set up test data
        Rule rule = Server.rulesForRounds.get(4);
        HashMap<Integer, String> cardsInGame = new HashMap<>();
        cardsInGame.put(1, "jack_of_hearts.png");
        cardsInGame.put(2, "queen_of_hearts.png");
        cardsInGame.put(3, "king_of_hearts.png");
        cardsInGame.put(4, "jack_of_clubs.png");
        int lewaNumber = 1;

        // Call the method to be tested
        int actualPoints = GameLogic.calculatePoints(rule, cardsInGame, lewaNumber);

        // Perform assertions to verify the correctness of the method
        int expectedPoints = -90;
        assertEquals(expectedPoints, actualPoints, "Points were not calculated correctly");
    }

    @Test
    public void testCalculatePointsRound4v2() {
        // Set up test data
        Rule rule = Server.rulesForRounds.get(4);
        HashMap<Integer, String> cardsInGame = new HashMap<>();
        cardsInGame.put(1, "5_of_hearts.png");
        cardsInGame.put(2, "queen_of_hearts.png");
        cardsInGame.put(3, "6_of_hearts.png");
        cardsInGame.put(4, "ace_of_clubs.png");
        int lewaNumber = 1;

        // Call the method to be tested
        int actualPoints = GameLogic.calculatePoints(rule, cardsInGame, lewaNumber);

        // Perform assertions to verify the correctness of the method
        int expectedPoints = 0;
        assertEquals(expectedPoints, actualPoints, "Points were not calculated correctly");
    }

    @Test
    public void testCalculatePointsRound5v1() {
        // Set up test data
        Rule rule = Server.rulesForRounds.get(5);
        HashMap<Integer, String> cardsInGame = new HashMap<>();
        cardsInGame.put(1, "king_of_hearts.png");
        cardsInGame.put(2, "queen_of_hearts.png");
        cardsInGame.put(3, "6_of_hearts.png");
        cardsInGame.put(4, "ace_of_clubs.png");
        int lewaNumber = 1;

        // Call the method to be tested
        int actualPoints = GameLogic.calculatePoints(rule, cardsInGame, lewaNumber);

        // Perform assertions to verify the correctness of the method
        int expectedPoints = -150;
        assertEquals(expectedPoints, actualPoints, "Points were not calculated correctly");
    }

    @Test
    public void testCalculatePointsRound5v2() {
        // Set up test data
        Rule rule = Server.rulesForRounds.get(5);
        HashMap<Integer, String> cardsInGame = new HashMap<>();
        cardsInGame.put(1, "5_of_hearts.png");
        cardsInGame.put(2, "queen_of_hearts.png");
        cardsInGame.put(3, "6_of_hearts.png");
        cardsInGame.put(4, "ace_of_clubs.png");
        int lewaNumber = 1;

        // Call the method to be tested
        int actualPoints = GameLogic.calculatePoints(rule, cardsInGame, lewaNumber);

        // Perform assertions to verify the correctness of the method
        int expectedPoints = 0;
        assertEquals(expectedPoints, actualPoints, "Points were not calculated correctly");
    }

    @Test
    public void testCalculatePointsRound6v1() {
        // Set up test data
        Rule rule = Server.rulesForRounds.get(6);
        HashMap<Integer, String> cardsInGame = new HashMap<>();
        cardsInGame.put(1, "5_of_hearts.png");
        cardsInGame.put(2, "queen_of_hearts.png");
        cardsInGame.put(3, "6_of_hearts.png");
        cardsInGame.put(4, "ace_of_clubs.png");
        int lewaNumber = 1;

        // Call the method to be tested
        int actualPoints = GameLogic.calculatePoints(rule, cardsInGame, lewaNumber);

        // Perform assertions to verify the correctness of the method
        int expectedPoints = 0;
        assertEquals(expectedPoints, actualPoints, "Points were not calculated correctly");
    }

    @Test
    public void testCalculatePointsRound6v2() {
        // Set up test data
        Rule rule = Server.rulesForRounds.get(6);
        HashMap<Integer, String> cardsInGame = new HashMap<>();
        cardsInGame.put(1, "5_of_hearts.png");
        cardsInGame.put(2, "queen_of_hearts.png");
        cardsInGame.put(3, "6_of_hearts.png");
        cardsInGame.put(4, "ace_of_clubs.png");
        int lewaNumber = 7;

        // Call the method to be tested
        int actualPoints = GameLogic.calculatePoints(rule, cardsInGame, lewaNumber);

        // Perform assertions to verify the correctness of the method
        int expectedPoints = -75;
        assertEquals(expectedPoints, actualPoints, "Points were not calculated correctly");
    }

    @Test
    public void testCalculatePointsRound6v3() {
        // Set up test data
        Rule rule = Server.rulesForRounds.get(6);
        HashMap<Integer, String> cardsInGame = new HashMap<>();
        cardsInGame.put(1, "5_of_hearts.png");
        cardsInGame.put(2, "queen_of_hearts.png");
        cardsInGame.put(3, "6_of_hearts.png");
        cardsInGame.put(4, "ace_of_clubs.png");
        int lewaNumber = 13;

        // Call the method to be tested
        int actualPoints = GameLogic.calculatePoints(rule, cardsInGame, lewaNumber);

        // Perform assertions to verify the correctness of the method
        int expectedPoints = -75;
        assertEquals(expectedPoints, actualPoints, "Points were not calculated correctly");
    }

    @Test
    public void testCalculatePointsRound6v4() {
        // Set up test data
        Rule rule = Server.rulesForRounds.get(6);
        HashMap<Integer, String> cardsInGame = new HashMap<>();
        cardsInGame.put(1, "5_of_hearts.png");
        cardsInGame.put(2, "queen_of_hearts.png");
        cardsInGame.put(3, "6_of_hearts.png");
        cardsInGame.put(4, "ace_of_clubs.png");
        int lewaNumber = 12;

        // Call the method to be tested
        int actualPoints = GameLogic.calculatePoints(rule, cardsInGame, lewaNumber);

        // Perform assertions to verify the correctness of the method
        int expectedPoints = 0;
        assertEquals(expectedPoints, actualPoints, "Points were not calculated correctly");
    }
}

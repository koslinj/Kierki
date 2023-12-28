package koslin.jan.projekt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static koslin.jan.projekt.server.Server.NUMBER_OF_PLAYERS;

public class Deck {
    public static List<String> cards = Arrays.asList(
            "2_of_clubs.png", "2_of_diamonds.png", "2_of_hearts.png", "2_of_spades.png",
            "3_of_clubs.png", "3_of_diamonds.png", "3_of_hearts.png", "3_of_spades.png",
            "4_of_clubs.png", "4_of_diamonds.png", "4_of_hearts.png", "4_of_spades.png",
            "5_of_clubs.png", "5_of_diamonds.png", "5_of_hearts.png", "5_of_spades.png",
            "6_of_clubs.png", "6_of_diamonds.png", "6_of_hearts.png", "6_of_spades.png",
            "7_of_clubs.png", "7_of_diamonds.png", "7_of_hearts.png", "7_of_spades.png",
            "8_of_clubs.png", "8_of_diamonds.png", "8_of_hearts.png", "8_of_spades.png",
            "9_of_clubs.png", "9_of_diamonds.png", "9_of_hearts.png", "9_of_spades.png",
            "10_of_clubs.png", "10_of_diamonds.png", "10_of_hearts.png", "10_of_spades.png",
            "ace_of_clubs.png", "ace_of_diamonds.png", "ace_of_hearts.png", "ace_of_spades.png",
            "jack_of_clubs.png", "jack_of_diamonds.png", "jack_of_hearts.png", "jack_of_spades.png",
            "king_of_clubs.png", "king_of_diamonds.png", "king_of_hearts.png", "king_of_spades.png",
            "queen_of_clubs.png", "queen_of_diamonds.png", "queen_of_hearts.png", "queen_of_spades.png"
    );

    public static List<List<String>> divideIntoPortions(List<String> list) {
        List<String> copy = new ArrayList<>(list);
        Collections.shuffle(copy);

        int size = copy.size() / NUMBER_OF_PLAYERS;

        List<List<String>> portions = new ArrayList<>();
        int index = 0;

        for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
            List<String> portion = new ArrayList<>(copy.subList(index, index + size));
            portions.add(portion);
            index += size;
        }

        return portions;
    }
}
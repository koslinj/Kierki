package koslin.jan.projekt.server;

import koslin.jan.projekt.Deck;
import koslin.jan.projekt.Rule;

import java.util.HashMap;

/**
 * The {@code GameLogic} class contains methods related to game logic, such as calculating points based on rules
 * and determining the validity of a card's color.
 */
public class GameLogic {

    /**
     * Calculates the points based on the specified rule, cards played in the game, and the current lewa number.
     *
     * @param rule           The rule for which points are calculated.
     * @param cardsInGame    The cards played in the current game.
     * @param lewaNumber     The current lewa number.
     * @return The calculated points based on the rule and cards played.
     */
    public static int calculatePoints(Rule rule, HashMap<Integer, String> cardsInGame, int lewaNumber) {
        int points = 0;
        if(rule.isRegardsEveryCard()){
            if (rule.getColor().equals("")){
                for (String card : cardsInGame.values()){
                    for (String type: rule.getType()) {
                        if(card.contains(type)) points += rule.getPoints();
                    }
                }
            } else {
                for (String card : cardsInGame.values()) {
                    if(rule.getType().size() == 0){
                        if(card.contains(rule.getColor())) points += rule.getPoints();
                    } else {
                        for (String type: rule.getType()) {
                            if(card.contains(type) && card.contains(rule.getColor())) points += rule.getPoints();
                        }
                    }
                }
            }
        } else {
            if(rule.getLewas().size() == 0) points += rule.getPoints();
            else {
                for (int lewa : rule.getLewas()){
                    if(lewa == lewaNumber) points += rule.getPoints();
                }
            }
        }

        return points;
    }

    /**
     * Determines if the color of the card is valid based on the specified conditions.
     *
     * @param card         The card for which the color validity is checked.
     * @param hideHearts   Indicates whether hearts should be hidden.
     * @param actualColor  The actual color set for the game.
     * @param hasActualColor  Indicates whether there is an actual color set.
     * @return {@code true} if the card's color is valid; otherwise, {@code false}.
     */
    public static boolean isValidColor(String card, boolean hideHearts, String actualColor, boolean hasActualColor) {
        String cardColor = Deck.colorFromCard(card);
        return (!hasActualColor || cardColor.equals(actualColor)) && !(hideHearts && cardColor.equals("hearts"));
    }
}

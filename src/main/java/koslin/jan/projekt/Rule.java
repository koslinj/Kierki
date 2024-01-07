package koslin.jan.projekt;

import java.util.List;

/**
 * The Rule class represents a scoring rule for specific round in a multiplayer card game.
 * It defines conditions for scoring points based on card attributes, color, and lewa numbers.
 */
public class Rule {
    int points;
    String color;
    List<String> type;
    boolean regardsEveryCard;
    List<Integer> lewas;

    /**
     * Constructs a Rule object for scoring points based on color and card type.
     *
     * @param points The points awarded for this rule.
     * @param color  The color of card for this rule.
     * @param type   The list of card types for this rule.
     */
    public Rule(int points, String color, List<String> type) {
        this.points = points;
        this.color = color;
        this.type = type;
        this.regardsEveryCard = true;
        this.lewas = null;
    }

    public int getPoints() {
        return points;
    }

    public String getColor() {
        return color;
    }

    public List<String> getType() {
        return type;
    }

    /**
     * Checks if the rule regards every card.
     * If rule regards every card, that means points are summed up for every card that matches the rule.
     * Otherwise, points are given once (for the Lewa)
     *
     * @return True if the rule regards every card, false otherwise.
     */
    public boolean isRegardsEveryCard() {
        return regardsEveryCard;
    }

    public void setRegardsEveryCard(boolean regardsEveryCard) {
        this.regardsEveryCard = regardsEveryCard;
    }

    public List<Integer> getLewas() {
        return lewas;
    }

    public void setLewas(List<Integer> lewas) {
        this.lewas = lewas;
    }
}

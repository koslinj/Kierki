package koslin.jan.projekt;

import java.util.List;

public class Rule {
    int points;
    String color;
    List<String> type;
    boolean regardsEveryCard;
    List<Integer> lewas;

    public Rule(int points, List<Integer> lewas) {
        this.points = points;
        this.color = null;
        this.type = null;
        this.regardsEveryCard = false;
        this.lewas = lewas;
    }

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

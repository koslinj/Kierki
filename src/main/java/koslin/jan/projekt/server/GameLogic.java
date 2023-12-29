package koslin.jan.projekt.server;

import koslin.jan.projekt.Rule;

import java.util.HashMap;

public class GameLogic {
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
}

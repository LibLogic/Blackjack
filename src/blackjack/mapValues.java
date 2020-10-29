package blackjack;

import java.util.ArrayList;
import java.util.Map;

import card_game.Card;

public interface mapValues {

	Map<Card, Integer> mapCardValues(ArrayList<Card> deck);
}

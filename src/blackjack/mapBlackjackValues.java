package blackjack;

import java.util.ArrayList;
import java.util.Map;

import card_game.Card;

public interface mapBlackjackValues {

	Map<Card, Integer> mapCardValues(ArrayList<Card> deck);
}

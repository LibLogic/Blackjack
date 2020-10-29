package blackjack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import card_game.Card;
import card_game.Deck;

public class BlackjackDeck extends Deck implements mapBlackjackValues {
	
	private static Map<Card, Integer> blackjackMap = new HashMap<>();

	public static void init() {
		BlackjackDeck blackjackDeck = new BlackjackDeck();
		blackjackDeck.mapCardValues(Deck.getDeck());
		shuffleDeck();
	}

	public Map<Card, Integer> mapCardValues(ArrayList<Card> deck) {
		int val = 2;
		for(int i = 0; i < 52; i++) {
			int modifiedValue = val;
			if(val > 9 && val < 14) {
				modifiedValue = 10;
			}
			if(val == 14) {
				modifiedValue = 11;
			}
			blackjackMap.put(deck.get(i), modifiedValue);
			if(val > 13) {
				val = 1;
			}
			val = (val + 1) % 15;
		}
		return blackjackMap;
	}
	
	public static int lookupCardValues(Card card) {
		return blackjackMap.get(card);
	}
}

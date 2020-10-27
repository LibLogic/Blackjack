package blackjack;

import java.util.ArrayList;

public class Hand implements DrawHand {
	
	ArrayList<Card> deck = Deck.getDeck();
	ArrayList<Card> hand = new ArrayList<>();
	
	public ArrayList<Card> drawHand(int handSize) {
		for(int i = 0; i < handSize; i++) {
			int rand = (int)(Math.random() * deck.size());
			hand.add(deck.remove(rand));
		}
		return hand;
	}
}

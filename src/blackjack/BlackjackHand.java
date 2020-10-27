package blackjack;

import java.util.ArrayList;

public class BlackjackHand extends Hand {
	
	public ArrayList<Card> drawHand(int handSize) {
		for(int i = 0; i < handSize; i++) {
			int rand = (int)(Math.random() * deck.size());
			hand.add(deck.remove(rand));
		}
		return hand;
	}

}

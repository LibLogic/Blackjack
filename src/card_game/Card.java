package card_game;

import blackjack.CardNames;
import blackjack.CardSuits;

public class Card {
	
	private CardSuits suit;
	private CardNames cardName;
	
	public Card(CardSuits suit, CardNames cardName) {
		this.suit = suit;
		this.cardName = cardName;
	}
	
	public String toString() {
		return  this.cardName.toString() + " OF " + this.suit.toString();
	}
}

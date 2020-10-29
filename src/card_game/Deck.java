package card_game;

import java.util.ArrayList;
import java.util.Collections;

import blackjack.CardNames;
import blackjack.CardSuits;

public class Deck {

	private static ArrayList<Card> deck = new ArrayList<>();
	
	protected Deck() {
		for(CardSuits suit : CardSuits.values()) {
			for(CardNames cardValue : CardNames.values()) {
				deck.add(new Card(suit, cardValue));
			}
		}
	}
	
	protected static void shuffleDeck() {
		Collections.shuffle(deck);;
	}
	
	public static ArrayList<Card> getDeck() {
		return deck;
	}

	public String toString() {
		String cardList = "";
		for(Card aCard : deck) {
			cardList += aCard.toString().toLowerCase() + "\n";
		}
		return cardList;
	}

}

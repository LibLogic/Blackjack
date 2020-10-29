package card_game;

import java.util.ArrayList;

import blackjack.CardNames;
import blackjack.CardSuits;

public class Deck {

	private static ArrayList<Card> deck = new ArrayList<>();
	
	public static void createDeck() {
		for(CardSuits suit : CardSuits.values()) {
			for(CardNames cardValue : CardNames.values()) {
				deck.add(new Card(suit, cardValue));
			}
		}
	}
	
	public static void shuffleDeck() {
		ArrayList<Card> temp = new ArrayList<>();
		int deckSize = deck.size();
		for (int i = 0; i < deckSize; i++) {
			int rand = (int)(Math.random() * deck.size());
			temp.add(deck.get(rand));
			deck.remove(rand);
		}
		deck = temp;
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

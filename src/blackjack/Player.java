package blackjack;

import java.util.ArrayList;

public class Player {
	
	private int id;
	private String name;
	private double availableCash = 100.00;
	private ArrayList<Card> hand = new ArrayList<>();
	
	public Player () {}
	
	public Player (int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Player (int id, String name, double availableCash) {
		this.id = id;
		this.name = name;
		this.availableCash = availableCash;
	}
	
	public void setAvailableCash(double availableCash){
		this.availableCash = availableCash;
	}
	
	public ArrayList<Card> getHand() {
		return hand;
	}

	public String getName() {
		return name;
	}
	
	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}
	
	public void printHand() {
		System.out.println(hand);
	}

	public String toString() {
		String result = String.format("Player %d \"%s\" has %.2f in available cash.", this.id, this.name, this.availableCash);
		return result;
	}
}

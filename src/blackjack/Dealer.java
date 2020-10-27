package blackjack;

import java.util.ArrayList;

public class Dealer extends Player {

	private String name;
	private double availableCash = 500.00;
	private ArrayList<Card> hand = new ArrayList<>();
	
	public Dealer (String name) {
		this.name = name;
	}
	
	public Dealer(String name, double availableCash) {
		this.name = name;
		this.availableCash = availableCash;
	}
	
	public ArrayList<Card> getHand() {
		return hand;
	}

	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}
	
	public String toString() {
		String dealerNameString = " ";
		if(!this.name.equals("")) {
			dealerNameString = " \"" + this.name + "\" ";
		}
		String result = String.format("The dealer%shas %.2f in available cash.", dealerNameString, this.availableCash);
		return result;
	}
}

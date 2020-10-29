package blackjack;

import java.util.ArrayList;

import card_game.Card;

public class Dealer extends Player {

	private String name;
	private int score;
	private double funds = 500.00;
	private ArrayList<Card> hand = new ArrayList<>();
	
	public Dealer (String name) {
		this.name = name;
	}
	
	public Dealer(String name, double funds) {
		this.name = name;
		this.funds = funds;
	}
	
	
	public double getFunds() {
		return funds;
	}

	public void setFunds(double funds) {
		this.funds = funds;
	}

	public Object getId() {
		return null;
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<Card> getHand() {
		return hand;
	}
	
	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public String toString() {
		String dealerNameString = " ";
		if(!this.name.equals("")) {
			dealerNameString = " \"" + this.name + "\" ";
		}
		String result = String.format("The dealer%shas %.2f in available cash.", dealerNameString, this.funds);
		return result;
	}
}

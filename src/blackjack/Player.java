package blackjack;

import java.util.ArrayList;

import card_game.Card;

public class Player {
	
	private int id;
	private String name;
	private double bet;
	private int score;
	private double funds = 100.00;
	private ArrayList<Card> hand = new ArrayList<>();
	
	public Player () {}
	
	public Player (int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Player (int id, String name, double funds) {
		this.id = id;
		this.name = name;
		this.funds = funds;
	}
	
	public double getFunds(){
		return funds;
	}
	
	public void setFunds(double funds){
		this.funds = funds;
	}
	
	public Object getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public double getBet() {
		return bet;
	}

	public void setBet(double bet) {
		this.bet = bet;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}
	
	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}
	
	public void clearHand() {
		this.hand.clear();
	}
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void printHand() {
		System.out.println(hand);
	}

	public String toString() {
		String result = String.format("Player %d \"%s\" has %.2f in available cash.", this.id, this.name, this.funds);
		return result;
	}


}

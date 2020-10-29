package blackjack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import card_game.Card;
import card_game.DealsHands;
import card_game.Deck;
import card_game.Hand;
import card_game.TakesBets;

public class BlackjackGame implements DealsHands, TakesBets {

	private static Map<Integer, Player> players = new HashMap<>();
	private static Player dealer = null;
	ArrayList<Card> deck = Deck.getDeck();
	
	public static void init(Scanner sc) {

		BlackjackDeck.init();
		System.out.println("Game Setup");
		
		System.out.print("\nEnter the dealers' first name\nor <ENTER> for an anonymous dealer: ");
		String dealerName = sc.nextLine();
		System.out.print("\nEnter the dealers' initial cash amount\nor <ENTER> for 500.00 default: ");

		String cash1 = sc.nextLine();
		if(cash1.equals("")) {
			dealer = new Dealer(dealerName);
		} else { 
			Double dealerCash = Double.parseDouble(cash1);
			dealer = new Dealer(dealerName, dealerCash);
		}

		int id = 1;
		String name;
		do {
			System.out.print("\nEnter your new players' first name\nor <ENTER> if finished entering players: ");
			name = sc.nextLine();
			if(name.equals("")) {
				break;
			}
			System.out.print("\nEnter the players' initial cash amount\nor <ENTER> for 100.00 default: ");
			String cash2 = sc.nextLine();
			if(cash2.equals("")) {
				players.put(id, new Player(id, name));
				id++;
			} else {
				Double playerCash = Double.parseDouble(cash2);
				players.put(id, new Player(id, name, playerCash));
				id++;
			}
		} while(!name.equals(""));
		
		System.out.println("\n---------------------------------------------------");
		System.out.println("Welcome to BlackJack");
		System.out.println("\n" + dealer.toString());
		System.out.println(BlackjackGame.printGame());
		System.out.println("---------------------------------------------------");
		System.out.println("Taking Bets............\n");
	}

	@Override
	public void takeBets(Scanner sc) {
		for(Player p : players.values()) {
			System.out.print(p.getName() + " place your bet. ");
			double betAmount = Double.parseDouble(sc.nextLine());
			p.setBet(betAmount);
		}
		System.out.println();
	}
	
	@Override
	public void dealHands() {
		for(Player p : players.values()) {
			Hand hand = new BlackjackHand();
			ArrayList<Card> currentHand = hand.drawHand(2);
			p.setHand(currentHand);
		}
		Hand hand = new BlackjackHand();
		ArrayList<Card> currentHand = hand.drawHand(2);
		dealer.setHand(currentHand);
	}
	
	public void runGame(Scanner sc) {
		for(Player currentPlayer : players.values()) {
			runRound(currentPlayer, sc);
		}
		runRound(dealer, sc);
	}

	private void runRound(Player currentPlayer, Scanner sc) {
		int score = 0;
		int aceCount = 0;
		ArrayList<Card> currentHand = currentPlayer.getHand();
		String currentPlayerName = "Dealer";
		
		for(Card card : currentHand) {
			aceCount = 0;
			int cardValue = BlackjackDeck.lookupCardValues(card);
			if (cardValue == 11) {
				aceCount++;
			}
			score += cardValue;
		}
		
		if(!(currentPlayer.getId() == null)) {
			currentPlayerName = currentPlayer.getName();
			System.out.println("Dealers' hand -->\t[HIDDEN-CARD, " + dealer.getHand().get(1) + "]");

		}
		if(currentHand.size() == 2 && score == 21) {
			System.out.println(currentPlayerName + " got BlackJack!\t" + currentHand + "\n");
			double amount = currentPlayer.getBet();
			double playerFunds = currentPlayer.getFunds();
			double dealerFunds = dealer.getFunds();
			dealerFunds -= (amount);
			playerFunds += (amount);
			dealer.setFunds(dealerFunds);
			currentPlayer.setFunds(playerFunds);
			currentPlayer.setScore(21);
		} else {
			System.out.println(currentPlayerName + "s' hand -->\t\t" + currentHand + " --> " + score);
		}
		
		if(currentPlayer.getId() == null) {
			dealer.setScore(score);
			while(score < 17) {
				int rand = (int)(Math.random() * deck.size());
				Card card = deck.remove(rand);
				currentHand.add(card);
				int cardValue = BlackjackDeck.lookupCardValues(card);
				if(cardValue == 11 && score + cardValue > 21) {
					cardValue = 1;
				}
				score += cardValue;
				System.out.println("Hit -->\t\t\t\t" + currentHand + " --> " + score);
				dealer.setScore(score);
				
				if(score > 21) {
					System.out.println("\n ***DEALER BUSTED***");
					dealer.setScore(0);
					break;
				} 
			}

			for(Player p : players.values()) {
				if(p.getScore() > 0) {
					double amount = p.getBet();
					double playerFunds = p.getFunds();
					double dealerFunds = dealer.getFunds();
					if(dealer.getScore() >= p.getScore()) {
						dealerFunds += amount;
						playerFunds -= amount;
						dealer.setFunds(dealerFunds);
						p.setFunds(playerFunds);
					} else {
						dealerFunds -= amount;
						playerFunds += amount;
						dealer.setFunds(dealerFunds);
						p.setFunds(playerFunds);
					}
				}
			}
			System.out.println("\n---------------------------------------------------");
			System.out.print("Dealers' score:\t\t" + dealer.getScore());
			System.out.printf("\tAvailable funds:\t%.2f", dealer.getFunds());
			for(Player p : players.values()) {
				System.out.print("\n" + p.getName() + "s' score:\t\t" + p.getScore());
				System.out.printf("\tAvailable funds:\t%.2f", p.getFunds());
			}
			for(Player p : players.values()) {
				p.clearHand();
			}
			System.out.print("\n\nWould you like to play another round? (y)es or (n)o: ");
			String answer = sc.nextLine();
			if (answer.equals("y")) {
				System.out.println("\n\n-------- New Round -------\n");
				takeBets(sc);
				dealHands();
				currentPlayer = players.get(1);
				runGame(sc);
			} else {
				System.out.println("\nThank you for playing,\nGoodBye");
				System.exit(0);
			}
		}

		while(score < 21) {
			System.out.print("\n(H)it or (S)tay? ");
			String answer = sc.nextLine();
			if(answer.equals("h")) {
				int rand = (int)(Math.random() * deck.size());
				Card card = deck.remove(rand);
				currentHand.add(card);
				int cardValue = BlackjackDeck.lookupCardValues(card);
				if(cardValue == 11 && score + cardValue > 21) {
					cardValue = 1;
				}
				score += cardValue;
				System.out.println("Dealers' hand --> \t[HIDDEN-CARD, " + dealer.getHand().get(0) + "]");
				System.out.println(currentPlayerName + "s' hand -->\t\t" + currentHand + " --> " + score);
				
				if(score > 21) {
					System.out.println("\n ***BUSTED***\n");
					double dealerFunds = dealer.getFunds();
					double amount = currentPlayer.getBet();
					double playerFunds = currentPlayer.getFunds();
					dealerFunds += amount;
					playerFunds -= amount;
					currentPlayer.setFunds(playerFunds);
					dealer.setFunds(dealerFunds);
					currentPlayer.setScore(0);
					break;
				}
			}
			
			if(answer.equals("s") || score == 21) {
				currentPlayer.setScore(score);
				System.out.println("===============================================================");
				System.out.println(currentPlayer.getName() +"s' Final Score: " + score);
				System.out.println("===============================================================\n");
				score = 0;
				break;
			}
		}	
	}

	public static String printGame() {
		String playersString = "";
		for(Integer p : players.keySet()) {
			Player aPlayer = players.get(p);
			playersString += aPlayer.toString()  + "\n";
		}
		return playersString;
	}

}
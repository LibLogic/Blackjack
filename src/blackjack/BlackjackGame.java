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

		BlackjackDeck blackjackDeck = new BlackjackDeck();
		blackjackDeck.deckInit();
		System.out.println("Game Setup");
		
		dealerInput(sc);
		playerInput(sc);

		System.out.println("\n---------------------------------------------------");
		System.out.println("Welcome to BlackJack");
		System.out.println(BlackjackGame.printGameStats());
		System.out.println("---------------------------------------------------");
		System.out.println("Taking Bets............\n");
	}
	
	
	public static void dealerInput(Scanner sc) {
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
	}
	
	public static void playerInput(Scanner sc) {
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

	@Override
	public void takeBets(Scanner sc) {
		for(Player p : players.values()) {			
			System.out.printf("%-26s", p.getName() + " place your bet.");
			while(true) {
				try {
				double betAmount = Double.parseDouble(sc.nextLine());
				p.setBet(betAmount);
				break;
				} catch(NumberFormatException e) {
					System.out.print("Please enter a bet amount: ");
				}
			}
		}
		System.out.println();
	}
	
	public void doRounds(Scanner sc) {
		for(Player currentPlayer : players.values()) {
			runRound(currentPlayer, sc);
		}
		runRound(dealer, sc);
	}

	private void runRound(Player currentPlayer, Scanner sc) {

		int score = 0;
		Map<String, Integer> acesSeen = new HashMap<>();
		ArrayList<Card> currentHand = currentPlayer.getHand();
		String currentPlayerName = "Dealer";
		for(Card card : currentHand) {
			int cardValue = BlackjackDeck.lookupCardValues(card);
			score += cardValue;
			
			if(score > 21) {
				score = checkAces(currentHand, acesSeen, score);
			}
		}
		
		if(!(currentPlayer.getId() == null)) {
			currentPlayerName = currentPlayer.getName();
			System.out.printf("%-28s%s%n", "Dealers' hand -->", "[HIDDEN-CARD, " + dealer.getHand().get(1) + "]");	
		}
		if(currentHand.size() == 2 && score == 21) {
			System.out.printf("%-28s%s%n%n", currentPlayerName + " got Blackjack!", currentHand + " --> Blackjack");			
			double amount = currentPlayer.getBet();
			double playerFunds = currentPlayer.getFunds();
			double dealerFunds = dealer.getFunds();
			dealerFunds -= (amount);
			playerFunds += (amount);
			dealer.setFunds(dealerFunds);
			currentPlayer.setFunds(playerFunds);
			currentPlayer.setScore(21);
		} else {
			System.out.printf("%-28s%s%n" , currentPlayerName + "s' hand -->", currentHand + " --> " + score);
		}
		
		if(currentPlayer.getId() == null) {
			dealer.setScore(score);
			while(score < 17) {
				int rand = (int)(Math.random() * deck.size());
				Card card = deck.remove(rand);
				currentHand.add(card);
				int cardValue = BlackjackDeck.lookupCardValues(card);
				score += cardValue;
				if(score > 21) {
					score = checkAces(currentHand, acesSeen, score);
				}				
				System.out.printf("%-28s%s%n", "Hit -->", currentHand + " --> " + score);
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
			System.out.printf("%-28s%-8d", "Dealers' score: ", dealer.getScore());
			System.out.printf("%s%.2f%n", "Available funds: ", dealer.getFunds());		
			
			for(Player p : players.values()) {			
				System.out.printf("%-28s%-8d", p.getName() + "s' score: ", p.getScore());
				System.out.printf("%s%.2f%n", "Available funds: ", p.getFunds());			
			}
			for(Player p : players.values()) {
				p.clearHand();
			}
			System.out.print("\n\nWould you like to play another round? (Y)es or (N)o: ");
			String answer = sc.nextLine();
			if (answer.equals("y")) {
				System.out.println();
				if(deck.size() < 26) {
					deck.clear();
					BlackjackDeck blackjackDeck = new BlackjackDeck();
					blackjackDeck.deckInit();
					System.out.println("\nStarting with a fresh deck.");
				}
				System.out.println("\n-------- New Round -------\n");
				takeBets(sc);
				dealHands();
				doRounds(sc);
			} else {
				System.out.println("\nThank you for playing,\nGoodBye");
				System.exit(0);
			}
		}

		while(score < 21) {
			System.out.print("\n(H)it or (S)tay? ");
			String answer = sc.nextLine();
			System.out.println();
			if(answer.equals("h")) {
				int rand = (int)(Math.random() * deck.size());
				Card card = deck.remove(rand);
				currentHand.add(card);
				int cardValue = BlackjackDeck.lookupCardValues(card);
				score += cardValue;
				
				if(score > 21) {
					score = checkAces(currentHand, acesSeen, score);
				}			
				System.out.printf("%-28s%s%n", "Dealers' hand -->", "[HIDDEN-CARD, " + dealer.getHand().get(1) + "]");	
				System.out.printf("%-28s%s%n", currentPlayerName + "s' hand -->", currentHand + " --> " + score);
			
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
	
	public int checkAces(ArrayList<Card> currentHand, Map<String, Integer> acesSeen, int score
			) {
		for(Card c : currentHand) {
			if(BlackjackDeck.lookupCardValues(c) == 11) {
				String ace = c.toString();
				if(!(acesSeen.containsKey(ace)) && score + 11 != 21) {
					acesSeen.put(ace, 0);
					score -= 10;
				}
			}
		}
		return score;
	}

	public static String printGameStats() {
		System.out.println("\n" + dealer.toString());
		String playersString = "";
		for(Integer p : players.keySet()) {
			Player aPlayer = players.get(p);
			playersString += aPlayer.toString()  + "\n";
		}
		return playersString;
	}
}
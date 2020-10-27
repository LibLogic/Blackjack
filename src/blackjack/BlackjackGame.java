package blackjack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BlackjackGame implements DealHands {

	private static Map<Integer, Player> players = new HashMap<>();
	private static Player dealer = null;

	
	public static void init() {

		Deck.init();
		System.out.println("Game Setup");
		Scanner sc = new Scanner(System.in);
		
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
		System.out.println("Starting New Game............");
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
	
	public void runGame() {
		Scanner sc = new Scanner(System.in);

		for(Player currentPlayer : players.values()) {
			ArrayList<Card> deck = Deck.getDeck();
			ArrayList<Card> currentHand = currentPlayer.getHand();
			System.out.println("\n" + currentPlayer.getName() + "s' hand --> " + currentHand);
			
			int count = 0;
			while(count < 3) {
				System.out.print("\n(H)it or (S)tay? ");
				String answer = sc.nextLine();
				if(answer.equals("h")) {
					int rand = (int)(Math.random() * deck.size());
					currentHand.add(deck.remove(rand));
					System.out.println("\n" + currentHand);
					count++;
				}
				if(answer.equals("s")) {
					break;
				}
			}
		}
		System.out.println("\nDealers' hand --> " + dealer.getHand());
sc.close();
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
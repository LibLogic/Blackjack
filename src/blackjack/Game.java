package blackjack;

import java.util.Scanner;

public class Game {
	
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		BlackjackGame.init(sc);
		BlackjackGame game = new BlackjackGame();
		game.dealHands();
		game.takeBets(sc);
		game.runGame(sc);
		sc.close();
	}

}

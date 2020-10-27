package blackjack;

public class Game {
	
	public static void main(String[] args) {
		BlackjackGame.init();
		BlackjackGame game = new BlackjackGame();
		game.dealHands();
		game.runGame();
	}

}

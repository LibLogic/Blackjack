package blackjack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BlackjackMapping extends Deck implements CardMapping {

	Map<Card, Integer> blackjackMap = new HashMap<>();
	@Override
	public void mapCardValues(ArrayList<Card> deck) {
		System.out.println(deck);
		int val = 2;
		for(int i = 0; i < 52; i++) {
			System.out.println(val);
			blackjackMap.put(deck.get(i), val);
			if(val > 13) {
				val = 1;
			}
			val = (val + 1) % 15;
			if(val == 14) {
				val = 1;
			}
		}


		System.out.println(blackjackMap);
	}

	@Override
	public void lookupCardValues() {
		// TODO Auto-generated method stub
		
	}

}

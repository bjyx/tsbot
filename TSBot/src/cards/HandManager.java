package cards;

import java.util.List;
import cards.CardList;

public class HandManager {
	private static List<Card> USAHand;
	private static List<Card> SSRHand;
	private static List<Card> Deck;
	private static List<Card> Discard;
	private static List<Card> Removed;
	private static List<Integer> Effects;
	
	public static void addToDeck(int era) {
		for (Card c : CardList.cardList) {
			if (c.getEra()==era) Deck.add(c);
		}
	}
}

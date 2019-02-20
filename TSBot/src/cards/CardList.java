package cards;

import java.util.List;

import events.*;

public class CardList {
	public static List<Card> cardList;
	private static int counter=0;
	
	public static void initialize() {
		CardList.addCard(null); //just to occupy the 0 slot
		CardList.addCard(new AsiaScoring());
	}
	
	public static void addCard(Card card) {
		cardList.add(card);
		counter++;
	}
	
	public static Card getCard(int i) {
		return cardList.get(i);
	}
	
	public static int numberOfCards() {
		return counter-1; // counter should be @ 111
	}
}

package cards;

import java.util.ArrayList;

import events.*;

public class CardList {
	public static ArrayList<Card> cardList;
	private static int counter=0;
	
	public static void initialize() {
		CardList.addCard(null); //just to occupy the 0 slot so I don't get peeved enough to use a map
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

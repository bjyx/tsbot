package cards;

import java.util.List;

public class CardList {
	public static List<Card> cardList;
	private static int counter=0;
	
	public static List<Card> addCard(Card card) {
		cardList.add(card);
		counter++;
		
		return cardList;
	} 
	
	
	
	public static int numberOfCards() {
		return counter-1; // counter should be @ 111
	}
}

package cards;

import java.util.List;
import java.util.Random;

import cards.CardList;
import events.Card;
import game.GameData;

public class HandManager {
	private static List<Card> USAHand;
	private static List<Card> SSRHand;
	private static List<Card> Deck;
	private static List<Card> Discard;
	private static List<Card> Removed;
	private static List<Integer> Effects;
	private static boolean China;
	
	public static void addToDeck(int era) {
		for (Card c : CardList.cardList) {
			if (c.getEra()==era&&!c.getId().equals("006")) Deck.add(c);
		}
	}
	
	public static void deal() {
		Random random = new Random();
		int handsize = (GameData.getEra()==0)?8:9;
		while (handsize > SSRHand.size()||handsize > USAHand.size()) {
			if (handsize > SSRHand.size()&&!Deck.isEmpty()) {
				SSRHand.add(Deck.remove(random.nextInt(Deck.size())));
			}
			if (handsize > USAHand.size()&&!Deck.isEmpty()) {
				USAHand.add(Deck.remove(random.nextInt(Deck.size())));
			}
			if(Deck.isEmpty()) {
				Deck.addAll(Discard);
				Discard.clear();
			}
		}
	}
	
	public static void play() {
		
	}
}

package cards;

import java.util.ArrayList;

import events.*;
/**
 * Implements a list of the cards used in the game.
 * @author [REDACTED]
 *
 */
public class CardList {
	/**
	 * Accesses the list itself.
	 */
	public static ArrayList<Card> cardList;
	/**
	 * The number of cards in the cardlist. There should be 111 after initiation.
	 */
	private static int counter=0;
	/**
	 * Initializes the list with all 110 Cards and a null card.
	 */
	public static void initialize() {
		CardList.addCard(null); //just to occupy the 0 slot so I don't get peeved enough to use a map
		CardList.addCard(new AsiaScoring());
	}
	/**
	 * Adds the Card to the list.
	 * @param card is the card to add - these must extend the abstract 'card' class.
	 */
	public static void addCard(Card card) {
		cardList.add(card);
		counter++;
	}
	/**
	 * Obtains the card at a position in the list.
	 * @param i is the card requested.
	 * @return the Card at index i in the cardList.
	 */
	public static Card getCard(int i) {
		return cardList.get(i);
	}
	/**
	 * A check to see if the number of cards is sufficient to start a game with.
	 * @return counter-1, representing all the cards in the list sans the null card.
	 */
	public static int numberOfCards() {
		return counter-1; // counter should be @ 111
	}
}

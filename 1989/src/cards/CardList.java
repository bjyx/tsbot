package cards;

import java.util.ArrayList;

import events.*;
import game.GameData;
/**
 * Implements a list of the cards used in the game.
 * @author adalbert
 *
 */
public class CardList {
	/**
	 * Accesses the list itself.
	 */
	public static ArrayList<Card> cardList = new ArrayList<Card>();
	/**
	 * The number of cards in the cardlist. There should be 111 after initiation.
	 */
	private static int counter=0;
	/**
	 * Initializes the list with all cards and a null card.
	 */
	public static void initialize() {
		cardList = new ArrayList<Card>();
		CardList.addCard(new Placeholder()); //just to occupy the 0 slot so I don't get peeved enough to use a map
		//TODO
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
		if (i<=numberOfCards()) return cardList.get(i);
		return null;
	}
	/**
	 * A check to see if the number of cards is sufficient to start a game with.
	 * @return counter-1, representing all the cards in the list sans the first null card.
	 */
	public static int numberOfCards() {
		return counter-1;
	}
}

package powerstruggle;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A Power Struggle in 1989.
 * @author adalbert
 *
 */
public class PowerStruggle {
	/**
	 * initiative.
	 */
	public int initiative = -1;
	/**
	 * How many times have the stakes been raised?
	 */
	public int stakes = 0;
	/**
	 * Which country this takes place in.
	 */
	public int region;
	/**
	 * The failed tactic that may not be played for the rest of this power struggle. There is only one Tactic Fails card.
	 */
	public int failed = -1;
	/**
	 * The current tactic.
	 */
	public int tactic;
	/**
	 * The deck of Power Struggle Cards.
	 */
	public ArrayList<StruggleCard> deck;
	/**
	 * 
	 */
	public static ArrayList<StruggleCard> DemHand;
	/**
	 * 
	 */
	public static ArrayList<StruggleCard> ComHand;
	/**
	 * 
	 */
	public int thresh;
	
	public void initializeDeck() {
		for (int i=0; i<6; i++) deck.add(new StruggleCard(0,0,1));
		for (int i=0; i<2; i++) {
			deck.add(new StruggleCard(0,1,6));
			deck.add(new StruggleCard(0,1,5));
			deck.add(new StruggleCard(0,2,6));
			deck.add(new StruggleCard(0,2,5));
			deck.add(new StruggleCard(1,0,3));
			for (int j=0; j<2; j++) {
				deck.add(new StruggleCard(0,1,4));
				deck.add(new StruggleCard(0,1,3));
				deck.add(new StruggleCard(0,2,4));
				deck.add(new StruggleCard(0,2,3));
				deck.add(new StruggleCard(1,4,3));
				deck.add(new StruggleCard(1,2,3));
				deck.add(new StruggleCard(2,2*i+j,0));
			}
		}
		for (int i=0; i<3; i++) {
			deck.add(new StruggleCard(0,3,6));
			deck.add(new StruggleCard(0,3,5));
		}
		deck.add(new StruggleCard(1,5,3));
		deck.add(new StruggleCard(1,6,3));
		
	}
	
	public static boolean inHand(int sp, char c, int r) {
		//m, s, r, p; l and w use r for something different
		ArrayList<Character> suited = new ArrayList<Character>(Arrays.asList('r','s','m','p'));
		int rank, suit, type;
		if (suited.contains(c)) {
			type = 0;
			suit = suited.indexOf(c);
			rank = r;
		}
		else if (c=='l') {
			type = 1;
			suit = r;
			rank = 3;
		}
		else if (c=='w') {
			type = 2;
			suit = r;
			rank = 0;
		}
		else return false;
		if (sp==0) {
			for (StruggleCard i : DemHand) {
				if (i.equals(new StruggleCard(type, suit, rank))) return true;
			}
		}
		else {
			for (StruggleCard i : ComHand) {
				if (i.equals(new StruggleCard(type, suit, rank))) return true;
			}
		}
		return false;
	}
}

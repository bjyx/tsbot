package events;

/**
 * A class defining decisions required from a multitude of cards. 
 * @author adalbert
 *
 */
public class Decision {
	/**
	 * The card involved in this decision.
	 */
	public int card;
	/**
	 * The superpower making the decision.
	 */
	public int sp;
	
	/**
	 * Creates a decision
	 * @param s is the superpower making this decision.
	 * @param c is the card the decision is being made on.
	 */
	public Decision(int s, int c) {
		card = c;
		sp = s;
	}
}

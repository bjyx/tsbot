package powerstruggle;
/**
 * Cards for the struggle.
 * @author wes4zhang
 *
 */
public class StruggleCard {
	/**
	 * The type of card: <br>
	 * 0 - normal <br>
	 * 1 - leader <br>
	 * 2 - wild <br>
	 */ 
	public int type;
	/**
	 * The kind of card. Varies by type.
	 * <br> Normal: 
	 * <br> - 0 Rally (rank always 1)
	 * <br> - 1 Strike
	 * <br> - 2 March
	 * <br> - 3 Petition
	 * <br><br> Leader: Suit matches icon of the region (i.e.: 0 Worker, 2 Elite, 4 Intellectual, 5 Student, 6 Church
	 * <br> Wild: 0 Support Falters, 1 Support Surges, 2 Scare Tactics, 3 Tactic Fails
	 */
	public int suit;
	/**
	 * The rank of the card. A defender must roll at or above the rank of the attacker card to gain initiative.
	 */
	public int rank;
	
	public StruggleCard(int t, int s, int r) {
		type = t;
		suit = s;
		rank = r;
	}
	
	/**
	 * Equals.
	 * @param e is another card.
	 * @return a boolean value, whether e is equivalent to this card
	 */
	public boolean equals(StruggleCard e) {
		return this.type==e.type&&this.suit==e.suit&&this.rank==e.rank;
	}
}

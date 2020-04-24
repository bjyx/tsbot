package powerstruggle;

import game.GameData;
import map.MapManager;

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
	
	public String toString() {
		switch(type) {
			case 0:
				if (suit==0) return "**Rally in the Square** 1 [r1]";
				if (suit==1) return "Strike " + rank + " [s" + rank + "]";
				if (suit==2) return "March " + rank + " [m" + rank + "]";
				if (suit==3) return "__Petition__ " + rank + " [p" + rank + "]";
				break;
			case 1:
				if (suit==0) return "Worker Leader 3 [l0]";
				if (suit==2) return "Elite Leader 3 [l2]";
				if (suit==4) return "Intellectual Leader 3 [l4]";
				if (suit==5) return "Student Leader 3 [l5]";
				if (suit==6) return "Church Leader 3 [l6]";
				break;
			case 2:
				if (suit==0) return "Support Falters W (-2 opponent cards) [w0]";
				if (suit==1) return "Support Surges W (+2 cards) [w1]";
				if (suit==2) return "Scare Tactics W (-1 opponent SP) [w2]";
				if (suit==3) return "Tactic Fails W (disable suit) [w3]";
				break;
			default:
				break;
		}
		return null;
	}
	
	/**
	 * Equals.
	 * @param e is another card.
	 * @return a boolean value, whether e is equivalent to this card
	 */
	public boolean equals(StruggleCard e) {
		return this.type==e.type&&this.suit==e.suit&&this.rank==e.rank;
	}
	/**
	 * Whether this card is playable in the current struggle. 
	 * @return
	 */
	public boolean playable(int sp, int x) {
		if (type==1) {	//play leader iff they control something
			boolean flag = false;
			for (int i=0; i<75; i++) {
				if (MapManager.get(i).inRegion(GameData.ps.region) && MapManager.get(i).icon==suit && MapManager.get(i).isControlledBy()==sp) {
					flag = true;
					break;
				}
			}
			if (!flag) return false;
		}
		if (sp==GameData.ps.initiative) {
			if (type==2&&suit==3) return false; //cannot play fails
			if (type==2&&suit==2) {
				if (x==-1) return false;			//province must exist
				if (MapManager.get(x).support[(sp+1)%2]==0) return false; //province must have enemy support
			}
			if (type==0&&suit==GameData.ps.failed) return false; //cannot play failed
			if (type==1&&(x==-1||x==GameData.ps.failed)) return false; //suit must exist
		}
		else {
			if (type==0&&suit!=GameData.ps.tactic) return false; //must match suit
			if (type==2&&suit!=3) return false; //cannot play not-fails
		}
		return true;
	}
}

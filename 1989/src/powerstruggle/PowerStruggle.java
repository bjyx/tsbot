package powerstruggle;

import java.util.ArrayList;
import java.util.Arrays;

import events.CardEmbedBuilder;
import game.Die;
import game.GameData;
import main.Common;
import map.MapManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;

/**
 * A Power Struggle in 1989.
 * @author adalbert
 *
 */
public class PowerStruggle {
	/**
	 * initiative.
	 */
	public static int initiative = -1;
	/**
	 * How many times have the stakes been raised?
	 */
	public static int stakes = 0;
	/**
	 * Which country this takes place in.
	 */
	public static int region;
	/**
	 * The failed tactic that may not be played for the rest of this power struggle. There is only one Tactic Fails card.
	 */
	public static int failed = -1;
	/**
	 * The current tactic.
	 */
	public static int tactic = -1;
	/**
	 * The deck of Power Struggle Cards.
	 */
	public static ArrayList<StruggleCard> deck;
	/**
	 * The Democrat's hand.
	 */
	public static ArrayList<StruggleCard> DemHand;
	/**
	 * The Communist's hand.
	 */
	public static ArrayList<StruggleCard> ComHand;
	/**
	 * The last message sent to the Dem containing the Dem's hand of struggle cards.
	 */
	private static Message lastDemHand;
	/**
	 * The last message sent to the Com containing the Com's hand of struggle cards.
	 */
	private static Message lastComHand;
	/**
	 * The rank of the attacker's card, used as a threshold for gaining initiative.
	 */
	public static int thresh;
	/**
	 * Whether a player controls a given icon's territory. 
	 */
	public boolean[][] icons = new boolean[8][2];
	
	public void initializeDeck() {
		deck = new ArrayList<StruggleCard>();
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
	
	public static boolean addToHand(int sp) {
		if (sp==0) {
			return DemHand.add(deck.remove((int) Math.random()*deck.size()));
		}
		else {
			return ComHand.add(deck.remove((int) Math.random()*deck.size()));
		}
	}
	
	public static boolean removeRandom(int sp) {
		if (sp==0) {
			DemHand.remove((int) Math.random()*DemHand.size());
		}
		else {
			ComHand.remove((int) Math.random()*ComHand.size());
		}
		return true;
	}
	
	public static boolean inHand(int sp, int type, int suit, int rank) {
		if (sp==0) {
			return DemHand.remove(new StruggleCard(type, suit, rank));
		}
		else {
			return ComHand.remove(new StruggleCard(type, suit, rank));
		}
	}
	
	public static boolean play(int sp, String card, String s) {
		if (card.length()!=2) return false;
		char c = card.charAt(0);
		int r = card.charAt(1)-'0';
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
		if (type==1) {	//play leader iff they control something
			boolean flag = false;
			for (int i=0; i<75; i++) {
				if (MapManager.get(i).inRegion(region) && MapManager.get(i).icon==suit && MapManager.get(i).isControlledBy()==sp) {
					flag = true;
					break;
				}
			}
			if (!flag) return false;
		}
		if (sp==initiative) {
			if (type==2&&suit==3) return false; //cannot play fails
			if (type==2&&suit==2) {
				if (MapManager.find(s)==-1) return false;			//province must exist
				if (MapManager.get(MapManager.find(s)).support[(sp+1)%2]==0) return false; //province must have enemy support
			}
			if (type==0&&suit==failed) return false; //cannot play failed
			if (type==1&&!suited.contains(s.charAt(0))) return false; //suit must exist
		}
		else {
			if (type==0&&suit!=tactic) return false; //must match suit
			if (type==2&&suit!=3) return false; //cannot play not-fails
		}
		if (!PowerStruggle.inHand(sp, type, suit, rank)) return false;
		//enact effects.
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setColor(Common.spColor(sp));
		if (sp==initiative) {
			if (type==2) {
				if (suit==0) {
					removeRandom(Common.opp(sp));
					removeRandom(Common.opp(sp));
					builder.setTitle("Support Falters For " + Common.ideology[Common.opp(sp)])
					.setDescription("Two cards have been discarded from the " + Common.adject[Common.opp(sp)] + " hand.")
					.addField("Initiative passed to the " + Common.players[Common.opp(sp)], "", false);
				}
				if (suit==1) {
					addToHand(sp);
					addToHand(sp);
					builder.setTitle("Support Surges For " + Common.ideology[sp])
					.setDescription(Common.adject[sp] + " hand draws two cards.")
					.addField("Initiative passed to the " + Common.players[Common.opp(sp)], "", false);
				}
				if (suit==2) {
					builder.changeInfluence(MapManager.find(s), (sp+1)%2, -1)
					.setTitle("Scare Tactics Employed Against" + Common.adject[Common.opp(sp)] + " supporters in " + MapManager.get(MapManager.find(s)))
					.addField("Initiative passed to the " + Common.players[Common.opp(sp)], "", false);
				}
				initiative = (sp+1)%2; //switch initiative
				tactic = -1; // this isn't changing anything but safety stopgap measure nonetheless
			}
			else {
				if (type==1) {
					tactic = suited.indexOf(s.charAt(0));
					builder.setTitle(Common.icons[suit]+" Leader leads " + Common.suits[tactic]);
				}
				else {
					tactic = suit;
					builder.setTitle(Common.suits[tactic]);
				}
				thresh = rank;
			}
		}
		else {
			if (type==2) {
				failed=tactic;
				builder.setTitle(Common.suits[tactic] + " Fails");
			}
			else {
				int roll = new Die().roll();
				if (roll>=thresh) {
					initiative = Common.opp(sp);
				}
			}
			tactic = -1;
		}
		return true;
	}
	public static void concede() {
		
	}
	public static void endStruggle() {
		
	}
	public static void sendHands() {
		if (lastDemHand!=null) lastDemHand.delete().complete();

    	if (lastComHand!=null) lastComHand.delete().complete();
		EmbedBuilder usahand = new EmbedBuilder().setTitle("Power Struggle Cards");
		for (StruggleCard c : DemHand) {
			usahand.addField(c.toString(), "", false);
		}
		lastDemHand=GameData.txtdem.sendMessage(usahand.build()).complete();
		EmbedBuilder ssrhand = new EmbedBuilder().setTitle("Power Struggle Cards");
		for (StruggleCard c : ComHand) {
			ssrhand.addField(c.toString(), "", false);
		}
		lastComHand=GameData.txtcom.sendMessage(ssrhand.build()).complete();
	}
}

package powerstruggle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import commands.StruggleCommand;
import commands.TimeCommand;
import events.CardEmbedBuilder;
import events.Decision;
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
	 * An integer for progression: <br>
	 * 
	 * 0 for a newly created struggle<br>
	 * +1 after each player decides on the stakes<br>
	 * +1 after someone has lost the struggle<br>
	 * +1 after influence loss and the commie is preparing to step down<br>
	 */
	public int progression = 0;
	/**
	 * Roll for initiative! No, it's not a roll, but the person with initiative plays first.
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
	public int tactic = -1;
	/**
	 * The deck of Power Struggle Cards.
	 */
	public ArrayList<StruggleCard> deck;
	/**
	 * The Democrat's hand.
	 */
	public ArrayList<StruggleCard> DemHand;
	/**
	 * The Communist's hand.
	 */
	public ArrayList<StruggleCard> ComHand;
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
	public int thresh;
	/**
	 * Whether a player controls a given icon's territory. 
	 */
	public boolean[][] icons = new boolean[8][2];
	/**
	 * The number of controlled spaces, used to determine the number of cards a given player receives. 
	 */
	public int[] control = {0,0};
	/**
	 * The winner.
	 */
	public int victor;
	/**
	 * The result of the first die roll, which determines the support removed from the region.
	 */
	public int supportloss;
	/**
	 * A table of results for the first die roll.
	 */
	public static final int[] table = {0, 0, 1, 1, 2, 2, 3, 4};
	/**
	 * The Power value for each region.
	 */
	public static final int[] power = {3, 3, 2, 1, 2, 1};
	/**
	 * How many times the communist has retained power for that region.
	 */
	public static int[] retained = {0,0,0,0,0,0};
	
	/**
	 * Constructor.
	 * @param r is the region being contested.
	 * @param in is the player initially having initiative. 
	 */
	public PowerStruggle(int r, int in) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Power Struggle in " + Common.countries[r] + "!")
			.setDescription(Common.players + "s hold initiative")
			.setColor(Color.YELLOW);
		region = r;
		for (int i=0; i<75; i++) {
			if (MapManager.get(i).inRegion(r)&&MapManager.get(i).isControlledBy()!=-1) {
				icons[MapManager.get(i).icon][MapManager.get(i).isControlledBy()]=true;
				control[MapManager.get(i).isControlledBy()]++;
			}
		}
		initiative = in;
		int d = 4+(2*control[0]);
		int c = 4+(2*control[1]);
		//TODO events
		if (HandManager.removeEffect(17)) {
			HandManager.Discard.add(17);
			d+=2;
			c-=2;
			builder.addField("Round Table Talks", "The Democrat gets two cards from the Communist.", false);
		}
		if (HandManager.removeEffect(72)) {
			for (int i=Common.bracket[r]; i<Common.bracket[r+1]; i++) {
				if (MapManager.get(i).icon==1&&MapManager.get(i).isControlledBy()==0) {
					d+=1;
					c-=1;
					builder.addField("Peasant Parties Revolt", "The Democrat gets one card from the Communist.", false);
					break;
				}
			}
			
		}
		if (HandManager.effectActive(70)&&r==4) {
			builder.addField("Securitate", "The Communist may query the Democrat's Power Struggle hand at any time using `DF.info iulian`.", false);
		}
		initializeDeck();
		builder.addField("Card Count", "Democrat: "+d+"\nCommunist: "+c, false);
		for (int i=0; i<d; i++) addToHand(0);
		for (int i=0; i<c; i++) addToHand(1);
		if (r==4&&HandManager.effectActive(540)) {
			HandManager.removeEffect(540);
			int rally=0;
			for (int i=0; i<15; i++) {
				if (deck.remove((int) Math.random()*deck.size()).equals(new StruggleCard(0,0,1))) {
					rally++;
				}
			}
			builder.addField("The Crowd Turns Against Ceausescu","There are " + rally + " Rally in the Squares in the next 15 cards.",false);
			GameData.dec = new Decision(0, 54);
			GameData.ops = new Operations(0, rally*3, true, true, false);
			Common.spChannel(0).sendMessage(Common.spRole(0).getAsMention() + ", you may conduct your action round in Romania courtesy of those protestors.").complete();
		}
		else {
			Common.spChannel(in).sendMessage(Common.spRole(in).getAsMention() + ", would you like to raise the stakes? Respond with `DF.struggle r`aise or `DF.struggle d`ecline.");
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();

	}
	
	public void raiseStakes(ArrayList<StruggleCard> cards, int sp) {
		stakes++;
		for (int i=0; i<3; i++) {
			if (sp==0) DemHand.remove(cards.get(i));
			if (sp==1) ComHand.remove(cards.get(i));
		}
		progression++;
	}
	
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
	
	public boolean addToHand(int sp) {
		if (sp==0) {
			return DemHand.add(deck.remove((int) Math.random()*deck.size()));
		}
		else {
			return ComHand.add(deck.remove((int) Math.random()*deck.size()));
		}
	}
	
	public boolean removeRandom(int sp) {
		if (sp==0) {
			DemHand.remove((int) Math.random()*DemHand.size());
		}
		else {
			ComHand.remove((int) Math.random()*ComHand.size());
		}
		return true;
	}
	
	public boolean inHand(int sp, int type, int suit, int rank) {
		if (sp==0) {
			return DemHand.remove(new StruggleCard(type, suit, rank));
		}
		else {
			return ComHand.remove(new StruggleCard(type, suit, rank));
		}
	}
	
	public boolean play(int sp, String card, String s) {
		if (card.length()!=2) return false;
		char c = card.charAt(0);
		int r = card.charAt(1)-'0';
		ArrayList<Character> suited = new ArrayList<Character>(Arrays.asList('r','s','m','p'));
		int rank, suit, type;
		int x = -1; //must initialize
		if (suited.contains(c)) {
			type = 0;
			suit = suited.indexOf(c);
			rank = r;
		}
		else if (c=='l') {
			type = 1;
			suit = r;
			rank = 3;
			x = suited.indexOf(s.charAt(0));
		}
		else if (c=='w') {
			type = 2;
			suit = r;
			rank = 0;
			if (suit==2) x=MapManager.find(s);
		}
		else return false;
		if (!(new StruggleCard(type, suit, rank).playable(sp, x))) return false; 
		if (!inHand(sp, type, suit, rank)) return false;
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
					.setDescription(Common.adject[sp] + " hand gains two cards.")
					.addField("Initiative passed to the " + Common.players[Common.opp(sp)], "", false);
				}
				if (suit==2) {
					builder.changeInfluence(x, (sp+1)%2, -1)
					.setTitle("Scare Tactics Employed Against" + Common.adject[Common.opp(sp)] + " supporters in " + MapManager.get(x).name)
					.addField("Initiative passed to the " + Common.players[Common.opp(sp)], "", false);
				}
				initiative = (sp+1)%2; //switch initiative
				tactic = -1; // this isn't changing anything but safety stopgap measure nonetheless
			}
			else {
				if (type==1) {
					tactic = x;
					builder.setTitle(Common.icons[suit]+" Leader leads " + Common.suits[tactic]);
				}
				else {
					tactic = suit;
					builder.setTitle(Common.suits[tactic] + "!");
				}
				thresh = rank;
				builder.setDescription("Strength: " + thresh);
			}
			if (sp==1?DemHand.isEmpty():ComHand.isEmpty()) { //whosever turn it is
				builder.addField("Worn Down", "The " + Common.players[Common.opp(initiative)] + " has run out of cards!", false);
				victor = sp;
				endStruggle();
			}
		}
		else {
			if (type==2) {
				failed=tactic;
				builder.setTitle(Common.suits[tactic] + " Fails")
				.setDescription("This tactic may not be used again during this Power Struggle.");
			}
			else {
				int roll = new Die().roll();
				if (roll>=thresh) {
					initiative = Common.opp(sp);
					builder.setTitle(Common.players[initiative] + " Seize Initiative");
				}
				else {
					builder.setTitle(Common.players[initiative] + " Retain Initiative");
				}
			}
			tactic = -1;
			if (initiative==0?DemHand.isEmpty():ComHand.isEmpty()) {
				builder.addField("Running Out of Steam", "The " + Common.players[initiative] + " has run out of cards!", false);
				victor = Common.opp(initiative);
				endStruggle();
			}
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
		return true;
	}
	public void concede() {
		victor = initiative;
		endStruggle();
	}
	public void endStruggle() {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Power Struggle Aftermath").setColor(Common.spColor(GameData.ps.victor));
		
		progression++;
		if (tactic == 0) stakes += 2;
		if (tactic == 3) stakes -= 2;
		int support = new Die().roll()+stakes;
		if (HandManager.removeEffect(62)) {
			if (victor==0) {
				builder.addField("Yakovlev Counsels Gorbachev", "Stakes increased by 1.", false);
				stakes++;
			}
		}
		supportloss = table[Math.max(0, Math.min(support, 7))];
		
		builder.setDescription("Stakes: " + stakes).addField("Support Roll (after modifiers)", ":game_die: " + CardEmbedBuilder.numbers[Math.max(0, Math.min(support, 7))], false);
		GameData.txtchnl.sendMessage(builder.build()).complete();
		Common.spChannel(Common.opp(victor)).sendMessage(Common.spRole(Common.opp(victor)).getAsMention() + ", you now have to remove " + supportloss + " support from " + Common.countries[region]).complete();
	}
	public void actuallyEndStruggle() {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Power Struggle Aftermath").setColor(Common.spColor(GameData.ps.victor));
		builder.bulkChangeInfluence(StruggleCommand.order, 1, StruggleCommand.values); //8.4.2 done
		int points = new Die().roll()+stakes; //8.4.3
		builder.addField("Points Roll (after modifiers)", ":game_die: " + CardEmbedBuilder.numbers[Math.max(0, Math.min(points, 7))], false);
		builder.changeVP((1-victor*2)*table[Math.max(0, Math.min(points, 7))]);
		if (points>=4&&victor==0) { //democrat rolls more than a 4
			HandManager.Removed.add(HandManager.activecard);
			builder.setDescription("Democrat seizes power in " + Common.countries[region] + "!");
			PowerStruggle.retained[region]=-1;
		}
		else {
			builder.setDescription("Communist retains power in " + Common.countries[region] + "!");
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
		progression++;
		if (PowerStruggle.retained[region]!=-1) Common.spChannel(1).sendMessage(Common.spRole(1).getAsMention() + ", you now have the option of a peaceful transition to Democratic Rule. If you wish to do so, write `TS.struggle s 1`. Otherwise, write `TS.struggle s 0`.").complete();
		else {
			Scoring.score(region);
			TimeCommand.eventDone = true;
			TimeCommand.prompt();
		}
	}
	
	public static void sendHands() {
		if (lastDemHand!=null) lastDemHand.delete().complete();

    	if (lastComHand!=null) lastComHand.delete().complete();
		EmbedBuilder usahand = new EmbedBuilder().setTitle("Power Struggle Cards");
		for (StruggleCard c : GameData.ps.DemHand) {
			usahand.addField(c.toString(), "", false);
		}
		lastDemHand=GameData.txtdem.sendMessage(usahand.build()).complete();
		EmbedBuilder ssrhand = new EmbedBuilder().setTitle("Power Struggle Cards");
		for (StruggleCard c : GameData.ps.ComHand) {
			ssrhand.addField(c.toString(), "", false);
		}
		lastComHand=GameData.txtcom.sendMessage(ssrhand.build()).complete();
	}
}

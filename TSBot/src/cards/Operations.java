package cards;

import java.util.Random;

import events.CardEmbedBuilder;
import game.GameData;
import map.MapManager;
/**
 * Class for handling all related to operations.
 * @author [REDACTED]
 *
 */
public class Operations {
	/**
	 * The superpower in charge of these operations.
	 */
	private int sp = -1;
	/**
	 * If the current set of ops can be used for influence placement.
	 */
	private boolean influence = true;
	/**
	 * If the current set of ops can be used for realignments.
	 */
	private boolean realignment = true;
	/**
	 * If the current set of ops can be used for a coup.
	 */
	private boolean coupdetat = true;
	/**
	 * If the current set of ops can be sent to space.
	 */
	private boolean spaceable = false;
	/**
	 * The number of ops available to use.
	 */
	public int opnumber = -1;
	/**
	 * The number of ops required to send things into space.
	 */
	public static final int[] spaceOps = {2, 2, 2, 2, 3, 3, 4, 4};
	/**
	 * The number of VP obtained by being the <b> first </b> to advance to that stage.
	 */
	public static final int[] spaceVP = {2, 0, 2, 0, 3, 0, 4, 2};
	/**
	 * The number of VP obtained by being the <b> second </b> to advance to that stage.
	 */
	public static final int[] spaceVP2 = {1, 0, 0, 0, 1, 0, 2, 0};
	/**
	 * The number you must roll under or at to advance to the next stage of the space race.
	 */
	public static final int[] spaceRoll = {3, 4, 3, 4, 3, 4, 3, 2};
	/**
	 * Whether all ops are being used in Asia--only relevant for the China Card.
	 */
	boolean allasia = true;
	/**
	 * Whether all ops are being used in Southeast Asia--only relevant for the op boost from the Vietnam Revolts.
	 */
	boolean allsea = true;
	/**
	 * Constructor, specifying the number of ops on the card used and the conditions under which they can be used.
	 * @param superpower is the superpower in charge of these Operations points.
	 * @param ops is the number of Operation Points usable in this instance of Operations.
	 * @param infl indicates whether one can place Influence using this instance of Operations.
	 * @param realign indicates whether one can Realigh using this instance of Operations.
	 * @param coup indicates whether one can conduct a Coup using this instance of Operations.
	 * @param space indicates whether one can send this instance of Operations to space.
	 */
	public Operations(int superpower, int ops, boolean infl, boolean realign, boolean coup, boolean space) {
		sp = superpower;
		opnumber = ops;
		influence = infl;
		realignment = realign;
		coupdetat = coup;
		spaceable = space;
	}
	/**
	 * Places the given amounts of influence in their respective countries.
	 * @param countries is an array containing the IDs of the countries targeted.
	 * @param amt is an array containing the amount of influence used on each country.
	 */
	public void influence(int[] countries, int[] amt) {
		if (!influence) {
			GameData.txtchnl.sendMessage(":x: You must do something else with these ops.");
			return;
		}
		int ops = 0;
		
		for (int i=0; i<countries.length; i++) {
			ops += (Math.max(0, MapManager.map.get(countries[i]).influence[(sp+1)%2]-MapManager.map.get(countries[i]).influence[sp]-MapManager.map.get(countries[i]).getStab()+1))*2 + amt[i]-Math.max(0, MapManager.map.get(countries[i]).influence[(sp+1)%2]-MapManager.map.get(countries[i]).influence[sp]-MapManager.map.get(countries[i]).getStab()+1);
			if (MapManager.map.get(countries[i]).getRegion()!=5) {
				allsea=false;
				if(MapManager.map.get(countries[i]).getRegion()!=4) {
					allasia=false;
				}
			}
			if (!(MapManager.map.get(countries[i]).influence[sp]>0)) {
				boolean flag = false;
				for (int adj : MapManager.map.get(countries[i]).getAdj()) {
					if (MapManager.map.get(adj).influence[sp]!=0) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					GameData.txtchnl.sendMessage(":x: Can you even reach that country with your influence?...");
					return;
				}
			}
		}
		if (allasia && HandManager.activecard==6) ops--; //handles China Card fringe case
		if (allsea && HandManager.Effects.contains(9)&&sp==1) ops--;
		if (ops<opnumber) {
			GameData.txtchnl.sendMessage(":x: Endeavor to use all available ops.");
			return;
		}
		if (ops>opnumber) {
			GameData.txtchnl.sendMessage(":x: Overstreching yourself is never good.");
			return;
		}
		realignment = false;
		coupdetat = false;
		spaceable = false;
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Influence Placement");
		if (new Random().nextInt(15)==0) builder.setDescription("\"You have a row of dominoes set up, you knock over the first one, and what will happen to the last one is the certainty that it will go over very quickly. So you could have a beginning of a disintegration that would have the most profound influences.\" \n-Dwight David Eisenhower, 1954");
		for (int i=0; i<countries.length; i++) {
			builder.changeInfluence(countries[i], sp, amt[i]);
		}
		GameData.txtchnl.sendMessage(builder.build());
		GameData.txtchnl.sendMessage("`Operations Complete`");
	}
	/**
	 * Realigns the given country in an attempt to remove influence.
	 * @param country is the country targeted by the realignment.
	 */
	public void realignment(int country) {
		if (!realignment) {
			GameData.txtchnl.sendMessage(":x: You must do something else with these ops.");
			return;
		}
		if (!MapManager.map.get(country).checkIsCoupable()) {
			GameData.txtchnl.sendMessage(":x: DEFCON restricts you from realigning this country.");
			return;
		}
		if (MapManager.map.get(country).influence[(sp+1)%2]==0) {
			GameData.txtchnl.sendMessage(":x: This country is fresh out of foreign influence, I can tell you that.");
			return;
		}
		if (!(MapManager.map.get(country).getRegion()==5)) {
			allsea=false;
			if(!(MapManager.map.get(country).getRegion()==4)) {
				allasia=false;
			}
		}
		if (opnumber<1) {
			if (allsea&&MapManager.map.get(country).getRegion()==5&&HandManager.Effects.contains(9)&&sp==1) {
				opnumber++;
				allsea=false;
			}
			else if (allasia&&(MapManager.map.get(country).getRegion()==4||allasia&&MapManager.map.get(country).getRegion()==5)&&HandManager.activecard==6) {
				opnumber++;
				allasia=false;
			}
			else {
				GameData.txtchnl.sendMessage("You've run out of ops...");
				opnumber--;
				return;
			}
		}
		influence = false;
		coupdetat = false;
		spaceable = false;
		Random rand = new Random();
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Realignment")
		.setDescription("Target: "+ MapManager.map.get(country));
		int[] rolls = new int[] {rand.nextInt(6)+1, rand.nextInt(6)+1};
		String[] modifiers = {"",""};
		if (MapManager.map.get(country).influence[0]>MapManager.map.get(country).influence[1]) {
			rolls[0]++;
			modifiers[0] += MapManager.map.get(country);
		}
		if (MapManager.map.get(country).influence[0]<MapManager.map.get(country).influence[1]) {
			rolls[1]++;
			modifiers[1] += MapManager.map.get(country);
		}
		for (int adj : MapManager.map.get(country).getAdj()) {
			if (adj==84) {
				rolls[0]++;
				modifiers[0] += MapManager.map.get(adj);
			}
			else if (adj==85) {
				rolls[1]++;
				modifiers[1] += MapManager.map.get(adj);
			}
			else {
				if (MapManager.map.get(adj).isControlledBy()!=-1) {
					rolls[MapManager.map.get(adj).isControlledBy()]++;
					modifiers[MapManager.map.get(adj).isControlledBy()] += MapManager.map.get(adj);
				}
			}
		}
		builder.addField("Rolls", ":flag_us::" + CardEmbedBuilder.numbers[rolls[0]] + "::heavy_plus:" + modifiers[0] + "\n:flag_su::" + CardEmbedBuilder.numbers[rolls[1]] + "::heavy_plus:" + modifiers[1], false);
		if (rolls[1]>rolls[0]) builder.changeInfluence(country, 0, -(rolls[1]-rolls[0]));
		if (rolls[0]>rolls[1]) builder.changeInfluence(country, 1, -(rolls[0]-rolls[1]));
		GameData.txtchnl.sendMessage(builder.build());
		opnumber--;
	}
	/**
	 * Coups the given country, to attempt to sway that country to the side of sp.
	 * @param country is the country targeted by the coup.
	 */
	public void coup(int country) {
		if (!coupdetat) {
			GameData.txtchnl.sendMessage(":x: You must do something else with these ops.");
			return;
		}
		if (!MapManager.map.get(country).checkIsCoupable()) {
			GameData.txtchnl.sendMessage(":x: DEFCON restrictions disallow you from couping this nation.");
			return;
		}
		if (!(MapManager.map.get(country).getRegion()==5)) {
			allsea=false;
			if(!(MapManager.map.get(country).getRegion()==4)) {
				allasia=false;
			}
		}
		if (allsea&&HandManager.Effects.contains(9)&&sp==1) opnumber++;
		if (allasia&&HandManager.activecard==6) opnumber++;
		if (HandManager.Effects.contains(43)) opnumber--;
		realignment = false;
		influence = false;
		spaceable = false;
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Coup d'État")
		.setDescription("Target: "+ MapManager.map.get(country));
		if (MapManager.map.get(country).isBattleground()) builder.changeDEFCON(-1)
				.addMilOps(sp, opnumber);
		int die = (new Random().nextInt(6))+1;
		int amt = opnumber + die - MapManager.map.get(country).getStab()*2;
		builder.addField("Roll: :" + CardEmbedBuilder.numbers[die] + ":", CardEmbedBuilder.intToEmoji(amt), false);
		
		if (amt>0) {
			if (amt>MapManager.map.get(country).influence[(sp+1)%2]) {
				builder.changeInfluence(country, sp, amt-MapManager.map.get(country).influence[(sp+1)%2]);
				builder.changeInfluence(country, (sp+1)%2, -MapManager.map.get(country).influence[(sp+1)%2]);
			}
			else {
				builder.changeInfluence(country, (sp+1)%2, -amt);
			}
		}
		else {
			builder.addField(":( Failure!", "", false);
		}
		GameData.txtchnl.sendMessage(builder.build());
	}
	/**
	 * Sends the card to space.
	 */
	public void space() {
		if (!spaceable) {
			GameData.txtchnl.sendMessage(":x: You must do something else with these ops.");
			return;
		}
		realignment = false;
		coupdetat = false;
		influence = false;
		GameData.toSpace(sp);
		int spaceLevel = GameData.getSpace(sp);
		int die = (new Random().nextInt(6))+1;
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Space Race")
		.setDescription("Card used: " + CardList.getCard(HandManager.activecard));
		if (die <= spaceRoll[spaceLevel]) {
			builder.addField("Roll: "+CardEmbedBuilder.numbers[die],getSpaceNames(spaceLevel),false);
			if (GameData.aheadInSpace()==(sp+1)%2) {
				builder.changeVP(-(sp*2-1)*spaceVP2[spaceLevel]);
			}
			else builder.changeVP(-(sp*2-1)*spaceVP[spaceLevel]);
			GameData.addSpace(sp);
		}
		else {
			builder.addField("Roll: "+CardEmbedBuilder.numbers[die],"Failure.",false);
		}
		GameData.txtchnl.sendMessage(builder.build());
	}
	/**
	 * Flavor text regarding the space race goes here.
	 * @param spaceLevel is the superpower's advancement on the race.
	 * @param sp is the superpower conducting the roll.
	 * @return a string, with relevant flavor text.
	 */
	public String getSpaceNames(int spaceLevel) {
		if (sp==0) {
			if (spaceLevel==0) return "Explorer 1 launched.";
			if (spaceLevel==1) return "AM-18 launched.";
			if (spaceLevel==2) return "\"You know, being a test pilot isn't always the healthiest business in the world.\" - Alan Shepard";
			if (spaceLevel==3) return "Success.";
			if (spaceLevel==4) return "Lunar Orbiter 1 launched.";
			if (spaceLevel==5) return "\"Houston, Tranquility Base here. The Eagle has landed.\" \n- Neil Armstrong, 1969";
			if (spaceLevel==6) return "Space Shuttle launched.";
			if (spaceLevel==7) return "Success.";
		}
		if (sp==1) {
			if (spaceLevel==0) return "Sputnik launched.";
			if (spaceLevel==1) return "Sputnik 2 launched.";
			if (spaceLevel==2) return "\"Poyekhali [Let's go]!\" \n- Yuri Gagarin, 1961";
			if (spaceLevel==3) return "Success.";
			if (spaceLevel==4) return "Luna 10 launched.";
			if (spaceLevel==5) return "N1 launched.";
			if (spaceLevel==6) return "Success.";
			if (spaceLevel==7) return "Success.";
		}
		return null;
	}
}

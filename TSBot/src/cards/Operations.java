package cards;

import java.util.Random;

import events.CardEmbedBuilder;
import game.GameData;
import game.PlayerList;
import map.Country;
import map.MapManager;
/**
 * Class for handling all related to operations.
 * @author [REDACTED]
 *
 */
public class Operations {
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
	private int opnumber = -1;
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
	 * Constructor, specifying the number of ops on the card used
	 * @param ops
	 * @param infl
	 * @param realign
	 * @param coup
	 * @param space
	 */
	public Operations(int ops, boolean infl, boolean realign, boolean coup, boolean space) {
		opnumber = ops;
		influence = infl;
		realignment = realign;
		coupdetat = coup;
		spaceable = space;
	}
	
	public void influence(int sp, int[] countries, int[] amt) {
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
	public void realignment(int sp, int country) {
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
				GameData.txtchnl.sendMessage(":x: You've run out of ops...");
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
	
	public void coup(int sp, int country) {
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
		realignment = false;
		influence = false;
		spaceable = false;
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Realignment")
		.setDescription("Target: "+ MapManager.map.get(country));
		if (MapManager.map.get(country).isBattleground()) builder.changeDEFCON(-1);
		builder.addMilOps(sp, opnumber);
		int die = (new Random().nextInt(6))+1;
		int amt = opnumber + die - MapManager.map.get(country).getStab()*2;
		if (amt>0) {
			if (amt>MapManager.map.get(country).influence[(sp+1)%2]) {
				MapManager.map.get(country).changeInfluence(sp, amt-MapManager.map.get(country).influence[(sp+1)%2]);
				MapManager.map.get(country).changeInfluence((sp+1)%2, MapManager.map.get(country).influence[(sp+1)%2]);
			}
		}
	}
	public void space(int sp) {
		if (!spaceable) {
			GameData.txtchnl.sendMessage(":x: You must do something else with these ops.");
			return;
		}
		realignment = false;
		coupdetat = false;
		influence = false;
	}
	
}

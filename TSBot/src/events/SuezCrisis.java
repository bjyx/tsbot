package events;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cards.HandManager;
import game.GameData;
import main.Launcher;
import map.MapManager;
/**
 * The Suez Canal Crisis Card.
 * @author adalbert
 *
 */
public class SuezCrisis extends Card {
	
	private static ArrayList<Integer> order;
	private static ArrayList<Integer> values;
	private static ArrayList<Integer> doable;
	private static final  List<Integer> inv = Arrays.asList(8, 18, 25);

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Anglo-French Forces Withdraw From Egypt")
		.setDescription("Eden set to resign amid diplomatic pressure in a victory for Nasser")
		.setFooter("\"We couldn't on one hand, complain about the Soviets intervening in Hungary and, on the other hand, approve of the British and the French picking that particular time to intervene against Nasser.\"\n"
				+ "- Richard Nixon, 1956", Launcher.url("people/nixon.png"))
		.setColor(Color.RED);
			if (doable.isEmpty()) {
				builder.addField("No countries to target!", "For some reason, the UK, France, and Israel are not associated with the US. France and Israel I can get, but the UK?", false);
			}
			else {
				builder.bulkChangeInfluence(order, 0, values);
			}
			GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return !HandManager.effectActive(1005);
	}

	@Override
	public String getId() {
		return "028";
	}

	@Override
	public String getName() {
		return "Suez Crisis";
	}

	@Override
	public int getOps() {
		return 3;
	}

	@Override
	public int getEra() {
		return 0;
	}

	@Override
	public int getAssociation() {
		return 1;
	}

	@Override
	public boolean isRemoved() {
		return true;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		doable = new ArrayList<Integer>();
		order = new ArrayList<Integer>();
		values = new ArrayList<Integer>();
		int maxInfRem = 0;
		for (int i : inv) {
			if (MapManager.get(i).influence[0]>0) {
				doable.add(i);
				maxInfRem += Math.min(MapManager.get(i).influence[0], 2);
			}
		}
		if (maxInfRem<=4) {
			order = doable;
			for (int i : order) {
				values.add(Math.max(-MapManager.get(i).influence[0], -2));
			}
			return true;
		}
		if (args.length%2!=1) return false; //each country must associate with a number
		for (int i=1; i<args.length; i+=2) {
			int c = MapManager.find(args[i]);
			if (order.indexOf(c)!=-1) return false; // no duplicates plox
			order.add(c);
			try{
				values.add(Integer.parseInt(args[i+1]));
			}
			catch (NumberFormatException e){
				return false; //this isn't an integer. xP
			}
		}
		int sum = 0;
		if (!doable.containsAll(order)) return false;
		for (int i=0; i<order.size(); i++) {
			if (values.get(i)>=0) return false; //no non-positive numbers please
			if (values.get(i)<-2) return false; // cannot remove >2 influence from a given country
			if (MapManager.get(order.get(i)).influence[0]+values.get(i)<0) return false; //don't give me negative influence values
			sum += values.get(i);
		}
		if (sum!=-4) return false; // up to 4 influence may be removed
		return true;
	}

	@Override
	public String getDescription() {
		if (HandManager.effectActive(1005)) return "The Tories are on a new wave of popularity. This event is not playable.";
		return "Remove at most four influence from the UK, France, and Israel.";
	}

	@Override
	public String getArguments() {
		return "Influence values ((*country* *value*)+). All entries in *country* must be either the UK, France, or Israel, and all entries in *value* must be negative integers at least -2 that do not exceed American influence in the associated country. The entries in *value* must sum to a number at least -4.\n" 
				+ "Example: TS.event gbr 2 il 1 france 1";
	}

}

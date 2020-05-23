package events;

import java.awt.Color;
import java.util.ArrayList;

import game.GameData;
import map.MapManager;

public class ForeignTelevision extends Card {
	private static ArrayList<Integer> order;
	private static ArrayList<Integer> values;
	
	private static ArrayList<Integer> doable;
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Foreign Television")
		.setColor(Color.blue);
		if (doable.isEmpty()) {
			builder.addField("No spaces to target!", "Congratulations. You've managed to dodge Foreign Television. How's that outpost in Dresden helping your case in the rest of the bloc?", false);
		}
		else {
			builder.bulkChangeInfluence(order, 1, values);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "056";
	}

	@Override
	public String getName() {
		return "Foreign Television";
	}

	@Override
	public int getOps() {
		return 2;
	}

	@Override
	public int getEra() {
		return 1;
	}

	@Override
	public int getAssociation() {
		return 0;
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
		for (int i=0; i<75; i++) {
			if (MapManager.get(i).support[1]>0) {
				doable.add(i);
				maxInfRem += Math.min(MapManager.get(i).support[1], 2);
			}
		}
		if (maxInfRem<=4) {
			order = doable;
			for (int i : order) {
				values.add(Math.max(-MapManager.get(i).support[1], -2));
			}
			return true;
		}
		if (args.length%2!=1) return false; //each country must associate with a number
		for (int i=1; i<args.length; i+=2) {
			int c = MapManager.find(args[i]);
			if (c==-1) return false;
			if (c==1) return false; //valley of the clueless
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
			if (values.get(i)>=0) return false; //no non-negative numbers please
			if (values.get(i)<-2) return false; // cannot remove >2 influence from a given country
			if (MapManager.get(order.get(i)).support[1]+values.get(i)<0) return false; //don't give me negative influence values
			sum += values.get(i);
		}
		if (sum!=-4) return false; // up to 4 influence may be removed...
		return true;
	}

	@Override
	public String getDescription() {
		return "Remove a total of 4 Communist SPs (no more than 2 per space). This card may not be used to remove SPs from Dresden.";
	}

	@Override
	public String getArguments() {
		return "Influence. The values must be negative (at least -2) and not cause Communist support values to be negative. They must also sum to -4. The locations must not include Dresden.";
	}

}

package events;

import java.awt.Color;
import java.util.ArrayList;

import game.GameData;
import map.MapManager;

public class StateRunMedia extends Card {
	private static ArrayList<Integer> order;
	private static ArrayList<Integer> values;
	
	private static ArrayList<Integer> doable;
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("State Run Media")
		.setColor(Color.red);
		if (doable.isEmpty()) {
			builder.addField("No countries to target!", "Congratulations. You've managed to dodge State-Run Media. Was it worth losing your shot at liberating the Communist bloc?", false);
		}
		else {
			builder.bulkChangeInfluence(order, 0, values);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "007";
	}

	@Override
	public String getName() {
		return "State Run Media";
	}

	@Override
	public int getOps() {
		return 2;
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
		for (int i=0; i<84; i++) {
			if (MapManager.get(i).support[0]>0) {
				doable.add(i);
				maxInfRem += Math.min(MapManager.get(i).support[0], 2);
			}
		}
		if (maxInfRem<=4) {
			order = doable;
			for (int i : order) {
				values.add(Math.max(-MapManager.get(i).support[0], -2));
			}
			return true;
		}
		if (args.length%2!=1) return false; //each country must associate with a number
		for (int i=1; i<args.length; i+=2) {
			order.add(MapManager.find(args[i]));
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
			if (MapManager.get(order.get(i)).support[0]+values.get(i)<0) return false; //don't give me negative influence values
			sum += values.get(i);
		}
		if (sum!=-4) return false; // up to 4 influence may be removed...
		return true;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Remove 4 Democratic SPs (no more than 2 per space).";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "Influence. The values must be negative (at least -2) and not cause Democratic support values to be negative. They must also sum to -4.";
	}

}

package events;

import java.awt.Color;
import java.util.ArrayList;

import game.GameData;
import map.MapManager;

public class FactoryPartyCells extends Card {
	private static ArrayList<Integer> order;
	private static ArrayList<Integer> values;
	
	private static ArrayList<Integer> doable;
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Factory Party Cells")
		.setColor(Color.red);
		if (doable.isEmpty()) {
			builder.addField("No spaces to target!", "How the f@%k does the Democrat not have *some* influence in a Worker space right now!?", false);
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
		return "028";
	}

	@Override
	public String getName() {
		return "Factory Party Cells";
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
		return false;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		doable = new ArrayList<Integer>();
		order = new ArrayList<Integer>();
		values = new ArrayList<Integer>();
		int maxInfRem = 0;
		for (int i=0; i<75; i++) {
			if (MapManager.get(i).support[0]>0&&MapManager.get(i).icon==0) {
				doable.add(i);
				maxInfRem += Math.min(MapManager.get(i).support[0], 2);
			}
		}
		if (maxInfRem<=3) {
			order = doable;
			for (int i : order) {
				values.add(Math.max(-MapManager.get(i).support[0], -2));
			}
			return true;
		}
		if (args.length%2!=1) return false; //each country must associate with a number
		for (int i=1; i<args.length; i+=2) {
			int c = MapManager.find(args[i]);
			if (c==-1) return false;
			if (order.indexOf(c)!=-1) return false; // no duplicates plox
			order.add(c);
			if (MapManager.get(c).icon!=0) return false; //workers only
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
		if (sum!=-3) return false; // up to 3 influence may be removed...
		return true;
	}

	@Override
	public String getDescription() {
		return "Remove a total of 3 Democratic Support from Worker spaces (no more than 2 per space).";
	}

	@Override
	public String getArguments() {
		return "Influence.";
	}

}

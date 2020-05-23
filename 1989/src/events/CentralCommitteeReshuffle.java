package events;

import java.awt.Color;
import java.util.ArrayList;

import game.GameData;
import main.Common;
import map.MapManager;
import powerstruggle.PowerStruggle;

public class CentralCommitteeReshuffle extends Card {

	private static ArrayList<Integer> order;
	private static ArrayList<Integer> values;

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder
			.setTitle("Central Committee of " + Common.countries[MapManager.get(order.get(0)).region] + " Swears in New Leader")
			.setColor(Color.red);
		builder.bulkChangeInfluence(order, 1, values);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "057";
	}

	@Override
	public String getName() {
		return "Central Committee Reshuffle";
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
		return 1;
	}

	@Override
	public boolean isRemoved() {
		return true;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		order = new ArrayList<Integer>();
		values = new ArrayList<Integer>();
		if (args.length%2!=1) return false;
		for (int i=1; i<args.length; i+=2) {
			int c = MapManager.find(args[i]);
			if (c==-1) return false;
			if (order.indexOf(c)!=-1) return false; // no duplicates plox
			order.add(c);
			if (PowerStruggle.retained[MapManager.get(c).region]==-1) return false; // must hold power in region
			if (i>1&&MapManager.get(c).region!=MapManager.get(order.get(0)).region) return false; //one region only
			try{
				values.add(Integer.parseInt(args[i+1]));
			}
			catch (NumberFormatException e){
				return false; //this isn't an integer. xP
			}
		}
		int sum = 0;
		for (int i=0; i<order.size(); i++) {
			if (values.get(i)<=0) return false; //no non-positive numbers please
			sum += values.get(i);
		}
		if (sum!=3) return false;
		return true;
	}

	@Override
	public String getDescription() {
		return "Place 3 Communist SPs in one country where the Communist holds power.";
	}

	@Override
	public String getArguments() {
		return "Support.";
	}

}

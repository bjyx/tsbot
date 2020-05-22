package events;

import java.awt.Color;
import java.util.ArrayList;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import main.Launcher;
import map.MapManager;

public class PeasantParties extends Card {

	private static ArrayList<Integer> order;
	private static ArrayList<Integer> values;
	
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Peasant Parties")
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
		return "032";
	}

	@Override
	public String getName() {
		return "Peasant Parties";
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
		order = new ArrayList<Integer>();
		values = new ArrayList<Integer>();
		if (args.length%2!=1) return false;
		for (int i=1; i<args.length; i+=2) {
			int c = MapManager.find(args[i]);
			if (c==-1) return false;
			if (order.indexOf(c)!=-1) return false; // no duplicates plox
			order.add(c);
			if (MapManager.get(c).icon==1) return false; //must be farmer
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
			if (values.get(i)>2) return false; //must be at most 2 per space.
			sum += values.get(i);
		}
		if (sum!=4) return false; //must sum to 4
		return true;
	}

	@Override
	public String getDescription() {
		return "Add 4 Communist SPs to Farmer spaces, no more than 2 per space. ";
	}

	@Override
	public String getArguments() {
		return "Influence values. All spaces must be Farmer spaces. All values must be positive integers at most 2, and must sum to 4.";
	}

}

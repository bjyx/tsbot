package events;

import java.awt.Color;
import java.util.ArrayList;

import game.GameData;
import main.Launcher;
import map.MapManager;

public class LiberationTheology extends Card {
	
	private static ArrayList<Integer> order;
	private static ArrayList<Integer> values;

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Liberation Theology")
		.setDescription("The preferential option for the poor")
		.setFooter("\"History is the scene of the revelation God makes of the mystery of his person. "
				+ "His word reaches us in the measure of our involvement in the evolution of history.\"\n"
				+ "- Gustavo Gutiérrez, 1975", Launcher.url("countries/pe.png"))
		.setColor(Color.RED);
		builder.bulkChangeInfluence(order, 1, values);
		GameData.txtchnl.sendMessage(builder.build());
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "075";
	}

	@Override
	public String getName() {
		return "Liberation Theology";
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
		return false;
	}

	@Override
	public boolean isFormatted(String[] args) {
		order = new ArrayList<Integer>();
		values = new ArrayList<Integer>();
		if (args.length%2!=1) return false;
		for (int i=2; i<args.length; i+=2) {
			int c = MapManager.find(args[i]);
			order.add(c);
			try{
				values.add(Integer.parseInt(args[i+1]));
			}
			catch (NumberFormatException e){
				return false; //this isn't an integer. xP
			}
			if (c==-1) return false;
			if (MapManager.get(c).region!=7) return false;
			if (order.indexOf(c)!=order.lastIndexOf(c)) return false; // no duplicates plox
		}
		int sum = 0;
		for (int i=0; i<order.size(); i++) {
			if (values.get(i)<=0) return false; //no non-positive numbers please
			if (values.get(i)>2) return false; // cannot add >2 influence to a given country
			sum += values.get(i);
		}
		if (sum!=3) return false;
		return true;
	}

	@Override
	public String getDescription() {
		return "Add a total 3 USSR Influence to Central America, no more than 2 per country.";
	}

	@Override
	public String getArguments() {
		return "Distribute three USSR Influence across the Central American countries, no more than 2 per country.";
	}

}

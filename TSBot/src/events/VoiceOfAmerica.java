package events;

import java.awt.Color;
import java.util.ArrayList;

import game.GameData;
import main.Launcher;
import map.MapManager;

public class VoiceOfAmerica extends Card {

	private static ArrayList<Integer> order;
	private static ArrayList<Integer> values;
	
	private static ArrayList<Integer> doable;
	
	@Override
	public void onEvent(int sp, String[] args) {
		// TODO Auto-generated method stub
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Broadcast from the Voice of America")
		.setDescription("Accurate, balanced, and comprehensive news to the world")
		.setFooter("\"The North Korean government doesn't jam us, "
				+ "but they try to keep people from listening through intimidation or worse. "
				+ "But people figure out ways to listen despite the odds. "
				+ "They're very resourceful.\"\n"
				+ "- David Jackson", Launcher.url("people/voa.png"))
		.setColor(Color.blue);
		if (doable.isEmpty()) {
			builder.addField("No countries to target!", "Congratulations. You've managed to dodge Voice of America. Was it worth losing the rest of the world to capitalism?", false);
		}
		else {
			builder.bulkChangeInfluence(order, 1, values);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return "074";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Voice of America";
	}

	@Override
	public int getOps() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getEra() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getAssociation() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isRemoved() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		doable = new ArrayList<Integer>();
		order = new ArrayList<Integer>();
		values = new ArrayList<Integer>();
		int maxInfRem = 0;
		for (int i=21; i<84; i++) { //not-Europe
			if (MapManager.get(i).influence[1]>0) {
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
			if (MapManager.get(order.get(i)).influence[1]+values.get(i)<0) return false; //don't give me negative influence values
			sum += values.get(i);
		}
		if (sum!=-4) return false; // up to 4 influence may be removed...
		return true;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Remove up to 4 influence in non-European countries (no more than 2 Influence per country).";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "Influence values ((*country* *value*)+). All entries in *country* must be aliases of non-European countries, and all entries in *value* must be negative integers at least -2 that do not exceed Soviet influence in the associated country. The entries in *value* must sum to a number at least -4.\n"
				+ "Example: TS.event cl -2 arg -1 peru -1";
	}

}

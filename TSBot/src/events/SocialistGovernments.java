package events;

import java.awt.Color;
import java.util.ArrayList;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import main.Launcher;
import map.MapManager;

public class SocialistGovernments extends Card {
	
	private static ArrayList<Integer> order;
	private static ArrayList<Integer> values;
	
	private static ArrayList<Integer> doable;
	
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Socialist Governments")
		.setFooter("\"De Gasperi’s policy is patience ... He seems to be feeling his way among the explosive problems he has to deal with, but perhaps this wary mine-detecting method is the stabilising force that holds the country in balance.\"\n"
				+ "- Anne McCormick, 1949", Launcher.url("leaders/times.png"))
		.setColor(Color.RED);
		if (doable.isEmpty()) {
			builder.addField("No countries to target!", "Western Europe is devoid of US Influence for some reason. How did that happen?", false);
		}
		else {	
			builder.setDescription("Coalition formed in " + MapManager.get(order.get(0)).name + ".");
			builder.bulkChangeInfluence(order, 0, values);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return !HandManager.effectActive(1004)&&!HandManager.effectActive(83); //disabled by the Iron Lady.
	}

	@Override
	public String getId() {
		return "007";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Socialist Governments";
	}

	@Override
	public int getOps() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public int getEra() {
		if (HandManager.effectActive(1005)) return 1;
		return 0;
	}

	@Override
	public int getAssociation() {
		// TODO Auto-generated method stub
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
		for (int i=0; i<21; i++) {
			if (MapManager.get(i).region<2&&MapManager.get(i).influence[0]>0) {
				doable.add(i);
				maxInfRem += Math.min(MapManager.get(i).influence[0], 2);
			}
		}
		if (maxInfRem<=3) {
			order = doable;
			for (int i : order) {
				values.add(Math.max(-MapManager.get(i).influence[0], -2));
			}
			return true;
		}
		if (args.length%2!=1) return false; //each country must associate with a number
		for (int i=1; i<args.length; i+=2) {
			int c = MapManager.find(args[i]);
			order.add(c);
			try{
				values.add(Integer.parseInt(args[i+1]));
			}
			catch (NumberFormatException e){
				return false; //this isn't an integer. xP
			}
			if (order.indexOf(c)!=order.lastIndexOf(c)) return false; // no duplicates plox
		}
		int sum = 0;
		if (!doable.containsAll(order)) return false;
		for (int i=0; i<order.size(); i++) {
			if (values.get(i)>=0) return false; //no non-negative numbers please
			if (values.get(i)<-2) return false; // cannot remove >2 influence from a given country
			if (MapManager.get(order.get(i)).influence[0]+values.get(i)<0) return false; //don't give me negative influence values
			sum += values.get(i);
		}
		if (sum!=-3) return false; // up to 3 influence may be removed... Won't do exactly three in case US manages to get wiped from Western Europe
		return true;
	}

	@Override
	public String getDescription() {
		return "Remove up to 3 US Influence in Western Europe (no more than 2 per country). *Cannot be played as an event if " + CardList.getCard(83) + " is in effect. Cannot be played for the event on Turns 1 and 2 if the US rolled a 4 or 5 on the `1945 UK Elections` Crisis.*";
	}

	@Override
	public String getArguments() {
		return "Influence values ((*country* *value*)+). All entries in *country* must be aliases of countries in Western Europe, and all entries in *value* must be negative integers at least -2 that do not exceed American influence in the associated country. The entries in *value* must sum to a number at least -3.\n"
				+ "Example: TS.event westgermany -1 gbr -1 espana -1";
	}

}

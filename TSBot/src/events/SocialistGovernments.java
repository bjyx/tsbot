package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import main.Launcher;
import map.MapManager;

public class SocialistGovernments extends Card {
	
	private int[] countries;
	private int[] values;
	
	@Override
	public void onEvent(String[] args) {
		countries = new int[(args.length-1)/2];
		values = new int[(args.length-1)/2];
		for (int i=1; i<args.length; i+=2) {
			countries[(i-1)/2]=MapManager.find(args[i]);
			values[(i-1)/2]=-(Integer.parseInt(args[i+1]));
		}
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Socialist Governments")
			.setDescription("Coalition formed in " + MapManager.get(countries[0]).name + ".")
			.setFooter("\"De Gasperi’s policy is patience ... He seems to be feeling his way among the explosive problems he has to deal with, but perhaps this wary mine-detecting method is the stabilising force that holds the country in balance.\"\n"
					+ "- Anne McCormick, 1949", Launcher.url("countries/gb.png"))
			.setColor(Color.RED);
		for (int i=0; i<countries.length; i++) {
			builder.bulkChangeInfluence(countries, 0, values);
		}
		GameData.txtchnl.sendMessage(builder.build());
	}

	@Override
	public boolean isPlayable() {
		return !HandManager.Effects.contains(83); //disabled by the Iron Lady.
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
		// TODO Auto-generated method stub
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
	public boolean isFormatted(String[] args) {
		if (args.length%2!=1) return false; //each country must associate with a number
		countries = new int[(args.length-1)/2];
		values = new int[(args.length-1)/2];
		for (int i=1; i<args.length; i+=2) {
			countries[(i-1)/2]=MapManager.find(args[i]);
			try{
				values[(i-1)/2]=Integer.parseInt(args[i+1]);
			}
			catch (NumberFormatException e){
				return false; //this isn't an integer. xP
			}
		}
		int sum = 0;
		for (int i=0; i<(args.length-1)/2; i++) {
			if (countries[i]==-1) return false; // countries must exist
			if (MapManager.get(countries[i]).region>1) return false; // all countries must be in Western Europe.
			if (values[i]<=0) return false; //no non-positive numbers please
			if (values[i]>2) return false; // cannot remove >2 influence from a given country
			if (MapManager.get(countries[i]).influence[0]-values[i]<0) return false; //don't give me negative influence values
			sum += values[i];
		}
		if (sum>3) return false; // up to 3 influence may be removed... Won't do exactly three in case US manages to get wiped from Western Europe
		return true;
	}

	@Override
	public String getDescription() {
		return "Remove up to 3 US Influence in Western Europe. *Cannot be played as an event if " + CardList.getCard(83) + " is in effect.*";
	}

	@Override
	public String getArguments() {
		return "Influence values ((*country* *value*)+). All entries in *country* must be aliases of countries in Western Europe, and all entries in *value* must be positive integers at most 2 that do not exceed American influence in the associated country. The entries in *value* must sum to a number at most 3.\n"
				+ "Example: TS.event westgermany 1 gbr 1 espana 1";
	}

}

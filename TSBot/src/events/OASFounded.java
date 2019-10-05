package events;

import java.awt.Color;
import java.util.ArrayList;

import game.GameData;
import main.Launcher;
import map.MapManager;

public class OASFounded extends Card {
	
	private static ArrayList<Integer> order;
	private static ArrayList<Integer> values;

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("OAS Founded")
		.setFooter("\"Democracy for peace, security, and development.\"\n"
				+ "- Motto of the OAS", Launcher.url("people/oas.png"))
		.setColor(Color.BLUE)
		.setDescription("21 countries sign charter at Ninth Pan-American Conference");
		builder.bulkChangeInfluence(order, 0, values);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return false;
	}

	@Override
	public String getId() {
		return "070";
	}

	@Override
	public String getName() {
		return "OAS Founded";
	}

	@Override
	public int getOps() {
		return 1;
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
		order = new ArrayList<Integer>();
		values = new ArrayList<Integer>();
		if (args.length%2!=1) return false;
		for (int i=2; i<args.length; i+=2) {
			int c = MapManager.find(args[i]);
			order.add(c);
			if (c==-1) return false;
			if (order.indexOf(c)!=order.lastIndexOf(c)) return false; // no duplicates plox
			if (MapManager.get(c).region!=8||MapManager.get(c).region!=7) return false;
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
		if (sum!=2) return false;
		return true;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "The US adds a total of 2 Influence to countries in Central and South America.";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "Country-Influence order. All influence values must sum to 2 and the countries must be in Central or South America.";
	}

}

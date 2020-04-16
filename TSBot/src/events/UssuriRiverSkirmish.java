package events;

import java.awt.Color;
import java.util.ArrayList;

import cards.HandManager;
import game.GameData;
import logging.Log;
import main.Launcher;
import map.MapManager;
/**
 * The Ussuri River Skirmish Card.
 * @author adalbert
 *
 */
public class UssuriRiverSkirmish extends Card {

	private static ArrayList<Integer> order;
	private static ArrayList<Integer> values;
	
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("China Attacks Soviet Border Post")
		.setFooter("\"Weapons are an important factor in war, but not the decisive factor; it is people, not things that are decisive.\"\n"
				+ "- Mao Zedong, 1938", Launcher.url("people/mao.png"))
		.setColor(Color.BLUE)
		.setDescription("Border friction on the Ussuri River");
		if (HandManager.China%2==0 || HandManager.China==-1) {
			builder.bulkChangeInfluence(order, 0, values);
		}
		else {
			builder.addField("Assault on Zhenbao Island", "The China Card has been given to the US, face-up.", false);
			Log.writeToLog("China Card is passed to US, face-up.");
			HandManager.China = 0;
		}
		
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "076";
	}

	@Override
	public String getName() {
		return "Ussuri River Skirmish";
	}

	@Override
	public int getOps() {
		return 3;
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
		if (HandManager.China == -1 || HandManager.China%2==0) {
			order = new ArrayList<Integer>();
			values = new ArrayList<Integer>();
			if (args.length%2!=1) return false;
			for (int i=2; i<args.length; i+=2) {
				int c = MapManager.find(args[i]);
				order.add(c);
				if (c==-1) return false;
				if (order.indexOf(c)!=order.lastIndexOf(c)) return false; // no duplicates plox
				if (!MapManager.get(c).inRegion(4)) return false;
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
				if (values.get(i)>2) return false; // cannot be > 2
				sum += values.get(i);
			}
			if (sum!=4) return false;
			return true;
		}
		else {
			return true; //you just get the China here
		}
	}

	@Override
	public String getDescription() {
		return "If the USSR has the China Card, the US receives the China Card face-up. Otherwise, the US places four influence in Asia, no more than 2 influence per country.";
	}

	@Override
	public String getArguments() {
		return "Distribution of four influence, with numbers no larger than 2 and countries in Asia. If the USSR has the China Card, this is not needed.";
	}

}

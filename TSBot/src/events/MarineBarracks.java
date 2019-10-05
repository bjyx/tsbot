package events;

import java.awt.Color;
import java.util.ArrayList;

import game.GameData;
import main.Launcher;
import map.MapManager;

public class MarineBarracks extends Card {

	private static ArrayList<Integer> doable;
	private static ArrayList<Integer> order;
	private static ArrayList<Integer> values;
	
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Barracks Bombed in Beirut")
			.setDescription("")
			.setColor(Color.red)
			.setFooter("\"When we entered Lebanon … there was no Hezbollah. We were accepted with perfumed rice and flowers by the Shia in the south. It was our presence there that created Hezbollah.\"\n"
					+ "- Ehud Barak, 2006", Launcher.url("people/barak.png"));
		builder.changeInfluence(27, 0, -MapManager.get(27).influence[0]); //remove all American influence in Liban
		builder.bulkChangeInfluence(order, 0, values);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "088";
	}

	@Override
	public String getName() {
		return "Marine Barracks Bombing";
	}

	@Override
	public int getOps() {
		return 2;
	}

	@Override
	public int getEra() {
		return 2;
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
		for (int i=21; i<31; i++) { //MidEast
			if (MapManager.get(i).influence[0]>0 && i != 27) { //what's the point of putting Lebanon here?
				doable.add(i);
				maxInfRem += Math.min(MapManager.get(i).influence[0], 2);
			}
		}
		if (maxInfRem<=2) {
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
			if (MapManager.get(order.get(i)).influence[0]+values.get(i)<0) return false; //don't give me negative influence values
			sum += values.get(i);
		}
		if (sum!=-2) return false; // up to 2 influence may be removed...
		return true;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Remove all US Influence from Lebanon and two additional US influence from anywhere in the Middle East.";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "Influence markers. Countries must be within the Middle East (but cannot be Lebanon). Influence values must add to 2. ";
	}

}

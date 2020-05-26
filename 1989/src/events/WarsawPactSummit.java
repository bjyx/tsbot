package events;

import java.awt.Color;
import java.util.ArrayList;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import game.GameData;
import logging.Log;
import main.Launcher;
import map.MapManager;

public class WarsawPactSummit extends Card {

	private static ArrayList<Integer> order;
	private static ArrayList<Integer> values;
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Warsaw Pact Holds Bucharest Summit")
		.setDescription(args[1].equals("hard")?"Hardliners argue for more decisive action":"Reformers advocate small steps")
		.setColor(Color.RED);
		if (args[1].equals("hard") ) {
			Log.writeToLog("Hardliners dominate.");
			GameData.dec = new Decision(1, 1);
			GameData.ops = new Operations(1, CardList.getCard(76).getOpsMod(1), false, true, false, 2, 76);
			GameData.txtcom.sendMessage(GameData.rolecom.getAsMention() + ", you may now conduct your support checks.").complete();
		}
		else {
			Log.writeToLog("Reformers dominate.");
			builder.bulkChangeInfluence(order, 1, values);
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
		return "Warsaw Pact Summit";
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
		if (args[1].equals("hard")) {
			return true;
		}
		else if (args[1].equals("soft")) {
			if (args.length%2!=0) return false;
			for (int i=2; i<args.length; i+=2) {
				int c = MapManager.find(args[i]);
				if (order.indexOf(c)!=-1) return false; // no duplicates plox
				order.add(c);
				if (MapManager.get(c).support[0]>0) return false;
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
			if (sum!=4) return false;
			return true;
		}
		else return false;
	}

	@Override
	public String getDescription() {
		return "The Communist may either conduct 2 Support Checks with a +2 modifier against Student and Intellectual spaces, or place a total of 4 Support in spaces with no Democratic Support.";
	}

	@Override
	public String getArguments() {
		return "Event: The first argument must be either `hard` or `soft`. If `soft`, additional arguments must be influence values.\n"
				+ "Decision: Only occurs if `hard`; uses support check syntax.";
	}

}

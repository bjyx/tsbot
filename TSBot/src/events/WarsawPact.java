package events;

import java.awt.Color;
import java.util.ArrayList;

import cards.HandManager;
import game.GameData;
import logging.Log;
import main.Launcher;
import map.MapManager;
/**
 * The Warsaw Pact Formed Card.
 * @author adalbert
 *
 */
public class WarsawPact extends Card {
	
	private static ArrayList<Integer> order;
	private static ArrayList<Integer> values;
	private static ArrayList<Integer> doable;

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Warsaw Pact Formed")
		.setFooter("\"The trouble with free elections is that you never know how they are going to turn out.\"\n"
				+ "- Vyacheslav Molotov, 1954", Launcher.url("people/molotov.png"))
		.setColor(Color.RED);
		if (args[1].equals("remove") ) {
			builder.setDescription("Stalin takes hard line on " + MapManager.get(order.get(0)).name + "'s autonomy");
			for (int i : order) {
				builder.changeInfluence(i, 0, -MapManager.get(i).influence[0]);
			}
		}
		else {
			builder.setDescription("Stalin further reins in puppet governments");
			builder.bulkChangeInfluence(order, 1, values);
		}
		Log.writeToLog("Warsaw Pact Active.");
		builder.addField("Western Reaction", "**NATO is now formable.**", false);
		HandManager.addEffect(16);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "016";
	}

	@Override
	public String getName() {
		return "Warsaw Pact Formed";
	}

	@Override
	public int getOps() {
		return 3;
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
		doable = new ArrayList<Integer>();
		order = new ArrayList<Integer>();
		values = new ArrayList<Integer>();
		if (args[1].equals("remove")) {
			for (int i=0; i<21; i++) {
				if (MapManager.get(i).inRegion(2)&&MapManager.get(i).influence[0]>0) {
					doable.add(i);
				}
			}
			if (doable.isEmpty()) return false; //you must add if doable is empty, and you can always add
			if (doable.size()<=4) {
				order = doable;
				return true;
			}
			for (int i=2; i<=5; i++) {
				int c = MapManager.find(args[i]);
				if (order.indexOf(c)!=-1) return false; // no duplicates plox
				order.add(c);
			}
			if (!doable.containsAll(order)) return false;
			return true;
		}
		else if (args[1].equals("add")) {
			if (args.length%2!=0) return false;
			for (int i=2; i<args.length; i+=2) {
				int c = MapManager.find(args[i]);
				if (order.indexOf(c)!=-1) return false; // no duplicates plox
				order.add(c);
				if (!MapManager.get(c).inRegion(2)) return false;
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
				if (values.get(i)>2) return false; // cannot add >2 influence to a given country
				sum += values.get(i);
			}
			if (sum!=5) return false;
			return true;
		}
		else return false;
	}

	@Override
	public String getDescription() {
		return "Either add 5 USSR influence to Eastern Europe or remove all USA influence from a maximum of four countries in Eastern Europe. Regardless of the choice, *allows the play of NATO.*";
	}

	@Override
	public String getArguments() {
		return "The first argument must be `remove` or `add`.\n"
				+ "- If the first argument is `remove`, list four Eastern European countries from which USA Influence should be removed. If at most four such countries have USA Influence, this is done automatically, and no further input is required.\n"
				+ "- If the first argument is `add`, distribute five USSR Influence across the Eastern European countries, no more than 2 per country. As no restrictions exist on their placement, this is required.\n"
				+ "_e.g. TS.event remove ddr pl osterreich yugoslavia_\n"
				+ "_e.g. TS.event add ddr 2 polska 2 yu 1_";
	}

}

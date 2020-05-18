package events;

import java.awt.Color;
import java.util.ArrayList;

import game.GameData;
import map.MapManager;

public class Nomenklatura extends Card {

	private static ArrayList<Integer> order;
	private static ArrayList<Integer> values;
	private static ArrayList<Integer> doable;

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Nomenklatura")
		.setDescription("The Communist Elite")
		.setColor(Color.RED);
		if (args[1].equals("remove") ) {
			for (int i : order) {
				builder.changeInfluence(i, 0, -MapManager.get(i).support[0]);
			}
		}
		else {
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
		return "016";
	}

	@Override
	public String getName() {
		return "Nomenklatura";
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
		doable = new ArrayList<Integer>();
		order = new ArrayList<Integer>();
		values = new ArrayList<Integer>();
		if (args[1].equals("remove")) {
			for (int i=0; i<75; i++) {
				if (MapManager.get(i).icon==2&&MapManager.get(i).support[0]>0) { //elite spaces
					doable.add(i);
				}
			}
			if (doable.isEmpty()) return false; //you must add if doable is empty, and you can always add
			order = doable;
			return true;
		}
		else if (args[1].equals("add")) {
			if (args.length%2!=0) return false;
			for (int i=2; i<args.length; i+=2) {
				int c = MapManager.find(args[i]);
				if (order.indexOf(c)!=-1) return false; // no duplicates plox
				order.add(c);
				if (!(MapManager.get(c).icon==2)) return false; // elite spaces only
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
			if (sum!=3) return false;
			return true;
		}
		else return false;
	}

	@Override
	public String getDescription() {
		return "Either add a total of 3 Communist Support to Elite spaces or remove all USA influence from Elite spaces";
	}

	@Override
	public String getArguments() {
		return "The first argument must be `remove` or `add`.\n"
				+ "- If the first argument is `remove`, no further input is required.\n"
				+ "- If the first argument is `add`, distribute three Communist Support across Elite spaces. As no restrictions exist on their placement, this is required.\n"
				+ "_e.g. TS.event remove_\n"
				+ "_e.g. TS.event add byd 1 dresden 2_";
	}

}

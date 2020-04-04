package events;

import java.awt.Color;
import java.util.ArrayList;

import cards.CardList;
import cards.Operations;
import game.GameData;
import logging.Log;
import main.Launcher;
import map.MapManager;

public class Che extends Card {

	public static ArrayList<Integer> doable;
	public static ArrayList<Integer> order;
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Che Guevara Foments Revolution")
		.setFooter("\"If we want to express how we want our children to be, "
				+ "we must say with all the heart of vehement revolutionaries: "
				+ "we want them to be like Che!\"\n"
				+ "- Fidel Castro", Launcher.url("people/castro.png"))
		.setColor(Color.RED);
		
		if (doable.isEmpty()) {
			builder.addField("No countries to target!","What do you mean, there's no Third World country being relentlessly exploited by capitalists!?",false);
		}
		else if (order.isEmpty()) {
			builder.addField("No targeted countires.","*Castro can be heard trashing his office.*",false);
		}
		else {
			boolean flag = true;
			int i = 0;
			builder.setDescription("Communist uprising in " + MapManager.get(order.get(i)).name+"!");
			while (flag && i < order.size()) {
				GameData.ops = new Operations(1, CardList.getCard(107).getOpsMod(1), false, false, true, false, false);
				int orig = MapManager.get(order.get(i)).influence[0];
				GameData.ops.coup(order.get(i));
				if (orig==MapManager.get(order.get(i)).influence[0]) {
					flag = false;
					builder.addField("Coup in " + MapManager.get(order.get(i)).name + "!", "Failed.", false);
					Log.writeToLog("Coup failed.");
				}
				else {
					builder.addField("Coup in " + MapManager.get(order.get(i)).name + "!", "Success - " + (orig-MapManager.get(order.get(i)).influence[0]) + " influence removed.", false);
				}
				i++;
			}
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "107";
	}

	@Override
	public String getName() {
		return "Che";
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
		for (int i=46; i<84; i++) {
			if (!(MapManager.get(i).isBattleground) && MapManager.get(i).influence[0]>0) {
				doable.add(i);
			}
		}
		if (doable.size()<1) {
			order = doable;
			return true;
		}
		for (int i=1; i<args.length; i++) {
			int c = MapManager.find(args[i]);
			order.add(c);
			if (c==-1) return false;
			if (order.indexOf(c)!=order.lastIndexOf(c)) return false;
		}
		if (!doable.containsAll(order)) return false;
		return true;
	}

	@Override
	public String getDescription() {
		return "The USSR __may__ immediately make a coup (using this card's operations value) on a non-battleground in Central America, South America, or Africa. If this removes any US Influence, the USSR may perform another such coup on a different country.";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "Both coup targets, in order. If no targets exist, this is not required (as this will not lose the game).\nTo pass on a legal coup, leave that section blank.";
	}

}

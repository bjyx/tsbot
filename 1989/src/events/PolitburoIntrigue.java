package events;

import java.awt.Color;
import java.util.ArrayList;

import cards.Operations;
import game.GameData;
import main.Common;
import map.MapManager;

public class PolitburoIntrigue extends Card {
	private static ArrayList<Integer> doable;
	private static ArrayList<Integer> order;
	private static ArrayList<Integer> values;
	@Override
	public void onEvent(int sp, String[] args) {
		boolean opponentInfluence=false;
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder
			.setTitle("Zhivkov Removed From Office")
			.setColor(Color.red);
		if (doable.isEmpty()) {
			builder.addField("No spaces to target!", "Why crack down on a loyal populace?", false);
			GameData.txtchnl.sendMessage(builder.build()).complete();
			return;
		}
		builder.bulkChangeInfluence(order, 0, values); //remove opponent sps
		GameData.txtchnl.sendMessage(builder.build()).complete();
		
		for (int i=Common.bracket[5]; i<Common.bracket[6]; i++) {
			if (MapManager.get(i).support[1]>0) {
				opponentInfluence=true;
				break;
			}
		}
		if(opponentInfluence) {
			GameData.ops=new Operations(1, getOpsMod(1), false, true, false, 1, 5); //one check in Bulgaria
			GameData.dec=new Decision(1, 1);
			Common.spChannel(1).sendMessage(Common.spRole(1).getAsMention()+", you may now conduct your support check in Bulgaria.").complete();
		}
		else {
			Common.spChannel(1).sendMessage("For the oddest reason, you cannot conduct a check in Bulgaria.").complete();
		}
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "098";
	}

	@Override
	public String getName() {
		return "Politburo Intrigue";
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
		for (int i=Common.bracket[5]; i<Common.bracket[6]; i++) {
			if (MapManager.get(i).support[0]>0) {
				doable.add(i);
				maxInfRem += MapManager.get(i).support[0];
			}
		}
		if (maxInfRem<=3) {
			order = doable;
			for (int i : order) {
				values.add(-MapManager.get(i).support[0]);
			}
			return true;
		}
		if (args.length%2!=1) return false; //each country must associate with a number
		for (int i=1; i<args.length; i+=2) {
			int c = MapManager.find(args[i]);
			if (c==-1) return false;
			if (order.indexOf(c)!=-1) return false; // no duplicates plox
			order.add(c);
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
			if (values.get(i)<-2) return false; //max 2
			if (MapManager.get(order.get(i)).support[0]+values.get(i)<0) return false; //don't give me negative influence values
			sum += values.get(i);
		}
		if (sum!=-3) return false; // up to 3 influence may be removed...
		return true;
	}

	@Override
	public String getDescription() {
		return "Remove 3 Democratic Support from Bulgaria (no more than 2 per space). Then the Communist may conduct 1 Support Check in Bulgaria using this card's Operations.";
	}

	@Override
	public String getArguments() {
		return "Event: Influence.\n"
				+ "Decision: Support Check.";
	}

}

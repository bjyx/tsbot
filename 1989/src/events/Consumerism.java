package events;

import java.awt.Color;
import java.util.ArrayList;

import cards.Operations;
import game.GameData;
import main.Common;
import map.MapManager;

public class Consumerism extends Card {
	private static ArrayList<Integer> order;
	private static ArrayList<Integer> values;
	private static ArrayList<Integer> doable;

	@Override
	public void onEvent(int sp, String[] args) {
		boolean lw = GameData.getEra()==2;
		boolean opponentInfluence=false;
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder
			.setTitle(GameData.getEra()==2?"Consumerism grips the East":"Consumerism grips workers")
			.setDescription("Demands for higher living standards skyrocket")
			.setColor(Color.blue);
		if (doable.isEmpty()) {
			builder.addField("No spaces to target!", "Congratulations. You've managed to dodge Gorby. Was it worth losing your shot at keeping the Curtain intact?", false);
			GameData.txtchnl.sendMessage(builder.build()).complete();
			return;
		}
		builder.bulkChangeInfluence(order, 1, values); //remove opponent sps
		GameData.txtchnl.sendMessage(builder.build()).complete();
		int c = MapManager.get(order.get(0)).region;
		for (int i=Common.bracket[lw?0:c]; i<Common.bracket[lw?6:(c+1)]; i++) {
			if (MapManager.get(i).support[1]>0) {
				opponentInfluence=true;
				break;
			}
		}
		if(opponentInfluence) {
			GameData.ops=new Operations(0, getOpsMod(0), false, true, false, lw?2:1); // checks
			GameData.dec=new Decision(0, 1); //uses a general channel for ops
			Common.spChannel(0).sendMessage(Common.spRole(0).getAsMention()+", you may now conduct your support check.").complete();
		}
		else {
			Common.spChannel(0).sendMessage(lw?"For the oddest reason, you cannot support check. Period... How the f@%k did you wipe the other side from the board!?":"Did you seriously select a region that would be wiped of influence after the first step.").complete();
		}
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "027";
	}

	@Override
	public String getName() {
		return "Consumerism";
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
		return 0;
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
		boolean lw = GameData.getEra()==2;
		for (int i=0; i<75; i++) {
			if (MapManager.get(i).support[1]>0&&(lw||MapManager.get(i).icon==0)) {
				doable.add(i);
				maxInfRem += MapManager.get(i).support[1];
			}
		}
		if (maxInfRem<=(lw?3:1)) {
			order = doable;
			for (int i : order) {
				values.add(-MapManager.get(i).support[1]);
			}
			return true;
		}
		if (args.length%2!=1) return false; //each country must associate with a number
		for (int i=1; i<args.length; i+=2) {
			int c = MapManager.find(args[i]);
			if (c==-1) return false;
			if (order.indexOf(c)!=-1) return false; // no duplicates plox
			order.add(c);
			if (!lw && MapManager.get(i).icon!=0) return false; //early/mid war worker space restriction
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
			if (MapManager.get(order.get(i)).support[1]+values.get(i)<0) return false; //don't give me negative influence values
			sum += values.get(i);
		}
		if (sum!=(lw?3:1)) return false; // up to 1 influence may be removed... or 3 influence in late war
		return true;
	}

	@Override
	public String getDescription() {
		return GameData.getEra()==2?"The Year is approaching its end. The Democrat removes a total of 3 Communist Support from the board and conducts two Support Checks using the Ops Value of this card.":"The Democrat removes 1 Communist Support from a Worker Space and conducts one Support Check on a Worker space in the same country using this card's Ops value. *Effect improves in the Late Year.*";
	}

	@Override
	public String getArguments() {
		return "Influence. Yes, even if it isn't the Late Year yet.";
	}

}

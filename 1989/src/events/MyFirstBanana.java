package events;

import java.awt.Color;
import java.util.ArrayList;

import cards.Operations;
import game.GameData;
import main.Common;
import map.MapManager;

public class MyFirstBanana extends Card {

	private static ArrayList<Integer> doable;
	private static ArrayList<Integer> order;
	private static ArrayList<Integer> values;
	
	@Override
	public void onEvent(int sp, String[] args) {
		boolean opponentInfluence=false;
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder
			.setTitle("My First Banana")
			.setColor(Color.blue);
		if (doable.isEmpty()) {
			builder.addField("No spaces to target!", "Congratulations. You've managed to dodge My First Banana. Hope the rest of Eastern Europe can still give you food.", false);
			GameData.txtchnl.sendMessage(builder.build()).complete();
			return;
		}
		builder.bulkChangeInfluence(order, 1, values); //remove opponent sps
		GameData.txtchnl.sendMessage(builder.build()).complete();
		
		for (int i=Common.bracket[0]; i<Common.bracket[1]; i++) {
			if (MapManager.get(i).support[1]>0) {
				opponentInfluence=true;
				break;
			}
		}
		if(opponentInfluence) {
			GameData.ops=new Operations(0, getOpsMod(0), false, true, false, 2, 0); //two checks in DDR
			GameData.dec=new Decision(0, 1); //uses a general channel for ops
			Common.spChannel(0).sendMessage(Common.spRole(0).getAsMention()+", you may now conduct your support checks.").complete();
		}
		else {
			Common.spChannel(0).sendMessage("For the oddest reason, you cannot support check East Germany.").complete();
		}
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "091";
	}

	@Override
	public String getName() {
		return "My First Banana";
	}

	@Override
	public int getOps() {
		return 3;
	}

	@Override
	public int getEra() {
		return 2;
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
		doable = new ArrayList<Integer>();
		order = new ArrayList<Integer>();
		values = new ArrayList<Integer>();
		int maxInfRem = 0;
		for (int i=Common.bracket[0]; i<Common.bracket[1]; i++) {
			if (MapManager.get(i).support[1]>0) {
				doable.add(i);
				maxInfRem += MapManager.get(i).support[1];
			}
		}
		if (maxInfRem<=2) {
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
		if (sum!=-2) return false; // up to 4 influence may be removed...
		return true;
	}

	@Override
	public String getDescription() {
		return "Remove 2 Communist Support from East Germany. The Democrat then makes two support checks on East Germany using this card's Operations.";
	}

	@Override
	public String getArguments() {
		return "Event: The Influence.\n"
				+ "Decision: The support checks.";
	}

}

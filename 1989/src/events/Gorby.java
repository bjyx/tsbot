package events;

import java.util.ArrayList;
import cards.Operations;
import game.GameData;
import main.Common;
import map.MapManager;

public class Gorby extends Card {
	
	private static ArrayList<Integer> doable;
	private static ArrayList<Integer> order;
	private static ArrayList<Integer> values;

	@Override
	public void onEvent(int sp, String[] args) {
		boolean opponentInfluence=false;
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder
			.setTitle("Gorbachev Charms the West")
			.setDescription("Soviet Arms Reduction Proposals wildly popular")
			.setColor(Common.spColor(sp));
		builder.bulkChangeInfluence(order, Common.opp(sp), values); //remove opponent sps
		GameData.txtchnl.sendMessage(builder.build()).complete();
		
		for (int i=Common.bracket[0]; i<Common.bracket[6]; i++) {
			if (MapManager.get(i).support[1]>0) {
				opponentInfluence=true;
				break;
			}
		}
		if(opponentInfluence) {
			GameData.ops=new Operations(sp, getOpsMod(sp), false, true, false, 1); //one check
			GameData.dec=new Decision(sp, 1); //uses a general channel for ops
			Common.spChannel(0).sendMessage(Common.spRole(0).getAsMention()+", you may now conduct your support check.").complete();
		}
		else {
			Common.spChannel(0).sendMessage("For the oddest reason, you cannot support check Poland.").complete();
		}
	}

	@Override
	public boolean isPlayable(int sp) {
		return GameData.getStab()>2; //Breakaway Baltics not played
	}

	@Override
	public String getId() {
		return "014";
	}

	@Override
	public String getName() {
		return "Gorbachev Charms the West";
	}

	@Override
	public int getOps() {
		return 4;
	}

	@Override
	public int getEra() {
		return 0;
	}

	@Override
	public int getAssociation() {
		return 2;
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
		for (int i=0; i<75; i++) {
			if (MapManager.get(i).support[Common.opp(sp)]>0) {
				doable.add(i);
				maxInfRem += Math.min(MapManager.get(i).support[Common.opp(sp)], 2);
			}
		}
		if (maxInfRem<=2) {
			order = doable;
			for (int i : order) {
				values.add(Math.max(-MapManager.get(i).support[Common.opp(sp)], -2));
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
			if (MapManager.get(order.get(i)).support[Common.opp(sp)]+values.get(i)<0) return false; //don't give me negative influence values
			sum += values.get(i);
		}
		if (sum!=-2) return false; // up to 4 influence may be removed...
		return true;
	}

	@Override
	public String getDescription() {
		return "Remove 2 of your Opponent's Support Points from the board. Then, conduct one support check using this card's Operations value.";
	}

	@Override
	public String getArguments() {
		return "Event: SPs to remove. \n"
				+ "Decision: Support check.";
	}

}

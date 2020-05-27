package events;

import java.awt.Color;
import java.util.ArrayList;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import game.GameData;
import map.MapManager;

public class CivicForum extends Card {

	private static ArrayList<Integer> order;
	private static ArrayList<Integer> values;
	
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Civic Forum")
			.setColor(Color.blue);
		builder.changeVP(1);
		builder.bulkChangeInfluence(order, sp, values);
		GameData.txtchnl.sendMessage(builder.build()).complete();
		if (MapManager.get(36).isControlledBy()==0) {
			GameData.ops = new Operations(0, CardList.getCard(90).getOpsMod(0), false, true, false, 2, 2);
			GameData.dec = new Decision(0, 1);
			GameData.txtdem.sendMessage(GameData.roledem.getAsMention() + ", you may now conduct your Support Checks in Czechoslovakia.").complete();
		}
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "090";
	}

	@Override
	public String getName() {
		return "Civic Forum";
	}

	@Override
	public int getOps() {
		return 4;
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
		order = new ArrayList<Integer>();
		values = new ArrayList<Integer>();
		if (args.length%2!=1) return false;
		for (int i=1; i<args.length; i+=2) {
			int c = MapManager.find(args[i]);
			if (c==-1) return false;
			if (order.indexOf(c)!=-1) return false; // no duplicates plox
			order.add(c);
			if (!MapManager.get(c).inRegion(2)) return false; // must be Czechoslovakia
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

	@Override
	public String getDescription() {
		return "The Democrat gains 1 VP and places 4 Democratic Support in Czechoslovakia. Then, if the Czech Writers Space is controlled by the Democrat, then the Democrat may conduct Support Checks in Czechoslovakia using this card's Operations.";
	}

	@Override
	public String getArguments() {
		return "Event: the Influence.\n"
				+ "Decision: the Support Checks.";
	}

}

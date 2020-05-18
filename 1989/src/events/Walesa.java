package events;

import java.awt.Color;
import java.util.ArrayList;

import cards.HandManager;
import cards.Operations;
import game.GameData;
import main.Common;
import main.Launcher;
import map.MapManager;
/**
 * The Walesa Card.
 * @author wes4zhang
 *
 */
public class Walesa extends Card {

	private static ArrayList<Integer> order;
	private static ArrayList<Integer> values;

	@Override
	public void onEvent(int sp, String[] args) {
		boolean opponentInfluence=false;
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder
			.setColor(Color.blue);
		builder.bulkChangeInfluence(order, 0, values);
		GameData.txtchnl.sendMessage(builder.build()).complete();
		
		for (int i=Common.bracket[1]; i<Common.bracket[2]; i++) {
			if (MapManager.get(i).support[1]>0) {
				opponentInfluence=true;
				break;
			}
		}
		if(opponentInfluence) {
			GameData.ops=new Operations(sp, getOpsMod(sp), false, true, false, 2, 1); //two checks in poland
			GameData.dec=new Decision(sp, 1); //uses a general channel for ops
			Common.spChannel(0).sendMessage(Common.spRole(0).getAsMention()+", you may now conduct your support check in Poland.").complete();
		}
		else {
			Common.spChannel(0).sendMessage("For the oddest reason, you cannot support check Poland.").complete();
		}
	}

	@Override
	public boolean isPlayable(int sp) {
		return HandManager.effectActive(2);
	}

	@Override
	public String getId() {
		return "003";
	}

	@Override
	public String getName() {
		return "Wałęsa";
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
		return true;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		order = new ArrayList<Integer>();
		values = new ArrayList<Integer>();
		if (args.length%2!=1) return false;
		for (int i=1; i<args.length; i+=2) {
			int c = MapManager.find(args[i]);
			order.add(c);
			if (c==-1) return false;
			if (order.indexOf(c)!=order.lastIndexOf(c)) return false; // no duplicates plox
			if (!MapManager.get(c).inRegion(1)) return false; // must be Poland
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
		return "Place 4 Democratic SPs in Poland (unrestricted). The Democrat may conduct two support checks in Poland using this card.";
	}

	@Override
	public String getArguments() {
		return "Event: Influence placement. \n"
				+ "Decision: Support Checks.";
	}

}

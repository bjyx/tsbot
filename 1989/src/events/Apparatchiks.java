package events;

import java.awt.Color;
import java.util.ArrayList;

import cards.Operations;
import game.GameData;
import main.Common;
import map.MapManager;
/**
 * The Apparatchiks Card
 * @author adalbert
 *
 */
public class Apparatchiks extends Card {
	private static ArrayList<Integer> order;
	private static ArrayList<Integer> values;

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder
			.setColor(Color.red);
		builder.bulkChangeInfluence(order, 1, values);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "012";
	}

	@Override
	public String getName() {
		return "Apparatchiks";
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
		order = new ArrayList<Integer>();
		values = new ArrayList<Integer>();
		if (args.length%2!=1) return false;
		for (int i=1; i<args.length; i+=2) {
			int c = MapManager.find(args[i]);
			order.add(c);
			if (c==-1) return false;
			if (order.indexOf(c)!=order.lastIndexOf(c)) return false; // no duplicates plox
			if (MapManager.get(c).icon!=3) return false; // must be bureaucratic
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

	@Override
	public String getDescription() {
		return "Place 3 Communist SPs in Bureaucratic spaces.";
	}

	@Override
	public String getArguments() {
		return "Influence. All spaces must be bureaucratic. The values must sum to 3.";
	}

}

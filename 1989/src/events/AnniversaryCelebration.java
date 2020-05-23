package events;

import java.awt.Color;
import java.util.ArrayList;

import game.GameData;
import main.Launcher;
import map.MapManager;

public class AnniversaryCelebration extends Card {

	private static ArrayList<Integer> order;
	private static ArrayList<Integer> values;
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("East Germany Celebrates 40th Anniversary")
			.setColor(Color.red);
		builder.bulkChangeInfluence(order, 1, values)
		.changeVP(-1);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "051";
	}

	@Override
	public String getName() {
		return "40th Anniversary Celebration";
	}

	@Override
	public int getOps() {
		return 2;
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
			if (!MapManager.get(c).inRegion(0)) return false; // must be EGe
			if (MapManager.get(c).icon>=2) return false; //must be farmer/worker
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
		if (sum!=(GameData.getScore()<0?4:2)) return false; //must sum to 4 if score is negative, and 2 if score is positive.
		return true;
	}

	@Override
	public String getDescription() {
		return "If the Communist is ahead in VPs, place 4 Communist SPs in East Germany; otherwise, place 2. Then, the Communist gains 1 VP.";
	}

	@Override
	public String getArguments() {
		return "The Support to place.";
	}

}

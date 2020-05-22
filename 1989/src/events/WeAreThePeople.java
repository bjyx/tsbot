package events;

import java.awt.Color;
import java.util.ArrayList;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import main.Common;
import main.Launcher;
import map.MapManager;

public class WeAreThePeople extends Card {

	private static ArrayList<Integer> order;
	private static ArrayList<Integer> values;
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Riots in Leipzig Grow Beyond Control")
			.setDescription("Chants of \"We Are The People!\" fill the streets")
			.setColor(Color.blue);
		builder.bulkChangeInfluence(order, sp, values);
		HandManager.addEffect(48);
		builder.addField("Weak Government", "The Communist may no longer conduct Support Checks in Leipzig.", false);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "048";
	}

	@Override
	public String getName() {
		return "We Are The People!";
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
		if (args.length==1) return true; //nothing happens
		if (args.length%2!=1) return false; //each country must associate with a number
		for (int i=1; i<args.length; i+=2) {
			int c = MapManager.find(args[i]);
			if (order.indexOf(c)!=-1) return false; // no duplicates plox
			order.add(c);
			if (c==-1) return false; //must exist
			if (!MapManager.get(c).inRegion(0)) return false; //must be East Germany
			try{
				values.add(Integer.parseInt(args[i+1]));
			}
			catch (NumberFormatException e){
				return false; //this isn't an integer. xP
			}
		}
		int sum = 0;
		int abssum = 0;
		for (int i=0; i<order.size(); i++) {
			if (values.get(i)==0) return false; //no zeroes
			if (order.get(i)==9^values.get(i)>0) return false; //can't put influence into Lutheran or influence out of anywhere else
			if (values.get(i)>2) return false; // cannot add >2 influence to a given country
			if (MapManager.get(order.get(i)).support[0]+values.get(i)<0) return false; //don't give me negative influence values
			sum += values.get(i);
			abssum += Math.abs(values.get(i));
		}
		if (sum!=0) return false; // there should be no change in the amount of influence
		if (abssum>8) return false; //up to 4 influence may be moved
		return true;
	}

	@Override
	public String getDescription() {
		return "The Democrat may move up to 4 Democratic Support from the Lutheran Church to any spaces in Germany (no more than 2 per space). *The Communist may no longer make Support Checks in Leipzig*.";
	}

	@Override
	public String getArguments() {
		return "The support to move around. Spaces must be in East Germany. The value associated with the Lutheran Church must be negative; all other values must be positive. The absolute values of the values sum to at most eight; the values themselves must sum to zero.";
	}

}

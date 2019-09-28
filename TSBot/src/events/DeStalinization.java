package events;

import java.awt.Color;
import java.util.ArrayList;

import game.GameData;
import main.Launcher;
import map.MapManager;

public class DeStalinization extends Card {
	
	private static boolean fringeCase = false;
	private static ArrayList<Integer> order;
	private static ArrayList<Integer> values;

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Khrushchev Denounces Stalinist Policies")
		.setDescription("Rejects predecessor's cult of personality and paranoia")
		.setFooter("\"\"\n"
				+ "- Richard Nixon, 1956", Launcher.url("countries/us.png"))
		.setColor(Color.RED);
			if (fringeCase) {
				builder.addField("No countries to target!", "For some reason, there is either no Soviet influence to move at all, or the whole world is under the sway of the USA. Some Soviet Premier. :s", false);
			}
			else if (order.isEmpty()) {
				builder.addField("No influence moved", "¯\\_(ツ)_/¯", false);
			}
			else {
				builder.bulkChangeInfluence(order, 1, values);
			}
			GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return "033";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "De-Stalinization";
	}

	@Override
	public int getOps() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public int getEra() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAssociation() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean isRemoved() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isFormatted(String[] args) {
		order = new ArrayList<Integer>();
		values = new ArrayList<Integer>();
		if (args.length==1) return true;
		boolean flag1 = true; // false if any soviet influence is on the map
		boolean flag2 = true; // false if any country is not controlled by the US
		for (int i = 0; i<84; i++) {
			if (MapManager.get(i).influence[1]>0) flag1=false;
			if (MapManager.get(i).isControlledBy()!=0) flag2=false;
			if (!(flag1||flag2)) break;
		}
		if (flag1||flag2) {
			fringeCase = true;
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
		int abssum = 0;
		for (int i=0; i<order.size(); i++) {
			if (order.get(i)==-1) return false;
			if (MapManager.get(order.get(i)).isControlledBy()==0 && values.get(i)>0) return false; //can't put influence in US controlled countries
			if (values.get(i)>2) return false; // cannot add >2 influence to a given country
			if (MapManager.get(order.get(i)).influence[0]+values.get(i)<0) return false; //don't give me negative influence values
			sum += values.get(i);
			abssum += Math.abs(values.get(i));
		}
		if (sum!=0) return false; // there should be no change in the amount of influence
		if (abssum>8) return false; //up to 4 influence may be moved
		return true;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Move up to four USSR influence; the destinations can be any country not controlled by the US.";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "Countries and influence values. The values are non-zero, but must add to zero. Their absolute values can add up to at most eight. No influence value may cause a country's USSR Influence level to fall below zero. If the number is associated with a positive integer, the country should not be controlled by the USA. Can omit all arguments altogether - this will have no effect.";
	}

}

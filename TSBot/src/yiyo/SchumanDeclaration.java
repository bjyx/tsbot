package yiyo;

import java.awt.Color;
import java.util.ArrayList;

import events.Card;
import events.CardEmbedBuilder;
import game.GameData;
import main.Launcher;
import map.MapManager;
/**
 * The Schumann Declaration Card from the Year-In and Year-Out Pack.
 * @author adalbert
 *
 */
public class SchumanDeclaration extends Card {

	private static boolean fringeCase = false;
	private static ArrayList<Integer> order;
	private static ArrayList<Integer> values;
	
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Schuman Declaration")
		.setDescription("France, West Germany and others unify coal and steel markets")
		.setFooter("\"Europe will not be made all at once, or according to a single plan. It will be built through concrete achievements which first create a de facto solidarity.\"\n"
				+ "- Robert Schuman, 1950", Launcher.url("yiyo/schuman.png"))
		.setColor(Color.BLUE);
			if (fringeCase) {
				builder.addField("No countries to target!", "For some reason, there is no USA influence in the important countries of Western Europe. Some President, and shame on you for not defending the Isles properly. :s", false);
			}
			else if (order.isEmpty()) {
				builder.addField("No influence moved", "¯\\\\\\_(ツ)\\_/¯", false);
			}
			else {
				builder.bulkChangeInfluence(order, 0, values);
			}
			GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "120";
	}

	@Override
	public String getName() {
		return "Schuman Declaration";
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
		if (args.length==1) return true; //null order
		boolean flag1 = true; // false if any us influence is in a WE country (region<2) adjacent to france/de
		for (int i = 0; i<19; i++) {
			if (MapManager.get(i).influence[0]>0&&MapManager.get(i).region<2&&(MapManager.get(i).adj.contains(19)||MapManager.get(i).adj.contains(8))) {
				flag1=false;
				break;
			}
		}
		if (flag1) {
			fringeCase = true;
			return true;
		}
		if (args.length%2!=1) return false; //each country must associate with a number
		for (int i=1; i<args.length; i+=2) {
			int c = MapManager.find(args[i]);
			order.add(c);
			try{
				values.add(Integer.parseInt(args[i+1]));
			}
			catch (NumberFormatException e){
				return false; //this isn't an integer. xP
			}
			if (order.indexOf(c)!=order.lastIndexOf(c)) return false; // no duplicates plox
		}
		int sum = 0;
		int abssum = 0;
		for (int i=0; i<order.size(); i++) {
			if (order.get(i)==-1) return false;
			if (MapManager.get(order.get(i)).region>=2) return false; //must be western europe
			if (!(MapManager.get(order.get(i)).adj.contains(19)||MapManager.get(order.get(i)).adj.contains(8))) return false; //must be adj to Germany or France, including West Germany and France
			if (values.get(i)>2) return false; // cannot add >2 influence to a given country
			if (MapManager.get(order.get(i)).influence[0]+values.get(i)<0) return false; //don't give me negative influence values
			sum += values.get(i);
			abssum += Math.abs(values.get(i));
		}
		if (sum!=0) return false; // there should be no change in the amount of influence
		if (abssum>10) return false; //up to 4 influence may be moved
		return true;
	}

	@Override
	public String getDescription() {
		return "The US may move up to five US influence between Western European countries adjacent to West Germany and France (including West Germany and France). No more than 2 influence may be placed in any given country.\n"
				+ "Allowed Countries: France, West Germany, Benelux, Denmark, Austria, Italy, Spain/Portugal, UK\n"
				+ "Disallowed: East Germany, Algeria";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "Countries and influence values. The values are non-zero, but must add to zero. Their absolute values can add up to at most ten. No influence value may cause a country's USSR Influence level to fall below zero. If the number is associated with a positive integer, the country should not be controlled by the USA. Can omit all arguments altogether - this will have no effect.";
	}

}

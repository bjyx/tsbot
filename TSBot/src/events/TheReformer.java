package events;

import java.awt.Color;
import java.util.ArrayList;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import logging.Log;
import main.Launcher;
import map.MapManager;

public class TheReformer extends Card {

	private static ArrayList<Integer> order;
	private static ArrayList<Integer> values;
	
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Gorbachev Takes Power in the Soviet Union")
		.setDescription("New Thinking for the USSR and the World")
		.setFooter("\"Our rockets can find Halley's comet, and fly to Venus with amazing accuracy, but side by side with these scientific and technical triumphs is an obvious lack of efficiency in using scientific achievements for economic needs, and many Soviet household appliances are of poor quality.\"\n"
				+ "- Mikhail Gorbachev, 1987", Launcher.url("people/gorbachev.png"))
		.setColor(Color.RED);
		builder.bulkChangeInfluence(order, 1, values);
		builder.addField("Perestroika", "With the new thinker in power, the policy of " + CardList.getCard(90) + " will gain more traction.", false);
		builder.addField("Glasnost", "The USSR may no longer conduct coups in Europe.", false);
		HandManager.addEffect(87);
		Log.writeToLog("The Reformer Active.");
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "087";
	}

	@Override
	public String getName() {
		return "The Reformer";
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
			if (MapManager.get(c).region>2) return false;
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
		for (int i=0; i<order.size(); i++) {
			if (values.get(i)<=0) return false; //no non-positive numbers please
			if (values.get(i)>2) return false; // cannot add >2 influence to a given country
			sum += values.get(i);
		}
		if (sum!=(GameData.getScore()<0?6:4)) return false;
		return true;
	}

	@Override
	public String getDescription() {
		return "Add 4 USSR Influence to Europe, no more than 2 per country; 6 may be added instead if the USSR is leading in Victory Points. *Improves the effect of " + CardList.getCard(90) + ".*";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "Influence markers. Countries must be in Europe and numbers must add up to 4 (6 if the USSR leads in VPs).";
	}

}

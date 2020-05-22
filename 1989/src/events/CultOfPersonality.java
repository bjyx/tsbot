package events;

import java.awt.Color;
import java.util.ArrayList;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import main.Launcher;
import map.MapManager;
/**
 * The Cult of Personality Card, specifically Ceausescu's cult.
 * @author wes4zhang
 *
 */
public class CultOfPersonality extends Card {

	private static ArrayList<Integer> order;
	private static ArrayList<Integer> values;
	
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Ceaușescu's Cult of Personality")
			.setFooter("\"Roman emperor! No, he already thinks he is a god.\"\n" + 
					"- A tour guide in response to David Binder, 1986", Launcher.url("people/qmark.png"))
			.setColor(Color.red);
		builder.bulkChangeInfluence(order, 1, values);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return !HandManager.effectActive(97); //tyrant is gone
	}

	@Override
	public String getId() {
		return "010";
	}

	@Override
	public String getName() {
		return "Cult of Personality";
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
			if (!MapManager.get(c).inRegion(4)) return false; // must be Romania
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
			if (values.get(i)>2) return false; //must be at most 2 per space.
			sum += values.get(i);
		}
		if (sum!=4) return false; //must sum to 4
		return true;
	}

	@Override
	public String getDescription() {
		if (HandManager.effectActive(97)) return "Ceaușescu has been deposed! Play for Operations only.";
		return "Add 4 Communist SPs to spaces in Romania associated with Workers or Farmers, no more than 2 per space. *This event prevented by " + CardList.getCard(97) + ".*";
	}

	@Override
	public String getArguments() {
		return "Influence values. All spaces must be Romanian Farmer/Worker spaces. All values must be positive integers at most 2, and must sum to 4.";
	}

}

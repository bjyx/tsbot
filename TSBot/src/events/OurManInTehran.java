package events;

import java.util.ArrayList;
import java.util.Random;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import map.MapManager;
import net.dv8tion.jda.core.EmbedBuilder;

public class OurManInTehran extends Card {

	public static ArrayList<Integer> cards = new ArrayList<Integer>();
	
	//flavor purposes
	public static int country = 23;
	private static final String[] leader = new String[] {"Anwar al-Sadat","Sabah III","Mohammed Reza Pahlavi","Faisal II","Menachem Begin","Hussein of Jordan","Fuad Chehab","Idris I","Faisal bin Abdulaziz al Saud","Shukri al-Quwatli"};
	public static final String[] capital = new String[] {"Cairo","Kuwait City","Tehran","Baghdad","Tel Aviv","Amman","Beirut","Tripoli","Riyadh","Damascus"};
	@Override
	public void onEvent(int sp, String[] args) {
		EmbedBuilder builder = new EmbedBuilder()
				.setTitle("Our Man in " + capital[country-21])
				.setAuthor(leader[country-21]);
		cards = new ArrayList<Integer>();
		for (int i=0; i<5; i++) {
			cards.add(HandManager.Deck.remove(new Random().nextInt(HandManager.Deck.size())));
		}
		for (Integer c : cards) {
			builder.addField(""+CardList.getCard(c), CardList.getCard(c).getDescription(), false);
		}
		GameData.dec = new Decision(0, 108);
		GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", here are the next five cards in the deck. Select the ones you wish to discard, or use `0` in case you want to leave all of them in the deck. (TS.decide **[ID]** **[ID]** ... )");
		GameData.txtusa.sendMessage(builder.build());
	}

	@Override
	public boolean isPlayable(int sp) {
		if (MapManager.get(23).isControlledBy()==0) {
			country = 23;
			return true;
		}
		for (int i=21; i<31; i++) {
			if (MapManager.get(i).isControlledBy()==0) {
				country = i;
				return true;
			}
		}
		return false;
	}

	@Override
	public String getId() {
		return "108";
	}

	@Override
	public String getName() {
		return "Our Man in Tehran";
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
		return 0;
	}

	@Override
	public boolean isRemoved() {
		return true;
	}

	@Override
	public boolean isFormatted(String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		return "The US gets to look at the next five cards in the deck and discard any of them, returning the rest to the deck. The deck is then reshuffled. *Only playable if the US controls at least one Middle Eastern country.*";
	}

	@Override
	public String getArguments() {
		return "Event: None.\n"
				+ "Decision: any number of IDs among the five drawn cards.";
	}

}

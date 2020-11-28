package events;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import game.GameData;
import net.dv8tion.jda.api.EmbedBuilder;

public class GoodbyeLenin extends Card {
	
	public static final ArrayList<Integer> red = new ArrayList<Integer>(Arrays.asList(2, 24, 26, 33, 54, 73, 81, 84));
	public static ArrayList<Integer> found = new ArrayList<Integer>();

	@Override
	public void onEvent(int sp, String[] args) {
		EmbedBuilder builder = new EmbedBuilder().setTitle("Red-Handed").setColor(Color.red);
		for (int c : HandManager.ComHand) {
			if (red.contains(c)) {
				found.add(c);
				builder.addField(CardList.getCard(c).toString(), CardList.getCard(c).getDescription(), false);
			}
		}
		GameData.txtdem.sendMessage(builder.build()).complete();
		GameData.dec = new Decision(0, 46);
		GameData.ops = new Operations(0, CardList.getCard(46).getOpsMod(0), true, true, false);
		GameData.txtdem.sendMessage(GameData.roledem.getAsMention() + ", select one of these cards to play for the event, or this card's ID (46) to play for Operations.").complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "046";
	}

	@Override
	public String getName() {
		return "Goodbye Lenin!";
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
		return true;
	}

	@Override
	public String getDescription() {
		return "The Communist reveals all of the following cards if they are in his hand:"
				+ "\n- " + CardList.getCard(2)
				+ "\n- " + CardList.getCard(24)
				+ "\n- " + CardList.getCard(26)
				+ "\n- " + CardList.getCard(33)
				+ "\n- " + CardList.getCard(54)
				+ "\n- " + CardList.getCard(73)
				+ "\n- " + CardList.getCard(81)
				+ "\n- " + CardList.getCard(84)
				+ "\nThe Democrat may take any one of these cards and play it for the event. Alternatively, the Democrat may play this card for its Operations.";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "Event: None.\n"
				+ "Decision 1: Any revealed card's ID for the event, or 46 (this card's ID) to play it for Operations, and any associated arguments.";
	}

}

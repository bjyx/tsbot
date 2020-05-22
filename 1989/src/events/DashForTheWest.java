package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import game.Die;
import game.GameData;
import main.Common;
import map.MapManager;

public class DashForTheWest extends Card {

	public static int card;
	@Override
	public void onEvent(int sp, String[] args) {
		int roll = new Die().roll();
		int c=0;
		for (int i=Common.bracket[0];i<Common.bracket[1];i++) {
			if (MapManager.get(i).isControlledBy()==1) {
				c++;
			}
		}
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.addField("Attempted Crossing","Roll: :game_die:"+roll, false);
		if (roll>c) {
			builder.setTitle("Student Successfully Crosses Berlin Wall")
			.setColor(Color.blue);
			builder.changeVP(1);
			builder.addField("Dash for the West", "The Democrat will now trigger a Democrat-associated starred event from the discard.", false);
			GameData.dec = new Decision(0,36);
			boolean temp = false;
			for (Integer i : HandManager.Discard) {
				if (CardList.getCard(i).isPlayable(0)&&CardList.getCard(i).isRemoved()&&CardList.getCard(i).getAssociation()==0) {
					temp = true;
					break;
				}
			}
			if (temp) {
				GameData.dec = new Decision(0,36);
			}
			else {
				builder.addField("No valid cards!", "...if there were any Democrat starred events in the discard pile.", false);
			}
		}
		else {
			builder.setTitle("Student Shot Attempting to Cross Berlin Wall")
			.setColor(Color.red);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "036";
	}

	@Override
	public String getName() {
		return "Dash for the West";
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
		return true;
	}

	@Override
	public String getDescription() {
		return "The Democrat rolls a die. If this roll is greater than the number of spaces controlled by the Communist in East Germany, the Democrat gains 1 VP and may trigger one Democrat-associated starred(\\*) event in the discard pile.";
	}

	@Override
	public String getArguments() {
		return "Event: None.\n"
				+ "Decision: The card to extract from the discard pile, and the arguments (if any) that go with that event.";
	}

}

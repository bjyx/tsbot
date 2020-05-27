package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import game.Die;
import game.GameData;
import main.Common;
import main.Launcher;
import map.MapManager;

public class TheWallMustGo extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		int[] die = {0,0};
		int[] mod = {0,0};
		int[] wins = {0,0};
		for (int i=Common.bracket[0]; i<Common.bracket[1]; i++) {
			if (MapManager.get(i).isControlledBy()!=-1) mod[MapManager.get(i).isControlledBy()]++;
		}
		
		while (wins[0]==2||wins[1]==2) {
			die[0] = new Die().roll() + mod[0];
			die[1] = new Die().roll() + mod[1];
			if (die[0]>die[1]) wins[0]++;
			if (die[1]>die[0]) wins[1]++;
			builder.addField("<:InflDC:696610045730881588>:"+CardEmbedBuilder.numbers[die[0]]+":-:" + CardEmbedBuilder.numbers[die[1]]+":<:InflCC:696610045575692308>",die[0]==die[1]?"A tie - roll again.":("A victory for "+(die[0]>die[1]?"the Democrats":"the Communists")+ "!"),false);
		}
		builder.setTitle(die[0]>die[1]?"Berlin Wall Torn Down":"Travel Restrictions Still Stand")
			.setColor(die[0]>die[1]?Color.BLUE:Color.RED);
		if (die[0]>die[1]) {
			builder.changeVP(3);
			GameData.dec = new Decision(1, 86);
			HandManager.addEffect(86); //improves Kohl
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "086";
	}

	@Override
	public String getName() {
		return "\"The Wall Must Go!\"";
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
		return "Both sides roll a die and add the number of spaces in East Germany they control. Reroll ties. If the Democrat wins two out of three rounds, he gains 3 VP and the Communist must remove 3 Communist Support from East Germany. *Success also improves the effect of " + CardList.getCard(87) + ".*";
	}

	@Override
	public String getArguments() {
		return "Event: None.\n"
				+ "Decision: Influence, if necessary.";
	}

}

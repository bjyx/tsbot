package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import game.Die;
import game.GameData;
import main.Common;
import map.MapManager;

public class Modrow extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		int roll = new Die().roll();
		int c=0;
		for (int i=Common.bracket[0];i<Common.bracket[1];i++) {
			if (MapManager.get(i).isControlledBy()==0) {
				c++;
			}
		}
		CardEmbedBuilder builder = new CardEmbedBuilder();
		HandManager.addEffect(83);
		builder.addField("Ouster", CardList.getCard(15) + " may no longer be played for the event.", false);
		builder.addField("Attempted Reform","Roll: :game_die:"+roll, false);
		if (roll>c) {
			builder.setTitle("Modrow")
			.setColor(Color.red);
			GameData.dec = new Decision(1,83);
		}
		else {
			builder.setTitle("Too Little, Too Late")
			.setDescription("Modrow's reforms fall flat as East Germany moves towards revolution")
			.setColor(Color.blue);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "083";
	}

	@Override
	public String getName() {
		return "Modrow";
	}

	@Override
	public int getOps() {
		return 2;
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
		return true;
	}

	@Override
	public String getDescription() {
		return "The Communist rolls a die. If this roll is greater than the number of spaces controlled by the Democrat in East Germany, place 4 Communist Support in East Germany (no more than 2 per space).";
	}

	@Override
	public String getArguments() {
		return "Event: None.\n"
				+ "Decision: Influence, if needed. ";
	}

}

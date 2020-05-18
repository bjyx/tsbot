package events;

import java.awt.Color;

import cards.HandManager;
import game.GameData;

public class RoundTableTalks extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Talks organized")
			.setDescription("Communist government to debate inclusion of banned parties in politics")
			.setColor(Color.blue);
		HandManager.addEffect(17);
		builder.addField("Round Table", "When the next Power Struggle happens, the Democrat gains two Power Struggle cards, and the Communist loses two.", false);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "017";
	}

	@Override
	public String getName() {
		return "Round Table Talks";
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
		return false;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		return "*When the next Power Struggle starts, the Democrat may draw two Power Struggle cards from the Communist hand.*";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

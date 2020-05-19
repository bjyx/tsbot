package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import map.MapManager;

public class StNicholasChurch extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("St. Nicholas Church conducts Peace Prayers")
			.setDescription("Leipzig residents demand peaceful resolution to Cold War")
			.setColor(Color.blue);
		builder.changeInfluence(9, 0, Math.max(0, MapManager.get(9).stab-MapManager.get(9).support[0]));
		HandManager.addEffect(24);
		builder.addField("Prelude", CardList.getCard(61) + " may now be played for the event.", false);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "024";
	}

	@Override
	public String getName() {
		return "St. Nicholas Church";
	}

	@Override
	public int getOps() {
		return 1;
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
		return "The Democrat gains control of the Lutheran Church in East Germany. *Allows play of " + CardList.getCard(61) + ".*";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

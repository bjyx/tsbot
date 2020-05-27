package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import game.GameData;
import logging.Log;
import main.Common;
import main.Launcher;
import map.MapManager;

public class LaszloTokes extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Romanian Pastor To Be Evicted")
			.setColor(Color.blue);
		builder.changeInfluence(55, 0, 1);
		builder.changeInfluence(59, 0, 1);
		HandManager.addEffect(73);
		Log.writeToLog("László Tőkés active.");
		builder.addField("Prelude", CardList.getCard(107) + " may now be played for the event.", false);
		GameData.txtchnl.sendMessage(builder.build()).complete();
		GameData.dec = new Decision(0, 1);
		GameData.ops = new Operations(0, CardList.getCard(73).getOpsMod(0), true, true, false, 2, 4);
		GameData.txtdem.sendMessage(GameData.roledem.getAsMention() + ", you may now conduct your Operations in Romania.").complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "073";
	}

	@Override
	public String getName() {
		return "László Tőkés";
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
	public boolean isFormatted(int sp, String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Place 1 Democratic Support in Harghita/Covasna and 1 Democratic Support in Timișoara. Then the Democrat conducts Operations in Romania using this card. *Allows the play of " + CardList.getCard(107) + ".*";
	}

	@Override
	public String getArguments() {
		return "Event: None.\n"
				+ "Decisions: Operations.";
	}

}

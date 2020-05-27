package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import game.GameData;
import logging.Log;

public class KohlReunification extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Kohl Proposes Reunification")
			.setColor(Color.blue);
		builder.changeVP(2);
		GameData.txtchnl.sendMessage(builder.build()).complete();
		if (HandManager.effectActive(86)) {
			GameData.ops = new Operations(0, CardList.getCard(87).getOpsMod(0), true, true, false);
			GameData.dec = new Decision(0, 1);
			GameData.txtdem.sendMessage(GameData.roledem.getAsMention() + ", you may now conduct your Operations in Romania.").complete();
		}
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
		return "Kohl Proposes Reunification";
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
		return "The Democrat receives 2 VP. If the Democrat won during the event of " + CardList.getCard(86) + ", the Democrat may conduct Operations using this card.";
	}

	@Override
	public String getArguments() {
		return "Event: None.\n"
				+ "Decision: Operations.";
	}

}

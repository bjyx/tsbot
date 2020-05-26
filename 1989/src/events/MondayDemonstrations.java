package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import game.GameData;
import map.MapManager;

public class MondayDemonstrations extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Monday Demonstrations!")
			.setDescription("Peace Prayers in Leipzig go revolutionary")
			.setColor(Color.blue);
		builder.changeInfluence(5, 0, Math.max(0, MapManager.get(5).stab+MapManager.get(5).support[1]-MapManager.get(5).support[0]));
		builder.changeInfluence(9, 0, Math.max(0, MapManager.get(9).stab+MapManager.get(9).support[1]-MapManager.get(9).support[0]));
		GameData.ops = new Operations(0, CardList.getCard(61).getOpsMod(0), false, true, false, 5, 0);
		GameData.dec = new Decision(0, 1);
		GameData.txtchnl.sendMessage(builder.build()).complete();
		GameData.txtdem.sendMessage(GameData.roledem.getAsMention() + ", you may now conduct five (!) support checks in East Germany.").complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return HandManager.effectActive(24);
	}

	@Override
	public String getId() {
		return "061";
	}

	@Override
	public String getName() {
		return "Monday Demonstrations";
	}

	@Override
	public int getOps() {
		return 4;
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
		if (!HandManager.effectActive(24)) return "St. Nicholas Church has not started this week's peace prayer yet. Play for Operations only.";
		return "Add sufficient Democratic SPs for control of the Lutheran Church and Leipzig. Then the Democrat makes **five (5)** support checks in East Germay using this card's Operations.";
	}

	@Override
	public String getArguments() {
		return "Event: None.\n"
				+ "Decision: Support checks.";
	}

}

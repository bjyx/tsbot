package events;

import java.awt.Color;

import cards.HandManager;
import game.GameData;
import main.Common;
import main.Launcher;
import map.MapManager;

public class EcoGlasnost extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Activists Protest Romanian Air Pollution")
			.setDescription("Disgruntled Bulgarian workers form environmentalist movement")
			.setColor(Color.blue);
		builder.changeInfluence(67, 0, 4);
		HandManager.addEffect(39);
		builder.addField("Ecoglasnost", "The Democrat gains 1 VP for every Communist Support Check in Ruse for the rest of the game.", false);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "039";
	}

	@Override
	public String getName() {
		return "Ecoglasnost";
	}

	@Override
	public int getOps() {
		return 2;
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
		return "Place 4 Democratic Support in Ruse. *The Democrat gains 1 VP for every Communist Support Check in Ruse.*";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

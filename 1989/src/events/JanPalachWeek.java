package events;

import java.awt.Color;

import game.GameData;
import main.Launcher;

public class JanPalachWeek extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Jan Palach Week")
		.setDescription("Self-immolation commemorated by demonstrations")
		.setColor(Color.blue);
		builder.changeInfluence(35, 0, 6);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "029";
	}

	@Override
	public String getName() {
		return "Jan Palach Week";
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
		return "Place 6 Democratic SPs in Charles University (the Czech Student space).";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

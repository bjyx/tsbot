package events;

import java.awt.Color;

import events.Card;
import game.GameData;

public class TheThirdWay extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("\"The Third Way\"")
		.setColor(Color.red);
		builder.changeVP(-2);
		builder.changeInfluence(11, 1, 3);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "079";
	}

	@Override
	public String getName() {
		return "The Third Way";
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
		// TODO Auto-generated method stub
		return "The Communist gains 2 VP. Place 3 Communist Support in the East German Writers space.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

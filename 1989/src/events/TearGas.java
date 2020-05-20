package events;

import java.awt.Color;

import cards.HandManager;
import game.GameData;
import logging.Log;
import main.Launcher;

public class TearGas extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Tear Gas")
			.setColor(Color.red);
		builder.addField("","The next support check conducted by the Communist in a Student space gets a +1 bonus.",false);
		HandManager.addEffect(30);
		Log.writeToLog("Tear Gas Active.");
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "030";
	}

	@Override
	public String getName() {
		return "Tear Gaas";
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
		return "*The next support check conducted by the Communist in a Student space gets a +1 modifier.*";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

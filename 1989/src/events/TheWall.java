package events;

import java.awt.Color;

import cards.HandManager;
import game.GameData;
import logging.Log;
import main.Launcher;

/**
 * The Wall Card.
 * @author adalbert
 *
 */
public class TheWall extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("The Wall")
			.setColor(Color.red)
			.setFooter("\"The Wall will still be standing in 50 and even in 100 years; if the reasons for it have not been removed by then.\"\n"
					+ "- Erich Honecker, 1989", Launcher.url("leaders/honecker.png"));
		builder.addField("","The next support check conducted by the Communist in East Germany ignores the adjacency maluses caused by Democratic Spaces.",false);
		HandManager.addEffect(9);
		Log.writeToLog("Wall Active.");
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "009";
	}

	@Override
	public String getName() {
		return "The Wall";
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
		return "*The next support check conducted by the Communist in East Germany ignores maluses caused by Democratic control.*";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

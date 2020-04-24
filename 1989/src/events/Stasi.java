package events;

import java.awt.Color;

import cards.HandManager;
import logging.Log;
import main.Launcher;

public class Stasi extends Card {
	
	public static int card = -1;

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Stasi")
		.setDescription("")
		.setColor(Color.red)
		.setFooter("\"It is time to realize that neither socialism, nor friendship, nor good-neighborliness, nor respect can be produced by bayonets, tanks or blood.\"\n"
				+ "- Eduard Shevardnadze", Launcher.url("people/shevardnadze.png"));
		builder.addField("<missing flavortext>","The Communist gets to see the Democrat's next play before playing the next action round.",false);
		HandManager.addEffect(13);
		Log.writeToLog("Stasi Active.");
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "013";
	}

	@Override
	public String getName() {
		return "Stasi";
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
		return "*For the rest of this turn, the Democrat has to reveal a card at the end of each of his Action Rounds. The Democrat will then play that card on his next Action Round.*";
	}

	@Override
	public String getArguments() {
		return "Event: None."
				+ "Decision: You will need to add a card ID when entering the command to advance time.";
	}

}

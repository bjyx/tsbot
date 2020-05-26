package events;

import java.awt.Color;

import cards.HandManager;
import logging.Log;
import main.Launcher;

public class Perestroika extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Gorbachev Proposes Domestic Reform")
		.setDescription("USSR urges Eastern Europe to follow suit")
		.setColor(Color.red)
		.setFooter("\"If the Russian word \'perestroika\' has easily entered the international lexicon, this is due to more than just interest in what is going on in the Soviet Union. Now the whole world needs restructuring, i.e. progressive development, a fundamental change.\"\n"
				+ "- Mikhail Gorbachev, 1987", Launcher.url("people/gorbachev.png"));
		builder.addField("Perestroika","The Communist adds 1 Operations point to any card played for operations for the rest of this turn.",false);
		HandManager.addEffect(25);
		Log.writeToLog("Perestroika active.");
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "025";
	}

	@Override
	public String getName() {
		return "Perestroika";
	}

	@Override
	public int getOps() {
		return 3;
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
		return "The Communist adds 1 Operations Point to any card played for Operations this turn.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

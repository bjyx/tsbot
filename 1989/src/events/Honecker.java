package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import logging.Log;
import main.Launcher;

public class Honecker extends Card {

	public static boolean emptyDiscard = false;
	public static int target;
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder
			.setFooter("\"We have done our perestroika; we have nothing to restructure.\"\n"
					+ "- Erich Honecker, 1985", Launcher.url("people/honecker.png"))
			.setColor(Color.RED);
		if (target==-1) {
			builder.addField("Oops!", "The discard pile is empty. You sure picked a good time to play this.", false);
		}
		else if (target==0) {
			builder.addField("No card selected!", "I suppose that pile of cards isn't really good for you right now?", false);
		}
		else {
			builder.addField("", "The Communist retrieves " + CardList.getCard(Integer.parseInt(args[1])) + " from the discard pile.", false);
			HandManager.getFromDiscard(sp, Integer.parseInt(args[1]));
		}
		builder.addField("", "The Communist may take an extra action round this turn.", false);
		HandManager.addEffect(15);
		Log.writeToLog("Honecker Active.");
	}

	@Override
	public boolean isPlayable(int sp) {
		return !HandManager.effectActive(83); //Modrow
	}

	@Override
	public String getId() {
		return "015";
	}

	@Override
	public String getName() {
		return "Honecker";
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
		if (HandManager.Discard.isEmpty()) {
			target = -1;
			return true;
		}
		if (args.length<2) return false;
		try {
			target = Integer.parseInt(args[1]);
		}
		catch (NumberFormatException err) {
			return false;
		}
		if (target==0) return true;
		if (CardList.getCard(target).getOps()==0) return false;
		if (HandManager.Discard.contains(target)) return true;
		return false;
	}

	@Override
	public String getDescription() {
		return "The Communist may retrieve any non-scoring card from the discard pile. *The Communist may conduct an extra action round this turn. May not be played after " + CardList.getCard(83) + " is played for the event.*";
	}

	@Override
	public String getArguments() {
		return "The card you want to take from the pile, or 0 if you do not want to take anything.";
	}

}

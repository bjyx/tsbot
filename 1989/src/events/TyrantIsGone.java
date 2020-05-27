package events;

import java.awt.Color;

import cards.HandManager;
import game.GameData;
import logging.Log;
import main.Launcher;

public class TyrantIsGone extends Card {
	
	public static int target;

	@Override
	public void onEvent(int sp, String[] args) {
		if (HandManager.effectActive(540)) {
			HandManager.addEffect(970); //dormant
			CardEmbedBuilder builder = new CardEmbedBuilder();
			builder.setTitle("Ceaușescus Prepare to Flee Bucharest")
			.setColor(Color.blue);
			GameData.txtchnl.sendMessage(builder.build()).complete();
		}
		else {
			GameData.dec = new Decision(0, 97);
			GameData.txtdem.sendMessage(GameData.roledem.getAsMention() + ", designate the Ceaușescus' location.").complete();
		}
		Log.writeToLog("Perestroika active.");
	}

	@Override
	public boolean isPlayable(int sp) {
		return HandManager.effectActive(54);
	}

	@Override
	public String getId() {
		return "097";
	}

	@Override
	public String getName() {
		return "The Tyrant is Gone";
	}

	@Override
	public int getOps() {
		return 2;
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
		if (!HandManager.effectActive(54)) 	return "Ceaușescu is still that popular. Play for Operations only.";
		return "Remove 4 Communist Support from Cluj-Napoca. The Democrat chooses a space in Romania with no Democratic Support; if this space is controlled by the Democrat at any point, the Democrat gains 2 VPs immediately; otherwise, the Communist gains 2 VPs at the end of the game.";
	}

	@Override
	public String getArguments() {
		return "Event: None. (This is to accomodate the other way this event can be activated.)"
				+ "\nDecision: The space.";
	}

}

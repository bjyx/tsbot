package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import commands.TimeCommand;
import game.GameData;
import logging.Log;
import main.Launcher;
/**
 * The Defectors Card.
 * @author adalbert
 *
 */
public class Defectors extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		if (GameData.isHeadlinePhase() && GameData.phasing()==0) {
			TimeCommand.hl1=true;
			GameData.txtchnl.sendMessage(new CardEmbedBuilder()
					.setTitle("Top Profile Soviet Officer Defects")
					.setDescription("")
					.setColor(Color.blue)
					.setFooter("\"It was like a bomb on top of everything else.\"\n"
							+ "- William L. M. King, re. Igor Gouzenko, 1945", Launcher.url("people/king.png"))
					.addField("Leaked Intelligence", "The headline "+CardList.getCard(HandManager.headline[1])+" cannot be carried out this turn.", false)
					.build()).complete();
			Log.writeToLog("Defectors cancel USSR headline.");
			HandManager.Discard.add(HandManager.headline[1]);
		}
		else if (!GameData.isHeadlinePhase()&&GameData.phasing()==1) {
			GameData.txtchnl.sendMessage(new CardEmbedBuilder()
					.changeVP(1)
					.setTitle("Well Known Soviet Artist Defects")
					.setDescription("")
					.setColor(Color.blue)
					.setFooter("\"It doesn't matter if every ballet is a success or not. The new experience gives me a lot.\"\n"
							+ "- Mikhail Baryshnikov, 1976", Launcher.url("people/baryshnikov.png"))
					.build()).complete();
		}
	}

	@Override
	public boolean isPlayable(int sp) {
		return GameData.phasing()==1||GameData.isHeadlinePhase();
	}

	@Override
	public String getId() {
		return "103";
	}

	@Override
	public String getName() {
		return "Defectors";
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
		return false;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		return "If played by the US in the headline phase, cancels the USSR's headline. If played by the USSR during an action round, the US gains 1 VP.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

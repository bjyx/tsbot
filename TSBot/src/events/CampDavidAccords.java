package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import events.Card;
import game.GameData;
import logging.Log;
import main.Launcher;
/**
 * The Camp David Accords Card.
 * @author adalbert
 *
 */
public class CampDavidAccords extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Camp David Accords Signed")
		.setDescription("US-mediated negotiations show promise in ending Arab-Israeli enmity")
		.setColor(Color.blue)
		.setFooter("\"I am convinced that we owe it to this generation and the generations to come, not to leave a stone unturned in our pursuit of peace.\"\n"
				+ "- Anwar al-Sadat, 1978", Launcher.url("people/sadat.png"));
		builder.changeVP(1);
		builder.changeInfluence(21, 0, 1); // Egypt
		builder.changeInfluence(25, 0, 1); // Israel
		builder.changeInfluence(26, 0, 1); // Jordan
		HandManager.addEffect(65);
		Log.writeToLog("Camp David Accords Active.");
		builder.addField("Egypt–Israel Peace Treaty", CardList.getCard(13) + " may no longer be played for the event.", false);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "065";
	}

	@Override
	public String getName() {
		return "Camp David Accords";
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
		return "The US gains 1 VP and adds 1 Influence to each of Egypt, Israel, and Jordan. " + CardList.getCard(13) + " may no longer be played for the event.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

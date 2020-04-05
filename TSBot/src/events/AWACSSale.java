package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import logging.Log;
import main.Launcher;
/**
 * The AWACS Sale to Saudis Card.
 * @author adalbert
 *
 */
public class AWACSSale extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("AWACS Sale Passes Congress")
			.setDescription("Airplane sales to Saudi Arabia approved")
			.setColor(Color.blue)
			.setFooter("\"Saudi Arabia has become a firm friend of the United States. As its influence dramatically expands in the world, Saudi Arabia has been not only a firm supporter of the peace process but a moderating and conciliatory force on a wide range of global issues.\"\n"
					+ "- Jimmy Carter, 1978", Launcher.url("people/carter.png"));
		builder.changeInfluence(29, 0, 2); //add 2 USA influence in Saudi
		builder.addField("",CardList.getCard(56) + " may no longer be played for the event.",false);
		Log.writeToLog("AWACS Active.");
		HandManager.addEffect(110);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "110";
	}

	@Override
	public String getName() {
		return "AWACS Sale to Saudis";
	}

	@Override
	public int getOps() {
		return 3;
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
		return "Add 2 US Influence to Saudi Arabia. *Prevents the play of " + CardList.getCard(56) + " for the event.*";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

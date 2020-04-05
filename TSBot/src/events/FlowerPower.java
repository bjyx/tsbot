package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import logging.Log;
import main.Launcher;
/**
 * The Flower Power Card.
 * @author adalbert
 *
 */
public class FlowerPower extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Anti-War Movement Mobilizes")
			.setDescription("")
			.setColor(Color.red)
			.setFooter("\"We shall not wilt. Let a thousand flowers bloom.\"\n"
					+ "- Abbie Hoffman, 1967", Launcher.url("people/hoffman.png"));
		builder.addField("Flower Brigade","All cards involving 'war' will give the USSR 2 victory points.",false);
		HandManager.addEffect(59);
		Log.writeToLog("Flower Power active.");
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return !HandManager.effectActive(97); //An Evil Empire
	}

	@Override
	public String getId() {
		return "059";
	}

	@Override
	public String getName() {
		return "Flower Power";
	}

	@Override
	public int getOps() {
		return 4;
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
		if (HandManager.effectActive(97)) return "Reagan has denounced the Soviet Union. This card may only be played for ops.";
		return "*The USSR will receive 2 VP for every US-played \"War\" card. Cancelled/prevented by " + CardList.getCard(97) + ".*";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

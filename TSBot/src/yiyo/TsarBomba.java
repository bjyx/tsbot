package yiyo;

import java.awt.Color;

import cards.HandManager;
import events.Card;
import events.CardEmbedBuilder;
import game.GameData;
import logging.Log;
import main.Launcher;
/**
 * The Tsar Bomba Card from the Year-In and Year-Out Pack.
 * @author adalbert
 *
 */
public class TsarBomba extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Soviets detonate largest atomic bomb to date").setDescription("Test shockwaves travel three times around world")
			.setFooter("\"I happened to read recently a remark by the American nuclear physicist W. Davidson, who noted that the explosion of one hydrogen bomb releases a greater amount of energy than all the explosions set off by all countries in all wars known in the entire history of mankind. And he, apparently, is right.\"\n"
					+ "- Nikita Khrushchev, 1959", Launcher.url("people/khrushchev.png"))
			.setColor(Color.red);
		if (!GameData.isHeadlinePhase()) builder.changeDEFCON(-1);
		builder.addField("Kuzma's Mother","**DEFCON will decrease by 1 after every US action round with a coup attempt.**",false);
		Log.writeToLog("Tsar Bomba Active.");
		HandManager.addEffect(126);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "126";
	}

	@Override
	public String getName() {
		return "Tsar Bomba";
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
		return "Unless played as a headline, **degrade DEFCON by 1.** *For the rest of the turn,* ***degrade DEFCON by 1 after each US action round during which *someone* attempted a coup.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

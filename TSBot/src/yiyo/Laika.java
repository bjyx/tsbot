package yiyo;

import java.awt.Color;

import cards.HandManager;
import events.Card;
import events.CardEmbedBuilder;
import game.GameData;
import logging.Log;
import main.Launcher;
/**
 * The Laika Card from the Year-In and Year-Out Pack.
 * @author adalbert
 *
 */
public class Laika extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Russian Satellite Carries Dog into Space")
			.setDescription("Laika rides Sputnik II around Earth")
			.setColor(Color.red)
			.setFooter("\"Laika was quiet and charming ... I wanted to do something nice for her: She had so little time left to live.\"\n"
					+ "- Vladimir Yazdovsky", Launcher.url("yiyo/yazdovsky.png"));
		builder.changeVP(-1);
		builder.addField("\"Muttnik\"","The USSR receives -1 to all Space Race rolls until it can send a man into space.",false);
		HandManager.addEffect(124);
		Log.writeToLog("Laika Active.");
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return GameData.getSpace(1)<3; //Man in Space
	}

	@Override
	public String getId() {
		return "124";
	}

	@Override
	public String getName() {
		return "Laika";
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
		return "The USSR gains 1 VP. *The USSR receives -1 to all space race die rolls. Cancelled/Unplayable after the Soviets reach \"Man In Space\" on the Space Race Track.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

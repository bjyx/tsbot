package yiyo;

import java.awt.Color;

import cards.HandManager;
import events.Card;
import events.CardEmbedBuilder;
import game.GameData;
import logging.Log;
/**
 * The Vasili Arkhipov Card from the Year-In and Year-Out Pack.
 * @author adalbert
 *
 */
public class VasiliArkhipov extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("B-59 under fire")
		.setDescription("Officers Savitsky and Maslennikov give authorization to fire special weapon")
		.setColor(sp==0?Color.blue:Color.red);
		builder.addField("Three-Man Authorization", "*At the start of any one action round after this, increase DEFCON by 1.* \n"
				+ "__To activate this card, type `TS.decide arkhipov` at the start of any of your action rounds after this one.__", false);
		HandManager.addEffect(1270+sp);
		Log.writeToLog("Vasili Arkhipov Active (benefits " + (sp==0?"US":"SU") + ").");
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "127";
	}

	@Override
	public String getName() {
		return "Vasily Arkhipov";
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
		return 2;
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
		return "*One time use: At the start of any action round after playing this card, increase DEFCON by one.*"
				+ "\n__To use, type `TS.decide arkhipov` at the start of the turn before playing your card.__";
	}

	@Override
	public String getArguments() {
		return "Use as described.";
	}

}

package yiyo;

import java.awt.Color;

import cards.HandManager;
import events.Card;
import events.CardEmbedBuilder;
import game.GameData;
import logging.Log;
/**
 * The "The Revolution That Surprised the World" Card from the Year-In and Year-Out Pack.
 * @author adalbert
 *
 */
public class PeoplePower extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Unrest in the Philippines")
		.setDescription("Cardinal Vidal protests recent election")
		.setColor(sp==0?Color.blue:Color.red);
		builder.addField("EDSA", "*After any die roll, you may choose to flip that die to its opposite face (1<->6, 2<->5, 3<->4) by discarding this card.* \n"
				+ "__You will be prompted to activate this card.__", false);
		Log.writeToLog("People Power Active (benefits " + (sp==0?"US":"SU") + ").");
		HandManager.addEffect(1350+sp);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "135";
	}

	@Override
	public String getName() {
		return "\"The Revolution That Surprised the World\"";
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
		return 2;
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
		// TODO Auto-generated method stub
		return "*One time use: after playing this card, you may discard this card to flip a die to the opposite face after it is rolled.* __You will be prompted after every die roll to decide.__";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "Event: None.\n"
				+ "Decision: `flip` or `accept`; `flip` flips the die to its opposite face in exchange for discarding this card.";
	}

}

package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import game.GameData;

public class Timisoara extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("The Chinese Solution")
		.setColor(Color.red);
		GameData.txtchnl.sendMessage(builder.build()).complete();
		GameData.dec = new Decision(1, 1);
		HandManager.addEffect(107); //how +2 is effected
		GameData.ops = new Operations(1, CardList.getCard(107).getOpsMod(1), false, true, false, 2, 4);
		GameData.txtcom.sendMessage(GameData.rolecom.getAsMention() + ", you may now conduct your support checks.").complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return HandManager.effectActive(73)&&!HandManager.effectActive(97); //tokes and no tyrant is gone
	}

	@Override
	public String getId() {
		return "107";
	}

	@Override
	public String getName() {
		return "Massacre at Timișoara";
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
		if (HandManager.effectActive(97)) return "Ceaușescu has been deposed! Play for Operations only.";
		return "The Communist conducts two support checks in Romania with a +2 modifier using this card's Operations. *May only be played after the event of "+CardList.getCard(73)+" has been activated. Cannot be played if " + CardList.getCard(97) + " has been played for the event.*";
	}

	@Override
	public String getArguments() {
		return "Event: None.\n"
				+ "Decision: Support checks.";
	}

}

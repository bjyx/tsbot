package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import events.Card;
import game.GameData;
import main.Launcher;

public class CampDavidAccords extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Camp David Accords Signed")
		.setDescription("US-mediated negotiations show promise in ending Arab-Israeli enmity")
		.setColor(Color.blue)
		.setFooter("\"Let them sign what they like. False peace will not last.\"\n"
				+ "- Yasser Arafat, 1979", Launcher.url("countries/ps.png"));
		builder.changeVP(1);
		builder.changeInfluence(21, 0, 1); // Egypt
		builder.changeInfluence(25, 0, 1); // Israel
		builder.changeInfluence(26, 0, 1); // Jordan
		HandManager.addEffect(65);
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
	public boolean isFormatted(String[] args) {
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

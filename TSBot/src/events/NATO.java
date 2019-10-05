package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import main.Launcher;

public class NATO extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("North Atlantic Treaty activated")
			.setDescription("Alliance formed to counter communist expansion")
			.setColor(Color.blue)
			.setFooter("\"Never have we been part of such a broad, solid and binding security alliance, which at the same time respects in its essence the sovereignty and will of our nation.\"\n"
					+ "- Václav Havel [anachronism]", Launcher.url("people/havel.png"));
		builder.addField("NATO Founded","European countries under US Control can no longer be couped, realigned, or targeted by " + CardList.getCard(36) + ", if the originator of those events is the USSR.",false);
		builder.addField("A Very Special Relationship","The effect of " + CardList.getCard(105) + " has been improved.",false);
		HandManager.addEffect(21);
		GameData.txtchnl.sendMessage(builder.build()).complete();

	}

	@Override
	public boolean isPlayable(int sp) {
		// One of Warsaw Pact or Marshall Plan must have been played for the event
		return HandManager.Effects.contains(16) || HandManager.Effects.contains(23);
	}

	@Override
	public String getId() {
		return "021";
	}

	@Override
	public String getName() {
		return "NATO";
	}

	@Override
	public int getOps() {
		return 4;
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
		return true;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		return "*This card requires a prior use of " + CardList.getCard(16) + " or " + CardList.getCard(23) + " for the event.*\n"
				+ "*US Controlled countries in Europe can no longer be the target of coups, realignments, or " + CardList.getCard(36) + " by the USSR.*\n"
				+ "*Improves the effect of " + CardList.getCard(105) + ".*";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

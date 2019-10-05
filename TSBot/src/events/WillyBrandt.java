package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import main.Launcher;

public class WillyBrandt extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Brandt Elected Chancellor")
			.setDescription("New policy repudiates longstanding Hallstein Doctrine")
			.setColor(Color.red)
			.setFooter("\"Berlin expects more than words. It expects political action.\"\n"
					+ "- Willy Brandt, 1961", Launcher.url("people/brandt.png"));
		builder.changeInfluence(19, 1, 1); //add 1 USSR influence in West Germany
		builder.addField("Ostpolitik","West Germany can now be couped/realigned by the Soviet Union if under US control.",false);
		HandManager.addEffect(55);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		// Don't tear down the wall
		return !HandManager.effectActive(96);
	}

	@Override
	public String getId() {
		return "055";
	}

	@Override
	public String getName() {
		return "Willy Brandt";
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
		return "Add 1 USSR Influence to West Germany. *NATO's effects no longer apply to West Germany.* *Cancelled/prevented by " + CardList.getCard(96) + ".*";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

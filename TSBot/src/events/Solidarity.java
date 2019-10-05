package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import main.Launcher;

public class Solidarity extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Solidarity gains momentum")
		.setDescription("Polish government forced to negotiate")
		.setColor(Color.blue)
		.setFooter("\"Freedom must be gained step by step, slowly. Freedom is a food which must be carefully administered when people are too hungry for it.\"\n"
				+ "- Lech Wałęsa, 1981", Launcher.url("people/walesa.png"));
		builder.changeInfluence(13, 0, 3);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return HandManager.effectActive(68);
	}

	@Override
	public String getId() {
		return "101";
	}

	@Override
	public String getName() {
		return "Solidarity";
	}

	@Override
	public int getOps() {
		return 2;
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
		return "*May only be played after the event of "+CardList.getCard(68)+" has been activated.* Add 3 US Influence to Poland.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

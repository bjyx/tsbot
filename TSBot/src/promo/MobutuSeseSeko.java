package promo;

import java.awt.Color;

import events.Card;
import events.CardEmbedBuilder;
import game.GameData;
import main.Launcher;
import map.MapManager;

public class MobutuSeseSeko extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Mobutu seizes power in Congo")
			.setDescription("")
			.setColor(Color.blue)
			.setFooter("\"Nothing counted for them but power...and what the exercise of power could bring them.\"\n"
					+ "- Jimmy Carter, 1978", Launcher.url("promo/mobutu.png"));
		builder.changeInfluence(62, 0, 2); //add 2 USA influence in Zaire
		builder.addField("Manifesto of N'Sele","Zaire's stability is now 3.",false);
		MapManager.get(62).stab = 3; //the one time a country's stab can change in the game
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "112";
	}

	@Override
	public String getName() {
		return "Mobutu Sese Seko";
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
	public boolean isFormatted(int sp, String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Add 2 US Influence to Zaire. Zaire now has a stability value of 3 instead of 1.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

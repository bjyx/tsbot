package promo;

import java.awt.Color;

import cards.Operations;
import events.Card;
import events.CardEmbedBuilder;
import game.GameData;
import main.Launcher;

public class BerlinWall extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		int spaceLevel = GameData.getSpace(sp);
		builder.setTitle("Wall Erected Around Berlin")
			.setDescription("")
			.setFooter("\"Freedom has many difficulties and democracy is not perfect, but we have never had to put a wall up to keep our people in, to prevent them from leaving us.\"\n"
					+ "- John F. Kennedy, 1963", Launcher.url("people/jfk.png"))
			.setColor(sp==0?Color.blue:Color.red);
		builder.changeInfluence(6, 1, 2); //dd+2
		if (GameData.aheadInSpace()!=1) {
			builder.addField("Barbed Wire Sunday", Operations.getSpaceNames(spaceLevel, sp), false); //I don't get why this advances space levels
			GameData.addSpace(1);
			builder.changeVP(-1*Operations.spaceVP2[spaceLevel]);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "113";
	}

	@Override
	public String getName() {
		return "Berlin Wall";
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
		return "Add 2 USSR Influence to East Germany. If the USSR is behind or tied with the US in the space race, the USSR advances one space on that track.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

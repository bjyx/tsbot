package events;

import java.awt.Color;

import game.GameData;
import main.Launcher;

/**
 * The Michnik card.
 * @author adalbert
 *
 */
public class Michnik extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Your President, Our Prime Minister")
		.setDescription("<missing flavortext>")
		.setColor(Color.blue)
		.setFooter("\"In an explosive situation, the responsible thing to do is defuse the mine.\"\n"
				+ "- Tadeusz Mazowiecki", Launcher.url("people/mazowiecki.png"));
		builder.changeInfluence(25, 0, 3);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "004";
	}

	@Override
	public String getName() {
		return "Michnik";
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
		return "Place 3 Democratic SPs in the Polish Writers space.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

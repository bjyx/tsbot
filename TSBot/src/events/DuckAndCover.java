package events;

import java.awt.Color;

import game.GameData;
import main.Launcher;
/**
 * The Duck and Cover Card.
 * @author adalbert
 *
 */
public class DuckAndCover extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("*Duck and Cover*")
			.setDescription("Civil defense film")
			.setColor(Color.blue)
			.setFooter("\"He did what we all must learn to do / You—and you—and you—and you! / Duck, and cover! \"\n"
					+ "- *Duck and Cover*", Launcher.url("people/bert.png"));
		builder.changeDEFCON(-1);
		builder.changeVP(5-GameData.getDEFCON());
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
		return "*Duck and Cover*";
	}

	@Override
	public int getOps() {
		return 3;
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
		return false;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		return "**Degrades DEFCON by 1.** \nThe US then gains (5 - DEFCON) VP.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

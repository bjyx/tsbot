package events;

import java.awt.Color;

import game.GameData;
import main.Launcher;

public class DrStrangelove extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("*Dr. Strangelove*")
			.setDescription("Or, How I Learned To Stop Worrying and Love the Bomb")
			.setColor(Color.gray)
			.setFooter("\"Gentlemen, you can't fight in here! This is the War Room!\"\n"
					+ "- Merkin Muffley, *Dr. Strangelove*", Launcher.url("people/strangelove.png"));
		builder.changeDEFCON(Integer.parseInt(args[1])-GameData.getDEFCON());
		builder.addMilOps(sp, 5);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "046";
	}

	@Override
	public String getName() {
		return "How I Learned To Stop Worrying";
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
		return 2;
	}

	@Override
	public boolean isRemoved() {
		return true;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		int x;
		try{
			x = Integer.parseInt(args[1]);
		}
		catch (NumberFormatException err) {
			return false;
		}
		if (1 <= x && x <= 5) return true;
		return false;
	}

	@Override
	public String getDescription() {
		return "Gain 5 Military Operations and **set DEFCON at any level**.";
	}

	@Override
	public String getArguments() {
		return "A number from 1-5, the new DEFCON level.";
	}

}

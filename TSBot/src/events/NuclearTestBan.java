package events;

import java.awt.Color;

import game.GameData;
import main.Launcher;

public class NuclearTestBan extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Partial Test Ban Treaty Signed")
			.setDescription("Treaty bans nuclear tests in the atmosphere, in outer space, and under water")
			.setFooter("\"And if that journey is a thousand miles, or even more, let history record that we, in this land, at this time, took the first step.\"\n"
					+ "- John F. Kennedy, 1963", Launcher.url("countries/us.png"))
			.setColor(Color.GRAY);
		builder.changeVP((sp==0?1:-1)*(GameData.getDEFCON()-2));
		builder.changeDEFCON(2);
		GameData.txtchnl.sendMessage(builder.build());
	}

	@Override
	public boolean isPlayable(int sp) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return "034";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Nuclear Test Ban";
	}

	@Override
	public int getOps() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public int getEra() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAssociation() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public boolean isRemoved() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFormatted(String[] args) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Gives VP equal to the current DEFCON minus 2 to the phasing player, then increases DEFCON by 2.";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "None.";
	}

}

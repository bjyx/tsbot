package events;

import java.awt.Color;

import game.GameData;
import main.Launcher;
import map.MapManager;

public class AllianceForProgress extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Alliance for Progress")
		.setDescription("")
		.setFooter("\"Let us once again transform the American Continent into "
				+ "a vast crucible of revolutionary ideas and efforts, "
				+ "a tribute to the power of the creative energies of free men and women, "
				+ "an example to all the world that liberty and progress walk hand in hand.\"\n"
				+ "- John F. Kennedy, 1961", Launcher.url("countries/us.png"))
		.setColor(Color.blue);
		int x = 0;
		String str = "";
		for (int i=64; i<84; i++) {
			if (MapManager.get(i).isControlledBy()==0&&MapManager.get(i).isBattleground) {
				x++;
				str += MapManager.get(i);
			}
		}
		builder.addField("Cooperating countries: ", str, false);
		builder.changeVP(x);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "078";
	}

	@Override
	public String getName() {
		return "Alliance for Progress";
	}

	@Override
	public int getOps() {
		return 3;
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
		return "The US receives 1 VP for every US-controlled battleground in Central and South America.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

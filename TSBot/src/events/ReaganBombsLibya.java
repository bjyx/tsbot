package events;

import java.awt.Color;

import game.GameData;
import main.Launcher;
import map.MapManager;

public class ReaganBombsLibya extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Reagan Bombs Libya")
		.setDescription("Avenges discotheque bombing")
		.setFooter("\"Reagan plays with fire. He doesn't care about international peace. He plays as if he was in the theater. Reagan wants to dominate the world. He wants to find justification to make war. If he does this, if it goes on like this, a cataclysm will take place. Reagan should come and see that I am not a terrorist in a trench with a grenade in my pocket.\"\n"
				+ "- Muammar al-Gaddafi", Launcher.url("people/gaddafi.png"))
		.setColor(Color.blue);
		builder.addField(":flag_ly:"+":InfluenceR:"+CardEmbedBuilder.intToEmoji(MapManager.get(28).influence[1]), "", false);
		builder.changeVP(MapManager.get(28).influence[1]/2);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "084";
	}

	@Override
	public String getName() {
		return "Reagan Bombs Libya";
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
		return "The US gains 1 VP for every 2 USSR Influence in Libya.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

package events;

import java.awt.Color;

import game.GameData;
import main.Launcher;
import map.MapManager;

public class Nasser extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Mohammed Naguib deposed in coup by Free Officer Movement")
			.setDescription("Gamal Abdel Nasser takes power in Egypt")
			.setFooter("\"I will live for your sake and die for the sake of your freedom and honor. "
					+ "Let them kill me; it does not concern me so long as I have instilled pride, honor, and freedom in you. "
					+ "If Gamal Abdel Nasser should die, each of you shall be Gamal Abdel Nasser...\"\n"
					+ "- Gamal Abdel Nasser",Launcher.url("people/nasser.png"))
			.setColor(Color.RED);
		builder.changeInfluence(21, 1, 2); //add 2 influence from the USSR
		builder.changeInfluence(21, 0, -(MapManager.get(21).influence[0]+1)/2); //remove half (round up) of the influence from the US
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "015";
	}

	@Override
	public String getName() {
		return "Nasser";
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
		return "Add 2 USSR Influence to Egypt. Remove half (rounded up) of the US Influence in Egypt.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

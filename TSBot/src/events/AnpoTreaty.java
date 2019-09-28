package events;

import java.awt.Color;

import cards.HandManager;
import game.GameData;
import main.Launcher;
import map.MapManager;

public class AnpoTreaty extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Treaty of Mutual Cooperation and Security between the United States and Japan")
			.setDescription("")
			.setColor(Color.blue)
			.setFooter("\"This treaty is as magnanimous as it is just to a defeated foe. "
					+ "We extend to Japan the hand of friendship and trust that with the closing of this chapter in the history of man, "
					+ "the last page of which we write today, and with the beginning of the new one, "
					+ "the first page of which we dictate tomorrow, her people and ours may march together to enjoy the full dignity of human life in peace and prosperity.\"\n"
					+ "- J. R. Jayawardene, regarding the Treaty of San Francisco, 1951", Launcher.url("countries/lk.png"));
		builder.changeInfluence(36, 0, Math.max(MapManager.get(36).stab+MapManager.get(36).influence[1]-MapManager.get(36).influence[0],0));
		builder.addField("San Francisco System","Japan can no longer be the target of Coups or Realignments by the USSR",false);
		HandManager.addEffect(27);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "027";
	}

	@Override
	public String getName() {
		return "US/Japan Mutual Defense Pact (Anpō Treaty)";
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
		return 0;
	}

	@Override
	public boolean isRemoved() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isFormatted(String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Add sufficient US Influence for control of Japan. Japan may no longer be the target of coups or realignments by the USSR. ";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "None.";
	}

}

package events;

import java.awt.Color;

import game.GameData;
import main.Launcher;
import map.Country;
import map.MapManager;

public class SouthAmScoring extends Card {

	private static final int presence = 2;
	private static final int domination = 5;
	private static final int control = 6;
	@Override
	public void onEvent(int sp, String[] args) {
		int[] totalCountries = {0,0};
		int[] battlegrounds = {0,0};
		String[] strings = {"","","",""};
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("South America Scoring")
			.setDescription("")
			.setColor(Color.green)
			.setFooter("\"We already had success in creating a democratic, national government that is revolutionary and popular. "
					+ "That is how socialism begins, not with decrees.\n\"" + 
					"- Salvador Allende, 1970", Launcher.url("leaders/allende.png"));
		int vp = 0;
		for (int i = 74; i<84; i++) { //Argentina and USA, resp.
			Country c = MapManager.get(i);
			if (c.isControlledBy()!=-1) {
				totalCountries[c.isControlledBy()]++;
				if (c.isBattleground) {
					battlegrounds[c.isControlledBy()]++;
					strings[c.isControlledBy()+2] += c;
				}
				else {
					strings[c.isControlledBy()] += c;
				}
			}
		}
		builder.addField(":flag_us:", strings[0]+"|"+strings[2], false);
		builder.addField(":flag_su:", strings[1]+"|"+strings[3], false);
		vp += battlegrounds[0]-battlegrounds[1];
		if (battlegrounds[0]==4) vp += control;
		else if (battlegrounds[0]>battlegrounds[1]&&totalCountries[0]>totalCountries[1]) vp += domination;
		else if (totalCountries[0]>0) vp += presence;
		if (battlegrounds[1]==4) vp -= control;
		else if (battlegrounds[1]>battlegrounds[0]&&totalCountries[1]>totalCountries[0]) vp -= domination;
		else if (totalCountries[1]>0) vp -= presence;
		builder.changeVP(vp);
		GameData.txtchnl.sendMessage(builder.build()).complete();

	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "081";
	}

	@Override
	public String getName() {
		return "South America Scoring";
	}

	@Override
	public int getOps() {
		return 0;
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
		return false;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Scores South America on a scale of 2/5/6. +1 for battlegrounds.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

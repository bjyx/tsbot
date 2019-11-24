package events;

import java.awt.Color;

import game.GameData;
import main.Launcher;
import map.Country;
import map.MapManager;

public class AfricaScoring extends Card {

	private static final int presence = 1;
	private static final int domination = 4;
	private static final int control = 6;
	@Override
	public void onEvent(int sp, String[] args) {
		int[] totalCountries = {0,0};
		int[] battlegrounds = {0,0};
		String[] strings = {"","","",""};
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Africa Scoring")
			.setDescription("")
			.setColor(Color.YELLOW)
			.setFooter("\"None of us has yet begun to grasp the full impact of this horror—on the quality of life in Africa, its economic potential and its social and political stability.\"\n" + 
					"- Kofi Annan", Launcher.url("people/annan.png"));
		int vp = 0;
		for (int i = 46; i<64; i++) { //Algeria and Costa Rica, resp.
			Country c = MapManager.get(i);
			if (c.isControlledBy()!=-1) {
				totalCountries[c.isControlledBy()]++;
				if (c.isBattleground) {
					battlegrounds[c.isControlledBy()]++;
					strings[c.isControlledBy()] += c;
				}
				else {
					strings[c.isControlledBy()+2] += c;
				}
			}
		}
		builder.addField(":flag_us:", strings[0]+" | "+strings[2], false);
		builder.addField(MapManager.get(85).toString(), strings[1]+" | "+strings[3], false);
		vp += battlegrounds[0]-battlegrounds[1];
		if (battlegrounds[0]==5) vp += control;
		else if (battlegrounds[0]>battlegrounds[1]&&totalCountries[0]>totalCountries[1]) vp += domination;
		else if (totalCountries[0]>0) vp += presence;
		if (battlegrounds[1]==5) vp -= control;
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
		return "079";
	}

	@Override
	public String getName() {
		return "Africa Scoring";
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
		return "Scores Africa on a scale of 1/4/6. +1 for battlegrounds.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

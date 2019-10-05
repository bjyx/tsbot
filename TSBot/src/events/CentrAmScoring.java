package events;

import java.awt.Color;

import game.GameData;
import main.Launcher;
import map.Country;
import map.MapManager;

public class CentrAmScoring extends Card {
	private static final int presence = 1;
	private static final int domination = 3;
	private static final int control = 5;
	@Override
	public void onEvent(int sp, String[] args) {
		int[] totalCountries = {0,0};
		int[] battlegrounds = {0,0};
		String[] strings = {"","","",""};
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Central America Scoring")
			.setDescription("")
			.setColor(new Color(214, 255, 110))
			.setFooter("\"Imperialism cannot conceive of a free people, a sovereign people, an independent people. "
					+ "Because, simply and plainly, for them the people is nothing more than an empty phrase.\"\n" + 
					"- Daniel Ortega, 1979", Launcher.url("people/ortega.png"));
		int vp = 0;
		for (int i = 64; i<74; i++) { //CostaRica and Argentina, resp.
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
				if (c.id==65 && c.isControlledBy()==1) vp--; //cu
				if (c.id==71 && c.isControlledBy()==1) vp--; //mx
			}
		}
		builder.addField(":flag_us:", strings[0]+"|"+strings[2], false);
		builder.addField(":flag_su:", strings[1]+"|"+strings[3], false);
		vp += battlegrounds[0]-battlegrounds[1];
		if (battlegrounds[0]==3) vp += control;
		else if (battlegrounds[0]>battlegrounds[1]&&totalCountries[0]>totalCountries[1]) vp += domination;
		else if (totalCountries[0]>0) vp += presence;
		if (battlegrounds[1]==3) vp -= control;
		else if (battlegrounds[1]>battlegrounds[0]&&totalCountries[1]>totalCountries[0]) vp -= domination;
		else if (totalCountries[1]>0) vp -= presence;
		builder.changeVP(vp);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return "037";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Central America Scoring";
	}

	@Override
	public int getOps() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getEra() {
		// TODO Auto-generated method stub
		return 1;
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
	public boolean isFormatted(int sp, String[] args) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Scores Central America on a scale of 1/3/5. +1 for battlegrounds, +1 for each country you control that borders the other superpower (Cuba, Mexico).";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "None.";
	}

}

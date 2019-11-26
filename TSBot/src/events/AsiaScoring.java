package events;

import java.awt.Color;

import cards.HandManager;
import game.GameData;
import main.Launcher;
import map.Country;
import map.MapManager;

public class AsiaScoring extends Card {
	private static final int presence = 3;
	private static final int domination = 7;
	private static final int control = 9;
	
	@Override
	public int getOps() {
		return 0;
	}
	@Override
	public int getAssociation() {
		return 2;
	}
	@Override
	public void onEvent(int sp, String[] args) {
		int[] totalCountries = {0,0};
		int[] battlegrounds = {0,0};
		String[] strings = {"","","",""};
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Asia Scoring")
			.setDescription("")
			.setColor(Color.orange)
			.setFooter("\"I can say without equivocation: "
					+ "the United States firmly supports the orderly progress towards self-government throughout the world. "
					+ "The United States has no imperialistic ambitions whatsoever in Asia or in any other part of the globe.\"\n"
					+ "- Richard Nixon, 1953", Launcher.url("people/nixon.png"));
		if (HandManager.effectActive(35)) {
			builder.addField("Formosa Resolution", "Taiwan is a battleground for scoring purposes.", false);
			MapManager.get(43).isBattleground = true;
		}
		int vp = 0;
		for (int i = 31; i<46; i++) { //Afghanistan and Algeria, resp.
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
				if (c.id==31 && c.isControlledBy()==0) vp++; //af
				if (c.id==39 && c.isControlledBy()==0) vp++; //kp
				if (c.id==36 && c.isControlledBy()==1) vp--; //jp
			}
		}
		builder.addField(":flag_us:", strings[0]+" | "+strings[2], false);
		builder.addField(MapManager.get(85).toString(), strings[1]+" | "+strings[3], false);
		if (HandManager.effectActive(73)) {
			battlegrounds[1]--; //shuttle diplomacy removes a battleground from Asia
			totalCountries[1]--; //a battleground is still a country...
			HandManager.removeEffect(73); //shuttle diplomacy is one use only
			HandManager.Discard.add(73);
			if (MapManager.get(36).isControlledBy()==1) vp++; // shame on you if you managed to lose *JAPAN* to the USSR
			builder.addField("Shuttle Diplomacy", "One less battleground to worry about!", false);
		}
		vp += battlegrounds[0]-battlegrounds[1];
		if (battlegrounds[0]==6) vp += control;
		else if (battlegrounds[0]>battlegrounds[1]&&totalCountries[0]>totalCountries[1]&&(totalCountries[0]-battlegrounds[0]>0)) vp += domination;
		else if (totalCountries[0]>0) vp += presence;
		if (battlegrounds[1]==6) vp -= control;
		else if (battlegrounds[1]>battlegrounds[0]&&totalCountries[1]>totalCountries[0]&&(totalCountries[1]-battlegrounds[1]>0)) vp -= domination;
		else if (totalCountries[1]>0) vp -= presence;
		builder.changeVP(vp);
		GameData.txtchnl.sendMessage(builder.build()).complete();
		MapManager.get(43).isBattleground = false; //reset Taiwan to non-battleground
	}
	@Override
	public String getId() {
		return "001";
	}
	@Override
	public String getName() {
		return "Asia Scoring";
	}
	@Override
	public int getEra() {
		return 0;
	}
	@Override
	public boolean isRemoved() {
		return false;
	}
	@Override
	public String getDescription() {
		return "Scores Asia on a scale of 3/7/9. +1 for battlegrounds, +1 for each country you control that borders the other superpower (Afghanistan, North Korea, Japan).";
	}
	@Override
	public boolean isPlayable(int sp) {
		return true; //always playable.
	}
	@Override
	public boolean isFormatted(int sp, String[] args) {
		return true;
	}
	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "None.";
	}
}

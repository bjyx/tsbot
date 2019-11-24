package events;

import java.awt.Color;

import cards.HandManager;
import game.GameData;
import main.Launcher;
import map.Country;
import map.MapManager;

public class MiddleEastScoring extends Card {
	private static final int presence = 3;
	private static final int domination = 5;
	private static final int control = 7;
	@Override
	public void onEvent(int sp, String[] args) {
		int[] totalCountries = {0,0};
		int[] battlegrounds = {0,0};
		String[] strings = {"","","",""};
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Middle East Scoring")
			.setDescription("")
			.setColor(Color.CYAN)
			.setFooter("\"The world petroleum story is one of the most inhuman known to man: "
					+ "in it, elementary moral and social principles are jeered at. "
					+ "If powerful oil trusts no longer despoil and humiliate our country "
					+ "it is not because these predators have become human, "
					+ "but because we have won a hard-fought battle which has been going on since the beginning of the century.\"\n" + 
					"- Mohammed Reza Pahlavi", Launcher.url("people/pahlavi.png"));
		int vp = 0;
		for (int i = 21; i<31; i++) { //Egypt and Afghanistan, resp.
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
		if (HandManager.Effects.contains(73)) {
			battlegrounds[1]--; //shuttle diplomacy removes a battleground from ME
			totalCountries[1]--; //a battleground is still a country...
			HandManager.Effects.remove(HandManager.Effects.indexOf(73)); //shuttle diplomacy is one use only
			HandManager.Discard.add(73);
			builder.addField("Shuttle Diplomacy", "One less battleground to worry about!", false);
		}
		vp += battlegrounds[0]-battlegrounds[1];
		if (battlegrounds[0]==6) vp += control;
		else if (battlegrounds[0]>battlegrounds[1]&&totalCountries[0]>totalCountries[1]) vp += domination;
		else if (totalCountries[0]>0) vp += presence;
		if (battlegrounds[1]==6) vp -= control;
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
		return "003";
	}

	@Override
	public String getName() {
		return "Middle East Scoring";
	}

	@Override
	public int getOps() {
		return 0;
	}

	@Override
	public int getEra() {
		return 0;
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
		return "Scores the Middle East on a scale of 3/5/7. +1 for battlegrounds.";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "None";
	}

}

package events;

import cards.HandManager;
import game.GameData;
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
		return 0;
	}
	@Override
	public void onEvent(String[] args) {
		int[] totalCountries = {0,0};
		int[] battlegrounds = {0,0};
		String[] strings = {"","","",""};
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Asia Scoring").setDescription("");
		int vp = 0;
		for (Country c : MapManager.map.subList(31, 46)) { //Afghanistan and Algeria, resp.
			if (c.isControlledBy()!=-1) {
				totalCountries[c.isControlledBy()]++;
				if (c.isBattleground()) {
					battlegrounds[c.isControlledBy()]++;
					strings[c.isControlledBy()+2] += ":flag_" + c.getISO3166() +":";
				}
				else {
					strings[c.isControlledBy()] += ":flag_" + c.getISO3166() +":";
				}
				if (c.getID()==31 && c.isControlledBy()==0) vp++; //af
				if (c.getID()==39 && c.isControlledBy()==0) vp++; //kp
				if (c.getID()==36 && c.isControlledBy()==1) vp--; //jp
			}
		}
		builder.addField(":flag_us:", strings[0]+"|"+strings[2], false);
		builder.addField(":flag_su:", strings[1]+"|"+strings[3], false);
		if (HandManager.Effects.contains(73)) {
			battlegrounds[1]--; //shuttle diplomacy removes a battleground from Asia
			totalCountries[1]--; //a battleground is still a country...
			HandManager.Effects.remove(HandManager.Effects.indexOf(73)); //shuttle diplomacy is one use only
			HandManager.Discard.add(73);
		}
		vp += battlegrounds[0]-battlegrounds[1];
		if (battlegrounds[0]==6) vp += control;
		else if (battlegrounds[0]>battlegrounds[1]&&totalCountries[0]>totalCountries[1]) vp += domination;
		else if (totalCountries[0]>0) vp += presence;
		if (battlegrounds[1]==6) vp -= control;
		else if (battlegrounds[1]>battlegrounds[0]&&totalCountries[1]>totalCountries[0]) vp -= domination;
		else if (totalCountries[1]>0) vp -= presence;
		builder.changeVP(vp);
		GameData.txtchnl.sendMessage(builder.build());
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
}

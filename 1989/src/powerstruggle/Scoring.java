package powerstruggle;

import java.awt.Color;

import cards.HandManager;
import events.CardEmbedBuilder;
import events.Decision;
import game.GameData;
import main.Common;
import map.Country;
import map.MapManager;

/**
 * A class to handle the scoring system of the game.
 * @author adalbert
 *
 */
public class Scoring {
	private static final int[] presence = {3,3,2,1,2,1};
	private static final int[] domination = {6,6,4,2,4,2};
	private static final int[] control = {9,9,6,4,6,3}; //literally the only reason I have to use this is because Hungary has 4 here
	
	public static void score(int region) {
		int[] totalCountries = {0,0};
		int[] battlegrounds = {0,0};
		int totalBG = 0;
		String[] strings = {"","","",""};
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle(Common.countries[region] + " Scoring")
			.setDescription("")
			.setColor(getColor(region));
		int vp = 0;
		for (int i = Common.bracket[region]; i<Common.bracket[region+1]; i++) {
			Country c = MapManager.get(i);
			if (c.isBattleground) {
				totalBG++;
			}
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
		builder.addField("Democrat", strings[0]+" | "+strings[2], false);
		builder.addField("Communist", strings[1]+" | "+strings[3], false);
		vp += battlegrounds[0]-battlegrounds[1];
		if (battlegrounds[0]==totalBG) vp += control[region];
		else if (battlegrounds[0]>battlegrounds[1]&&totalCountries[0]>totalCountries[1]&&(totalCountries[0]-battlegrounds[0]>0)) vp += domination[region];
		else if (totalCountries[0]>0) vp += presence[region];
		if (battlegrounds[1]==totalBG) vp -= control[region];
		else if (battlegrounds[1]>battlegrounds[0]&&totalCountries[1]>totalCountries[0]&&(totalCountries[1]-battlegrounds[1]>0)) vp -= domination[region];
		else if (totalCountries[1]>0) vp -= presence[region];
		builder.changeVP(vp);
		GameData.txtchnl.sendMessage(builder.build()).complete();
		if (region==4&&HandManager.effectActive(970)) {
			GameData.dec = new Decision(0, 97);
			GameData.txtdem.sendMessage(GameData.roledem.getAsMention() + ", designate the Ceaușescus' location.").complete();
		}
	}
	
	/**
	 * Assigns a color to each region. 
	 * @return a Color.
	 */
	public static Color getColor(int region) {
		if (region==0) return Color.gray;
		if (region==1) return Color.yellow;
		if (region==2) return new Color(35, 106, 14);
		if (region==3) return Color.orange;
		if (region==4) return Color.lightGray;
		if (region==5) return new Color(113, 196, 88);
		return Color.darkGray;
	}
}

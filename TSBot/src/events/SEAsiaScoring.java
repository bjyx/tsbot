package events;

import java.awt.Color;

import game.GameData;
import main.Launcher;
import map.Country;
import map.MapManager;
/**
 * The Southeast Asia Scoring Card.
 * @author adalbert
 *
 */
public class SEAsiaScoring extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		int[] totalCountries = {0,0};
		int[] battlegrounds = {0,0};
		String[] strings = {"","","",""};
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Southeast Asia Scoring")
			.setDescription("")
			.setColor(new Color(255, 196, 0))
			.setFooter("Our cause is just; our people are united from North to South; "
					+ "we have a tradition of undaunted struggle and the great sympathy and support of the fraternal socialist countries and progressive people all over the world. We shall win!\n" + 
					"- Hô Chi Minh, 1966", Launcher.url("leaders/hochiminh.png"));
		int vp = 0;
		for (int i = 31; i<46; i++) { //Afghanistan and Algeria, resp.
			Country c = MapManager.get(i);
			if (c.isControlledBy()!=-1 && c.region==5) {
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
		vp += battlegrounds[0]+totalCountries[0]-battlegrounds[1]-totalCountries[1];
		builder.changeVP(vp);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "038";
	}

	@Override
	public String getName() {
		return "Southeast Asia Scoring";
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
		return true;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Scores Southeast Asia for 1 VP for each country controlled, and 1 extra VP for controlling a battleground (i.e. Thailand).";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

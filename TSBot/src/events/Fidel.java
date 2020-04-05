package events;

import java.awt.Color;

import game.GameData;
import main.Launcher;
import map.MapManager;
/**
 * The Fidel Card.
 * @author adalbert
 *
 */
public class Fidel extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Cuban Revolution")
			.setDescription("Fulgencio Batista overthrown; Castro takes power")
			.setColor(Color.red)
			.setFooter("\"We are telling these countries to make their own revolution.\"\n"
					+ "- Che Guevara, 1962", Launcher.url("people/che.png"));
		builder.changeInfluence(65, 0, -MapManager.get(65).influence[0]); //remove all American influence in Cuba
		builder.changeInfluence(65, 1, Math.max(0, 3-MapManager.get(65).influence[1])); //control Cuba with USSR influence
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "008";
	}

	@Override
	public String getName() {
		return "Fidel";
	}

	@Override
	public int getOps() {
		return 2;
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
		return "Removes all US Influence from Cuba, and adds sufficient USSR influence to control the country.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

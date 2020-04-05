package events;

import java.awt.Color;

import game.GameData;
import main.Launcher;
import map.MapManager;
/**
 * The Romanian Abdication Card.
 * @author adalbert
 *
 */
public class RomanianAbdication extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Michael I Abdicates Throne of Romania")
			.setDescription("People's Republic declared in Bucharest")
			.setFooter("\"If the people want me to come back, of course, I will come back. "
					+ "Romanians have had enough suffering imposed on them to have the right to be consulted on their future.\"\n"
					+ "- Mihai I, 1990", Launcher.url("people/michael.png"))
			.setColor(Color.red);
		builder.changeInfluence(14, 0, -MapManager.get(14).influence[0]);
		builder.changeInfluence(14, 1, Math.max(0, 3-MapManager.get(14).influence[1]));
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "012";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Romanian Abdication";
	}

	@Override
	public int getOps() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getEra() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAssociation() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean isRemoved() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Removes all US Influence in Romania, and adds sufficient USSR Influence for control.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

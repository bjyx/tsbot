package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import main.Common;
import main.Launcher;
import map.MapManager;

/**
 * The Solidarity Legalized card, representing the 
 * @author adalbert
 *
 */
public class Solidarity extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Solidarity Recognized")
			.setDescription("Jaruzelski invites banned trade union to talks")
			.setFooter("<missing flavortext>\n" + 
					"- <missing flavor>", Launcher.url("people/qmark.png"))
			.setColor(Color.blue);
		for (int i=Common.bracket[1]; i<Common.bracket[2]; i++) {
			if (MapManager.get(i).icon<=1&&MapManager.get(i).isControlledBy()==-1) {
				builder.changeInfluence(i, 0, 1);
			}
		}
		HandManager.addEffect(2);
		builder.addField("<missing flavor>", "The Communist may no longer conduct Support Checks in Gdańsk.", false);
		builder.addField("<missing flavor>", CardList.getCard(3) + " may now be played for the event.", false);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "002";
	}

	@Override
	public String getName() {
		return "__Solidarity Legalized__"; //RED CARD - first character is an underscore
	}

	@Override
	public int getOps() {
		return 4;
	}

	@Override
	public int getEra() {
		return 0;
	}

	@Override
	public int getAssociation() {
		return 0;
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
		return "Place 1 Democratic Support in every uncontrolled Farmer and Worker Space in Poland. *The Communist player is no longer allowed to conduct Support Checks in Gdańsk. Allows the play of " + CardList.getCard(3) + ".*";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

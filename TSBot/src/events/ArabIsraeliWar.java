package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import game.Die;
import game.GameData;
import logging.Log;
import main.Launcher;
import map.MapManager;
/**
 * The Arab-Israeli War Card.
 * @author adalbert
 *
 */
public class ArabIsraeliWar extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		Die die = new Die();
		int mod = die.roll();
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.addMilOps(1,  2);
		String adjacents = "";
		Log.writeToLog("Roll: " + mod);
		for (int i : MapManager.get(25).adj) {
			if (MapManager.get(i).isControlledBy()==0) {
				mod--;
				adjacents += MapManager.get(i);
				Log.writeToLog(MapManager.get(i).iso.toUpperCase() + ": -1");
			}
		}
		if (MapManager.get(25).isControlledBy()==0) {
			mod--;
			adjacents += MapManager.get(25);
			Log.writeToLog("IL: -1");
		}
		builder.addField("Arab League invades Israel", die + (mod==die.result?"":(" - " + adjacents)), false);
		if (mod>=4) {
			builder.setTitle("Arab-Israeli War: Arab Victory")
				.setDescription("Palestinian State established in Jerusalem; Congress in uproar")
				.setFooter("\"It will be a war of annihilation. It will be a momentous massacre in history that will be talked about like the massacres of the Mongols or the Crusades.\"\n"
						+ "- Azzam Pasha, 1947", Launcher.url("people/arableague.png"))
				.setColor(Color.red);
			builder.changeInfluence(25, 1, MapManager.get(25).influence[0]);
			builder.changeInfluence(25, 0, -MapManager.get(25).influence[0]);
			builder.changeVP(-2);
		}
		else {
			builder.setTitle("Israel Triumphant Against Neighbors")
				.setDescription("Land ceded from neighboring nations")
				.setFooter("\"We have always said that in our war with the Arabs we had a secret weapon — no alternative.\"\n"
						+ "- Golda Meir, 1969",Launcher.url("people/meir.png"))
				.setColor(Color.blue);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		// Cannot be played under Camp David Accords
		return !HandManager.Effects.contains(65); 
	}

	@Override
	public String getId() {
		return "013";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Arab-Israeli War";
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
		return false;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		if (HandManager.effectActive(65)) return "The Camp David Accords are active, and you can't have an Arab-Israeli War without Egypt. This card may only be played for ops. *This card will not trigger Flower Power.*";
		return "The USSR rolls 1 die and adds 2 Military Ops. Subtract 1 from the roll for every country adjacent to Israel that the US controls, and an additional 1 if the US controls Israel. On a modified roll of 4-6, the USSR gains 2 VP and replace all influence in Israel with your own. \n*Cannot be played if " + CardList.getCard(65) + " is in effect.*";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

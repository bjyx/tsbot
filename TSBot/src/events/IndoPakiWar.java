package events;

import java.awt.Color;

import game.Die;
import game.GameData;
import logging.Log;
import main.Launcher;
import map.MapManager;
/**
 * The Indo-Pakistani War Card.
 * @author adalbert
 *
 */
public class IndoPakiWar extends Card {
	
	private static int target;

	@Override
	public void onEvent(int sp, String[] args) {
		Die die = new Die();
		int mod = die.roll();
		Log.writeToLog("Roll: " + mod);
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.addMilOps(sp,  2);
		String adjacents = "";
		for (int i : MapManager.get(target).adj) {
			if (MapManager.get(i).isControlledBy()==(sp+1)%2) {
				mod--;
				adjacents += MapManager.get(i);
				Log.writeToLog(MapManager.get(i).iso.toUpperCase() + ": -1");
			}
		}
		builder.addField(target==34?"Pakistan Invades India":"India Invades Pakistan", die + (mod==die.result?":":(": - " + adjacents)), false);
		if (mod>=4) {
			builder.setTitle("Kashmir conflict resolved in favor of " + (target==34?"Pakistan":"India"))
				.setDescription("")
				.setFooter("\"Millions of lives were sacrificed to create this country. Pakistan is said to be the dream of Mohammad Iqbal and the creation of Muhammad Ali Jinnah, the Quaid-e-Azam. Was anything wrong with the dream or with the one who made the dream come true?\"\n"
						+ "- Zulfikar Ali Bhutto, 1978",Launcher.url("people/bhutto.png"))
				.setColor(sp==0?Color.blue:Color.red);
			builder.changeInfluence(target, sp, MapManager.get(target).influence[(sp+1)%2]);
			builder.changeInfluence(target, (sp+1)%2, -MapManager.get(target).influence[(sp+1)%2]);
			builder.changeVP(sp==0?2:-2);
		}
		else {
			builder.setTitle("Offensive Repulsed")
				.setDescription("Simla Accord marks return to status quo antebellum")
				.setFooter("\"India wants to avoid a war at all costs but it is not a one-sided affair, you cannot shake hands with a clenched fist.\"\n"
						+ "- Indira Gandhi, 1971",Launcher.url("people/indira.png"))
				.setColor(sp==0?Color.red:Color.blue);
		}
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
		return "024";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Indo-Pakistani War";
	}

	@Override
	public int getOps() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getEra() {
		// TODO Auto-generated method stub
		return 0;
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
		if (args.length<2) return false;
		target = MapManager.find(args[1]);
		if (target!=34 && target != 40) return false;
		return true;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Add 2 Military Operations to your count and select one of India or Pakistan to invade. Roll a die and subtract 1 for each neighbor of the target under your opponent's control. On a modified die roll of 4-6, gain 2 VP and replace all opponent's influence with your own.";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "A valid alias for either India or Pakistan, indicating the country to invade.";
	}

}

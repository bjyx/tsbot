package events;

import java.awt.Color;

import cards.HandManager;
import game.Die;
import game.GameData;
import logging.Log;
import main.Launcher;
import map.MapManager;
/**
 * The Korean War Card.
 * @author adalbert
 *
 */
public class KoreanWar extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		Die die = new Die();
		int mod = die.roll();
		Log.writeToLog("Roll: " +mod);
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.addMilOps(1,  2);
		String adjacents = "";
		for (int i : MapManager.get(42).adj) {
			if (MapManager.get(i).isControlledBy()==0) {
				mod--;
				adjacents += MapManager.get(i);
				Log.writeToLog(MapManager.get(i).iso.toUpperCase()+": -1");
			}
		}
		if (GameData.ccw && HandManager.China==-1) { //woo chinese civil war
			mod--;
			adjacents += MapManager.get(86);
		}
		builder.addField("North Korea Invades South Korea", die + (mod==die.result?":":(": - " + adjacents)), false);
		if (mod>=4) {
			builder.setTitle("Communist Victory in Korean War")
				.setDescription("Kim Il-Sung gives victory speech in Seoul; Congress in uproar")
				.setFooter("\"As of now South Korea is twice our size in population terms. But once we win over half the South's people in a confederation, we will be two parts to the South's one. We would then win either a general election or a war.\"\n"
						+ "- Kim Il-Sung, 1973", Launcher.url("people/kim.png"))
				.setColor(Color.red);
			builder.changeInfluence(42, 1, MapManager.get(42).influence[0]);
			builder.changeInfluence(42, 0, -MapManager.get(42).influence[0]);
			builder.changeVP(-2);
		}
		else {
			builder.setTitle("Korean War Devolves Into Stalemate")
				.setDescription("Armistice declared at Panmunjom")
				.setFooter("\"Of the nations of the world, Korea alone, up to now, is the sole one which has risked its all against communism. "
						+ "The magnificence of the courage and fortitude of the Korean people defies description. "
						+ "They have chosen to risk death rather than slavery. Their last words to me were: 'Don't scuttle the Pacific!'\"\n"
						+ "- Douglas MacArthur, 1951",Launcher.url("people/macarthur.png"))
				.setColor(Color.blue);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "011";
	}

	@Override
	public String getName() {
		return "Korean War";
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
		return "The USSR rolls 1 die and adds 2 Military Ops. Subtract 1 from the roll for every country adjacent to South Korea that the US controls. On a modified roll of 4-6, the USSR gains 2 VP and replace all influence in South Korea with your own.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

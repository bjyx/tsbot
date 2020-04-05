package yiyo;

import java.awt.Color;

import events.Card;
import events.CardEmbedBuilder;
import game.GameData;
import main.Launcher;
import map.MapManager;
/**
 * The Helsinki Accords Card from the Year-In and Year-Out Pack.
 * @author adalbert
 *
 */
public class HelsinkiAccords extends Card {
	
	public static boolean fringe = true;
	public static int target;

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Helsinki Accords Signed")
			.setDescription("Declaration brings an end to European cooperation conference")
			.setColor(Color.blue)
			.setFooter("\"If it all fails, Europe will be no worse off than it is now. If even a part of it succeeds, the lot the people in Eastern Europe will be that much better, and the cause of freedom will advance at least that far.\"\n"
					+ "- Gerald Ford, 1975", Launcher.url("yiyo/ford.png"));
		if (fringe) {
			builder.addField("No valid targets!", "The USSR has kept an incredibly tight rein on all of its puppet states.", false);
		}
		else {
			builder.changeInfluence(target, 1, -2);
			builder.changeVP(MapManager.get(target).influence[0]);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "133";
	}

	@Override
	public String getName() {
		return "The Helsinki Accords";
	}

	@Override
	public int getOps() {
		return 4;
	}

	@Override
	public int getEra() {
		return 1;
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
		fringe = true;
		for (int i=0; i<84; i++) {
			if (MapManager.get(i).isControlledBy()==1 && MapManager.get(i).influence[0]>0) {
				fringe = false;
				break;
			}
		}
		if (fringe) return true;
		if (args.length<2) return false;
		target = MapManager.find(args[1]);
		if (target==-1) return false;
		if (MapManager.get(target).isControlledBy()==1 && MapManager.get(target).influence[0]>0) return true;
		return false;
	}

	@Override
	public String getDescription() {
		return "The US selects a country controlled by the USSR with at least 1 US Influence, and gains VP equal to the amount of US influence in the country to a maximum of 5 VP. The USSR loses 2 Influence in the target.";
	}

	@Override
	public String getArguments() {
		return "The target country. If none exist no argument is required.";
	}

}

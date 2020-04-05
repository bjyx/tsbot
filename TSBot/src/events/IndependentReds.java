package events;

import java.awt.Color;
import java.util.ArrayList;

import game.GameData;
import logging.Log;
import main.Launcher;
import map.MapManager;
/**
 * The Independent Reds Card.
 * @author adalbert
 *
 */
public class IndependentReds extends Card {
	
	private static final int[] possible = {2, 4, 10, 14, 20}; //bg, cs, hu, ro, yu;
	
	private ArrayList<Integer> doable;
	
	private static int target;

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle(MapManager.get(target).name + " splits from Soviet Bloc")
			.setDescription("")
			.setFooter("\"Stop sending people to kill me. We've already captured five of them... If you don't stop sending killers, I'll send one to Moscow, and I won't have to send a second.\"\n"
					+ "- Josip Broz Tito, 19XX",Launcher.url("people/tito.png"))
			.setColor(Color.BLUE);
		if (doable.isEmpty()) {
			builder.addField("No countries to target!", "None of the targetable countries are valid targets for US Influence placement.", false);
			Log.writeToLog("No effect.");
		}
		else {
			builder.changeInfluence(target, 0, MapManager.get(target).influence[1]-MapManager.get(target).influence[0]);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "022";
	}

	@Override
	public String getName() {
		return "Independent Reds";
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isRemoved() {
		return true;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		doable = new ArrayList<Integer>();
		for (int i : possible) {
			if (MapManager.get(i).influence[1]-MapManager.get(i).influence[0]>0) {
				doable.add(i);
			}
		}
		if (doable.size()<=1) {
			if (doable.isEmpty()) target=20; //default flavor Yugoslavia
			else target = doable.get(0);
			return true;
		}
		if (args.length<2) return false;
		if (doable.contains(MapManager.find(args[1]))) {
			target = MapManager.find(args[1]);
			return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
		return "Add sufficient US Influence to *one* of the following countries such that it equals the amount USSR Influence in that country:\n"
				+ "- Bulgaria\n"
				+ "- Czechoslovakia\n"
				+ "- Hungary\n"
				+ "- Romania\n"
				+ "- Yugoslavia";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "A valid alias for one of Bulgaria, Czechoslovakia, Hungary, Romania, or Yugoslavia. If at most one of these countries is available for placing, the bot will handle it automatically; in this case no argument is required.";
	}

}

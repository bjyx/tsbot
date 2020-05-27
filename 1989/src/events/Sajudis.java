package events;

import java.awt.Color;

import cards.CardList;
import game.GameData;
import main.Common;
import map.MapManager;

public class Sajudis extends Card {

	public static int target;
	
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Sąjūdis Sweeps Lithuanian Elections")
		.setDescription("")
		.setColor(Color.blue);
		builder.changeInfluence(target, 0, Math.max(0,MapManager.get(target).stab+MapManager.get(target).support[1]-MapManager.get(target).support[0]));
		builder.changeStab();
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "033";
	}

	@Override
	public String getName() {
		return "Sąjūdis";
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
		return 0;
	}

	@Override
	public boolean isRemoved() {
		return true;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		target = MapManager.find(args[1]);
		return MapManager.get(target).icon==7; //Minority space
	}

	@Override
	public String getDescription() {
		return "The Democrat gains 1 VP and places sufficient support in any Minority space to gain control. *The USSR's stability decreases, allowing play of "+CardList.getCard(81)+".*";
	}

	@Override
	public String getArguments() {
		return "A valid alias for a minority space (Razgrad or Harghita-Covasna).";
	}

}

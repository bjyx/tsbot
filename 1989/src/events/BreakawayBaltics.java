package events;

import java.awt.Color;

import cards.CardList;
import cards.Operations;
import game.GameData;
import map.MapManager;

public class BreakawayBaltics extends Card {

	public static int target;

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Lithuanian Communist Party Breaks From Soviet Union")
		.setDescription("Multiparty elections scheduled for next year")
		.setColor(Color.blue);
		builder.changeInfluence(target, 0, Math.max(0,MapManager.get(target).stab+MapManager.get(target).support[1]-MapManager.get(target).support[0]));
		builder.changeStab();
		GameData.ops = new Operations(0, CardList.getCard(84).getOpsMod(0), false, true, false, 1);
		GameData.dec = new Decision(0, 1);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return GameData.getStab()==3;
	}

	@Override
	public String getId() {
		return "084";
	}

	@Override
	public String getName() {
		return "Breakaway Baltic Republics";
	}

	@Override
	public int getOps() {
		return 4;
	}

	@Override
	public int getEra() {
		return 2;
	}

	@Override
	public int getAssociation() {
		return 0;
	}
	//nice
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
		if (GameData.getStab()>3) return "The USSR is too stable. Play for Operations only.";
		return "The Democrat gains 5 VP and places sufficient support in any Minority space to gain control. The Democrat may then conduct a support check using this card's Operations. *The USSR's stability decreases, allowing play of "+CardList.getCard(109)+".*";
	}

	@Override
	public String getArguments() {
		return "Event: A valid alias for a minority space (Razgrad or Harghita-Covasna).\n"
				+ "Decision: Support Check syntax.";
	}

}

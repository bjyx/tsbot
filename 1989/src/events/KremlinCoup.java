package events;

import java.awt.Color;

import cards.CardList;
import cards.Operations;
import game.GameData;
import main.Common;
import map.MapManager;
import powerstruggle.PowerStruggle;

public class KremlinCoup extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Coup in the Kremlin!")
		.setDescription("Gorbachev Toppled from Power")
		.setColor(Color.red);
		builder.changeStab();
		for (int i=0; i<6; i++) {
			if (PowerStruggle.retained[i]!=-1) {
				for (int j=Common.bracket[i]; j<Common.bracket[i+1]; j++) {
					if (MapManager.get(j).icon==2) {
						builder.changeInfluence(j, 1, Math.max(0,MapManager.get(j).stab+MapManager.get(j).support[0]-MapManager.get(j).support[1]));
					}
				}
				for (int j=Common.bracket[i]; j<Common.bracket[i+1]; j++) {
					if (MapManager.get(j).icon==3) {
						GameData.ops = new Operations(1, getOpsMod(1), false, true, false, 1, 109);
						GameData.ops.realignment(j);
					}
				}
			}
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return GameData.getStab()==2;
	}

	@Override
	public String getId() {
		return "109";
	}

	@Override
	public String getName() {
		return "Kremlin Coup!";
	}

	@Override
	public int getOps() {
		return 3;
	}

	@Override
	public int getEra() {
		return 2;
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
		if (GameData.getStab()>2) return "The USSR is too stable. Play for Operations only.";
		return "The Democrat gains 1 VP and places sufficient support in any Minority space to gain control. *The USSR's stability decreases, allowing play of "+CardList.getCard(81)+".*";
	}

	@Override
	public String getArguments() {
		return "A valid alias for a minority space (Razgrad or Harghita-Covasna).";
	}

}

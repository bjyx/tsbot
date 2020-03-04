package yiyo;

import java.awt.Color;

import events.Card;
import events.CardEmbedBuilder;
import game.GameData;
import main.Launcher;
import map.MapManager;

public class BrotherSam extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("João Goulart Overthrown")
			.setDescription("Brazil replaces socialist-leaning president with American aid")
			.setColor(Color.blue)
			.setFooter("\"I think we ought to take every step that we can, be prepared to do everything that we need to do.\"\n"
					+ "- Lyndon B. Johnson, 1964", Launcher.url("yiyo/lbj.png"));
		builder.changeInfluence(76, 1, -1);
		int x=0;
		String str = "";
		for (int i=74; i<84; i++) {
			if (!MapManager.get(i).isBattleground && MapManager.get(i).isControlledBy()==0) x++;
			str += MapManager.get(i);
		}
		builder.addField("Cooperating countries: ", str, false);
		builder.changeInfluence(76, 0, x);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "132";
	}

	@Override
	public String getName() {
		return "Operation Brother Sam";
	}

	@Override
	public int getOps() {
		return 2;
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
		return true;
	}

	@Override
	public String getDescription() {
		return "Remove 1 USSR Influence from Brazil. Add 1 US Influence to Brazil for every non-battleground country controlled by the US in South America.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

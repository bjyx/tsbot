package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import game.GameData;
import map.MapManager;

public class PublicAgainstViolence extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Public Against Violence")
		.setColor(Color.red);
		builder.changeInfluence(29, 0, 2); //kosice
		builder.changeInfluence(33, 0, 2); //presov
		if (MapManager.get(26).support[1]>0) {
			HandManager.addEffect(105); //how +2 is effected
			GameData.ops = new Operations(0, CardList.getCard(105).getOpsMod(0), false, true, false, 1);
			GameData.txtchnl.sendMessage(builder.build()).complete();
			GameData.ops.realignment(26); //bratislava
		}
		else {
			builder.addField("No target available!", "Bratislava is... also against violence.", false);
			GameData.txtchnl.sendMessage(builder.build()).complete();
		}
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "105";
	}

	@Override
	public String getName() {
		return "Public Against Violence";
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
		return "Place 2 Democratic Support in each of Košice and Prešov. The Democrat then performs one Support Check on Bratislava with a +2 modifier.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

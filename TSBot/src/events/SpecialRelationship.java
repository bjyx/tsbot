package events;

import java.awt.Color;

import cards.HandManager;
import game.GameData;
import main.Launcher;
import map.MapManager;

public class SpecialRelationship extends Card {

	private static int target;
	
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("A Very Special Relationship").setDescription("Britain lends its support to the US in Europe").setFooter("\"Mr. Prime Minister, I am delighted to welcome you here today as an old friend; as an old friend not only in government, but as an old friend personally.\"\n"
				+ "- Richard Nixon, 1970", Launcher.url("leaders/nixon.png")).setColor(Color.blue);
		if (HandManager.Effects.contains(21)) {
			builder.changeInfluence(target, 0, 2).changeVP(2);
		}
		else {
			builder.changeInfluence(target, 0, 1);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		// TODO Auto-generated method stub
		return MapManager.get(18).isControlledBy()==0;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return "105";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Special Relationship";
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
		return 0;
	}

	@Override
	public boolean isRemoved() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		target = MapManager.find(args[1]);
		if (HandManager.Effects.contains(21)) {
			if (MapManager.get(target).region==0||MapManager.get(target).region==1) return true;
		}
		else {
			if (MapManager.get(target).adj.contains(18)) return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
		if (MapManager.get(18).isControlledBy()!=0) return "The US does not control the UK. Play this for Operations.";
		if (HandManager.effectActive(21)) return "NATO is not active. The US adds 1 Influence to a country adjacent to the UK. *This effect will be improved after NATO is founded. Can only be played if the US controls the UK.*";
		return "NATO is active. The US gains 2 VP and adds 2 Influence to any country in Western Europe. *Can only be played if the US controls the UK.*";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "A country that satisfies the condition expressed in the description.";
	}

}

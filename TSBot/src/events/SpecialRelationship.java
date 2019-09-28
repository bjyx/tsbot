package events;

import java.awt.Color;

import cards.HandManager;
import game.GameData;
import map.MapManager;

public class SpecialRelationship extends Card {

	private static int target;
	
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("").setDescription("").setFooter("", "").setColor(Color.blue);
		if (HandManager.Effects.contains(21)) {
			builder.changeInfluence(target, 0, 2).changeVP(2);
		}
		else {
			builder.changeInfluence(target, 0, 1);
		}
		GameData.txtchnl.sendMessage(builder.build());
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
	public boolean isFormatted(String[] args) {
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
		// TODO Auto-generated method stub
		return "If NATO is active, the US gains 2 VP and adds 2 Influence to any country in Western Europe; if NATO is not active, then the US adds 1 Influence to a country adjacent to the UK. *Can only be played if the US controls the UK.*";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "A country that satisfies the condition expressed in the description.";
	}

}

package events;

import java.awt.Color;

import game.GameData;
import main.Launcher;
import map.MapManager;

public class KitchenDebates extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Kitchen Debates!").setDescription("Khrushchev and Nixon Talk Technology")
		.setFooter("\"Certainly it will, and everything I say is to be translated into Russian and broadcast across the Soviet Union. That's a fair bargain.\"\n"
				+ "- Richard M. Nixon, 1959", Launcher.url("countries/us.png"))
		.setColor(Color.blue);
		builder.changeVP(2);
		builder.addField("Nixon's Chest Jab","Poke! Poke! Poke!",false);
		GameData.txtchnl.sendMessage(builder.build());
	}

	@Override
	public boolean isPlayable(int sp) {
		int usa=0,ssr=0;
		for (int i=0; i<84; i++) {
			if (MapManager.get(i).isBattleground) {
				if (MapManager.get(i).isControlledBy()==0) usa++;
				else if (MapManager.get(i).isControlledBy()==1) ssr++;
			}
		}
		return usa>ssr;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return "048";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Kitchen Debates";
	}

	@Override
	public int getOps() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getEra() {
		// TODO Auto-generated method stub
		return 1;
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
	public boolean isFormatted(String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "The US gains 2 VP and pokes the opponent in the chest. *Only playable if the US has more battlegrounds than the USSR.*";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "None.";
	}

}

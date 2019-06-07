package events;

import java.awt.Color;

import game.GameData;
import main.Launcher;
import map.MapManager;

public class ArabIsraeliWar extends Card {

	@Override
	public void onEvent(String[] args) {
		int die = (int) (Math.random()*6+1);
		int mod = die;
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.addMilOps(1,  2);
		String adjacents = "";
		for (int i : MapManager.get(25).adj) {
			if (MapManager.get(i).isControlledBy()==0) {
				mod--;
				adjacents += MapManager.get(i);
			}
		}
		if (MapManager.get(25).isControlledBy()==0) {
			mod--;
			adjacents += MapManager.get(25);
		}
		builder.addField("Arab League invades Israel", "Roll: " + CardEmbedBuilder.numbers[die] + (mod==die?"":(" - " + adjacents)), false);
		if (die>=4) {
			builder.setTitle("")
				.setDescription("Palestinian State established in Jerusalem; Congress in uproar")
				.setFooter("\"To contribute posi­tively to the work of building the state, let those with strength give strength, let those with knowledge give knowledge, let those with money give money, and let all people who truly love their country, their nation and democ­racy unite closely and build an independent and sovereign democratic state.\"\n"
						+ "- Kim Il-Sung, 1945", Launcher.url("countries/kp.png"))
				.setColor(Color.red);
			builder.changeInfluence(42, 1, MapManager.get(42).influence[0]);
			builder.changeInfluence(42, 0, -MapManager.get(42).influence[0]);
			builder.changeVP(-2);
		}
		else {
			builder.setTitle("Korean War Devolves Into Stalemate")
				.setDescription("Armistice declared at Panmunjom")
				.setFooter("\"That we were able to snatch victory from the jaws of defeat ... does not relieve us from the blame of having placed our own flesh and blood in such a predicament.\"\n"
						+ "- Major General Floyd L. Parks, 195X",Launcher.url("countries/us.png"))
				.setColor(Color.blue);
		}
		GameData.txtchnl.sendMessage(builder.build());
	}

	@Override
	public boolean isPlayable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getOps() {
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return null;
	}

}

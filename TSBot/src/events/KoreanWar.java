package events;

import java.awt.Color;

import cards.HandManager;
import game.GameData;
import main.Launcher;
import map.MapManager;

public class KoreanWar extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		int die = (int) (Math.random()*6+1);
		int mod = die;
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.addMilOps(1,  2);
		String adjacents = "";
		for (int i : MapManager.get(42).adj) {
			if (MapManager.get(i).isControlledBy()==0) {
				mod--;
				adjacents += MapManager.get(i);
			}
		}
		if (GameData.ccw && HandManager.China==-1) { //woo chinese civil war
			mod--;
			adjacents += MapManager.get(86);
		}
		builder.addField("North Korea Invades South Korea", "Roll: :" + numbers[die] + (mod==die?":":(": - " + adjacents)), false);
		if (die>=4) {
			builder.setTitle("Communist Victory in Korean War")
				.setDescription("Kim Il-Sung gives victory speech in Seoul; Congress in uproar")
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
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "011";
	}

	@Override
	public String getName() {
		return "Korean War";
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
		return 1;
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
		return "The USSR rolls 1 die and adds 2 Military Ops. Subtract 1 from the roll for every country adjacent to South Korea that the US controls. On a modified roll of 4-6, the USSR gains 2 VP and replace all influence in South Korea with your own.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

package events;

import java.awt.Color;

import game.GameData;
import main.Launcher;
import map.MapManager;

public class IndoPakiWar extends Card {
	
	private static int target;

	@Override
	public void onEvent(int sp, String[] args) {
		int die = (int) (Math.random()*6+1);
		int mod = die;
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.addMilOps(sp,  2);
		String adjacents = "";
		for (int i : MapManager.get(target).adj) {
			if (MapManager.get(i).isControlledBy()==(sp+1)%2) {
				mod--;
				adjacents += MapManager.get(i);
			}
		}
		builder.addField(target==34?"Pakistan Invades India":"India Invades Pakistan", "Roll: :" + numbers[die] + (mod==die?":":(": - " + adjacents)), false);
		if (die>=4) {
			builder.setTitle("Kashmir conflict resolved in favor of " + (target==34?"Pakistan":"India"))
				.setDescription("")
				.setFooter("\"The number of women who have been kidnapped and raped makes my heart bleed. "
						+ "The wild forces thus let loose on the State are marching on with the aim of capturing Srinagar, the summer Capital of my Government, as first step to over-running the whole State [of Kashmir].\"\n"
						+ "- Maharaja Hari Singh, 1947",Launcher.url("people/harisingh.png"))
				.setColor(sp==0?Color.blue:Color.red);
			builder.changeInfluence(target, sp, MapManager.get(target).influence[(sp+1)%2]);
			builder.changeInfluence(target, (sp+1)%2, -MapManager.get(target).influence[(sp+1)%2]);
			builder.changeVP(sp==0?2:-2);
		}
		else {
			builder.setTitle("Offensive Repulsed")
				.setDescription("Simla Accord marks return to status quo antebellum")
				.setFooter("\"`CONTINUING PROPAGANDA RE ACHIEVEMENTS OF PAK FORCES SEEMS TO HAVE CONVINCED MOST THAT ONLY PAK FORBEARANCE SAVED THE INDIANS FROM DISASTER.`\"\n"
						+ "- Telegram from the US Embassy in Karachi, 1965",Launcher.url("people/wargames2.png"))
				.setColor(sp==0?Color.red:Color.blue);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return "024";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Indo-Pakistani War";
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
		return 2;
	}

	@Override
	public boolean isRemoved() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		if (args.length<2) return false;
		target = MapManager.find(args[1]);
		if (target!=34 && target != 40) return false;
		return true;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Add 2 Military Operations to your count and select one of India or Pakistan to invade. Roll a die and subtract 1 for each neighbor of the target under your opponent's control. On a modified die roll of 4-6, gain 2 VP and replace all opponent's influence with your own.";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "A valid alias for either India or Pakistan, indicating the country to invade.";
	}

}

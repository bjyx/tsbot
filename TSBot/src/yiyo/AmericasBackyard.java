package yiyo;

import java.awt.Color;

import cards.Operations;
import events.Card;
import events.CardEmbedBuilder;
import events.Decision;
import game.GameData;
import main.Launcher;
import map.MapManager;

public class AmericasBackyard extends Card {
	
	public static int USSRControl=0; //for later
	public static int target1;
	public static int target2;
	public static boolean opponentInfluence;

	@Override
	public void onEvent(int sp, String[] args) {
		opponentInfluence=false;
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("US Intervenes in " + MapManager.get(target1).name)
			.setDescription("")
			.setFooter("\"You must understand that having lost American lives to restore democracy in Panama, we cannot allow Noriega to go to any other country than the United States.\"\n" + 
					"- James Baker, 1989", Launcher.url("yiyo/baker.png"))
			.setColor(Color.blue);
		builder.changeInfluence(target1, 0, 2);
		builder.changeInfluence(target2, 0, 2);
		builder.addField("A Just Cause and an Urgent Fury", "The US may conduct two realignments in Central America.", false);
		USSRControl=0;
		GameData.txtchnl.sendMessage(builder.build()).complete();
		
		for (int i=64; i<74; i++) {
			if (MapManager.get(i).influence[1]>0) {
				opponentInfluence=true;
				if (MapManager.get(i).isControlledBy()==1) {
					USSRControl++;
				}
			}
		}
		if(opponentInfluence) {
			GameData.ops=new Operations(sp, 2, false, true, false, false, false, 136); //you asked for 2 flat :|
			GameData.dec=new Decision(sp, 136);
			GameData.txtusa.sendMessage(GameData.roleusa.getAsMention()+", you may now conduct realignments on a country of your choosing in Central America.").complete();
		}
		else GameData.txtusa.sendMessage("Your opponent has not infiltrated Central America yet.").complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "136";
	}

	@Override
	public String getName() {
		return "America's Backyard";
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
		if (args.length < 3) return false;
		target1 = MapManager.find(args[1]);
		target2 = MapManager.find(args[2]);
		if (target1==-1||target2==-1) return false; //must exist xp
		if (MapManager.get(target1).isBattleground||MapManager.get(target2).isBattleground) return false; //non-battlegrounds only
		if (MapManager.get(target1).region==7&&MapManager.get(target2).region==7) return true; //central america
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

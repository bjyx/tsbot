package cards;

import java.util.Random;

import events.CardEmbedBuilder;
import game.GameData;
import map.Country;
import map.MapManager;

public class Operations {
	private boolean realignment = false; //0 = anything else - that uses ops in one fell swoop, 1 = realignment
	private int opnumber = -1;
	private int restrictions = 7;
	public static final int[] spaceOps = {2, 2, 2, 2, 3, 3, 4, 4};
	public static final int[] spaceVP = {2, 0, 2, 0, 3, 0, 4, 2};
	public static final int[] spaceVP2 = {1, 0, 0, 0, 1, 0, 2, 0};
	public static final int[] spaceRoll = {3, 4, 3, 4, 3, 4, 3, 2};
	
	public Operations(int ops) {
		opnumber = ops;
	}
	
	public void influence(int sp, int[] countries, int[] amt) {
		if (HandManager.playmode!='o') {
			GameData.txtchnl.sendMessage(":x: Changed your mind on this card already?");
			return;
		}
		if (realignment) {
			GameData.txtchnl.sendMessage(":x: You're already doing realignments.");
			return;
		}
		int ops = 0;
		for (int i=0; i<countries.length; i++) {
			ops += (Math.max(0, MapManager.map.get(countries[i]).influence[(sp+1)%2]-MapManager.map.get(countries[i]).influence[sp]-MapManager.map.get(countries[i]).getStab()+1))*2 + amt[i]-Math.max(0, MapManager.map.get(countries[i]).influence[(sp+1)%2]-MapManager.map.get(countries[i]).influence[sp]-MapManager.map.get(countries[i]).getStab()+1);
		}
		if (ops!=opnumber) {
			GameData.txtchnl.sendMessage(":x: Endeavor to use all available ops.");
			return;
		}
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Influence Placement");
		if (new Random().nextInt(14)==0) builder.setDescription("\"You have a row of dominoes set up, you knock over the first one, and what will happen to the last one is the certainty that it will go over very quickly. So you could have a beginning of a disintegration that would have the most profound influences.\" \n-Dwight David Eisenhower, 1954");
		for (int i=0; i<countries.length; i++) {
			builder.changeInfluence(countries[i], sp, amt[i]);
		}
		GameData.txtchnl.sendMessage(builder.build());
		GameData.txtchnl.sendMessage("`Operations Complete`");
	}
	public void realignment(int sp, int country) {
		if (HandManager.playmode!='o') {
			GameData.txtchnl.sendMessage(":x: Changed your mind on this card already?");
			return;
		}
		realignment = true;
		if (opnumber<1) {
			GameData.txtchnl.sendMessage(":x: You've run out of ops...");
			return;
		}
		if (MapManager.map.get(country).influence[(sp+1)%2]==0) {
			GameData.txtchnl.sendMessage(":x: This country is fresh out of foreign influence, I can tell you that.");
			return;
		}
		Random rand = new Random();
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Realignment")
		.setDescription("Target: "+ MapManager.map.get(country));
		int[] rolls = new int[] {rand.nextInt(6)+1, rand.nextInt(6)+1};
		String[] modifiers = {"",""};
		if (MapManager.map.get(country).influence[0]>MapManager.map.get(country).influence[1]) {
			rolls[0]++;
			modifiers[0] += MapManager.map.get(country);
		}
		if (MapManager.map.get(country).influence[0]<MapManager.map.get(country).influence[1]) {
			rolls[1]++;
			modifiers[1] += MapManager.map.get(country);
		}
		for (int adj : MapManager.map.get(country).getAdj()) {
			if (adj==84) {
				rolls[0]++;
				modifiers[0] += MapManager.map.get(adj);
			}
			else if (adj==85) {
				rolls[1]++;
				modifiers[1] += MapManager.map.get(adj);
			}
			else {
				if (MapManager.map.get(adj).isControlledBy()!=-1) {
					rolls[MapManager.map.get(adj).isControlledBy()]++;
					modifiers[MapManager.map.get(adj).isControlledBy()] += MapManager.map.get(adj);
				}
			}
		}
		builder.addField("Rolls", ":flag_us::" + CardEmbedBuilder.numbers[rolls[0]] + "::heavy_plus:" + modifiers[0] + "\n:flag_su::" + CardEmbedBuilder.numbers[rolls[1]] + "::heavy_plus:" + modifiers[1], false);
		if (rolls[1]>rolls[0]) builder.changeInfluence(country, 0, -(rolls[1]-rolls[0]));
		if (rolls[0]>rolls[1]) builder.changeInfluence(country, 1, -(rolls[0]-rolls[1]));
		GameData.txtchnl.sendMessage(builder.build());
		GameData.txtchnl.sendMessage("`Operations Complete`");
	}
	
	public void coup(int sp, int country) {
		if (HandManager.playmode!='o') {
			GameData.txtchnl.sendMessage(":x: Changed your mind on this card already?");
			return;
		}
		if (realignment) {
			GameData.txtchnl.sendMessage(":x: You're already doing realignments.");
			return;
		}
		if (restrictions%2==0) return;
		if (!MapManager.map.get(country).checkIsCoupable()) return;
		if (MapManager.map.get(country).isBattleground()) GameData.setDEFCON(GameData.getDEFCON()-1);
		GameData.addMilOps(sp, opnumber);
		int die = (new Random().nextInt(6))+1;
		int amt = opnumber + die - MapManager.map.get(country).getStab()*2;
		if (amt>0) {
			if (amt>MapManager.map.get(country).influence[(sp+1)%2]) {
				MapManager.map.get(country).changeInfluence(sp, amt-MapManager.map.get(country).influence[(sp+1)%2]);
				MapManager.map.get(country).changeInfluence((sp+1)%2, MapManager.map.get(country).influence[(sp+1)%2]);
			}
		}
	}
	public void space(int sp) {
		if (HandManager.playmode!='s') {
			GameData.txtchnl.sendMessage(":x: Changed your mind on this card already?");
			return;
		}
		if (realignment) {
			GameData.txtchnl.sendMessage(":x: You're already doing realignments.");
			return;
		}
	}
	
}

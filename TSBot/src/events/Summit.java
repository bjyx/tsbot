package events;

import java.awt.Color;

import game.GameData;
import main.Launcher;
import map.MapManager;

public class Summit extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Summit Between the Superpowers")
			.setDescription("Nuclear Arms Control and other topics to be discussed in Moscow meeting")
			.setColor(Color.gray).setFooter("", Launcher.url("countries/"));
		int die_usa = (int) (6*Math.random())+1;
		int die_ssr = (int) (6*Math.random())+1;
		String str_usa = "";
		String str_ssr = "";
		//Europe
		int[] bg = new int[]{0,0};
		int[] c = new int[]{0,0};
		for (int i=0; i<21; i++) {
			if (MapManager.get(i).isControlledBy()!=-1) {
				c[MapManager.get(i).isControlledBy()]++;
				if (MapManager.get(i).isBattleground) {
					bg[MapManager.get(i).isControlledBy()]++;
				}
			}
		}
		if (bg[0]>bg[1]&&c[0]>c[1]&&(bg[0]==5||c[0]>bg[0])) {
			die_usa++;
			str_usa += "Europe\n";
		}
		if (bg[1]>bg[0]&&c[1]>c[0]&&(bg[1]==5||c[1]>bg[1])) {
			die_ssr++;
			str_ssr += "Europe\n";
		}
		//Middle East
		bg = new int[]{0,0};
		c = new int[]{0,0};
		for (int i=21; i<31; i++) {
			if (MapManager.get(i).isControlledBy()!=-1) {
				c[MapManager.get(i).isControlledBy()]++;
				if (MapManager.get(i).isBattleground) {
					bg[MapManager.get(i).isControlledBy()]++;
				}
			}
		}
		if (bg[0]>bg[1]&&c[0]>c[1]&&(bg[0]==5||c[0]>bg[0])) {
			die_usa++;
			str_usa += "Middle East\n";
		}
		if (bg[1]>bg[0]&&c[1]>c[0]&&(bg[1]==5||c[1]>bg[1])) {
			die_ssr++;
			str_ssr += "Middle East\n";
		}
		//Asia
		bg = new int[]{0,0};
		c = new int[]{0,0};
		for (int i=31; i<46; i++) {
			if (MapManager.get(i).isControlledBy()!=-1) {
				c[MapManager.get(i).isControlledBy()]++;
				if (MapManager.get(i).isBattleground) {
					bg[MapManager.get(i).isControlledBy()]++;
				}
			}
		}
		if (bg[0]>bg[1]&&c[0]>c[1]&&(bg[0]==5||c[0]>bg[0])) {
			die_usa++;
			str_usa += "Asia\n";
		}
		if (bg[1]>bg[0]&&c[1]>c[0]&&(bg[1]==5||c[1]>bg[1])) {
			die_ssr++;
			str_ssr += "Asia\n";
		}
		//Africa
		bg = new int[]{0,0};
		c = new int[]{0,0};
		for (int i=46; i<64; i++) {
			if (MapManager.get(i).isControlledBy()!=-1) {
				c[MapManager.get(i).isControlledBy()]++;
				if (MapManager.get(i).isBattleground) {
					bg[MapManager.get(i).isControlledBy()]++;
				}
			}
		}
		if (bg[0]>bg[1]&&c[0]>c[1]&&(bg[0]==5||c[0]>bg[0])) {
			die_usa++;
			str_usa += "Africa\n";
		}
		if (bg[1]>bg[0]&&c[1]>c[0]&&(bg[1]==5||c[1]>bg[1])) {
			die_ssr++;
			str_ssr += "Africa\n";
		}
		//Central America
		bg = new int[]{0,0};
		c = new int[]{0,0};
		for (int i=64; i<74; i++) {
			if (MapManager.get(i).isControlledBy()!=-1) {
				c[MapManager.get(i).isControlledBy()]++;
				if (MapManager.get(i).isBattleground) {
					bg[MapManager.get(i).isControlledBy()]++;
				}
			}
		}
		if (bg[0]>bg[1]&&c[0]>c[1]&&(bg[0]==5||c[0]>bg[0])) {
			die_usa++;
			str_usa += "Central America\n";
		}
		if (bg[1]>bg[0]&&c[1]>c[0]&&(bg[1]==5||c[1]>bg[1])) {
			die_ssr++;
			str_ssr += "Central America\n";
		}
		//South America
		bg = new int[]{0,0};
		c = new int[]{0,0};
		for (int i=74; i<84; i++) {
			if (MapManager.get(i).isControlledBy()!=-1) {
				c[MapManager.get(i).isControlledBy()]++;
				if (MapManager.get(i).isBattleground) {
					bg[MapManager.get(i).isControlledBy()]++;
				}
			}
		}
		if (bg[0]>bg[1]&&c[0]>c[1]&&(bg[0]==5||c[0]>bg[0])) {
			die_usa++;
			str_usa += "South America\n";
		}
		if (bg[1]>bg[0]&&c[1]>c[0]&&(bg[1]==5||c[1]>bg[1])) {
			die_ssr++;
			str_ssr += "South America\n";
		}
		builder.addField(":flag_us:", str_usa, true)
			.addField(CardEmbedBuilder.intToEmoji(die_usa)+"-"+CardEmbedBuilder.intToEmoji(die_ssr), "", true)
			.addField(":flag_su:", str_ssr, true);
		
		if (die_usa>die_ssr) {
			builder.changeVP(2);
			GameData.txtusa.sendMessage(GameData.roleusa.getAsMention()+", decide how you want to change DEFCON. (TS.decide 0 or -1 or 1)").complete();
			GameData.dec = new Decision(0, 45);
		}
		else if (die_ssr>die_usa) {
			builder.changeVP(-2);
			GameData.txtssr.sendMessage(GameData.rolessr.getAsMention()+", decide how you want to change DEFCON. (TS.decide 0 or -1 or 1)").complete();
			GameData.dec = new Decision(1, 45);
		}
		else {
			builder.addField("Inconclusive meeting", "No change in VP", false);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "045";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Summit";
	}

	@Override
	public int getOps() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getEra() {
		return 1;
	}

	@Override
	public int getAssociation() {
		return 2;
	}

	@Override
	public boolean isRemoved() {
		return false;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Both players roll a die and add 1 for every region they dominate or control. No rerolls. The person who rolls higher gains 2 VP and may increase **or decrease DEFCON** by 1 (This includes not changing DEFCON).";
	}

	@Override
	public String getArguments() {
		return "Event: None.\n"
				+ "Decision: 1, 0, or -1, indicating the amount to change DEFCON by.";
	}

}

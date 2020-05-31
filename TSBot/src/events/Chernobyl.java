package events;

import java.awt.Color;

import cards.HandManager;
import game.GameData;
import logging.Log;
import main.Launcher;
import map.Country;
/**
 * The Chernobyl Card.
 * @author adalbert
 *
 */
public class Chernobyl extends Card {
	
	public static boolean[] regionBan = new boolean[10];
	public static int reg;
	
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Chernobyl Meltdown")
			.setDescription("Fallout spreads across Europe in largest nuclear disaster to date")
			.setColor(Color.blue)
			.setFooter("\"We were just not prepared for that sort of situation.\"\n"
					+ "- Mikhail Gorbachev [retrospective], 1996", Launcher.url("people/gorbachev.png"));
		builder.addField("Fallout","The USSR is not allowed to use operations to place influence in " + Country.regions[reg] + " for the rest of the turn.",false);
		HandManager.addEffect(94);
		Log.writeToLog("Chernobyl Active.");
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "094";
	}

	@Override
	public String getName() {
		return "Chernobyl";
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
		regionBan = new boolean[10];
		String x = args[1];
		if (x.charAt(0)=='e') {
			regionBan[0]=true;
			regionBan[1]=true;
			regionBan[2]=true;
			reg = 0;
		}
		else if (x.charAt(0)=='a') {
			if (x.charAt(1)=='f') {
				regionBan[6]=true;
				reg = 6;
			}
			else {
				regionBan[4]=true;
				regionBan[5]=true;
				reg = 4;
			}
		}
		else if (x.charAt(0)=='f') {
			regionBan[6]=true;
			reg = 6;
		}
		else if (x.charAt(0)=='c') {
			regionBan[7]=true;
			reg = 7;
		}
		else if (x.charAt(0)=='s') {
			regionBan[8]=true;
			reg = 8;
		}
		else if (x.charAt(0)=='m') {
			regionBan[3]=true;
			reg = 3;
		}
		return regionBan[0]||regionBan[3]||regionBan[4]||regionBan[6]||regionBan[7]||regionBan[8];
	}

	@Override
	public String getDescription() {
		return "*For the rest of the turn, the USSR may not use Operations to place Influence Points in a specified region.*";
	}

	@Override
	public String getArguments() {
		return "The region, designated by the following strings with any case:\n"
				+ "- europe, eu, e, other words starting with 'e'\n"
				+ "- asia, as, a, other words starting with 'a'\n"
				+ "- middle east, me, m, other words starting with 'm'\n"
				+ "- africa, af, f, other words starting with 'af' or 'f'\n"
				+ "- centralamerica, centram, ca, c, other words starting with 'c'\n"
				+ "- southamerica, southam, sa, s, other words starting with 's'";
	}

}

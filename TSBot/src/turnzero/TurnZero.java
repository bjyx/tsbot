package turnzero;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import cards.HandManager;
import events.CardEmbedBuilder;
import events.Decision;
import game.GameData;
import game.PlayerList;
import main.Launcher;
import net.dv8tion.jda.core.EmbedBuilder;

public class TurnZero {
	protected static class LeaderCard {
		protected String name;
		protected int type; 
		public LeaderCard(String n, int t) {
			name=n;type=t;
		}
		public String toString() {
			return name;
		}
	}
	
	public static int crisis=-1;
	private static List<Integer> europe = Arrays.asList(0,1,2);
	private static List<Integer> asia = Arrays.asList(3,4,5);
	public static List<LeaderCard> USA = Arrays.asList(
			new TurnZero.LeaderCard("Kennan", 1),
			new TurnZero.LeaderCard("Acheson", 1),
			new TurnZero.LeaderCard("Marshall", 2),
			new TurnZero.LeaderCard("Dulles", -1),
			new TurnZero.LeaderCard("OSS", 0)
			);
	public static List<LeaderCard> USSR = Arrays.asList(
			new TurnZero.LeaderCard("Andropov", 1),
			new TurnZero.LeaderCard("Khrushchev", 1),
			new TurnZero.LeaderCard("Molotov", 2),
			new TurnZero.LeaderCard("Beria", -1),
			new TurnZero.LeaderCard("KGB", 0)
			);
	public static LeaderCard[] played = new LeaderCard[] {null, null};
	public static int[] results = new int[] {3, 4, 3, 4, 3, 4};
	/**
	 * Start the crisis designated by the integer i.
	 * @param i is the ID of the crisis.
	 */
	public static boolean startCrisis() {
		if (GameData.ccw) asia.remove((Integer) 4);
		if (asia.isEmpty()) return false;
		if (europe.isEmpty()) {
			crisis = asia.remove((int) (Math.random()*asia.size()));
		}
		else crisis = europe.remove((int) (Math.random()*europe.size()));
		switch(crisis){
			case 0:
				GameData.txtchnl.sendMessage(new EmbedBuilder().setTitle("Yalta and Potsdam").setAuthor("Turn Zero Crisis").setImage(Launcher.url("tz/yalta.png")).build());
				break;
			case 1:
				GameData.txtchnl.sendMessage(new EmbedBuilder().setTitle("VE Day").setAuthor("Turn Zero Crisis").setImage(Launcher.url("tz/ve.png")).build());
				break;
			case 2:
				GameData.txtchnl.sendMessage(new EmbedBuilder().setTitle("1945 UK Election").setAuthor("Turn Zero Crisis").setImage(Launcher.url("tz/election.png")).build());
				break;
			case 3:
				GameData.txtchnl.sendMessage(new EmbedBuilder().setTitle("Israel").setAuthor("Turn Zero Crisis").setImage(Launcher.url("tz/israel.png")).build());
				break;
			case 4:
				GameData.txtchnl.sendMessage(new EmbedBuilder().setTitle("Chinese Civil War").setAuthor("Turn Zero Crisis").setImage(Launcher.url("tz/ccw.png")).build());
				break;
			case 5:
				GameData.txtchnl.sendMessage(new EmbedBuilder().setTitle("VJ Day").setAuthor("Turn Zero Crisis").setImage(Launcher.url("tz/vj.png")).build());
				break;
			default:
				break;
		}
		GameData.txtssr.sendMessage(PlayerList.getSSR().getAsMention()+ ", please play a statecraft card.");
		GameData.txtusa.sendMessage(PlayerList.getUSA().getAsMention()+ ", please play a statecraft card.");
		sendHands();
		return true;
	}
	/**
	 * Ends a started crisis by rolling a die.
	 */
	public static void endCrisis() {
		int die = (int) (Math.random()*6) + 1;
		EmbedBuilder builder = new EmbedBuilder().setAuthor("Turn Zero Crisis Resolution").addField(":flag_us:", getEffectEmoji(played[0].type), true).addField(":game_die:",CardEmbedBuilder.intToEmoji(die),true).addField(":flag_su:", getEffectEmoji(played[1].type), true);
		if (!(played[0].type==-1||played[1].type==-1)) {
			die += (played[0].type-played[1].type);
		}
		results[crisis] = die;
		switch(crisis) {
		case 0:
			if (die<=1) {
				builder
					.setTitle("FDR at Potsdam")
					.setDescription("Soviets planning puppet regimes")
					.setColor(Color.RED)
					.addField("European Communism","USSR may add 1 Influence anywhere in Europe.",false)
					.addField("Asian Antagonism", "USSR may add one of Vietnam Revolts or Arab-Israeli War to hand before dealing.", false)
					.addField("TS.decide *[country] [card]*","[country]: an alias of any country in Europe\n[card]: One of `9` or `13`, respectively Vietnam Revolts and Arab-Israeli War", false)
					.setFooter("\"I think that if I give him everything I possibly can and ask for nothing from him in return, 'noblesse oblige', he won't try to annex anything and will work with me for a world of democracy and peace.\"\n"
							+ "- Franklin Delano Roosevelt, 1945", Launcher.url("countries/us.png"));
				GameData.dec = new Decision(1,1001);
			}
			else if (die <= 3) {
				builder.setTitle("FDR dies").setDescription("Truman to attend Potsdam Conference").addField("Historical Result", "Nothing happens.", false).setColor(Color.GRAY);
			}
			else if (die <= 5) {
				builder.setTitle("Truman at Yalta").setDescription("Defensive measures taken to ensure European freedom").setColor(Color.blue)
					.addField("Recovery Plan","USA adds Marshall Plan to hand before dealing.",false);
			}
			else {
				builder.setTitle("Truman at Yalta").setDescription("Hard-line policy towards Soviet aggression formulated").setColor(Color.blue)
				.addField("Recovery Plan","USA adds Marshall Plan to hand before dealing.",false)
				.addField("Containment","USA will go first in all turns during the Early War",false)
				.setFooter("\"[D]espite the contrast between his relatively modest background and the international glamour of his aristocratic predecessor, [Truman] had the courage and resolution to reverse the policy that appeared to him naive and dangerous...\"\n"
						+ "- George Lenczowski, 1945", Launcher.url("countries/pl.png"));
				HandManager.addEffect(1001);
			}
			break;
		case 1:
			if (die<=1) {
				builder
					.setTitle("")
					.setDescription("")
					.setColor(Color.RED)
					.addField("Soviets over the Elbe","USSR adds 1 Influence in ",false)
					.addField("Asian Antagonism", "USSR may add one of Vietnam Revolts or Arab-Israeli War to hand before dealing.", false)
					.addField("TS.decide *[country] [card]*","[country]: an alias of any country in Europe\n[card]: One of `9` or `13`, respectively Vietnam Revolts and Arab-Israeli War", false)
					.setFooter("\"I think that if I give him everything I possibly can and ask for nothing from him in return, 'noblesse oblige', he won't try to annex anything and will work with me for a world of democracy and peace.\"\n"
							+ "- Franklin Delano Roosevelt, 1945", Launcher.url("countries/us.png"));
				GameData.dec = new Decision(1,1001);
			}
			else if (die <= 3) {
				builder.setTitle("Eastern Europe overrun").setDescription("Soviets tighten control over occupied territory")
					.addField("Communist Regimes","USSR receives two additional Eastern European influence during setup.",false);;
			}
			else if (die <= 5) {
				builder.setTitle("Berlin falls").setDescription("Soviet flag flying over Reichstag building")
				.addField("Historical Result", "Nothing happens.", false).setColor(Color.GRAY);
					
			}
			else {
				builder.setTitle("Truman at Yalta").setDescription("Hard-line policy towards Soviet aggression formulated")
				.addField("Recovery Plan","USA adds Marshall Plan to hand before dealing.",false)
				.addField("Containment","USA will go first in all turns during the Early War",false)
				.setFooter("\"[D]espite the contrast between his relatively modest background and the international glamour of his aristocratic predecessor, [Truman] had the courage and resolution to reverse the policy that appeared to him naive and dangerous...\"\n"
						+ "- George Lenczowski, 1945", Launcher.url("countries/pl.png"));
				HandManager.addEffect(1001);
			}
			break;
			
		case 2:
			
			break;
			
		case 3:
			
			break;
		
		case 4:
			
			break;
			
		case 5:
			
			break;
		
		default:
			//bruh what
			break;
		}
	}
	
	public static boolean playLeaderCard(int sp, String s) {
		if (sp==0) {
			for (LeaderCard c : USA) {
				if (s.equals(c.name)) {
					played[0]=c;
					if (c.type!=0) USA.remove(USA.indexOf(c));
					return true;
				}
			}
			return false;
		}
		if (sp==1) {
			for (LeaderCard c : USSR) {
				if (s.equals(c.name)) {
					played[1]=c;
					if (c.type!=0) USSR.remove(USSR.indexOf(c));
					return true;
				}
			}
			return false;
		}
		return false;
	}
	
	public static void sendHands() {
		EmbedBuilder usahand = new EmbedBuilder().setTitle("Statecraft Cards");
		for (LeaderCard c : USA) {
			usahand.addField(c.name, getEffect(c.type), false);
		}
		GameData.txtusa.sendMessage(usahand.build());
		EmbedBuilder ssrhand = new EmbedBuilder().setTitle("Statecraft Cards");
		for (LeaderCard c : USSR) {
			ssrhand.addField(c.name, getEffect(c.type), false);
		}
		GameData.txtusa.sendMessage(ssrhand.build());
	}
	public static String getEffect(int i) {
		if (i==1) return "Adds 1 point in favor of your side to the die (subtract for the USSR, add for the US).";
		if (i==2) return "Adds 2 points in favor of your side to the die (subtract for the USSR, add for the US).";
		if (i==-1) return "Cancels the effect of your opponent's card.";
		if (i==0) return "This is a dummy card with no effect. *Can be played multiple times.*";
		return null;
	}
	public static String getEffectEmoji(int i) {
		if (i==-1) return ":regional_indicator_x:";
		return CardEmbedBuilder.intToEmoji(i);
	}
}

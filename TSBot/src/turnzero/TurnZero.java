package turnzero;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import cards.CardList;
import cards.HandManager;
import events.CardEmbedBuilder;
import events.Decision;
import game.GameData;
import game.PlayerList;
import main.Launcher;
import map.MapManager;
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
				GameData.txtchnl.sendMessage(new EmbedBuilder().setTitle("Yalta and Potsdam").setAuthor("Turn Zero Crisis").setColor(Color.blue).setImage(Launcher.url("tz/yalta.png")).build()).complete();
				break;
			case 1:
				GameData.txtchnl.sendMessage(new EmbedBuilder().setTitle("VE Day").setAuthor("Turn Zero Crisis").setColor(Color.red).setImage(Launcher.url("tz/ve.png")).build()).complete();
				break;
			case 2:
				GameData.txtchnl.sendMessage(new EmbedBuilder().setTitle("1945 UK Election").setAuthor("Turn Zero Crisis").setColor(Color.blue).setImage(Launcher.url("tz/election.png")).build()).complete();
				break;
			case 3:
				GameData.txtchnl.sendMessage(new EmbedBuilder().setTitle("Israel").setAuthor("Turn Zero Crisis").setColor(Color.red).setImage(Launcher.url("tz/israel.png")).build()).complete();
				break;
			case 4:
				GameData.txtchnl.sendMessage(new EmbedBuilder().setTitle("Chinese Civil War").setAuthor("Turn Zero Crisis").setColor(Color.blue).setImage(Launcher.url("tz/ccw.png")).build()).complete();
				break;
			case 5:
				GameData.txtchnl.sendMessage(new EmbedBuilder().setTitle("VJ Day").setAuthor("Turn Zero Crisis").setColor(Color.red).setImage(Launcher.url("tz/vj.png")).build()).complete();
				break;
			default:
				break;
		}
		GameData.txtssr.sendMessage(PlayerList.getSSR().getAsMention()+ ", please play a statecraft card.").complete();
		GameData.txtusa.sendMessage(PlayerList.getUSA().getAsMention()+ ", please play a statecraft card.").complete();
		sendHands();
		return true;
	}
	/**
	 * Ends a started crisis by rolling a die.
	 */
	public static void endCrisis() {
		int die = (int) (Math.random()*6) + 1;
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setAuthor("Turn 0 Crisis Resolution").addField(":flag_us:", getEffectEmoji(played[0].type), true).addField(":game_die:",CardEmbedBuilder.intToEmoji(die),true).addField(MapManager.get(85).toString(), getEffectEmoji(played[1].type), true);
		if (!(played[0].type==-1||played[1].type==-1)) { //if neither player uses the nullify card
			die += (played[0].type-played[1].type);
		}
		results[crisis] = die;
		switch(crisis) {
		case 0:
			if (die<=1) {
				builder
					.setTitle("FDR at Potsdam")
					.setDescription("Soviet appeasement policy proves ineffective")
					.setColor(Color.RED)
					.addField("European Communism","USSR may add 1 Influence anywhere in Europe during setup.",false)
					.addField("Asian Antagonism", "USSR may add one of Vietnam Revolts or Arab-Israeli War to hand before dealing. Please select that card now.", false)
					.addField("TS.decide *[card]*","[card]: One of `9`, `13`, or `0`, respectively Vietnam Revolts, Arab-Israeli War, or neither.", false)
					.setFooter("\"I think that if I give him everything I possibly can and ask for nothing from him in return, 'noblesse oblige', he won't try to annex anything and will work with me for a world of democracy and peace.\"\n"
							+ "- Franklin Delano Roosevelt, 1945", Launcher.url("tz/fdr.png"));
				GameData.dec = new Decision(1,1001);
				HandManager.addEffect(100101);
			}
			else if (die <= 3) {
				builder.setTitle("FDR dies").setDescription("Truman to attend Potsdam Conference").addField("Historical Result", "Nothing happens.", false).setColor(Color.GRAY);
			}
			else if (die <= 5) {
				builder.setTitle("Truman at Yalta").setDescription("Defensive measures taken to ensure European freedom").setColor(Color.blue)
					.addField("Recovery Plan","USA may add Marshall Plan to hand before dealing. Please decide now.",false)
					.addField("TS.decide *[card]*","[card]: One of `23` or `0`, respectively Marshall Plan and nothing.", false); 
				GameData.dec = new Decision(0,1001);
			}
			else {
				builder.setTitle("Truman at Yalta").setDescription("Hard-line policy towards Soviet aggression formulated").setColor(Color.blue)
				.addField("Recovery Plan","USA may add Marshall Plan to hand before dealing. Please decide now.",false)
				.addField("TS.decide *[card]*","[card]: One of `23` or `0`, respectively Marshall Plan and nothing.", false)
				.addField("Containment","USA will go first in all turns during the Early War",false)
				.setFooter("\"[D]espite the contrast between his relatively modest background and the international glamour of his aristocratic predecessor, [Truman] had the courage and resolution to reverse the policy that appeared to him naive and dangerous...\"\n"
						+ "- George Lenczowski, 1945", Launcher.url("tz/pl.png"));
				HandManager.addEffect(1001);
				GameData.dec = new Decision(0,1001);
			}
			break;
		case 1:
			if (die<=1) {
				builder
					.setTitle("Soviets Cross the Elbe River")
					.setDescription("Expected to push for communist government over all of Germany")
					.setColor(Color.RED);
				builder.changeInfluence(19, 1, 1).changeInfluence(0, 1, 1)
					.addField("Communist Regimes","USSR receives two additional Eastern European influence during setup.",false)
					.setFooter("\"Tsar Alexander reached Paris.\"\n"
							+ "- Joseph Stalin, 1945", Launcher.url("people/stalin.png"));
				HandManager.addEffect(100201);
			}
			else if (die <= 3) {
				builder.setTitle("Eastern Europe overrun").setDescription("Soviets tighten control over occupied territory").setColor(Color.RED)
					.addField("Communist Regimes","USSR receives two additional Eastern European influence during setup.",false);
				HandManager.addEffect(100201);
			}
			else if (die <= 5) {
				builder.setTitle("Berlin falls").setDescription("Soviet flag flying over Reichstag building")
				.addField("Historical Result", "Nothing happens.", false).setColor(Color.GRAY);
					
			}
			else {
				builder.setTitle("Just Far Enough").setDescription("American troops enter Berlin in triumph").setColor(Color.BLUE);
				builder.changeInfluence(6, 0, 2).changeInfluence(6, 1, -1) //dd 2/2
				.addField("Allied Berlin","The rules for Europe Scoring have changed. " + CardList.getCard(10) + " may no longer be played for the event.",false)
				.setFooter("\"\"\n"
						+ "- ", Launcher.url("tz/unk.png"));
				HandManager.addEffect(1002);
			}
			break;
			
		case 2:
			if (die<=1) {
				builder
					.setTitle("Landslide Labour Victory in UK Elections")
					.setDescription("Lack of Tory Opposition turns government left")
					.setColor(Color.RED)
					.setFooter("\"You will be judged by what you succeed at gentlemen, not by what you attempt.\"\n"
							+ "- Clement Attlee, 1945", Launcher.url("tz/attlee.png"));
				builder.changeInfluence(18, 0, -2);
			}
			else if (die <= 3) {
				builder.setTitle("Labour Victory in UK Elections").setDescription("Attlee to replace Churchill as PM").addField("Historical Result", "Nothing happens.", false).setColor(Color.GRAY);
			}
			else if (die <= 5) {
				builder.setTitle("Inconclusive UK Elections").setDescription("Tory and Labour expected to form coalition government").setColor(Color.blue)
					.addField("Coalition",CardList.getCard(7) + " may not be played on Turn 1 or 2 for the event.",false);
				HandManager.addEffect(1004);
			}
			else {
				builder.setTitle("Churchill Re-elected").setDescription("Tories win landslide victory").setColor(Color.blue)
				.addField("Conservative Popularity",CardList.getCard(7)+" will appear as a Mid-War card."+CardList.getCard(28)+" can no longer be played for the event.", false)
				.setFooter("\"The inherent vice of capitalism is the unequal sharing of blessings. The inherent virtue of Socialism is the equal sharing of miseries.\"\n"
						+ "- Winston Churchill, 1945", Launcher.url("people/churchill.png"));
				HandManager.addEffect(1005);
			}
			break;
			
		case 3:
			if (die<=1) {
				builder
					.setTitle("???")
					.setDescription("Unstable Coalition threatens to collapse")
					.setColor(Color.RED)
					.addField("????","USSR receives two additional Middle Eastern influence during setup (no more than 1 per country).",false)
					.setFooter("\"???\"\n"
							+ "- Joseph Stalin, 1945", Launcher.url("people/stalin.png"));
				HandManager.addEffect(100401);
			}
			else if (die <= 3) {
				builder.setTitle("Soviets fund anti-Israel militants").setDescription("").setColor(Color.RED);
				builder.changeInfluence(30, 1, 1);
			}
			else if (die <= 5) {
				builder.setTitle("Tensions between Arabs and Israelis").setDescription("Notably distracted superpowers")
				.addField("Historical Result", "Nothing happens.", false).setColor(Color.GRAY);
					
			}
			else {
				builder.setTitle("Palestine Question Experiences Sudden De-Escalation").setDescription("Irgun and Palestinian Forces Demobilize").setColor(Color.BLUE);
				builder.changeInfluence(25, 0, 1).changeInfluence(26, 0, 1).changeInfluence(27, 0, 1) //dd 2/2
				.addField("Allied Berlin","The rules for Europe Scoring have changed. " + CardList.getCard(10) + " may no longer be played for the event.",false)
				.setFooter("\"In Israel, in order to be a realist you must believe in miracles.\"\n"
						+ "- David Ben-Gurion, 1956", Launcher.url("tz/bengurion.png"));
				HandManager.addEffect(1002);
			}
			break;
		
		case 4:
			if (die<=1) {
				builder
					.setTitle("Chinese Civil War ends in Communist \"Victory\"")
					.setDescription("New Government on good terms with Soviets")
					.setColor(Color.RED)
					.setFooter("\"There are a lot of things we can learn from the Soviet Union.\"\n"
							+ "- Mao Zedong, 1956", Launcher.url("people/mao.png"))
					.addField("Border Treaty",CardList.getCard(76) + " has been removed from the game.",false);
				HandManager.addEffect(100501);
			}
			else if (die <= 3) {
				builder.setTitle("Huaihai Campaign").setDescription("Communists kick Nationalists off mainland").addField("Historical Result", "Nothing happens.", false).setColor(Color.GRAY);
			}
			else if (die <= 5) {
				builder.setTitle("").setDescription("Nationalists holding back Chinese").setColor(Color.blue)
					.addField("Winter Offensive",CardList.getCard(7) + " may not be played on Turn 1 or 2 for the event.",false);
				HandManager.China+=2;
			}
			else {
				builder.setTitle("Communists Held Back in China").setDescription("Nationalists retain mainland stronghold with US aid").setColor(Color.blue)
				.addField("Conservative Popularity",CardList.getCard(7)+" will appear as a Mid-War card."+CardList.getCard(28)+" can no longer be played for the event.", false)
				.setFooter("\"We must use every inch of our blood to take back every inch of our land, you ten thousand youths and soldiers.\"\n"
						+ "- Chiang Kai-Shek, ???", Launcher.url("people/chiang.png"));
				HandManager.addEffect(1005);
			}
			break;
			
		case 5:
			
			break;
		
		default:
			//bruh what
			break;
		}
		
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}
	
	public static boolean playLeaderCard(int sp, String s) {
		if (sp==0) {
			for (LeaderCard c : USA) {
				if (s.toLowerCase().equals(c.name.toLowerCase())) {
					played[0]=c;
					if (c.type!=0) USA.remove(USA.indexOf(c));
					return true;
				}
			}
			return false;
		}
		if (sp==1) {
			for (LeaderCard c : USSR) {
				if (s.toLowerCase().equals(c.name.toLowerCase())) {
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
		GameData.txtusa.sendMessage(usahand.build()).complete();
		EmbedBuilder ssrhand = new EmbedBuilder().setTitle("Statecraft Cards");
		for (LeaderCard c : USSR) {
			ssrhand.addField(c.name, getEffect(c.type), false);
		}
		GameData.txtusa.sendMessage(ssrhand.build()).complete();
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

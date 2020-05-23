package commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cards.HandManager;
import events.CardEmbedBuilder;
import game.GameData;
import game.PlayerList;
import main.Common;
import map.MapManager;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import powerstruggle.PowerStruggle;
import powerstruggle.Scoring;
import powerstruggle.StruggleCard;

public class StruggleCommand extends Command {
	public static ArrayList<Integer> doable;
	public static ArrayList<Integer> order;
	public static ArrayList<Integer> values;

	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		if (GameData.hasGameEnded()) {
			sendMessage(e, ":x: Have you tried turning it off and on again?");
			return;
		}
		if (!GameData.hasGameStarted()) {
			sendMessage(e, ":hourglass: There's a time and place for everything, but not now.");
			return;
		}
		if (!PlayerList.getArray().contains(e.getAuthor())) {
			sendMessage(e, ":x: Excuse me, but who are *you* playing as? There's no third way here.");
			return;
		}
		if (e.getChannel().equals(GameData.txtchnl)) {
			sendMessage(e, ":x: Don't. You're compromising your play.");
			return;
		}
		if (GameData.ps==null) {
			sendMessage(e, ":x: Where's your scoring card?");
			return;
		}
		if (GameData.dec!=null) {
			sendMessage(e, ":x: I feel decisions are more important.");
			return;
		}
		if (args.length<2) {
			sendMessage(e, ":x: What for?");
			return;
		}
		int sp = PlayerList.getQueried(e.getAuthor());
		if (args[1].charAt(0)=='c') {
			if (GameData.ps.progression!=2) {
				sendMessage(e, ":x: This isn't the time.");
				return;
			}
			if (sp==GameData.ps.initiative) {
				sendMessage(e, ":x: So long as you have initiative, there is no giving up this fight.");
				return;
			}
			if (GameData.ps.tactic==-1) {
				sendMessage(e, ":x: Not your turn yet.");
				return;
			}
			GameData.ps.concede();
		}
		if (args[1].charAt(0)=='d') {
			if (GameData.ps.progression>=2) {
				sendMessage(e, ":x: This isn't the time.");
				return;
			}
			if (sp==GameData.ps.initiative^GameData.ps.progression==0) {
				sendMessage(e, ":x: It's not your turn to do this yet.");
				return;
			}
			GameData.ps.progression++;
			if (GameData.ps.progression==1) Common.spChannel((GameData.ps.initiative+1)%2).sendMessage(Common.spRole((GameData.ps.initiative+1)%2).getAsMention()+", you may choose to raise the stakes.").complete();
			else Common.spChannel(GameData.ps.initiative).sendMessage(Common.spRole(GameData.ps.initiative).getAsMention()+", play your card.").complete();
		}
		else if (args.length<3) {
			sendMessage(e, ":x: How?");
			return;
		}
		/*
		 * An acceptable time for each:
		 * r/d - progression = 0/1, you have three cards to throw away for r
		 * p - progression = 2, it's your turn (tactics = -1 for initiative, tactics = some other number for no initiative)
		 * c - progression = 2, you are defending, it's your turn
		 * s - progression = 4, you are the commie, you hold power
		 * l - progression = 3, you are the one losing support
		 */
		if (args[1].charAt(0)=='r') {
			if (GameData.ps.progression>=2) {
				sendMessage(e, ":x: This isn't the time.");
				return;
			}
			if (sp==GameData.ps.initiative^GameData.ps.progression==0) {
				sendMessage(e, ":x: It's not your turn to do this yet.");
				return;
			}
			if (args.length < 5) {
				sendMessage(e, ":x: What cards will you throw away?");
				return;
			}
			ArrayList<StruggleCard> list = new ArrayList<StruggleCard>();
			for (int i=0; i<3; i++) {
				String card = args[2+i];
				if (card.length()!=2) {
					sendMessage(e, ":x: Not a card ID.");
					return;
				}
				char c = card.charAt(0);
				int r = card.charAt(1)-'0';
				ArrayList<Character> suited = new ArrayList<Character>(Arrays.asList('r','s','m','p'));
				int rank, suit, type;
				if (suited.contains(c)) {
					type = 0;
					suit = suited.indexOf(c);
					rank = r;
				}
				else if (c=='l') {
					type = 1;
					suit = r;
					rank = 3;
				}
				else if (c=='w') {
					type = 2;
					suit = r;
					rank = 0;
				}
				else {
					sendMessage(e, ":x: Not a card ID.");
					return;
				}
				
				list.add(new StruggleCard(type, suit, rank));
				if ((sp==0&&Collections.frequency(GameData.ps.DemHand, list.get(i))<Collections.frequency(list, list.get(i)))||sp==1&&Collections.frequency(GameData.ps.ComHand, list.get(i))<Collections.frequency(list, list.get(i))) {
					sendMessage(e, ":x: You do not have this.");
					return;
				}
			}
			GameData.ps.raiseStakes(list, sp);
			//TODO later
		}
		if (args[1].charAt(0)=='s') {
			if (GameData.ps.progression!=4) {
				sendMessage(e, ":x: This isn't the time.");
				return;
			}
			if (sp!=1) {
				sendMessage(e, ":x: The Democrat's goal is to gain power in these countries. You cannot step down.");
				return;
			}
			if (PowerStruggle.retained[GameData.ps.region]==-1) {
				sendMessage(e, ":x: You've already lost.");
				return;
			}
			int x;
			try{x = Integer.parseInt(args[2]);}catch(NumberFormatException err) {sendMessage(e,":x: NaN");return;}
			CardEmbedBuilder builder = new CardEmbedBuilder();
			builder.setTitle("Power Struggle Aftermath").setColor(Common.spColor(Common.opp(x)));
			if (x==0) {
				HandManager.Discard.add(HandManager.activecard);
				PowerStruggle.retained[GameData.ps.region]++;
				builder.changeVP(-1*PowerStruggle.power[GameData.ps.region]*PowerStruggle.retained[GameData.ps.region]);
			}
			else if (x==1) {
				builder.setDescription("Communist cedes power to Democrat.");
				HandManager.Removed.add(HandManager.activecard);
				PowerStruggle.retained[GameData.ps.region]=-1;
			}
			else {
				sendMessage(e, ":x: 0 or 1.");
				return;
			}
			GameData.txtchnl.sendMessage(builder.build()).complete();
			Scoring.score(GameData.ps.region);
			TimeCommand.eventDone = true;
			TimeCommand.prompt();
		}
		if (args[1].charAt(0)=='l') {
			if (GameData.ps.progression!=3) {
				sendMessage(e, ":x: This isn't the time.");
				return;
			}
			if (sp!=Common.opp(GameData.ps.victor)) {
				sendMessage(e, ":x: No.");
				return;
			}
			//copied and modified from VoA
			doable = new ArrayList<Integer>();
			order = new ArrayList<Integer>();
			values = new ArrayList<Integer>();
			int maxInfRem = 0;
			for (int i=Common.bracket[GameData.ps.region]; i<Common.bracket[GameData.ps.region+1]; i++) {
				if (MapManager.get(i).support[1]>0) {
					doable.add(i);
					maxInfRem += MapManager.get(i).support[0];
				}
			}
			if (maxInfRem<=GameData.ps.supportloss) {
				order = doable;
				for (int i : order) {
					values.add(-MapManager.get(i).support[0]);
				}
			}
			else {
				if (args.length%2!=0) return; //each country must associate with a number
				for (int i=2; i<args.length; i+=2) {
					order.add(MapManager.find(args[i]));
					try{
						values.add(Integer.parseInt(args[i+1]));
					}
					catch (NumberFormatException err){
						return; //this isn't an integer. xP
					}
				}
				int sum = 0;
				if (!doable.containsAll(order)) return;
				for (int i=0; i<order.size(); i++) {
					if (values.get(i)>=0) return; //no non-negative numbers please
					if (MapManager.get(order.get(i)).support[1]+values.get(i)<0) return; //don't give me negative influence values
					sum += values.get(i);
				}
				if (sum!=-GameData.ps.supportloss) return; // up to supportloss influence may be removed...
			}
			GameData.ps.actuallyEndStruggle();
		}
		if (args[1].charAt(0)=='p') {
			if (GameData.ps.progression!=2) {
				sendMessage(e, ":x: This isn't the time.");
				return;
			}
			if (sp==GameData.ps.initiative^GameData.ps.tactic==-1) {
				sendMessage(e, ":x: Not your turn yet.");
				return;
			}
			if (!GameData.ps.play(sp, args[2], args[3])) {
				sendMessage(e, ":x: Second argument must be a valid card. If it's a leader, third argument must be a suit; if it's Scare Tactics, third argument must be a space.");
				return;
			}
		}
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("DF.powerstruggle","DF.struggle","DF.ps");
	}

	@Override
	public String getDescription() {
		return "Anything related to the Power Struggle.";
	}

	@Override
	public String getName() {
		return "Power Struggle (powerstruggle, struggle, ps)";
	}

	@Override
	public List<String> getUsageInstructions() {
		return Arrays.asList("DF.struggle <usage> <parameters>\n"
				+ "<usage>:\n"
				+ "- [r]aise/[d]ecline, respectively to raise the stakes or decline to do so. May only be done at the start of the struggle. Indicate three cards to discard from your hand.\n"
				+ "- [p]lay, to play a card. Indicate said card.\n"
				+ "- [c]oncede, to concede the struggle. May only be done as the defender. No parameters required.\n"
				+ "- [s]tepdown, to decide whether to voluntarily cede power as the communist. May only be done if you have not been forced from office by the Point roll. 0 to retain power, 1 to cede power. \n"
				+ "- [l]osesupport, to delete your influence after you have lost.");
	}

}

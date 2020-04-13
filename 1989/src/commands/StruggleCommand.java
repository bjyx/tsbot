package commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cards.CardList;
import cards.HandManager;
import events.CardEmbedBuilder;
import game.Die;
import game.GameData;
import game.PlayerList;
import main.Common;
import map.MapManager;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import powerstruggle.PowerStruggle;

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
		if (e.getChannel().equals(GameData.txtchnl)) {
			sendMessage(e, ":x: Don't. You're compromising your play.");
			return;
		}
		if (GameData.ps==null) {
			sendMessage(e, ":x: Where's your scoring card?");
			return;
		}
		if (args.length<2) {
			sendMessage(e, ":x: What for?");
			return;
		}
		int sp = PlayerList.getQueried(e.getAuthor());
		if (args[1].charAt(0)=='c') {
			if (sp==GameData.ps.initiative) {
				sendMessage(e, ":x: So long as you have initiative, there is no giving up this fight.");
				return;
			}
			GameData.ps.concede();
		}
		if (args[1].charAt(0)=='d') {
			//TODO later
		}
		else if (args.length<3) {
			sendMessage(e, ":x: How?");
			return;
		}
		if (args[1].charAt(0)=='r') {
			//TODO later
		}
		if (args[1].charAt(0)=='s') {
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
		}
		if (args[1].charAt(0)=='l') {
			doable = new ArrayList<Integer>();
			order = new ArrayList<Integer>();
			values = new ArrayList<Integer>();
			int maxInfRem = 0;
			for (int i=0; i<75; i++) {
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
			//TODO later
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
				+ "- [r]aise/[d]ecline, respectively to raise the stakes or decline to. May only be done at the start of the struggle. Indicate three cards to discard from your hand.\n"
				+ "- [p]lay, to play a card. Indicate said card.\n"
				+ "- [c]oncede, to concede the struggle. May only be done as the defender. No parameters required.\n"
				+ "- [s]tepdown, to decide whether to voluntarily cede power as the communist. May only be done if you have not been forced from office by the Point roll. 0 to retain power, 1 to cede power. \n"
				+ "- [l]osesupport, to delete your influence after you have lost.");
	}

}

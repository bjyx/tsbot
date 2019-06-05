package commands;

import java.util.Arrays;
import java.util.List;

import cards.HandManager;
import game.GameData;
import game.PlayerList;
import map.MapManager;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class OperationsCommand extends Command {

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
			sendMessage(e, ":x: Excuse me, but who are *you* playing as? China's abstracted as a card and the rest of the world has a board space each.");
			return;
		}
		if (HandManager.activecard==0) {
			sendMessage(e, ":x: What's the play?");
			return;
		}
		if (HandManager.playmode!='o') {
			sendMessage(e, ":x: Trying to change your mind already?");
			return;
		}
		String usage = args[1];
		boolean result = false;
		if (usage.equals("influence")||usage.equals("i")) {
			if (args.length<2) {
				sendMessage(e, ":x: To where?");
				return;
			}
			if (args.length%2!=0) {
				sendMessage(e, ":x: An influence value must be associated with every listed country.");
				return;
			}
			int[] countries = new int[(args.length-2)/2];
			int[] amt = new int[(args.length-2)/2];
			for (int i=2; i<args.length; i+=2) {
				countries[(i-2)/2] = MapManager.find(args[i]);
				if (countries[(i-2)/2]==-1) {
					sendMessage(e, ":x: "+args[i]+" isn't a country or alias of one.");
					return;
				}
				if (MapManager.get(countries[(i-2)/2]).region==9) {
					sendMessage(e, ":x: No targeting your opponent. Cheater.");
					return;
				}
				amt[(i-2)/2] = Integer.parseInt(args[i+1]);
				if (amt[(i-2)/2]<=0) {
					sendMessage(e, ":x: Positive integers only, please - this is not De-Stalinization.");
					return;
				}
			}
			result = GameData.ops.influence(countries, amt);
		}
		if (usage.equals("realignment")||usage.equals("r")) {
			int country = MapManager.find(args[2]);
			if (country==-1) {
				sendMessage(e, ":x: "+args[2]+" isn't a country or alias of one.");
				return;
			}
			if (MapManager.get(country).region==9) {
				sendMessage(e, ":x: No targeting your opponent. Cheater.");
				return;
			}
			result = GameData.ops.realignment(country);
		}
		if (usage.equals("coup")||usage.equals("c")) {
			int country = MapManager.find(args[2]);
			if (country==-1) {
				sendMessage(e, ":x: "+args[2]+" isn't a country or alias of one.");
				return;
			}
			if (MapManager.get(country).region==9) {
				sendMessage(e, ":x: No targeting your opponent. Cheater.");
				return;
			}
			result = GameData.ops.coup(country);
		}
		if (!result) {
			return;
		}
		TimeCommand.operationsDone = true;
		if (HandManager.playmode == 'l') TimeCommand.eventRequired = true;
	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return Arrays.asList("TS.ops","TS.operations");
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Play the card on exactly one of three types of operations: Place Influence, Realign, or Coup.";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Operations Play (operations, ops)";
	}

	@Override
	public List<String> getUsageInstructions() {
		// TODO Auto-generated method stub
		return Arrays.asList("TS.operations *<operation type>* *<other arguments>*\n"
				+ "**Operation Types**\n"
				+ "- *influence*: arguments alternate between countries and influence values.\n"
				+ "        - __Example:__ TS.operations influence egypt 1 lb 1 syr 1 urdun 1\n"
				+ "        - _This will place one Influence in Egypt, one in Lebanon, one in Syria, and one in Jordan._"
				+ "- *realignment* argument will consist of one country.\n"
				+ "        - __Example:__ TS.operations realignment cuba\n"
				+ "        - _This will attempt a realignment on Cuba. Realignments can be repeated as many times as you have Operations._"
				+ "- *coup*: argument will consist of one country.\n"
				+ "        - __Example:__ TS.operations coup irn\n"
				+ "        - _This will attempt a coup on Iran. Coups consume all of the Operations on a card and can therefore be performed once per Action Round._");
	}

}

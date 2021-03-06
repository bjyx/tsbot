package commands;

import java.util.Arrays;
import java.util.List;

import cards.HandManager;
import game.GameData;
import game.PlayerList;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
/**
 * The command handling cards played for the Operations. 
 * @author adalbert
 *
 */
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
		if (e.getChannel().equals(GameData.txtchnl)) {
			sendMessage(e, ":x: Don't. You're compromising your play.");
			return;
		}
		if (HandManager.activecard==0) {
			sendMessage(e, ":x: What's the play?");
			return;
		}
		if (!e.getAuthor().equals(PlayerList.getArray().get(GameData.phasing()))) {
			sendMessage(e, ":x: You don't get these ops. Your opponent does.");
			return;
		}
		if (!TimeCommand.operationsRequired) {
			sendMessage(e, ":x: Trying to change your mind already?");
			return;
		}
		if (args.length<3) {
			sendMessage(e, ":x: How might you use these, might I ask?");
			return;
		}
		boolean result = GameData.ops.ops(args);
		
		if (!result) {
			return;
		}
		GameData.checkScore(false, false);
		GameData.ops = null;
		TimeCommand.operationsDone = true;
		if (HandManager.playmode == 'l') TimeCommand.eventRequired = true;
		TimeCommand.prompt();
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("TS.ops","TS.operations","TS.o");
	}

	@Override
	public String getDescription() {
		return "Play the card on exactly one of three types of operations: Place Influence, Realign, or Coup.";
	}

	@Override
	public String getName() {
		return "Operations Play (operations, ops, o)";
	}

	@Override
	public List<String> getUsageInstructions() {
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

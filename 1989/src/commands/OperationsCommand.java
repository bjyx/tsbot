package commands;

import java.util.Arrays;
import java.util.List;

import cards.HandManager;
import game.GameData;
import game.PlayerList;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
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
		// TODO Auto-generated method stub
		return Arrays.asList("DF.ops","DF.operations","DF.o");
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Play the card on exactly one of three types of operations: Place Influence, Realign, or Coup.";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Operations Play (operations, ops, o)";
	}

	@Override
	public List<String> getUsageInstructions() {
		// TODO Auto-generated method stub
		return Arrays.asList("DF.operations *<operation type>* *<other arguments>*\n"
				+ "**Operation Types**\n" //note: this is a carryover from TS
				+ "- *support*: arguments alternate between countries and values.\n"
				+ "        - __Example:__ DF.operations influence berlin 1 ddw 1 chemnitz 1\n"
				+ "        - _This will place one Support Point in Berlin, one in the German Writers space, and one in Karl-Marx-Stadt (now Chemnitz, which is a valid alias because `karlmarxstadt` is a pain to type)._"
				+ "- *check*: argument will consist of one country.\n"
				+ "        - __Example:__ DF.operations check halle\n"
				+ "        - _This will attempt a support check on Halle. Support checks may only occur twice per use of operations unless otherwise specified._");
	}

}

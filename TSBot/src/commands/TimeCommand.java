package commands;

import java.util.Arrays;
import java.util.List;

import cards.HandManager;
import game.GameData;
import game.PlayerList;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class TimeCommand extends Command {
	/**
	 * An indicator for whether the text of the event on the card in question has occurred
	 */
	public static boolean cardPlayed = true;
	public static boolean hl1 = true;
	public static boolean hl2 = true;
	public static boolean eventDone = false;
	public static boolean operationsDone = false;
	public static boolean eventRequired = false;
	public static boolean operationsRequired = false;
	public static boolean spaceRequired = false;
	public static boolean spaceDone = false;
	public static boolean decisionRequired = false;
	public static boolean decisionDone = false;
	public static boolean trapDone = true;
	public static boolean NORAD = true;
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
		if (!cardPlayed) {
			sendMessage(e, ":x: You are required to play a card every turn.");
			return;
		}
		if (!hl1||!hl2) {
			sendMessage(e, ":x: There remains an unresolved headline.");
			return;
		}
		if (!trapDone) {
			sendMessage(e, ":x: You must discard a card to Quagmire/Bear Trap.");
			return;
		}
		if (eventRequired&&!eventDone) {
			sendMessage(e, ":x: You played a card for the event; you must finish it.");
			return;
		}
		if (decisionRequired&&!decisionDone) {
			sendMessage(e, ":x: There is still an unresolved event. TS.decision exists.");
			return;
		}
		if (operationsRequired&&!operationsDone) {
			sendMessage(e, ":x: You played a card for the operations; you must finish it.");
			return;
		}
		if (spaceRequired&&!spaceDone) {
			sendMessage(e, ":x: Wasn't there a card earmarked for that space program?");
			return;
		}
		if (!NORAD) {
			sendMessage(e, ":flag_ca: You have Canada as an ally for a reason, you know.");
			return;
		}
		GameData.advanceTime();
		if (GameData.isHeadlinePhase()) hl1 = (hl2 = false);
		// TODO return here later after done with Quagmire and BearTrap classes
		// if (HandManager.Effects.contains(42) && !GameData.isHeadlinePhase() && (GameData.phasing()==0)) trapDone = false;
		// else if (HandManager.Effects.contains(44) && !GameData.isHeadlinePhase() && (GameData.phasing()==1)) trapDone = false;
		
		else if ((GameData.phasing()==1 && !HandManager.SUNHand.isEmpty()) || (GameData.phasing()==0 && !HandManager.USAHand.isEmpty())) cardPlayed = false;
	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return Arrays.asList("TS.time", "TS.+", "TS.done");
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Advance the turn after you've done everything you need to do - this action will fail if something else needs to be done to finish the turn.";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Advance time (time, +, done)";
	}

	@Override
	public List<String> getUsageInstructions() {
		// TODO Auto-generated method stub
		return Arrays.asList("TS.time");
	}

}

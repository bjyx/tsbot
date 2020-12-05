package commands;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import events.CardEmbedBuilder;
import events.Decision;
import game.GameData;
import game.PlayerList;
import main.Common;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
/**
 * The command handling the flow of time.
 * @author adalbert
 *
 */
public class TimeCommand extends Command {
	/**
	 * An indicator for whether the text of the event on the card in question has occurred
	 */
	public static boolean cardPlayed = true;
	public static boolean cardPlayedSkippable = true;
	public static boolean usHolds = false; //used for determining end of turn
	public static boolean eventDone = false;
	public static boolean operationsDone = false;
	public static boolean eventRequired = false;
	public static boolean operationsRequired = false;
	
	//TODO more as needed
	
	public static boolean canAdvance() {
		return cardPlayed&&cardPlayedSkippable&&!(eventRequired^eventDone)&&!(operationsDone^operationsRequired);
	}
	
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
		if (!SetupCommand.setup) {
			sendMessage(e, ":x: This is not something to be doing right now.");
			return;
		}
		if (!cardPlayed) {
			sendMessage(e, ":x: You are required to play a card.");
			return;
		}
		if (!cardPlayedSkippable) {
			sendMessage(e, ":x: Declare your intent to skip. Alternatively, play a card, or discard it.");
			return;
		}
		if (eventRequired&&!eventDone) {
			sendMessage(e, ":x: You played a card for the event; you must finish it.");
			return;
		}
		if (operationsRequired&&!operationsDone) {
			sendMessage(e, ":x: You played a card for the operations; you must finish it.");
			return;
		}
		
		GameData.checkVictory(false);
		
		GameData.advanceTime(); //everything else
		HandManager.activecard = 0;
		if ((GameData.phasing()==1 && !HandManager.JihHand.isEmpty()) || (GameData.phasing()==0 && HandManager.USAHand.size()>1)) { //phasing player must play a card if he isn't in a skippable action round
			cardPlayed = false;
		}
		else { //if hand is empty or if US has one card left
			cardPlayedSkippable = false;
		}
		eventDone = false;
		operationsDone = false;
		eventRequired = false;
		operationsRequired = false;
		usHolds = false; 
		TimeCommand.prompt();
	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return Arrays.asList("LB.time", "LB.+", "LB.done");
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
		return Arrays.asList("LB.time");
	}

	public static void prompt() {
		if (!cardPlayed) {
			Common.spChannel(GameData.phasing()).sendMessage(Common.spRole(GameData.phasing()).getAsMention() + ", please play a card. (LB.play [card] [use])").complete();
		}
		else if (!cardPlayedSkippable) {
			Common.spChannel(GameData.phasing()).sendMessage(Common.spRole(GameData.phasing()).getAsMention() + ", please play a card. Set card to 0 to skip, or -1 to discard your last card (if any). These are equivalent if you do not have a card. (LB.play [card] [use])").complete();
		}
		else if (eventRequired&&!eventDone) {
			if (CardList.getCard(HandManager.activecard).getAssociation()!=2) Common.spChannel(CardList.getCard(HandManager.activecard).getAssociation()).sendMessage(Common.spRole(CardList.getCard(HandManager.activecard).getAssociation()).getAsMention() + ", please play your event. (LB.event [args])").complete();
			else Common.spChannel(GameData.phasing()).sendMessage(Common.spRole(GameData.phasing()).getAsMention() + ", please play your event. (LB.event [args])").complete();
		}
		else if (operationsRequired&&!operationsDone) {
			Common.spChannel(GameData.phasing()).sendMessage(Common.spRole(GameData.phasing()).getAsMention() + ", please play your operations. (LB.ops [args])").complete();
		}
		else if (canAdvance()) {
			Common.spChannel(GameData.phasing()).sendMessage(Common.spRole(GameData.phasing()).getAsMention() + ", please advance the time. (LB.time/TS.+)").complete();
		}
	}
	public static void reset() {
		cardPlayed = true;
		cardPlayedSkippable = true;
		eventDone = false;
		operationsDone = false;
		eventRequired = false;
		operationsRequired = false;
	}
}

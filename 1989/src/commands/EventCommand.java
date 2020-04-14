package commands;

import java.util.Arrays;
import java.util.List;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import game.GameData;
import game.PlayerList;
import logging.Log;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
/**
 * The command handling cards played for the event. 
 * @author adalbert
 *
 */
public class EventCommand extends Command {

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
		if (GameData.ps!=null) {
			sendMessage(e, ":x: No.");
			return;
		}
		if (TimeCommand.eventRequired^TimeCommand.eventDone) {
			sendMessage(e, ":x: Trying to change your mind already?");
			return;
		}
		if (HandManager.activecard==0) {
			sendMessage(e, ":x: What's the play?");
			return;
		}
		if (!e.getAuthor().equals(PlayerList.getArray().get(CardList.getCard(HandManager.activecard).getAssociation()==2?GameData.phasing():CardList.getCard(HandManager.activecard).getAssociation()))) {
			sendMessage(e, ":x: Oh, you're not the one playing this event. Your opponent is.");
			return;
		}
		if (GameData.dec != null) {
			sendMessage(e, ":x: Active Decision Warning.");
			return;
		}
		if (!CardList.getCard(HandManager.activecard).isPlayable(CardList.getCard(HandManager.activecard).getAssociation()==2?GameData.phasing():CardList.getCard(HandManager.activecard).getAssociation())) {
			HandManager.Discard.add(HandManager.activecard);
			sendMessage(e, "This event is not playable."); //only comes up during headlines, where you cannot prevent this from being played
		}
		else {
			if (!CardList.getCard(HandManager.activecard).isFormatted(PlayerList.getArray().indexOf(e.getAuthor()), args)) {
				sendMessage(e, ":x: Format your arguments correctly.");
				return;
			}
			if (CardList.getCard(HandManager.activecard).isRemoved()) {
				HandManager.Removed.add(HandManager.activecard);
			}
			else if (CardList.getCard(HandManager.activecard).getOps()!=0) { //TODO more cards?
				HandManager.Discard.add(HandManager.activecard);
			}
			Log.writeToLog(CardList.getCard(HandManager.activecard).getName()+":");
			CardList.getCard(HandManager.activecard).onEvent(PlayerList.getArray().indexOf(e.getAuthor()), args);
		}
		GameData.checkScore(false, false);
		if (GameData.dec==null) {
			TimeCommand.eventDone = true;
			if (HandManager.playmode == 'f') {
				TimeCommand.operationsRequired = true;
				GameData.ops = new Operations(GameData.phasing(), CardList.getCard(HandManager.activecard).getOpsMod(GameData.phasing()), true, true, false);
			}
			TimeCommand.prompt();
		}
	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return Arrays.asList("DF.event", "DF.e");
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Play the active card for the event.";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Event Play (event, e)";
	}

	@Override
	public List<String> getUsageInstructions() {
		return Arrays.asList("DF.event *<arguments>*\n"
				+ "- Events may require arguments. The arguments required will be specified by the \"Info\" command.");
	}

}

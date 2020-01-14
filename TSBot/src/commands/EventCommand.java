package commands;

import java.util.Arrays;
import java.util.List;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import game.GameData;
import game.PlayerList;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

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
		if (!TimeCommand.eventRequired && TimeCommand.hl1 && TimeCommand.hl2) {
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
		if (!CardList.getCard(HandManager.activecard).isPlayable(CardList.getCard(HandManager.activecard).getAssociation()==2?GameData.phasing():CardList.getCard(HandManager.activecard).getAssociation())) {
			HandManager.Discard.add(HandManager.activecard);
			sendMessage(e, "This event is not playable.");
		}
		else {
			if (!CardList.getCard(HandManager.activecard).isFormatted(PlayerList.getArray().indexOf(e.getAuthor()), args)) {
				sendMessage(e, ":x: Format your arguments correctly.");
				return;
			}
			if (CardList.getCard(HandManager.activecard).isRemoved()) {
				HandManager.Removed.add(HandManager.activecard);
			}
			else if (HandManager.activecard!=73) {
				HandManager.Discard.add(HandManager.activecard);
			}
			CardList.getCard(HandManager.activecard).onEvent(PlayerList.getArray().indexOf(e.getAuthor()), args);
		}
		GameData.checkScore(false, false);
		if (GameData.dec==null) {
			if (GameData.isHeadlinePhase()) {
				if (TimeCommand.hl1) TimeCommand.hl2 = true;
				else {TimeCommand.hl1 = true;
				if (HandManager.precedence==0&&TimeCommand.hl2==false) {
					HandManager.activecard=HandManager.headline[1];
				}
				else if (HandManager.precedence==1) {
					HandManager.activecard=HandManager.headline[0];
				}}
				TimeCommand.prompt();
				return;
			}
			TimeCommand.eventDone = true;
			if (HandManager.playmode == 'f') {
				TimeCommand.operationsRequired = true;
				GameData.ops = new Operations(GameData.phasing(), CardList.getCard(HandManager.activecard).getOpsMod(GameData.phasing()), true, true, true, false, false);
			}
			TimeCommand.prompt();
		}
	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return Arrays.asList("TS.event");
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Play the active card for the event.";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Event Play (event)";
	}

	@Override
	public List<String> getUsageInstructions() {
		return Arrays.asList("TS.event *<arguments>*\n"
				+ "- Events may require arguments. The arguments required will be specified by the \"Info\" command.");
	}

}

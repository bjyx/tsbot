package commands;

import java.util.Arrays;
import java.util.List;

import cards.HandManager;
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
		if (HandManager.playmode!='e'&&HandManager.playmode!='h') {
			sendMessage(e, ":x: Trying to change your mind already?");
			return;
		}
		if (HandManager.activecard==0) {
			sendMessage(e, ":x: What's the play?");
			return;
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
		return null;
	}

	@Override
	public List<String> getUsageInstructions() {
		return Arrays.asList(" ");
	}

}

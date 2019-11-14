package commands;

import java.util.Arrays;
import java.util.List;

import cards.HandManager;
import game.GameData;
import game.PlayerList;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class SpaceCommand extends Command {

	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		// TODO Auto-generated method stub
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
		if (!e.getAuthor().equals(PlayerList.getArray().get(GameData.phasing()))) {
			sendMessage(e, ":x: You're not the one spacing the card...");
			return;
		}
		if (HandManager.playmode!='s') {
			sendMessage(e, ":x: Trying to change your mind already?");
			return;
		}
		GameData.ops.space();
		TimeCommand.spaceDone = true;
		TimeCommand.prompt();
	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return Arrays.asList("TS.space");
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Send the card to space.";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "SPAAAAAAAAAAAACE";
	}

	@Override
	public List<String> getUsageInstructions() {
		// TODO Auto-generated method stub
		return Arrays.asList("TS.space to space a card.");
	}

}

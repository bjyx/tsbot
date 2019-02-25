package commands;

import java.util.Arrays;
import java.util.List;

import cards.HandManager;
import game.GameData;
import game.PlayerList;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class StartCommand extends Command {
	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		if (GameData.hasGameEnded()) {
			sendMessage(e, ":x: Have you tried turning it off and on again?");
			return;
		}
		if (GameData.hasGameStarted()) {
			sendMessage(e, ":hourglass: There's a time and place for everything, but not now.");
			return;
		}
		if (!PlayerList.getArray().contains(e.getAuthor())) {
			sendMessage(e, ":x: Excuse me, but who are *you* playing as? China's abstracted as a card and the rest of the world has a board space each.");
			return;
		}
		if (PlayerList.getArray().contains(null)) {
			sendMessage(e, ":x: Can't have a Cold War if one of the superpowers doesn't exist.");
			return;
		}
		sendMessage(e, ":hourglass: Seven minutes to midnight... and counting.");
		GameData.startGame();
		PlayerList.detSides();
		HandManager.addToDeck(0);
		HandManager.deal();
		sendMessage(e, PlayerList.getSSR().getAsMention() + ", please place six influence markers in Eastern Europe.");
	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return Arrays.asList("TS.start");
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Commence the Cold War.";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Start (start)";
	}

	@Override
	public List<String> getUsageInstructions() {
		// TODO Auto-generated method stub
		return Arrays.asList("TS.start - How it all begins");
	}

}

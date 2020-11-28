package commands;

import java.util.Arrays;
import java.util.List;

import cards.HandManager;
import game.GameData;
import game.PlayerList;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
/**
 * The command handling cards played on the space race. 
 * @author adalbert
 *
 */
public class SpaceCommand extends Command {

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
		GameData.checkScore(false, false);
		TimeCommand.prompt();
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("TS.space", "TS.s");
	}

	@Override
	public String getDescription() {
		return "Send the card to space.";
	}

	@Override
	public String getName() {
		return "Space Race (space, s)";
	}

	@Override
	public List<String> getUsageInstructions() {
		return Arrays.asList("TS.space to space a card.");
	}

}

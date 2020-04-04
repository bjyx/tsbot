package commands;

import java.util.Arrays;
import java.util.List;

import game.GameData;
import game.PlayerList;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ResetCommand extends Command {
	
	private static boolean[] consent = {false, false};

	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		consent[PlayerList.getArray().indexOf(e.getAuthor())]=true;
		if (!GameData.hasGameStarted()) {
			sendMessage(e, ":x: The game hasn't even started yet.");
			return;
		}
		if (!GameData.hasGameEnded()&&!(consent[0]&&consent[1])) {
			sendMessage(e, ":warning: You sure about this? Get the other person to agree.");
			return;
		}
		consent[0]=false;
		consent[1]=false;
		GameData.reset();
		
	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return Arrays.asList("TS.reset");
	}

	@Override
	public String getDescription() {
		return "Get ready for a do-over. **Both players need to agree if the game isn't over yet.**";
	}

	@Override
	public String getName() {
		return "Reset";
	}

	@Override
	public List<String> getUsageInstructions() {
		// TODO Auto-generated method stub
		return Arrays.asList("TS.reset - Resets all variables. Get ready to do this again.");
	}

}
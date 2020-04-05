package commands;

import java.util.Arrays;
import java.util.List;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import readwrite.ReadWrite;
/**
 * The "save" command. 
 * @author adalbert
 *
 */
public class WriteCommand extends Command {

	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		if (TimeCommand.canAdvance()) sendMessage(e, ReadWrite.write());
	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return Arrays.asList("TS.write","TS.save");
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Creates a string containing the details of the game currently being played.";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Save Game";
	}

	@Override
	public List<String> getUsageInstructions() {
		// TODO Auto-generated method stub
		return Arrays.asList("TS.write/save - Save a game to play later. **This may only occur at the end of an action round, when one can use `TS.+`.** I can't save the entire state of the game, people.\n"
				+ "**It is recommended that you not mess with the save string, as this may lead to unexpected results when plugged back in.**");
	}

}

package commands;

import java.util.List;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class TimeCommand extends Command {
	
	public boolean canAdvanceTime = false;
	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		if (!canAdvanceTime) {
			
		}

	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getUsageInstructions() {
		// TODO Auto-generated method stub
		return null;
	}

}

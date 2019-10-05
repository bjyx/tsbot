package commands;

import java.util.Arrays;
import java.util.List;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CodeCommand extends Command {

	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		MessageBuilder message = new MessageBuilder().append("Want to suggest better quotes or report a bug? Drop a comment here: https://github.com/bjyx/tsbot");
		sendMessage(e, message.build());
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("TS.code");
	}

	@Override
	public String getDescription() {
		return "In case you wanted to learn about the bot's inner workings.";
	}

	@Override
	public String getName() {
		return "Code Dump (code)";
	}

	@Override
	public List<String> getUsageInstructions() {
		return Arrays.asList("TS.code - Returns a link that can show you the inner workings of the bot.");
	}

}

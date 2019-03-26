package commands;

import java.util.List;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
/**
 * The command that handles any decisions to be made with regards to any card that cannot be handled by the event command list. The following cards have such an effect:
 * {@code 010 Blockade} - airlift or concede (USSR Event, USA decision)
 * {@code 020 Olympic Games} - compete or boycott (opponent chooses)
 * {@code 040 Missile Crisis} - resolve [COUNTRY] (country must be one of Cuba, West Germany, or Turkey)
 * {@code 042 Quagmire} - fuck quagmire
 * {@code 044 Bear Trap} - fuck bear trap
 * {@code 045 Summit} - decrease or increase (or not at all)
 * {@code 067 Grain Sales to Soviets} - return, event, operations, space
 * {@code 095 Latin American Debt Crisis} - discard or concede
 * {@code 098 Aldrich Ames} - The USSR gets to pick a specific card from the US's hand
 * {@code 104 Cambridge Five} - country for influence placement?
 * {@code 106 NORAD} - country for influence placement
 * {@code 108 Our Man in Tehran} - any of the five cards seen
 * 
 * @author [REDACTED]
 *
 */
public class DecisionCommand extends Command {

	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		// TODO Auto-generated method stub

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

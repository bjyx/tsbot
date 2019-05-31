package commands;

import java.util.List;

import cards.HandManager;
import game.GameData;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class TimeCommand extends Command {
	/**
	 * An indicator for whether the text of the event on the card in question has occurred
	 */
	public static boolean cardPlayed = true;
	public static boolean hl1 = true;
	public static boolean hl2 = true;
	public static boolean eventDone = false;
	public static boolean operationsDone = false;
	public static boolean eventRequired = false;
	public static boolean operationsRequired = false;
	public static boolean spaceRequired = false;
	public static boolean spaceDone = false;
	public static boolean decisionRequired = false;
	public static boolean decisionDone = false;
	public static boolean trapDone = true;
	public static boolean NORAD = true;
	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		if (!cardPlayed) return;
		if (!hl1||!hl2) return;
		if (!trapDone) return;
		if (eventRequired&&!eventDone) return;
		if (decisionRequired&&!decisionDone) return;
		if (operationsRequired&&!operationsDone) return;
		if (spaceRequired&&!spaceDone) return;
		if (!NORAD) return;
		GameData.advanceTime();
		if (GameData.isHeadlinePhase()) hl1 = (hl2 = false);
		// TODO return here later after done with Quagmire and BearTrap classes
		// if (HandManager.Effects.contains(42) && !GameData.isHeadlinePhase() && (GameData.phasing()==0)) trapDone = false;
		// else if (HandManager.Effects.contains(44) && !GameData.isHeadlinePhase() && (GameData.phasing()==1)) trapDone = false;
		
		else if ((GameData.phasing()==1 && !HandManager.SUNHand.isEmpty()) || (GameData.phasing()==0 && !HandManager.USAHand.isEmpty())) cardPlayed = false;
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

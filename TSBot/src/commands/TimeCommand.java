package commands;

import java.util.Arrays;
import java.util.List;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import events.Decision;
import game.GameData;
import game.PlayerList;
import map.MapManager;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class TimeCommand extends Command {
	/**
	 * An indicator for whether the text of the event on the card in question has occurred
	 */
	public static boolean cardPlayed = true;
	public static boolean cardPlayedSkippable = true;
	public static boolean hl1 = true;
	public static boolean hl2 = true;
	public static boolean eventDone = false;
	public static boolean operationsDone = false;
	public static boolean eventRequired = false;
	public static boolean operationsRequired = false;
	public static boolean spaceRequired = false;
	public static boolean spaceDone = false;
	public static boolean trapDone = true;
	public static boolean NORAD = true;
	
	public static boolean isCardDiscarded = true;
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
		if (!cardPlayed) {
			sendMessage(e, ":x: You are required to play a card.");
			return;
		}
		if (!cardPlayedSkippable) {
			sendMessage(e, ":x: Declare your intent to skip. Alternatively, play a card.");
			return;
		}
		if (!hl1||!hl2) {
			sendMessage(e, ":x: There remains an unresolved headline.");
			return;
		}
		if (!trapDone) {
			sendMessage(e, ":x: I hate Quagmire and Bear Trap as much as you do, but you're still supposed to discard to that.");
			return;
		}
		if (eventRequired&&!eventDone) {
			sendMessage(e, ":x: You played a card for the event; you must finish it.");
			return;
		}
		if (operationsRequired&&!operationsDone) {
			sendMessage(e, ":x: You played a card for the operations; you must finish it.");
			return;
		}
		if (spaceRequired&&!spaceDone) {
			sendMessage(e, ":x: Wasn't there a card earmarked for that space program?");
			return;
		}
		if (!NORAD) {
			sendMessage(e, ":flag_ca: You have Canada as an ally for a reason, you know. Eh.");
			return;
		}
		if (!isCardDiscarded) {
			sendMessage(e, ":x: You may choose to discard a card - make that decision first.");
			return;
		}
		GameData.checkScore(false, false);
		
		if ((HandManager.effectActive(490) && !HandManager.handContains(0, 49))||(HandManager.effectActive(491) && !HandManager.handContains(1, 49))) {
			HandManager.removeEffect(490);
			HandManager.removeEffect(491);
		}
		
		GameData.advanceTime();
		
		if (GameData.ccw && HandManager.China==-1 && MapManager.get(86).isControlledBy()==1) {
			HandManager.China = 1;
		}
		Operations.allowedUSA = Operations.influencePossible(0);
		Operations.allowedSUN = Operations.influencePossible(1);
		if (GameData.isHeadlinePhase()) {
			if (GameData.hasAbility(0, 6)) {
				isCardDiscarded = false;
				GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", you may now elect to discard one of your held cards. (`TS.decide *card no.*`, or `TS.decide 0` if you do not want to discard anything)").complete();
				GameData.dec = new Decision(0, 0);
				return;
			}
			if (GameData.hasAbility(1, 6)) {
				isCardDiscarded = false;
				GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", you may now elect to discard one of your held cards. (`TS.decide *card no.*`, or `TS.decide 0` if you do not want to discard anything)").complete();
				GameData.dec = new Decision(1, 0);
				return;
			}
			GameData.startTurn();
			cardPlayed = false;
			if (!(GameData.getSpace(0)>=4&&GameData.getSpace(1)<4)) {
				GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", play a headline card.").complete();
			}
			if (!(GameData.getSpace(1)>=4&&GameData.getSpace(0)<4)) {
				GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", play a headline card.").complete();
			}
		}
		else if (HandManager.Effects.contains(42) && (GameData.phasing()==0)) {
			boolean canDiscard = false;
			boolean scoring = false;
			for (Integer c : HandManager.USAHand) {
				if (CardList.getCard(c).getOpsMod(0)>=2) {
					canDiscard = true;
				}
				else if (CardList.getCard(c).getOps()==0) {
					scoring = true;
				}
			}
			if (canDiscard) {
				trapDone = false;
				GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", you must discard a card worth two Operations points or more to Quagmire. (TS.decide [card])").complete();
			}
			else if (scoring) {
				trapDone = false;
				GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", you are out of cards to discard. You must now play a scoring card. (TS.decide [card])").complete();
			}
			else {
				GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", you are out of cards to discard. This action round will be passed over.").complete();
			}
		}
		else if (HandManager.Effects.contains(44) && (GameData.phasing()==1)) {
			boolean canDiscard = false;
			boolean scoring = false;
			for (Integer c : HandManager.SUNHand) {
				if (CardList.getCard(c).getOpsMod(0)>=2) {
					canDiscard = true;
				}
				else if (CardList.getCard(c).getOps()==0) {
					scoring = true;
				}
			}
			if (canDiscard) {
				trapDone = false;
				GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", you must discard a card worth two Operations points or more to Bear Trap. (TS.decide [card])").complete();
			}
			else if (scoring) {
				trapDone = false;
				GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", you are out of cards to discard. You must now play a scoring card. (TS.decide [card])").complete();
			}
			else {
				GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", you are out of cards to discard. This action round will be passed over.").complete();
			}
		}
		else if (GameData.getAR()>14) {
			cardPlayedSkippable = false;
			if (GameData.phasing()==1) GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", you have an extra action round. You may play a card or pass the turn (TS.play 0).").complete();
			else GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", you have an extra action round. You may play a card or pass the turn (TS.play 0).").complete();
		}
		else if ((GameData.phasing()==1 && !HandManager.SUNHand.isEmpty()) || (GameData.phasing()==0 && !HandManager.USAHand.isEmpty())) {
			cardPlayed = false;
			if (GameData.phasing()==1) GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", play a card.").complete();
			else GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", play a card.").complete();
		}
		else {
			cardPlayedSkippable = false;
			if (GameData.phasing()==1) GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", you are out of cards. You may pass the turn (TS.play 0) or play the China Card if you have it.").complete();
			else GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", you are out of cards. You may pass the turn (TS.play 0) or play the China Card if you have it.").complete();
		}
		eventDone = false;
		operationsDone = false;
		eventRequired = false;
		operationsRequired = false;
		spaceRequired = false;
		spaceDone = false;
	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return Arrays.asList("TS.time", "TS.+", "TS.done");
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Advance the turn after you've done everything you need to do - this action will fail if something else needs to be done to finish the turn.";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Advance time (time, +, done)";
	}

	@Override
	public List<String> getUsageInstructions() {
		// TODO Auto-generated method stub
		return Arrays.asList("TS.time");
	}

}

package commands;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import events.CardEmbedBuilder;
import events.Decision;
import game.GameData;
import game.PlayerList;
import map.MapManager;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
/**
 * The command handling the flow of time.
 * @author adalbert
 *
 */
public class TimeCommand extends Command {
	/**
	 * An indicator for whether the text of the event on the card in question has occurred
	 */
	public static boolean cardPlayed = true;
	public static boolean cardPlayedSkippable = true;
	public static boolean eventDone = false;
	public static boolean operationsDone = false;
	public static boolean eventRequired = false;
	public static boolean operationsRequired = false;
	public static boolean spaceRequired = false;
	public static boolean spaceDone = false;
	public static boolean extraCheck = true;
	public static boolean isCardDiscarded = true;
	
	public static boolean canAdvance() {
		return cardPlayed&&cardPlayedSkippable&&!(eventRequired^eventDone)&&!(operationsDone^operationsRequired)&&!(spaceDone^spaceRequired)&&isCardDiscarded&&extraCheck;
	}
	
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
		if (GameData.getTurn()==0) {
			sendMessage(e, ":x: This is not something to be doing right now; you don't even have influence on the board!");
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
		if (!extraCheck) {
			sendMessage(e, ":x: You can use an extra support check. For some reason, you have military support in China.");
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
		if (!isCardDiscarded) {
			sendMessage(e, ":x: You may choose to discard a card - make that decision first.");
			return;
		}
		GameData.changeScore(0); //removes We Will Bury You if it's still active for some reason
		GameData.checkScore(false, false);
		
		if ((HandManager.effectActive(490) && !HandManager.handContains(0, 49))||(HandManager.effectActive(491) && !HandManager.handContains(1, 49))) {
			HandManager.removeEffect(490);
			HandManager.removeEffect(491);
		}
		GameData.advanceTime();
		HandManager.activecard = 0;
		//Operations.allowedUSA = Operations.influencePossible(0);
		//Operations.allowedSUN = Operations.influencePossible(1);
		if (GameData.isHeadlinePhase()) {
			if (GameData.hasAbility(0, 6)) {
				isCardDiscarded = false;
				//GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", you may now elect to discard one of your held cards. (`TS.decide *card no.*`, or `TS.decide 0` if you do not want to discard anything)").complete();
				GameData.dec = new Decision(0, 0);
				return;
			}
			if (GameData.hasAbility(1, 6)) {
				isCardDiscarded = false;
				//GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", you may now elect to discard one of your held cards. (`TS.decide *card no.*`, or `TS.decide 0` if you do not want to discard anything)").complete();
				GameData.dec = new Decision(1, 0);
				return;
			}
			GameData.startTurn();
			cardPlayed = false;
			if (!(GameData.getT2(0)>=4&&GameData.getT2(1)<4)) {
				//GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", play a headline card.").complete();
			}
			if (!(GameData.getT2(1)>=4&&GameData.getT2(0)<4)) {
				//GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", play a headline card.").complete();
			}
		}/*
		else if (HandManager.Effects.contains(42) && (GameData.phasing()==0)) {
			boolean canDiscard = false;
			boolean scoring = false;
			for (Integer c : HandManager.DemHand) {
				if (CardList.getCard(c).getOpsMod(0)>=2) {
					canDiscard = true;
				}
				else if (CardList.getCard(c).getOps()==0) {
					scoring = true;
				}
			}
			if (canDiscard) {
				trapDone = false;
				GameData.txtdem.sendMessage(GameData.roledem.getAsMention() + ", you must discard a card worth two Operations points or more to Quagmire. (TS.decide [card])").complete();
			}
			else if (scoring) {
				trapDone = false;
				GameData.txtdem.sendMessage(GameData.roledem.getAsMention() + ", you are out of cards to discard. You must now play a scoring card. (TS.decide [card])").complete();
			}
			else {
				GameData.txtdem.sendMessage(GameData.roledem.getAsMention() + ", you are out of cards to discard. This action round will be passed over.").complete();
			}
		}
		else if (HandManager.Effects.contains(44) && (GameData.phasing()==1)) {
			boolean canDiscard = false;
			boolean scoring = false;
			for (Integer c : HandManager.ComHand) {
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
		}*/
		else if (GameData.getAR()>14) { //skippable eighth action round
			cardPlayedSkippable = false;
			//if (GameData.phasing()==1) GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", you have an extra action round. You may play a card or pass the turn (TS.play 0).").complete();
			//else GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", you have an extra action round. You may play a card or pass the turn (TS.play 0).").complete();
		}
		else if ((GameData.phasing()==1 && !HandManager.ComHand.isEmpty()) || (GameData.phasing()==0 && !HandManager.DemHand.isEmpty())) { //phasing player must play a card if he isn't in a skippable eighth action round
			cardPlayed = false;
			//if (GameData.phasing()==1) GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", play a card.").complete();
			//else GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", play a card.").complete();
		}
		else { //if hand is empty
			cardPlayedSkippable = false;
			//if (GameData.phasing()==1) GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", you are out of cards. You may pass the turn (TS.play 0) or play the China Card if you have it.").complete();
			//else GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", you are out of cards. You may pass the turn (TS.play 0) or play the China Card if you have it.").complete();
		}
		eventDone = false;
		operationsDone = false;
		eventRequired = false;
		operationsRequired = false;
		spaceRequired = false;
		spaceDone = false;
		TimeCommand.prompt();
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

	public static void prompt() {
		if (!cardPlayed) {
			if (GameData.phasing()==0) GameData.txtdem.sendMessage(GameData.roledem.getAsMention() + ", please play a card. (TS.play [card] [use])").complete();
			else GameData.txtcom.sendMessage(GameData.rolecom.getAsMention() + ", please play a card. (TS.play [card] [use])").complete();
		}
		else if (!cardPlayedSkippable) {
			if (GameData.phasing()==0) GameData.txtdem.sendMessage(GameData.roledem.getAsMention() + ", please play a card. Set card to 0 to skip turn. (TS.play [card] [use])").complete();
			else GameData.txtcom.sendMessage(GameData.rolecom.getAsMention() + ", please play a card. Set card to 0 to skip turn. (TS.play [card] [use])").complete();
		}
		else if (eventRequired&&!eventDone) {
			if (CardList.getCard(HandManager.activecard).getAssociation()==0) GameData.txtdem.sendMessage(GameData.roledem.getAsMention() + ", please play your event. (TS.event [args])").complete();
			else if (CardList.getCard(HandManager.activecard).getAssociation()==1) GameData.txtcom.sendMessage(GameData.rolecom.getAsMention() + ", please play your event. (TS.event [args])").complete();
			else if (GameData.phasing()==0) GameData.txtdem.sendMessage(GameData.roledem.getAsMention() + ", please play your event. (TS.event [args])").complete();
			else GameData.txtcom.sendMessage(GameData.rolecom.getAsMention() + ", please play your event. (TS.event [args])").complete();
		}
		else if (operationsRequired&&!operationsDone) {
			if (GameData.phasing()==0) GameData.txtdem.sendMessage(GameData.roledem.getAsMention() + ", please play your operations. (TS.ops [args])").complete();
			else GameData.txtcom.sendMessage(GameData.rolecom.getAsMention() + ", please play your operations. (TS.ops [args])").complete();
		}
		else if (spaceRequired&&!spaceDone) {
			if (GameData.phasing()==0) GameData.txtdem.sendMessage(GameData.roledem.getAsMention() + ", confirm that you are sending this card to space. (TS.space [args])").complete();
			else GameData.txtcom.sendMessage(GameData.rolecom.getAsMention() + ", confirm that you are sending this card to space. (TS.space [args])").complete();
		}
		else if (!isCardDiscarded) {
			if (GameData.phasing()==0) GameData.txtdem.sendMessage(GameData.roledem.getAsMention() + ", you may discard a card. Set card to 0 to not do so. (TS.decide [card])").complete();
			else GameData.txtcom.sendMessage(GameData.rolecom.getAsMention() + ", you may discard a card. Set card to 0 to not do so. (TS.decide [card])").complete();
		}
		else if (!extraCheck) {
			GameData.dec = new Decision(GameData.phasing(), 121);
			if (GameData.phasing()==0) GameData.txtdem.sendMessage(GameData.roledem.getAsMention() + ", would you like to lower the DEFCON level?  (TS.decide yes/no)").complete();
			else GameData.txtcom.sendMessage(GameData.rolecom.getAsMention() + ", would you like to lower the DEFCON level? (TS.decide yes/no)").complete();
		}
		else if (canAdvance()) {
			if (GameData.phasing()==0) GameData.txtdem.sendMessage(GameData.roledem.getAsMention() + ", please advance the time. (TS.time/TS.+)").complete();
			else GameData.txtcom.sendMessage(GameData.rolecom.getAsMention() + ", please advance the time. (TS.time/TS.+)").complete();
		}
	}

}

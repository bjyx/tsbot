package commands;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import events.CardEmbedBuilder;
import events.Decision;
import events.Stasi;
import game.GameData;
import game.PlayerList;
import main.Common;
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
	public static boolean trapDone = true;
	
	public static boolean canAdvance() {
		return cardPlayed&&cardPlayedSkippable&&!(eventRequired^eventDone)&&!(operationsDone^operationsRequired)&&!(spaceDone^spaceRequired)&&isCardDiscarded&&extraCheck&&trapDone;
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
		if (!extraCheck&&GameData.arsLeft()==0) { //Turn Sequence: 3
			GameData.ops = new Operations(GameData.aheadInSpace(), CardList.getCard(1).getOpsMod(GameData.aheadInSpace()), false, true, false, 1);
			GameData.dec = new Decision(GameData.aheadInSpace(), 1); //whoever has the ability is obligatorily ahead in space
			Common.spChannel(GameData.aheadInSpace()).sendMessage(Common.spRole(GameData.aheadInSpace()).getAsMention() + ", you can now use your extra support check.");
		}
		if (GameData.phasing()==0&&HandManager.effectActive(13)&&GameData.getAR()!=14) {
			if (args.length<2) {
				sendMessage(e, ":x: The Stasi are watching. .̄_.̄");
				return;
			}
			try {
				Stasi.card = Integer.parseInt(args[1]);
			}
			catch (NumberFormatException err) {
				sendMessage(e, ":x: The Stasi are not amused. This is clearly not a card. .̄_.̄");
				return;
			}
			if (!HandManager.DemHand.contains(Stasi.card)) {
				sendMessage(e, ":x: The Stasi are not amused at your lies. This will go on your next report. .̄_.̄");
				return;
			}
		}
		
		if (HandManager.activecard!=99) GameData.changeScore(0); //removes Ligachev if it's still active for some reason
		GameData.checkScore(false, false);
		
		if ((HandManager.effectActive(490) && !HandManager.handContains(0, 49))||(HandManager.effectActive(491) && !HandManager.handContains(1, 49))) {
			HandManager.removeEffect(490);
			HandManager.removeEffect(491);
		}
		GameData.advanceTime(); //everything else in the turn sequence
		HandManager.activecard = 0;
		if (HandManager.effectActive(13)&&GameData.getAR()!=14) {
			GameData.txtchnl.sendMessage(new CardEmbedBuilder().setTitle("Intel from the Stasi").setDescription("Dissidents planning to use " + CardList.getCard(Integer.parseInt(args[1]))).setColor(Color.red).build()).complete();
		}
		if (GameData.getAR()==1) { //ar1
			if (GameData.hasAbility(0, 6)||GameData.hasAbility(0, 6)) extraCheck = false;
			Operations.seven%=2;
			Operations.eight%=2;
			GameData.startTurn();
			if (GameData.hasAbility(0, 5)) {
				isCardDiscarded = false;
				GameData.txtdem.sendMessage(GameData.roledem.getAsMention() + ", you may now elect to discard one of your held cards. (`TS.decide *card no.*`, or `TS.decide 0` if you do not want to discard anything)").complete();
				GameData.dec = new Decision(0, 205);
				return;
			}
			if (GameData.hasAbility(1, 5)) {
				isCardDiscarded = false;
				GameData.txtcom.sendMessage(GameData.rolecom.getAsMention() + ", you may now elect to discard one of your held cards. (`TS.decide *card no.*`, or `TS.decide 0` if you do not want to discard anything)").complete();
				GameData.dec = new Decision(1, 205);
				return;
			}
		}
		if (HandManager.Effects.contains(5) && (GameData.phasing()==1)) {
			trapDone = false;
		}
		else if (GameData.getAR()>14) { //skippable eighth action round from Honecker
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
		return Arrays.asList("DF.time", "DF.+", "DF.done");
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
		return Arrays.asList("DF.time");
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
		else if (!trapDone) {
			GameData.txtcom.sendMessage(GameData.rolecom.getAsMention() + ", you must discard a card to attempt to break up this General Strike. Alternatively, you may play a scoring card for the event, but this will allow the strike to continue. (TS.decide [card])").complete();
		}
		else if (!isCardDiscarded) {
			Common.spChannel(GameData.aheadInSpace()).sendMessage(Common.spRole(GameData.phasing()).getAsMention() + ", you may discard a card. Set card to 0 to not do so. (TS.decide [card])").complete();
		}
		else if (!extraCheck) {
			Common.spChannel(GameData.aheadInSpace()).sendMessage(Common.spRole(GameData.phasing()).getAsMention() + ", ").complete();
		}
		else if (canAdvance()) {
			if (GameData.phasing()==0) GameData.txtdem.sendMessage(GameData.roledem.getAsMention() + ", please advance the time. (TS.time/TS.+)").complete();
			else GameData.txtcom.sendMessage(GameData.rolecom.getAsMention() + ", please advance the time. (TS.time/TS.+)").complete();
		}
	}
	public static void reset() {
		cardPlayed = true;
		cardPlayedSkippable = true;
		eventDone = false;
		operationsDone = false;
		eventRequired = false;
		operationsRequired = false;
		spaceRequired = false;
		spaceDone = false;
		extraCheck = true;
		isCardDiscarded = true;
	}
}

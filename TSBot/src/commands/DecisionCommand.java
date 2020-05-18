package commands;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import events.CambridgeFive;
import events.CardEmbedBuilder;
import events.Decision;
import events.FiveYearPlan;
import events.GrainSales;
import events.MissileEnvy;
import events.OlympicGames;
import events.OurManInTehran;
import events.StarWars;
import game.Die;
import game.GameData;
import game.PlayerList;
import logging.Log;
import main.Launcher;
import map.MapManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import turnzero.TurnZero;
import yiyo.AmericasBackyard;
import yiyo.ApolloSoyuz;
/**
 * The command that handles any decisions to be made with regards to any card that cannot be handled by the event command list. The following cards have such an effect:
 * <ul>
 * <li> {@code 010 Blockade} - airlift or concede (USSR Event, USA decision)
 * <li> {@code 020 Olympic Games} - compete or boycott (opponent chooses)
 * <li> {@code 040 Missile Crisis} - resolve [COUNTRY] (country must be one of Cuba, West Germany, or Turkey)
 * <li> {@code 042 Quagmire} - fuck quagmire
 * <li> {@code 044 Bear Trap} - fuck bear trap
 * <li> {@code 045 Summit} - decrease or increase (or not at all; person conducting decision contingent on die rolls)
 * <li> {@code 067 Grain Sales to Soviets} - return, event, operations, space (contingent on card received)
 * <li> {@code 095 Latin American Debt Crisis} - discard or concede (USSR event, USA decision)
 * <li> {@code 098 Aldrich Ames} - The USSR gets to pick a specific card from the US's hand (contingent on seeing the US's hand)
 * <li> {@code 104 Cambridge Five} - country for influence placement (contingent on the US having scoring cards)
 * <li> {@code 106 NORAD} - country for influence placement (literal disconnect)
 * <li> {@code 108 Our Man in Tehran} - any of the five cards seen (contingent on seeing cards)
 * </ul>
 * The command will also handle all "operations" that come about as a result of events, as these may be contingent on seeing cards:
 * <ul>
 * <li> {@code 020 Olympic Games} - 4 Ops, any action (after a boycott)
 * <li> {@code 026 CIA Created} - 1 Op, any action
 * <li> {@code 047 Junta} - 2 Ops, realignments/coup in Latin America
 * <li> {@code 049 Missile Envy} - variable, any action
 * <li> {@code 057 ABM Treaty} - 4 Ops, any action
 * <li> {@code 062 Lone Gunman} - 1 Op, any action
 * <li> {@code 067 Grain Sales to Soviets} - variable, quite literally *any* action
 * <li> {@code 089 Soviets Shoot Down KAL-007} - 4 Ops, influence/realignments
 * <li> {@code 090 Glasnost} - 4 Ops, influence/realignments
 * <li> {@code 096 Tear Down This Wall} - 3 Ops, realignments/coup in Europe
 * </ul>
 * The command will also handle all events that come about as a result of another event:
 * <ul>
 * <li> {@code 005 Five-Year Plan} - US events
 * <li> {@code 047 Missile Envy} - Phasing player's event OR neutral event
 * <li> {@code 067 Grain Sales to Soviets} - Any event
 * <li> {@code 085 Star Wars} - Non-scoring cards
 * 
 * The command will also handle discarding of the card at the end of the turn. 
 * @author [REDACTED]
 *
 */
public class DecisionCommand extends Command {

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
		if (args.length==1) {
			sendMessage(e, ":x: Indecision is not an option.");
			return;
		}
		if (HandManager.effectActive(128) && ((!TimeCommand.cardPlayed&&GameData.phasing()==1)||!TimeCommand.checkpointC) && !GameData.isHeadlinePhase() && GameData.phasing()==PlayerList.getArray().indexOf(e.getAuthor()) && args[1].equalsIgnoreCase("charlie")) {
			HandManager.removeEffect(128);
			Log.writeToLog("Checkpoint Charlie Cancelled");
			if (!TimeCommand.checkpointC) {
				TimeCommand.checkpointC = true;
				GameData.dec=null;
			}
			GameData.txtchnl.sendMessage(new CardEmbedBuilder().addMilOps(GameData.phasing(), -GameData.getMilOps(GameData.phasing())).setTitle("De-Escalation at Checkpoint Charlie").setDescription("Tanks slowly inch away from standoff in Berlin").setFooter("\"It's not a very nice solution, but a wall is a hell of a lot better than a war.\"\n- John F. Kennedy, 1961", Launcher.url("people/jfk.png")).setColor(Color.green).build()).complete();
			if (HandManager.Effects.contains(42) && (GameData.phasing()==0)) {
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
					TimeCommand.trapDone = false;
					GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", you must discard a card worth two Operations points or more to Quagmire. (TS.decide [card])").complete();
				}
				else if (scoring) {
					TimeCommand.trapDone = false;
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
					TimeCommand.trapDone = false;
					GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", you must discard a card worth two Operations points or more to Bear Trap. (TS.decide [card])").complete();
				}
				else if (scoring) {
					TimeCommand.trapDone = false;
					GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", you are out of cards to discard. You must now play a scoring card. (TS.decide [card])").complete();
				}
				else {
					GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", you are out of cards to discard. This action round will be passed over.").complete();
				}
			}
			else if (HandManager.effectActive(115) && HandManager.checkScoring()<2) {
				TimeCommand.cardPlayedSkippable = false; //obligatory skip
			}
			else if (GameData.getAR()>14) { //skippable eighth action round
				TimeCommand.cardPlayedSkippable = false;
				//if (GameData.phasing()==1) GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", you have an extra action round. You may play a card or pass the turn (TS.play 0).").complete();
				//else GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", you have an extra action round. You may play a card or pass the turn (TS.play 0).").complete();
			}
			else if ((GameData.phasing()==1 && !HandManager.SUNHand.isEmpty()) || (GameData.phasing()==0 && !HandManager.USAHand.isEmpty())) { //phasing player must play a card if he isn't in a skippable eighth action round
				TimeCommand.cardPlayed = false;
				//if (GameData.phasing()==1) GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", play a card.").complete();
				//else GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", play a card.").complete();
			}
			else { //if hand is empty
				TimeCommand.cardPlayedSkippable = false;
				//if (GameData.phasing()==1) GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", you are out of cards. You may pass the turn (TS.play 0) or play the China Card if you have it.").complete();
				//else GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", you are out of cards. You may pass the turn (TS.play 0) or play the China Card if you have it.").complete();
			}
			TimeCommand.eventDone = false;
			TimeCommand.operationsDone = false;
			TimeCommand.eventRequired = false;
			TimeCommand.operationsRequired = false;
			TimeCommand.spaceRequired = false;
			TimeCommand.spaceDone = false;
			if (HandManager.effectActive(1210+GameData.phasing())) {
				GameData.txtchnl.sendMessage(new CardEmbedBuilder().changeDEFCON(2).setTitle("Nuclear Proliferation").setColor(GameData.phasing()==0?Color.blue:Color.red).build()).complete();
				HandManager.removeEffect(1210+GameData.phasing());
				HandManager.addEffect(1212);
			}
			TimeCommand.prompt();
			return;
		}
		if ((HandManager.effectActive(1270)||HandManager.effectActive(1271))&&args[1].equals("arkhipov")) {
			
			if (HandManager.effectActive(1270)) {
				if (e.getAuthor().equals(PlayerList.getSSR())) {
					sendMessage(e, ":x: You aren't a puppeteer. Especially not for your opponent.");
					return;
				}
				HandManager.removeEffect(1270);
			}
			else if (HandManager.effectActive(1271)) {
				if (e.getAuthor().equals(PlayerList.getUSA())) {
					sendMessage(e, ":x: You aren't a puppeteer. Especially not for your opponent.");
					return;
				}
				HandManager.removeEffect(1271);
			}
			else {
				//...how?
				return;
			}
			Log.writeToLog("Arkhipov Used");
			CardEmbedBuilder builder = new CardEmbedBuilder();
			builder.changeDEFCON(1);
			builder.setTitle("Nuclear War Narrowly Averted")
			.setDescription("Soviet submarine officer Arkhipov refuses authorization to launch nuclear torpedo")
			.setColor(Color.green);
			GameData.txtchnl.sendMessage(builder.build()).complete();
		}
		if ((HandManager.effectActive(400)||HandManager.effectActive(401))&&args[1].equals("resolve")) {
			if (args.length==2) {
				sendMessage(e, ":x: Write a country. Yes, you still write in the country even if you only have one option.");
				return;
			}
			CardEmbedBuilder builder = new CardEmbedBuilder();
			
			int i = MapManager.find(args[2]);
			if (i==-1) {
				sendMessage(e, ":x: That's not a country.");
				return;
			}
			builder.setTitle("Crisis Resolved")
			.setDescription("Missiles pulled out of " + MapManager.get(i).name)
			.setFooter("","")
			.setColor(Color.green);
			if (HandManager.Effects.contains(400)) {
				if (e.getAuthor().equals(PlayerList.getSSR())) {
					sendMessage(e, ":x: You aren't a puppeteer. Especially not for your opponent.");
					return;
				}
				if (i!=19&&i!=17) {
					sendMessage(e, ":x: Those are not where your missiles are...");
					return;
				}
				if (MapManager.get(i).influence[0]<2) {
					sendMessage(e, ":x: So apparently you can't pull your missiles out of a country you installed them in. So much for peace.");
					return;
				}
				builder.changeInfluence(i, 0, -2);
				HandManager.removeEffect(400);
			}
			else {
				if (e.getAuthor().equals(PlayerList.getUSA())) {
					sendMessage(e, ":x: You aren't a puppeteer. Especially not for your opponent.");
					return;
				}
				if (i!=65) {
					sendMessage(e, ":x: Those are not where your missiles are...");
					return;
				}
				if (MapManager.get(i).influence[1]<2) {
					sendMessage(e, ":x: So apparently you can't pull your missiles out of a country you installed them in. So much for peace.");
					return;
				}
				builder.changeInfluence(i, 1, -2);
				HandManager.removeEffect(401);
			}
			Log.writeToLog("Cuban Missile Crisis cancelled");
			GameData.txtchnl.sendMessage(builder.build()).complete();
			return;
		}
		if (!TimeCommand.trapDone && !GameData.isHeadlinePhase()) {
			int x;
			if (HandManager.Effects.contains(42) && (GameData.phasing()==0)) {
				if (e.getAuthor().equals(PlayerList.getSSR())) {
					sendMessage(e, ":x: You aren't a puppeteer. Especially not for your opponent.");
					return;
				}
				try {
					x = Integer.parseInt(args[1]);
				}
				catch (NumberFormatException err) {
					sendMessage(e, ":x: That's not a number.");
					return;
				}
				if (!HandManager.USAHand.contains(x)) {
					sendMessage(e, ":x: Don't generate cards out of thin air.");
					return;
				}
				boolean canDiscard = false;
				for (Integer c : HandManager.USAHand) {
					if (CardList.getCard(c).getOpsMod(0)>=2) {
						canDiscard = true;
					}
				}
				if (canDiscard) {
					if (CardList.getCard(x).getOpsMod(0)<2) {
						sendMessage(e, ":x: This is not going to help here.");
						return;
					}
					if (x!=49 && CardList.getCard(49).getOpsMod(0)>=2 && HandManager.effectActive(490)) {
						sendMessage(e, ":x: https://www.gmtgames.com/nnts/FAQv5.pdf, ruling on Missile Envy use under Quagmire.");
						return;
					}
					Log.writeToLog("Quagmire: Discarded " + CardList.getCard(x).getName());
					HandManager.discard(0, x);
					EmbedBuilder builder = new CardEmbedBuilder().setTitle("Quagmire!").setDescription("Discarded " + CardList.getCard(x));
					Die die = new Die();
					die.roll();
					builder.addField(die.toString(), die.result<=4?"Success - Quagmire cancelled!":"Failure", false).setColor(die.result<=4?Color.blue:Color.red);
					if (die.result<=4) {
						Log.writeToLog("Quagmire cancelled.");
						HandManager.removeEffect(42);
					}
					GameData.txtchnl.sendMessage(builder.build()).complete();
					TimeCommand.trapDone=true;
					TimeCommand.prompt();
				}
				else {
					if (CardList.getCard(x).getOpsMod(0)!=0) {
						sendMessage(e, ":x: You must play your scoring cards.");
						return;
					}
					Log.writeToLog("Quagmire: Played " + CardList.getCard(x).getName());
					CardList.getCard(x).onEvent(0, new String[] {});
					HandManager.discard(0, x);
					TimeCommand.trapDone=true;
					TimeCommand.prompt();
				}
				
			}
			else if (HandManager.Effects.contains(44) && (GameData.phasing()==1)) {
				if (e.getAuthor().equals(PlayerList.getUSA())) {
					sendMessage(e, ":x: You aren't a puppeteer. Especially not for your opponent.");
					return;
				}
				try {
					x = Integer.parseInt(args[1]);
				}
				catch (NumberFormatException err) {
					sendMessage(e, ":x: That's not a number.");
					return;
				}
				if (!HandManager.SUNHand.contains(x)) {
					sendMessage(e, ":x: Don't generate cards out of thin air.");
					return;
				}
				boolean canDiscard = false;
				for (Integer c : HandManager.SUNHand) {
					if (CardList.getCard(c).getOpsMod(1)>=2) {
						canDiscard = true;
					}
				}
				if (canDiscard) {
					if (CardList.getCard(x).getOpsMod(1)<2) {
						sendMessage(e, ":x: This is not going to help here.");
						return;
					}
					if (x!=49 && CardList.getCard(49).getOpsMod(1)>=2 && HandManager.effectActive(491)) {
						sendMessage(e, ":x: https://www.gmtgames.com/nnts/FAQv5.pdf, ruling on Missile Envy use under Bear Trap.");
						return;
					}
					Log.writeToLog("Bear Trap: Discarded " + CardList.getCard(x).getName());
					HandManager.discard(1, x);
					EmbedBuilder builder = new CardEmbedBuilder().setTitle("Bear Trap!").setDescription("Discarded " + CardList.getCard(x));
					Die die = new Die();
					die.roll();
					builder.addField(die.toString(), die.result<=4?"Success - Bear Trap cancelled!":"Failure", false).setColor(die.result<=4?Color.red:Color.blue);
					if (die.result<=4) {
						Log.writeToLog("Bear Trap cancelled.");
						HandManager.removeEffect(44);
					}
					GameData.txtchnl.sendMessage(builder.build()).complete();
					TimeCommand.trapDone=true;
				}
				else {
					if (CardList.getCard(x).getOpsMod(1)!=0) {
						sendMessage(e, ":x: You must play your scoring cards.");
						return;
					}
					Log.writeToLog("Bear Trap: Played " + CardList.getCard(x).getName());
					CardList.getCard(x).onEvent(1, new String[] {});
					HandManager.discard(1, x);
					TimeCommand.trapDone=true;
				}
			}
			else {
				sendMessage(e, "You shouldn't be seeing this, but you are not a puppeteer.");
			}
			return;
		}
		if (GameData.dec == null) {
			sendMessage(e, ":x: No pending decisions. Are you sure you've satisfied the conditions for that?");
			return;
		}
		int event = GameData.dec.card;
		int sp = GameData.dec.sp;
		if (e.getAuthor().equals(PlayerList.getArray().get((sp+1)%2))) {
			sendMessage(e, ":x: You aren't a puppeteer. Especially not for your opponent.");
			return;
		}
		if (event==1001) {
			if (sp==0) { 
				int card;
				try {
					card = Integer.parseInt(args[1]);
				}
				catch (NumberFormatException err) {
					sendMessage(e, ":x: Give the number of the card you wish to have, or 0 if you do not wish to do so.");
					return;
				}
				if (card==0) {
					EmbedBuilder builder = new CardEmbedBuilder().setTitle("Yalta Conference").setDescription("Obtained nothing.").setColor(Color.gray);
					GameData.txtchnl.sendMessage(builder.build()).complete();
					Log.writeToLog("Yalta: Obtained nothing.");
				}
				else if (card==23) {
					EmbedBuilder builder = new CardEmbedBuilder().setTitle("Yalta Conference").setDescription("Obtained Marshall Plan.").setColor(Color.blue);
					GameData.txtchnl.sendMessage(builder.build()).complete();
					HandManager.addEffect(100123); //lol
					Log.writeToLog("Yalta: Obtained Marshall Plan.");
				}
				else {
					sendMessage(e, ":x: Marshall Plan or bust.");
				}
			}
			else if (sp==1) { 
				int card;
				try {
					card = Integer.parseInt(args[1]);
				}
				catch (NumberFormatException err) {
					sendMessage(e, ":x: Give the number of the card you wish to have, or 0 if you do not wish to do so.");
					return;
				}
				if (card==0) {
					EmbedBuilder builder = new CardEmbedBuilder().setTitle("Yalta Conference").setDescription("Obtained nothing.").setColor(Color.gray);
					GameData.txtchnl.sendMessage(builder.build()).complete();
					Log.writeToLog("Yalta: Obtained nothing.");
				}
				else if (card==9) {
					EmbedBuilder builder = new CardEmbedBuilder().setTitle("Yalta Conference").setDescription("Obtained Vietnam Revolts.").setColor(Color.red);
					GameData.txtchnl.sendMessage(builder.build()).complete();
					Log.writeToLog("Yalta: Obtained Vietnam Revolts.");
					HandManager.addEffect(100109); //lol
				}
				else if (card==13) {
					EmbedBuilder builder = new CardEmbedBuilder().setTitle("Yalta Conference").setDescription("Obtained Arab-Israeli War.").setColor(Color.red);
					GameData.txtchnl.sendMessage(builder.build()).complete();
					Log.writeToLog("Yalta: Obtained Arab-Israeli War.");
					HandManager.addEffect(100113); //lol
				}
				else {
					sendMessage(e, ":x: One of those two or bust.");
				}
			}
			else {
				//how did you get here?
				return;
			}
			GameData.dec = null;
			if (TurnZero.startCrisis()) {
				return;
			}
			//contingency that shouldn't happen anyways
			CardList.initialize();
			HandManager.addToDeck(0);
			if (HandManager.removeEffect(100109)) {
				HandManager.Deck.remove((Integer)9);
				HandManager.SUNHand.add(9);
			}
			if (HandManager.removeEffect(100113)) {
				HandManager.Deck.remove((Integer)13);
				HandManager.SUNHand.add(13);
			}
			if (HandManager.removeEffect(100123)) {
				HandManager.Deck.remove((Integer)23);
				HandManager.USAHand.add(23);
			}
			HandManager.deal();
			SetupCommand.USSR = true;
			GameData.txtssr.sendMessage(PlayerList.getSSR().getAsMention() + ", please place six influence markers in Eastern Europe. (Use TS.setup)").complete();
			return;
		}
		if (event==4176) {
			if (args[1].equalsIgnoreCase("reroll")) {
				Log.writeToLog("Used Space Race Ability 6.");
				Operations.coupReroll += 2;
				GameData.txtchnl.sendMessage(new EmbedBuilder().setTitle("Space Race Advantage").setDescription("The "+(sp==0?"US":"USSR")+" has used its technological edge for a second shot at a coup!").setColor(sp==0?Color.blue:Color.red).build());
				GameData.ops.coupPreDet(Operations.target6, new Die().roll());
			}
			else if (args[1].equalsIgnoreCase("accept")) {
				GameData.ops.coupPreDet(Operations.target6, Operations.die6);
			}
			else {
				sendMessage(e, ":x: Please respond with the options given.");
				return;
			}
			synchronized(GameData.sync) {
				notify();
			}
			return;
		}
		if (event==0) {
			int card;
			try {
				card = Integer.parseInt(args[1]);
			}
			catch (NumberFormatException err) {
				sendMessage(e, ":x: Give the number of the card you wish to discard, or 0 if you do not wish to do so.");
				return;
			}
			if (card==0) {
				sendMessage(e, "Roger.");
				EmbedBuilder builder = new CardEmbedBuilder().setTitle("Space Race Advantage").setDescription("Discarded nothing.");
				GameData.txtchnl.sendMessage(builder.build()).complete();
				TimeCommand.isCardDiscarded=true;
			}
			else if (card<=CardList.numberOfCards()) {
				if (!HandManager.discard(sp, card)) {
					sendMessage(e, ":x: You don't have this card.");
					return;
				}
				EmbedBuilder builder = new CardEmbedBuilder().setTitle("Space Race Advantage").setDescription("Discarded " + CardList.getCard(card));
				GameData.txtchnl.sendMessage(builder.build()).complete();
				TimeCommand.isCardDiscarded=true;
				Log.writeToLog("Used Space Ability 6 to discard " + CardList.getCard(card).getName());
			}
			else {
				sendMessage(e, ":x: Give the number of the card you wish to discard.");
				return;
			}
			GameData.startTurn();
			TimeCommand.cardPlayed = false;
			if (!(GameData.getSpace(0)>=4&&GameData.getSpace(1)<4)) {
				GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", play a headline card.").complete();
			}
			if (!(GameData.getSpace(1)>=4&&GameData.getSpace(0)<4)) {
				GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", play a headline card.").complete();
			}
			TimeCommand.eventDone = false;
			TimeCommand.operationsDone = false;
			TimeCommand.eventRequired = false;
			TimeCommand.operationsRequired = false;
			TimeCommand.spaceRequired = false;
			TimeCommand.spaceDone = false;
			GameData.dec=null;
			return;
		}
		if (event==5) {
			if (!CardList.getCard(FiveYearPlan.card).isFormatted(0, args)) {
				sendMessage(e, ":x: Format your arguments correctly.");
				return;
			}
			if (!CardList.getCard(FiveYearPlan.card).isPlayable(0)) {
				sendMessage(e, "Oops. Forgot you can't play that card for an event. Let's rectify that.");
				HandManager.discard(1, FiveYearPlan.card);
			}
			else {
				if (CardList.getCard(FiveYearPlan.card).isRemoved()) {
					HandManager.removeFromGame(1, FiveYearPlan.card);
				}
				else {
					HandManager.discard(1, FiveYearPlan.card);
				}
				Log.writeToLog(CardList.getCard(FiveYearPlan.card).getName()+":");
				CardList.getCard(FiveYearPlan.card).onEvent(0, args);
			}
		}
		if (event==10) {
			if (args[1].equals("airlift")) {
				if (args.length < 3) {
					sendMessage(e, ":x: Allocate some resources to your airlift.");
					return;
				}
				int card;
				try {
					card = Integer.parseInt(args[2]);
				}
				catch (NumberFormatException err) {
					sendMessage(e, ":x: Now how is that a card!?");
					return;
				}
				if (CardList.getCard(card).getOpsMod(0)<3) {
					sendMessage(e, ":x: This won't be enough.");
					return;
				}
				if (!HandManager.discard(0, card)) {
					sendMessage(e, ":x: We don't have that. At least not at our disposal.");
					return;
				}
				Log.writeToLog("US discards "+CardList.getCard(card).getName()+".");
				CardEmbedBuilder builder = new CardEmbedBuilder();
				builder.setTitle("Berlin Airlift")
					.setDescription("West Berlin successfully supplied for over a year; Soviets forced to lift blockade")
					.setFooter("\"We can haul anything.\" \n"
							+ "- Curtis LeMay, 1948",Launcher.url("people/lemay.png"))
					.setColor(Color.blue);
				builder.addField("Soviets lift blockade of Berlin.", "Discarded card: "+CardList.getCard(card), false);
				GameData.txtchnl.sendMessage(builder.build()).complete();
			}
			else if (args[1].equals("concede")) {
				Log.writeToLog("US discards nothing.");
				CardEmbedBuilder builder = new CardEmbedBuilder();
				builder.setTitle("Berlin Blockade")
					.setDescription("Concessions made to the Soviet Union to prevent starvation")
					.setFooter("\"What happens to Berlin, happens to Germany; what happens to Germany, happens to Europe.\" \n"
							+ "- Vyacheslav Molotov, 1948",Launcher.url("people/molotov.png"))
					.setColor(Color.red);
				builder.changeInfluence(20, 0, -MapManager.get(20).influence[0]);
				GameData.txtchnl.sendMessage(builder.build()).complete();
			}
			else {
				sendMessage(e, ":x: That's not an option.");
				return;
			}
		}
		if (event==20) {
			if (args[1].equals("boycott")) {
				Log.writeToLog("Olympics Boycotted.");
				CardEmbedBuilder builder = new CardEmbedBuilder();
				builder.setTitle("Olympics Boycotted!")
					.setDescription("")
					.setFooter("\"To act differently would be tantamount to approving of the anti-Olympic actions of the U.S. authorities and organizers of the games.\" \n"
							+ "- Tass, 1984",Launcher.url("people/tass.png"))
					.setColor(Color.DARK_GRAY);
				builder.changeDEFCON(-1);
				GameData.txtchnl.sendMessage(builder.build()).complete();
				GameData.txtchnl.sendMessage("Awaiting " + (GameData.phasing()==0?GameData.roleusa.getAsMention():GameData.rolessr.getAsMention())+" to play 4 Operations points (remember to adjust to account for Red Scare/Purge/Containment/Brezhnev Doctrine).").complete();
				GameData.dec = new Decision(OlympicGames.host, 201);
				GameData.ops = new Operations(sp, CardList.getCard(34).getOpsMod(sp), true, true, true, false, false);
				return;
			}
			else if (args[1].equals("compete")) {
				CardEmbedBuilder builder = new CardEmbedBuilder();
				int[] die = {0,0};
				
				while (die[0] == die[1]) {
					die[0] = new Die().roll();
					die[1] = new Die().roll();
					die[OlympicGames.host] += 2;
					builder.addField(":flag_us::"+CardEmbedBuilder.numbers[die[0]]+":-:" + CardEmbedBuilder.numbers[die[1]]+":" + MapManager.get(85),die[0]==die[1]?"A tie - roll again.":("And "+(die[0]>die[1]?"the Americans":"the Soviets")+ " take home the gold!"),false);
				}
				builder.setTitle("Olympics in " + (OlympicGames.host==0?"Los Angeles": "Moscow"))
					.setDescription("")
					.setFooter("\"You were born to be a player. You were meant to be here. This moment is yours.\"\n"
							+ "- Herb Brooks, 1980",Launcher.url("people/brooks.png"))
					.setColor(die[0]>die[1]?Color.BLUE:Color.RED);
				builder.changeVP(die[0]>die[1]?2:-2);
				GameData.txtchnl.sendMessage(builder.build()).complete();
			}
			else {
				sendMessage(e, ":x: That's not an option.");
				return;
			}
		}
		if (event==201) {
			boolean result = GameData.ops.ops(args);
			if (!result) {
				return;
			}
		}
		if (event==26) {
			boolean result = GameData.ops.ops(args);
			if (!result) {
				return;
			}
		}
		if (event==32) {
			boolean result = GameData.ops.ops(args);
			if (!result) {
				return;
			}
		}
		if (event==45) {
			int i;
			try {
				i = Integer.parseInt(args[1]);
			}
			catch (NumberFormatException err) {
				sendMessage(e, ":x: That's not an option. That's not even a number!");
				return;
			}
			if (Math.abs(i)>1) {
				sendMessage(e, ":x: Ah, nope. The number must be -1, 0, or 1. DEFCON can only change so quickly.");
				return;
			}
			CardEmbedBuilder builder = new CardEmbedBuilder();
			if (i==-1) {
				builder.setTitle("Escalation")
				.setDescription("Espionage incident threatens planned summit")
				.setColor(Color.BLACK);
			}
			else if (i==1) {
				builder.setTitle("Deténte")
				.setDescription("Soviet-American Summit leads to a decrease in tensions")
				.setColor(Color.WHITE);
			}
			else {
				builder.setTitle("Summit Inconclusive")
				.setDescription("Meeting ends without reaching an agreement on arms control")
				.setColor(Color.GRAY);
			}
			builder.changeDEFCON(i);
			GameData.txtchnl.sendMessage(builder.build()).complete();
		}
		if (event==47) {
			boolean result = GameData.ops.ops(args);
			if (!result) {
				return;
			}
		}
		if (event==49) {
			int i;
			try {
				i = Integer.parseInt(args[1]);
			}
			catch (NumberFormatException err) {
				sendMessage(e, ":x: Card IDs are integers. I suppose you forgot that.");
				return;
			}
			if (i==6) {
				sendMessage(e, ":x: 别摸我.");
				return;
			}
			if (!(sp==0?HandManager.USAHand.contains(i):HandManager.SUNHand.contains(i))) {
				sendMessage(e, ":x: Don't conjure cards out of thin air...");
				return;
			}
			if (CardList.getCard(i).getOps()<MissileEnvy.maxops) {
				sendMessage(e, ":x: Don't be a trickster. There are cards with a higher OP value.");
				return;
			}
			CardEmbedBuilder builder = new CardEmbedBuilder();
			Log.writeToLog("Handed over " + CardList.getCard(i).getName());
			builder.setTitle("The Arms Race and Nuclear War")
				.setDescription("Helen Caldicott")
				.setColor(sp==0?Color.red:Color.blue)
				.setFooter("The superpowers often behave like two heavily armed blind men "
						+ "feeling their way around a room, each believing himself in mortal peril from the other, "
						+ "whom he assumes to have perfect vision... Of course, over time, "
						+ "even two armed blind men can do enormous damage to each other, "
						+ "not to speak of the room.\n" + 
						"- Henry Kissinger, 1979", Launcher.url("people/kissinger.png"));
			builder.addField("Missile Envy", sp==0?"The USA":"The USSR" + " has given " + CardList.getCard(i) + " in exchange for " + CardList.getCard(49) + ".", false);
			GameData.txtchnl.sendMessage(builder.build()).complete();
			MissileEnvy.card = i;
			HandManager.getFromDiscard(sp, 49);
			if (CardList.getCard(i).getAssociation()==sp) {
				HandManager.discard(sp, i);
				GameData.ops = new Operations((sp+1)%2, CardList.getCard(i).getOpsMod((sp+1)%2), true, true, true, false, false);
			}
			else {
				HandManager.removeFromHand(sp, i);
			}
			if (HandManager.effectActive(59)&&((i==13&&!HandManager.effectActive(65))||i==11||i==24||i==36||i==102)&&sp==0) {
				Log.writeToLog("Flower Power:");
				CardEmbedBuilder fp = new CardEmbedBuilder();
				fp.setTitle("Flower Power")
					.setDescription("Anti-war protests erupt against the " + CardList.getCard(i).getName() + "!")
					.setFooter("\"I think that we're up against the strongest, well-trained, "
							+ "militant, revolutionary group that has ever assembled in America.\" \n"
							+ "- Jim Rhodes, 1970", Launcher.url("people/rhodes.png"))
					.setColor(Color.red);
				fp.changeVP(-2);
				GameData.txtchnl.sendMessage(fp.build()).complete();
			} //flowerpower still activates here
			GameData.dec = new Decision((sp+1)%2, 491);
			return;
		}
		if (event==491) {
			int i = MissileEnvy.card;
			if (CardList.getCard(i).getAssociation()==(sp+1)%2) {
				boolean result = GameData.ops.ops(args);
				if (!result) return;
			}
			else if (CardList.getCard(i).isPlayable(sp)) {
				if (!CardList.getCard(i).isFormatted(sp, args)) {
					sendMessage(e, ":x: Format your arguments correctly.");
					return;
				}
				if (CardList.getCard(i).isRemoved()) {
					HandManager.Removed.add(i);
				}
				else if (HandManager.activecard!=73) {
					HandManager.Discard.add(i);
				}
				Log.writeToLog(CardList.getCard(i).getName()+":");
				CardList.getCard(i).onEvent(sp, args);
			}
			HandManager.addEffect(490 + (sp+1)%2); //handles part 2
		}
		if (event==57) {
			boolean result = GameData.ops.ops(args);
			if (!result) {
				return;
			}
		}
		if (event==62) {
			boolean result = GameData.ops.ops(args);
			if (!result) {
				return;
			}
		}
		if (event==67) {
			List<Character> modes = Arrays.asList('r','e','o','s','u');

			if (args.length<2) {
				sendMessage(e, ":x: You're playing... what, exactly? And how?");
				return;
			}
			int card = GrainSales.card;
			char mode = args[1].charAt(0);
			if (!modes.contains(mode)) {
				sendMessage(e, ":x: Modes can be any of r, e, o, s, or u. Not the one you chose, though.");
				return;
			}
			if (GameData.isHeadlinePhase()&&(card == 32)) { //UN Intervention be played in the headline phase
				sendMessage(e, ":x: That is not a card you can play in the headline.");
				return;
			}
			if (mode == 'o'&&CardList.getCard(card).getOps()==0) { //All cards have either an op value or is a scoring card that is obligatorily played for the event
				sendMessage(e, ":x: This card must be played for the event only.");
				return;
			}
			if (mode == 's' && (GameData.getSpace(0)==8||CardList.getCard(card).getOpsMod(0)<Operations.spaceOps[GameData.getSpace(0)])) {
				sendMessage(e, ":x: You cannot play this card on the space race.");
				return;
			}
			if (mode=='s'&&GameData.hasSpace(0)) {
				sendMessage(e, ":x: Wait until next turn to space this card.");
				return;
			}
			if ((mode=='e'||mode=='u')&&!CardList.getCard(card).isPlayable(0)) {
				sendMessage(e, "This card's event is currently disabled. (Perhaps read the description again? Or, if you intended to use it for ops, try using 'o' there instead of 'e'.");
				return;
			}
			if (mode=='u'&&!HandManager.handContains(0, 32)) {
				sendMessage(e, "Actually have the 'UN Intervention' Card in your hand first.");
				return;
			}
			if (mode=='u'&&CardList.getCard(card).getAssociation()!=1) {
				sendMessage(e, "This is not a card you can match with UN Intervention - just play it for Ops directly.");
				return;
			}
			if (HandManager.effectActive(59)&&((card==13&&!HandManager.effectActive(65))||card==11||card==24||card==36||card==102)&&(mode=='e'||mode=='o')) {
				CardEmbedBuilder builder = new CardEmbedBuilder();
				builder.setTitle("Flower Power")
					.setDescription("Anti-war protests erupt against the " + CardList.getCard(card).getName() + "!")
					.setFooter("\"I think that we're up against the strongest, well-trained, "
							+ "militant, revolutionary group that has ever assembled in America.\" \n"
							+ "- Jim Rhodes, 1970", Launcher.url("people/rhodes.png"))
					.setColor(Color.red);
				builder.changeVP(-2);
				GameData.txtchnl.sendMessage(builder.build()).complete();
			}
			CardEmbedBuilder builder = new CardEmbedBuilder();
			builder.setTitle("Soviet Union Accepts Grain Deal")
			.setDescription("9 million tons of grain to be bought per year")
			.setColor(Color.blue);
			if (mode=='r') {
				Log.writeToLog("Card is returned.");
				HandManager.transfer(0, card);
				GameData.ops = new Operations (0, CardList.getCard(67).getOpsMod(0), true, true, true, false, false);
				GrainSales.status = 'o';
				builder.addField("Obtained card: " + CardList.getCard(card), "Card to be returned to the USSR.", false);
				GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", you have returned the card. You may conduct operations using Grain Sales to Soviets.").complete();
				GameData.dec = new Decision(0, 671);
			}
			if (mode=='e') {
				if (CardList.getCard(card).getAssociation()==1) {
					if (CardList.getCard(card).isRemoved()) {
						HandManager.removeFromGame(0, card);
					}
					else if (card!=73) { //obsolescence
						HandManager.discard(0, card);
					}
					else HandManager.removeFromHand(0, card);
					Log.writeToLog("Card is played for event first.");
					builder.addField("Obtained card: " + CardList.getCard(card), "Card to be used for the event before operations.", false);
					GrainSales.status = 'f';
					GameData.dec = new Decision(1, 671);
				}
				else {
					if (CardList.getCard(card).isRemoved()) {
						HandManager.removeFromGame(0, card);
					}
					else if (card!=73) {
						HandManager.discard(0, card);
					}
					else HandManager.removeFromHand(0, card);
					Log.writeToLog("Card is played for event.");
					builder.addField("Obtained card: " + CardList.getCard(card), "Card to be used as event.", false);
					GrainSales.status = 'e';
					GameData.dec = new Decision(0, 671);
				}
				GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", you may implement this event.").complete();
			}
			if (mode=='o') {
				if (CardList.getCard(card).getAssociation()==1&&CardList.getCard(card).isPlayable(0)) {
					if (CardList.getCard(card).isRemoved()) {
						HandManager.removeFromGame(0, card);
					}
					else if (card!=73) {
						HandManager.discard(0, card);
					}
					else HandManager.removeFromHand(0, card);
					builder.addField("Obtained card: " + CardList.getCard(card), "Card to be used for operations before the event.", false);
					GrainSales.status = 'l'; //event last
					GameData.dec = new Decision(0, 671);
					Log.writeToLog("Card is played for ops first.");
				}
				else {
					HandManager.discard(0, card);
					builder.addField("Obtained card: " + CardList.getCard(card), "Card to be used for operations.", false);
					GrainSales.status = 'o'; //ops only
					GameData.dec = new Decision(0, 671);
					Log.writeToLog("Card is played for ops.");
				}
				GameData.ops = new Operations(0, CardList.getCard(card).getOpsMod(0), true, true, true, false, false);
				GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", you may now perform the operations.").complete();

			}
			if (mode=='s') {
				HandManager.discard(0, card);
				GrainSales.status = 's';
				builder.addField("Obtained card: " + CardList.getCard(card), "Card to be used on the space race.", false);
				GameData.ops = new Operations(0, CardList.getCard(card).getOpsMod(0), false, false, false, true, false);
				GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", you may now send this card to space.").complete();
				Log.writeToLog("Card is sent to space.");
				GameData.dec = new Decision(0, 671);
			}
			if (mode=='u') {
				EmbedBuilder un = new CardEmbedBuilder().setTitle("UN INTERVENTION!")
						.setDescription("The UN collectively agrees on something for once")
						.setFooter("\"It is not the Soviet Union or indeed any other big Powers who need the United Nations for their protection. "
								+ "It is all the others.\"\n"
								+ "- Dag Hammarskjöld, 1960", Launcher.url("people/hammarskjold.png"))
						.setColor(Color.blue)
						.addField("UN Security Council Resolution", "The event of "+ CardList.getCard(GrainSales.card)+" has been condemned by the UN, and will not occur.", false);
				GameData.txtchnl.sendMessage(un.build()).complete();
				HandManager.discard(0, card);
				HandManager.discard(0, 32);
				GrainSales.status = 'o';
				builder.addField("Obtained card: " + CardList.getCard(GrainSales.card), "Card matched with UN Intervention for operations.", false);
				GameData.ops = new Operations(0, CardList.getCard(card).getOpsMod(0), true, true, true, false, false);
				GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", you may now perform the operations.");
				GameData.dec = new Decision(0, 671);
				Log.writeToLog("UN Intervention played with card for ops.");
			}
			GameData.txtchnl.sendMessage(builder.build()).complete();
			return;
		}
		if (event==671) {
			if (GrainSales.status=='e'||GrainSales.status=='f') {
				if (!CardList.getCard(GrainSales.card).isFormatted(0, args)) {
					sendMessage(e, ":x: Format your arguments correctly.");
					return;
				}
				Log.writeToLog(CardList.getCard(GrainSales.card).getName()+":");
				CardList.getCard(GrainSales.card).onEvent(0, args);
			}
			if (GrainSales.status=='o'||GrainSales.status=='l') {
				boolean result = GameData.ops.ops(args);
				
				if (!result) {
					return;
				}
			}
			if (GrainSales.status=='s') {
				GameData.ops.space();
			}
			if (GrainSales.status=='l') {
				GrainSales.status='e';
				GameData.dec = new Decision(1, 671);
				return;
			}
			if (GrainSales.status=='f') {
				GameData.dec = new Decision(0, 671);
				GrainSales.status='o';
				return;
			}
		}
		if (event==85) {
			if (!CardList.getCard(StarWars.target).isFormatted(0, args)) {
				sendMessage(e, ":x: Format your arguments correctly.");
				return;
			}
			else {
				Log.writeToLog(CardList.getCard(StarWars.target).getName()+":");
				CardList.getCard(StarWars.target).onEvent(0, args);
			}
			if (HandManager.effectActive(59)&&((StarWars.target==13&&!HandManager.effectActive(65))||StarWars.target==11||StarWars.target==24||StarWars.target==36||StarWars.target==102)) {
				Log.writeToLog("Flower Power:");
				CardEmbedBuilder builder = new CardEmbedBuilder();
				builder.setTitle("Flower Power")
					.setDescription("Anti-war protests erupt against the " + CardList.getCard(StarWars.target).getName() + "!")
					.setFooter("\"I think that we're up against the strongest, well-trained, "
							+ "militant, revolutionary group that has ever assembled in America.\" \n"
							+ "- Jim Rhodes, 1970", Launcher.url("people/rhodes.png"))
					.setColor(Color.red);
				builder.changeVP(-2);
				GameData.txtchnl.sendMessage(builder.build()).complete();
			}
		}
		if (event==89) {
			boolean result = GameData.ops.ops(args);
			if (!result) {
				return;
			}
		}
		if (event==90) {
			boolean result = GameData.ops.ops(args);
			if (!result) {
				return;
			}
		}
		if (event==95) {
			if (args[1].equals("aid")) {
				if (args.length < 3) {
					sendMessage(e, ":x: Allocate some resources to that relief package.");
					return;
				}
				int card;
				try {
					card = Integer.parseInt(args[2]);
				}
				catch (NumberFormatException err) {
					sendMessage(e, ":x: Now how is that a card!?");
					return;
				}
				if (CardList.getCard(card).getOpsMod(0)<3) {
					sendMessage(e, ":x: This won't be enough.");
					return;
				}
				if (!HandManager.discard(0, card)) {
					sendMessage(e, ":x: We don't have that. At least not at our disposal.");
					return;
				}
				Log.writeToLog("US discards " + CardList.getCard(card).getName());
				CardEmbedBuilder builder = new CardEmbedBuilder();
				builder.setTitle("Latin American Debt Crisis")
					.setDescription("IMF Intervention turns Latin America to free-market capitalism")
					.setColor(Color.blue);
				builder.addField("Debt crisis relieved.", "Discarded card: "+CardList.getCard(card), false);
				GameData.txtchnl.sendMessage(builder.build()).complete();
			}
			else if (args[1].equals("concede")) {
				Log.writeToLog("US discards nothing.");
				GameData.dec = new Decision(1, 951);
				GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", the US has failed to address the debt crisis. Select two countries in South America to double your influence in.").complete();
			}
			else {
				sendMessage(e, ":x: That's not an option.");
				return;
			}
		}
		if (event==951) {
			ArrayList<Integer> doable = new ArrayList<Integer>();
			ArrayList<Integer> order = new ArrayList<Integer>();
			for (int i=74; i<84; i++) {
				if (MapManager.get(i).influence[1]>0) doable.add(i);
			}
			if (doable.size()<=2) order = doable;
			else {
				if (args.length < 3) {
					sendMessage(e, ":x: Two countries—seize that opportunity.");
					return;
				}
				int k = MapManager.find(args[1]);
				int l = MapManager.find(args[2]);
				if (k==l) {
					sendMessage(e, ":x: Duplicate.");
					return;
				}
				if (k==-1 || l==-1) {
					sendMessage(e, ":x: Non-existent country.");
					return;
				}
				if (doable.contains(k)&&doable.contains(l)) {
					order.add(k);
					order.add(l);
				}
				else {
					sendMessage(e, ":x: Invalid country.");
					return;
				}
			}
			CardEmbedBuilder builder = new CardEmbedBuilder();
			builder.setTitle("Latin American Debt Crisis")
				.setDescription("Debt forces economic restructuring in Latin American Countries")
				.setColor(Color.red);
			for (Integer i : order) {
				builder.changeInfluence(i, 1, MapManager.get(i).influence[1]);
			}
			GameData.txtchnl.sendMessage(builder.build()).complete();
		}
		if (event==96) {
			boolean result = GameData.ops.ops(args);
			if (!result) {
				return;
			}
		}
		if (event==98) {
			int card;
			try {
				card = Integer.parseInt(args[1]);
			}
			catch (NumberFormatException err) {
				sendMessage(e, ":x: Give the number of the card you wish to discard.");
				return;
			}
			if (0<card && card<=CardList.numberOfCards()) {
				if (!HandManager.discard(0, card)) {
					sendMessage(e, ":x: Ames hasn't told you of this card. Better to make use of what intelligence you have.");
					return;
				}
				Log.writeToLog("Discarded " + CardList.getCard(card).getName());
				EmbedBuilder builder = new CardEmbedBuilder().setTitle("Compromised Operations").setDescription("Discarded " + CardList.getCard(card) + " to Aldrich Ames.").setColor(Color.RED);
				GameData.txtchnl.sendMessage(builder.build()).complete();
			}
			else {
				sendMessage(e, ":x: Give the number of the card you wish to discard.");
				return;
			}
		}
		if (event==104) {
			int i = MapManager.find(args[1]);
			if (i!=-1 && (CambridgeFive.regions[MapManager.get(i).region]||(CambridgeFive.regions[4]&&i==86&&MapManager.get(i).influence[1]<3))) { //special case for CCW
				CardEmbedBuilder builder = new CardEmbedBuilder();
				builder.setTitle("Intelligence Secrets Leaked")
				.setDescription("Planned operations in " + MapManager.get(i).name + " busted early")
				.setFooter("\"The agents we sent into Albania were armed men intent on murder, sabotage and assassination ..."
						+ "They knew the risks they were running. I was serving the interests of the Soviet Union "
						+ "and those interests required that these men were defeated. "
						+ "To the extent that I helped defeat them, even if it caused their deaths, I have no regrets.\"\n"
						+ "- Kim Philby, on Operation Valuable", Launcher.url("people/philby.png"))
				.setColor(Color.RED);
				builder.changeInfluence(i, 1, 1);
				GameData.txtchnl.sendMessage(builder.build()).complete();
			}
			else {
				sendMessage(e, ":x: Philby would like to inform you that you aren't acting on his intel.");
				return;
			}
		}
		if (event==106) {
			int i = MapManager.find(args[1]);
			if (i!=1 && MapManager.get(i).influence[0]>0) {
				Log.writeToLog("NORAD: ");
				CardEmbedBuilder builder = new CardEmbedBuilder();
				builder.setTitle("Two Minutes to Midnight")
				.setDescription("Allied forces in " + MapManager.get(i).name + " put on high alert")
				.setColor(Color.BLUE);
				builder.changeInfluence(i, 0, 1);
				GameData.txtchnl.sendMessage(builder.build()).complete();
				TimeCommand.NORAD = true;
			}
			else {
				sendMessage(e, ":x: You must drop this influence in a country with US influence.");
				return;
			}
		}
		if (event==108) {
			ArrayList<Integer> order = new ArrayList<Integer>();
			CardEmbedBuilder builder = new CardEmbedBuilder();
			builder.setTitle("Our Man in " + OurManInTehran.capital[OurManInTehran.country-21])
			.setDescription("The President and his ally in the Middle East")
			.setFooter("\"We have no other nation on earth... closer to us in planning our mutual military security. "
					+ "We have no other nation with whom we have closer consultation on regional problems that concern us both. "
					+ "And there is no leader with whom I have a deeper sense of personal gratitude and personal friendship.\"\n"
					+ "- James E. Carter, 1978", Launcher.url("people/carter.png"))
			.setColor(Color.BLUE);
			for (int i = 1; i<args.length; i++) {
				try {
					int x = Integer.parseInt(args[i]);
					order.add(x);
					if (order.indexOf(x)!=order.lastIndexOf(x)) {
						sendMessage(e, ":x: No duplicates.");
						return;
					}
				}
				catch (NumberFormatException err) {
					sendMessage(e, ":x: This is not a card. This isn't even a number!");
					return;
				}
			}
			String str = "";
			if (order.size()==1 && order.get(0)==0) {
				sendMessage(e, "Roger.");
				builder.addField("", "Discarded nothing.", false);
			}
			else {
				if (OurManInTehran.cards.containsAll(order)) {
					for (Integer i : order) {
						HandManager.Discard.add(i);
						OurManInTehran.cards.remove(i);
						str += CardList.getCard(i) + "\n";
					}
				}
				else {
					sendMessage(e, ":x: This definitely didn't turn up in your sneak preview.");
					return;
				}
				builder.addField("Discarded the following:", str, false);
			}
			HandManager.Deck.addAll(OurManInTehran.cards);
			GameData.txtchnl.sendMessage(builder.build()).complete();
		}
		if (event==115) {
			boolean result = GameData.ops.ops(args);
			if (!result) {
				return;
			}
		}
		if (event==116) {
			boolean result = GameData.ops.ops(args);
			if (!result) {
				return;
			}
		}
		if (event==119) {
			boolean result = GameData.ops.ops(args);
			if (!result) {
				return;
			}
		}
		if (event==121) {
			if (args[1].equalsIgnoreCase("yes")) {
				Log.writeToLog("Nuclear Proliferation: ");
				GameData.txtchnl.sendMessage(new CardEmbedBuilder().changeDEFCON(-1).setTitle("Nuclear Proliferation").setColor(GameData.phasing()==0?Color.blue:Color.red).build()).complete();
			}
			else if (args[1].equalsIgnoreCase("no")) {
				GameData.txtchnl.sendMessage(new CardEmbedBuilder().setTitle("Nuclear Proliferation").setDescription("No change in DEFCON.").setColor(GameData.phasing()==0?Color.blue:Color.red).build()).complete();
			}
			else {
				sendMessage(e, ":x: Please respond with the options given.");
				return;
			}
			GameData.dec = null;
			TimeCommand.nukePro = true;
			TimeCommand.prompt();
			return;
		}
		if (event==128) {
			CardEmbedBuilder builder = new CardEmbedBuilder();
			builder.setTitle("Standoff in Berlin continues")
			.setDescription("")
			.setColor(Color.gray);
			boolean flag = true;
			for (int i : HandManager.USAHand) {
				if (CardList.getCard(i).getAssociation()==1) {
					flag = false;
					break;
				}
			}
			if (flag) {
				Log.writeToLog("Checkpoint Charlie: ");
				builder.addField("No Card to Discard!", "Tensions inflame further.", false);
				builder.changeDEFCON(-1);
				builder.setColor(Color.black);
			}
			else if (args.length<2) {
				sendMessage(e, ":x: You have a card to discard. :|");
				return;
			} //otherwise must say the card 
			else {
				int discard;
				try {
					discard = Integer.parseInt(args[1]);
				}
				catch (NumberFormatException err) {
					sendMessage(e, ":x: NaN");
					return;
				}
				if (discard==0) {
					sendMessage(e, ":x: Choose a card—there is one you can discard.");
				}
				if (!HandManager.USAHand.contains(discard)) {
					sendMessage(e, ":x: You do not have that card at your disposal. :|");
					return;
				}; //must be in USA hand
				if (CardList.getCard(discard).getAssociation()!=1) {
					sendMessage(e, ":x: USSR events only. ");
					return;//must be a USSR event
				}
				Log.writeToLog("Checkpoint Charlie: ");
				builder.addField("", "Discarded " + CardList.getCard(discard) + ". A replacement has been drawn.", false);
				HandManager.discard(0, discard);
				Random random = new Random();
				HandManager.USAHand.add(HandManager.Deck.remove(random.nextInt(HandManager.Deck.size())));
				if(HandManager.Deck.isEmpty()) {
					HandManager.Deck.addAll(HandManager.Discard);
					HandManager.Discard.clear();
				}
			}
			GameData.txtchnl.sendMessage(builder.build()).complete();
			
			if (HandManager.Effects.contains(42) && (GameData.phasing()==0)) {
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
					TimeCommand.trapDone = false;
					GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", you must discard a card worth two Operations points or more to Quagmire. (TS.decide [card])").complete();
				}
				else if (scoring) {
					TimeCommand.trapDone = false;
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
					TimeCommand.trapDone = false;
					GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", you must discard a card worth two Operations points or more to Bear Trap. (TS.decide [card])").complete();
				}
				else if (scoring) {
					TimeCommand.trapDone = false;
					GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", you are out of cards to discard. You must now play a scoring card. (TS.decide [card])").complete();
				}
				else {
					GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", you are out of cards to discard. This action round will be passed over.").complete();
				}
			}
			else if (HandManager.effectActive(115) && HandManager.checkScoring()<2) {
				TimeCommand.cardPlayedSkippable = false; //obligatory skip
			}
			else if (GameData.getAR()>14) { //skippable eighth action round
				TimeCommand.cardPlayedSkippable = false;
				//if (GameData.phasing()==1) GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", you have an extra action round. You may play a card or pass the turn (TS.play 0).").complete();
				//else GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", you have an extra action round. You may play a card or pass the turn (TS.play 0).").complete();
			}
			else if ((GameData.phasing()==1 && !HandManager.SUNHand.isEmpty()) || (GameData.phasing()==0 && !HandManager.USAHand.isEmpty())) { //phasing player must play a card if he isn't in a skippable eighth action round
				TimeCommand.cardPlayed = false;
				//if (GameData.phasing()==1) GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", play a card.").complete();
				//else GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", play a card.").complete();
			}
			else { //if hand is empty
				TimeCommand.cardPlayedSkippable = false;
				//if (GameData.phasing()==1) GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", you are out of cards. You may pass the turn (TS.play 0) or play the China Card if you have it.").complete();
				//else GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", you are out of cards. You may pass the turn (TS.play 0) or play the China Card if you have it.").complete();
			}
			TimeCommand.eventDone = false;
			TimeCommand.operationsDone = false;
			TimeCommand.eventRequired = false;
			TimeCommand.operationsRequired = false;
			TimeCommand.spaceRequired = false;
			TimeCommand.spaceDone = false;
			if (HandManager.effectActive(1210+GameData.phasing())) {
				GameData.txtchnl.sendMessage(new CardEmbedBuilder().changeDEFCON(2).setTitle("Nuclear Proliferation").setColor(GameData.phasing()==0?Color.blue:Color.red).build()).complete();
				HandManager.removeEffect(1210+GameData.phasing());
				HandManager.addEffect(1212);
			}
			TimeCommand.prompt();
			return;
		}
		if (event==130) {
			int discard;
			try {
				discard = Integer.parseInt(args[1]);			
			}
			catch (NumberFormatException err) {
				sendMessage(e, ":x: NaN");
				return;
			}
			if (discard==0) {
				//continue
			}
			else if (CardList.getCard(discard).getAssociation()==(sp+1)%2) {
				sendMessage(e, ":x: The event cannot be your opponent's.");
				return;
			}
			else if (CardList.getCard(discard).getOps()==0) {
				sendMessage(e, ":x: Trying to get out of scoring this region? I don't think so.");
				return;
			}

			else if (!HandManager.discard(sp, discard)) {
				sendMessage(e, ":x: This event is not in your hand.");
				return;
			}
			//flavor only
			int victor;
			if (discard==0) {
				victor=(sp+1)%2;
			}
			else if (CardList.getCard(discard).getOpsMod(sp)==2) {
				victor=-1;
			}
			else if (CardList.getCard(discard).getOpsMod(sp)>2) {
				victor=sp;
			}
			else {
				victor =(sp+1)%2;
			}
			//actual things
			CardEmbedBuilder builder = new CardEmbedBuilder();
			builder.setTitle(victor==-1?"Fischer-Spassky Match Ends in Draw":(victor==0?"Fischer Defeats Spassky":"Spassky Defeats Fischer"))
				.setDescription(victor==-1?"":("\"Match of the Century\" a victory for the "+(victor==0?"US":"USSR")))
				.setFooter("\"Let's not bother with such nonsense – I'll play the [Tartakower] Defence. What can he achieve?\" \n"
						+ "- Boris Spassky, 1972",Launcher.url("yiyo/spassky.png"))
				.setColor(victor==-1?Color.gray:(victor==0?Color.blue:Color.red));
			builder.addField(discard==0?"No discard.":"Discarded card: "+CardList.getCard(discard),"", false);
			if (discard==0) builder.changeVP((sp*2-1)*2);
			else builder.changeVP((sp*2-1)*(2-CardList.getCard(discard).getOpsMod(sp)));
			GameData.txtchnl.sendMessage(builder.build()).complete();
		}
		if (event==131) {
			if (sp==0) {
				ApolloSoyuz.usa = new ArrayList<Integer>();
				if (args.length>ApolloSoyuz.sun.size()+1) {
					sendMessage(e, ":x: You can't contribute more.");
					return; //at most this many
				}
			}
			else {
				ApolloSoyuz.sun = new ArrayList<Integer>();
				if (args.length>ApolloSoyuz.usa.size()+1) {
					sendMessage(e, ":x: You can't contribute more.");
					return; //at most this many
				}
			}
			for (int i=1; i<4; i++) {
				if (args.length<i+1) break; //if less than given, end
				int card;
				try {
					card = Integer.parseInt(args[i]);
				}
				catch (NumberFormatException err) {
					sendMessage(e, ":x: NaN");
					return;
				}
				if (CardList.getCard(card).getOps()==0) {
					sendMessage(e, ":x: Trying to get out of scoring this region? I don't think so.");
					return;
				}
				if (sp==0) {
					if (HandManager.USAHand.contains(card)) ApolloSoyuz.usa.add(card);
					else {
						sendMessage(e, ":x: Use cards that you actually have.");
						return;
					}
				}
				else {
					if (HandManager.SUNHand.contains(card)) ApolloSoyuz.sun.add(card);
					else {
						sendMessage(e, ":x: Use cards that you actually have.");
						return;
					}
				}
			}
			int usops=0, suops=0;
			for (Integer i : ApolloSoyuz.usa) {
				Log.writeToLog("US Card: " + CardList.getCard(i).getName());
				HandManager.discard(0, i);
				usops += CardList.getCard(i).getOpsMod(0);
			}
			for (Integer i : ApolloSoyuz.sun) {
				Log.writeToLog("SU Card: " + CardList.getCard(i).getName());
				HandManager.discard(1, i);
				suops += CardList.getCard(i).getOpsMod(1);
			}
			int victor = usops==suops?-1:(usops>suops?0:1);
			CardEmbedBuilder builder = new CardEmbedBuilder();
			builder.setTitle("Joint Space Project")
				.setDescription(victor==-1?"":("Unprecedented collaboration between the superpowers advances "+(victor==0?"US":"USSR")+" technologically"))
				.setFooter("\"They know that from outer space our planet looks even more beautiful. It is big enough for us to live peacefully on it, but it is too small to be threatened by nuclear war\" \n"
						+ "- Leonid Brezhnev, 197X",Launcher.url("people/brezhnev.png"))
				.setColor(victor==-1?Color.gray:(victor==0?Color.blue:Color.red));
			builder.addField(victor==0?"**Apollo**":"Apollo","Discarded cards: " + ApolloSoyuz.usa, false);
			builder.addField(victor==1?"**Soyuz**":"Soyuz","Discarded cards: " + ApolloSoyuz.sun, false);
			if (victor!=-1) {
				builder.addField("Test Project", Operations.getSpaceNames(GameData.getSpace(victor), victor),false);
				if (GameData.aheadInSpace()==(victor+1)%2) {
					builder.changeVP(-(victor*2-1)*Operations.spaceVP2[GameData.getSpace(victor)]);
				}
				else builder.changeVP(-(victor*2-1)*Operations.spaceVP[GameData.getSpace(victor)]);
				GameData.addSpace(victor);
			}
			//f*ck the regular dealing method
			for (int i=0; i<ApolloSoyuz.usa.size(); i++) {
				HandManager.USAHand.add(HandManager.Deck.remove(new Random().nextInt(HandManager.Deck.size())));
				if(HandManager.Deck.isEmpty()) {
					HandManager.Deck.addAll(HandManager.Discard);
					HandManager.Discard.clear();
				}
			}
			for (int i=0; i<ApolloSoyuz.sun.size(); i++) {
				HandManager.SUNHand.add(HandManager.Deck.remove(new Random().nextInt(HandManager.Deck.size())));
				if(HandManager.Deck.isEmpty()) {
					HandManager.Deck.addAll(HandManager.Discard);
					HandManager.Discard.clear();
				}
			}
			GameData.txtchnl.sendMessage(builder.build()).complete();
		}
		if (event==135) { //what a pain to code
			if (args[1].equalsIgnoreCase("flip")) {
				Log.writeToLog("The Revolution That Surprised The World:");
				HandManager.removeEffect(1350);
				HandManager.removeEffect(1351);
				GameData.txtchnl.sendMessage(new EmbedBuilder().setTitle("Marcos leaves the Philippines").setDescription("People's Power Revolution succeeds in ousting dictator").setFooter("\"My spirit will rise from the grave and the world shall know that I was right.\"\n- Ferdinand Marcos, 1989", Launcher.url("yiyo/marcos.png")).setColor(sp==0?Color.blue:Color.red).addField("\"The revolution that surprised the world\"", "A die result of " + GameData.diestore + " has been flipped!", false).build());
				GameData.diestore = 7-GameData.diestore;
			}
			else if (args[1].equalsIgnoreCase("accept")) {
				//nothing happens
			}
			else {
				sendMessage(e, ":x: Please respond with the options given.");
				return;
			}
			synchronized(GameData.sync) {//reeeee
				notify();
			}
			return;
		}
		if (event==136) {
			boolean result = GameData.ops.ops(args);
			if (!result) {
				return;
			}
			int newUSSR = 0;
			for (int i=64; i<74; i++) {
				if (MapManager.get(i).isControlledBy()==1) {
					newUSSR++;
				}
			}
			if (AmericasBackyard.USSRControl - newUSSR > 0) {
				CardEmbedBuilder builder = new CardEmbedBuilder();
				builder.setTitle("America's Backyard")
				.setDescription("The USSR is no longer in control of " + (AmericasBackyard.USSRControl - newUSSR) + (AmericasBackyard.USSRControl - newUSSR==1?" country.":" countries."))
				.setColor(Color.blue);
				builder.changeDEFCON(AmericasBackyard.USSRControl - newUSSR);
				GameData.txtchnl.sendMessage(builder.build()).complete();
			}
		}
		// TODO more events as enumerated above as they come
		GameData.checkScore(false, false);
		if (GameData.dec.card==event&&GameData.dec.sp==sp) GameData.dec=null; //if the decision is left as is and completes successfully, reset
		if (GameData.dec==null) {
			if (GameData.isHeadlinePhase()) {
				if (TimeCommand.hl1) TimeCommand.hl2 = true;
				else TimeCommand.hl1 = true;
				if (HandManager.precedence==0&&TimeCommand.hl2==false) {
					HandManager.activecard=HandManager.headline[1];
				}
				else if (HandManager.precedence==1) {
					HandManager.activecard=HandManager.headline[0];
				}	
				TimeCommand.prompt();
				return;
			}
			TimeCommand.eventDone = true;
			if (HandManager.playmode == 'f') {
				TimeCommand.operationsRequired = true;
				GameData.ops = new Operations(GameData.phasing(), CardList.getCard(HandManager.activecard).getOpsMod(GameData.phasing()), true, true, true, false, false);
			}
			TimeCommand.prompt();
		}
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("TS.decide","TS.decision","TS.choose");
	}

	@Override
	public String getDescription() {
		return "When prompted, supply input requested by an event.";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Decision making";
	}

	@Override
	public List<String> getUsageInstructions() {
		// TODO Auto-generated method stub
		return Arrays.asList("Usage information will be given by prompt.");
	}

}

package commands;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import events.*;
import game.Die;
import game.GameData;
import game.PlayerList;
import logging.Log;
import main.Common;
import main.Launcher;
import map.MapManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
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
		if (!TimeCommand.trapDone) {
			int x;
			if (e.getAuthor().equals(PlayerList.getDem())) {
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
			if (!HandManager.ComHand.contains(x)) {
				sendMessage(e, ":x: Don't generate cards out of thin air.");
				return;
			}
			if (CardList.getCard(x).getOps()!=0) {
				Log.writeToLog("General Strike: Discarded " + CardList.getCard(x).getName());
				HandManager.discard(1, x);
				EmbedBuilder builder = new CardEmbedBuilder().setTitle("General Strike!").setDescription("Discarded " + CardList.getCard(x));
				Die die = new Die();
				die.roll();
				builder.addField(die.toString(), (die.result + CardList.getCard(x).getOpsMod(1)>5)?"Success - Bear Trap cancelled!":"Failure", false).setColor((die.result + CardList.getCard(x).getOpsMod(1)>5)?Color.red:Color.blue);
				if (die.result + CardList.getCard(x).getOpsMod(1)>5) { //must EXCEED 5 - correct me if I'm wrong
					Log.writeToLog("Strike broken.");
					HandManager.removeEffect(5);
				}
				GameData.txtchnl.sendMessage(builder.build()).complete();
				TimeCommand.trapDone=true;
				TimeCommand.prompt();
			}
			else {
				Log.writeToLog("General Strike: Played " + CardList.getCard(x).getName());
				CardList.getCard(x).onEvent(1, new String[] {});
				HandManager.removeFromHand(1, x);
				TimeCommand.trapDone=true;
				TimeCommand.eventRequired=true; //just so the Power Struggle works without needing conditionals
			}
			return;
		}
		if (GameData.dec == null) {
			sendMessage(e, ":x: No pending decisions. Are you sure you've satisfied the conditions for that?");
			return;
		}
		int event = GameData.dec.card;
		int sp = GameData.dec.sp;
		if (e.getAuthor().equals(PlayerList.getArray().get((GameData.dec.sp+1)%2))) {
			sendMessage(e, ":x: You aren't a puppeteer. Especially not for your opponent.");
			return;
		}
		//end of turn shenanigans.
		if (event==0) {
			
		}
		/*
		 * Cards that let you have ops, mostly support checks, use id 1
		 * - 001 Legacy of Martial Law
		 * - 003 Walesa
		 * - 014 Gorby
		 * - 021 Common European Home
		 * ...
		 * 
		 * - 206 Space Ability 6
		 */
		if (event==1) { //in general, events that let you have ops route here
			boolean result = GameData.ops.ops(args);
			if (!result) {
				return;
			}
		}
		
		/*
		 * 006 - Brought in for Questioning
		 */
		if (event==6) { //brought in for questioning: = FYP
			if (!CardList.getCard(Questioning.card).isFormatted(1, args)) {
				sendMessage(e, ":x: Format your arguments correctly.");
				return;
			}
			if (!CardList.getCard(Questioning.card).isPlayable(1)) { //should never trigger but good to have
				sendMessage(e, "Oops. Forgot you can't play that card for an event. Let's rectify that.");
				HandManager.discard(0, Questioning.card);
			}
			else {
				if (CardList.getCard(Questioning.card).isRemoved()) {
					HandManager.removeFromGame(0, Questioning.card);
				}
				else {
					HandManager.discard(0, Questioning.card);
				}
				Log.writeToLog(CardList.getCard(Questioning.card).getName()+":");
				CardList.getCard(Questioning.card).onEvent(1, args);
			}
		}
		if (event==20) {
			int i;
			try {
				i = Integer.parseInt(args[1]);
			}
			catch (NumberFormatException err) {
				sendMessage(e, ":x: Card IDs are integers. I suppose you forgot that.");
				return;
			}
			if (!HandManager.DemHand.contains(i)) {
				sendMessage(e, ":x: Don't conjure cards out of thin air...");
				return;
			}
			if (CardList.getCard(i).getOps()<DeutscheMarks.maxops) {
				sendMessage(e, ":x: Don't be a trickster. There are cards with a higher OP value.");
				return;
			}
			CardEmbedBuilder builder = new CardEmbedBuilder();
			Log.writeToLog("Handed over " + CardList.getCard(i).getName());
			builder.setTitle("East German Government Ransoms Dissident")
			.setDescription("West Germany to pay in Deutsche Marks")
			.setColor(Color.red);
			builder.addField("Hard currency", "The Democrat player has given " + CardList.getCard(i) + " as ransom for an East German dissident.", false);
			GameData.txtchnl.sendMessage(builder.build()).complete();
			DeutscheMarks.card = i;
			if (CardList.getCard(i).getAssociation()==1&&CardList.getCard(i).isPlayable(1)) {
				HandManager.removeFromHand(sp, i);
			}
			else {
				HandManager.discard(sp, i);
				GameData.ops = new Operations(1, CardList.getCard(i).getOpsMod(1), true, true, false);
			}
			GameData.dec = new Decision(1, 201);
			return;
		}
		if (event==36) {
			int i;
			try {
				i = Integer.parseInt(args[1]);
			}
			catch (NumberFormatException err) {
				sendMessage(e, ":x: Card IDs are integers. I suppose you forgot that.");
				return;
			}
			if (!HandManager.Discard.contains(i)) {
				sendMessage(e, ":x: This card must be in the pile.");
				return;
			}
			if (CardList.getCard(i).getAssociation()!=0) {
				sendMessage(e, ":x: Democrat cards only.");
				return;
			}
			if (!CardList.getCard(i).isRemoved()) {
				sendMessage(e, ":x: Card cannot be recurring.");
				return;
			}
			if (!CardList.getCard(i).isPlayable(0)) { //should never trigger but good to have
				sendMessage(e, ":x: You can't even play that event!");
				return;
			}
			if (!CardList.getCard(i).isFormatted(0, Arrays.copyOfRange(args, 1, args.length))) {
				sendMessage(e, ":x: Format your arguments correctly.");
				return;
			}
			
			else {
				HandManager.Removed.add(i);
				HandManager.Discard.remove((Integer) i);
				Log.writeToLog(CardList.getCard(i).getName()+":");
				CardList.getCard(i).onEvent(0, Arrays.copyOfRange(args, 1, args.length));
			}
		}
		/*
		 * 041 Ceausescu
		 */
		if (event==41) {
			boolean result = GameData.ops.ops(args);
			if (!result) {
				return;
			}
			boolean cluj = false;
			for (int i : MapManager.get(51).adj) {
				if (MapManager.get(i).support[0]>0) {
					cluj = true;
					break;
				}
			}
			if (cluj) {
				CardEmbedBuilder builder = new CardEmbedBuilder();
				Log.writeToLog("The Democrat has influence around Cluj.");
				builder.setTitle("Incomplete Crackdown")
				.setColor(Color.blue);
				builder.changeInfluence(50, 1, -1);
				GameData.txtchnl.sendMessage(builder.build()).complete();
			}
		}
		if (event==46) {
			int c = Integer.parseInt(args[1]);
			CardEmbedBuilder builder = new CardEmbedBuilder();
			builder.setTitle("Goodbye Lenin!").setColor(Color.blue);
			if (c==46) {
				boolean result = GameData.ops.ops(Arrays.copyOfRange(args, 1, args.length));
				if (!result) return;
				builder.addField(CardList.getCard(c).getName(), "Used card for Operations.", false);
			}
			else {
				if (!GoodbyeLenin.found.contains(c)) {
					sendMessage(e, ":x: Act on what you know.");
					return;
				}
				if (!CardList.getCard(c).isFormatted(PlayerList.getArray().indexOf(e.getAuthor()), Arrays.copyOfRange(args, 1, args.length))) {
					sendMessage(e, ":x: Format your arguments correctly.");
					return;
				}
				HandManager.removeFromGame(1, c);
				Log.writeToLog(CardList.getCard(c).getName()+":");
				CardList.getCard(c).onEvent(0, Arrays.copyOfRange(args, 1, args.length));
				builder.addField(CardList.getCard(c).getName(), "Played for the event.", false);
			}
			GameData.txtchnl.sendMessage(builder.build()).complete();
		}
		if (event==54) {
			boolean result = GameData.ops.ops(args);
			if (!result) {
				return;
			}
			Common.spChannel(GameData.ps.initiative).sendMessage(Common.spRole(GameData.ps.initiative).getAsMention() + ", would you like to raise the stakes? Respond with `DF.struggle r`aise or `DF.struggle d`ecline.");
		}
		if (event==67) {
			if (!CardList.getCard(ReformerRehabilitated.target).isFormatted(sp, args)) {
				sendMessage(e, ":x: Format your arguments correctly.");
				return;
			}
			else {
				Log.writeToLog(CardList.getCard(ReformerRehabilitated.target).getName()+":");
				CardList.getCard(ReformerRehabilitated.target).onEvent(sp, args);
			}
		}
		if (event==80) {
			ArrayList<Integer>order = new ArrayList<Integer>();
			ArrayList<Integer>values = new ArrayList<Integer>();
			if (args.length%2!=1) return;
			for (int i=1; i<args.length; i+=2) {
				int c = MapManager.find(args[i]);
				if (c==-1) return;
				if (order.indexOf(c)!=-1) return; // no duplicates plox
				order.add(c);
				if (!MapManager.get(c).inRegion(7)) return; // must be Balkan
				if (MapManager.get(c).icon!=0) return; //must be worker
				try{
					values.add(Integer.parseInt(args[i+1]));
				}
				catch (NumberFormatException err){
					return; //this isn't an integer. xP
				}
			}
			int sum = 0;
			for (int i=0; i<order.size(); i++) {
				if (values.get(i)<=0) return; //no non-positive numbers please
				sum += values.get(i);
			}
			if (sum!=Nepotism.results[Nepotism.roll]) return;
			
			CardEmbedBuilder builder = new CardEmbedBuilder();
			builder
				.setTitle("Nepotism")
				.setColor(Color.red);
			builder.bulkChangeInfluence(order, 1, values);
			GameData.txtchnl.sendMessage(builder.build()).complete();
		}
		if (event==201) {
			
			if (CardList.getCard(DeutscheMarks.card).getAssociation()==1&&CardList.getCard(DeutscheMarks.card).isPlayable(1)) {
				if (!CardList.getCard(DeutscheMarks.card).isFormatted(PlayerList.getArray().indexOf(e.getAuthor()), args)) {
					sendMessage(e, ":x: Format your arguments correctly.");
					return;
				}
				if (CardList.getCard(DeutscheMarks.card).isRemoved()) {
					HandManager.Removed.add(DeutscheMarks.card);
				}
				else if (CardList.getCard(DeutscheMarks.card).getOps()!=0&&HandManager.activecard!=17) { //not scoring card, not suspended events (RTT, 
					HandManager.Discard.add(DeutscheMarks.card);
				}
				Log.writeToLog(CardList.getCard(DeutscheMarks.card).getName()+":");
				CardList.getCard(DeutscheMarks.card).onEvent(1, args);
			}
			else {
				boolean result = GameData.ops.ops(args);
				if (!result) return;
			}
		}
		/*
		 * Space Ability 3.
		 */
		if (event==203) {
			int order;
			CardEmbedBuilder builder = new CardEmbedBuilder();
			builder.setTitle("April 26 Editorial")
			.setDescription(GameData.dec.sp==0?"Foreign News Supports Protests":"People's Daily Condemns Protests")
			
			.setColor(Common.spColor(GameData.dec.sp));
			if (GameData.dec.sp==1) builder.setFooter("\"If we are tolerant of or conniving with this disturbance and let it go unchecked, a seriously chaotic state will appear.\"\n"
					+ "- *People's Daily*, April 26", Launcher.url("people/lipeng.png"));
			try {
				order = Integer.parseInt(args[1]);
			}
			catch (NumberFormatException err) {
				sendMessage(e, ":x: This is not a card. This isn't even a number!");
				return;
			}
			if (Operations.three.contains(order)) {
				if (GameData.dec.sp==0) HandManager.DemHand.add(order);
				else HandManager.ComHand.add(order);
				Operations.three.remove((Integer) order);
			}
			else {
				sendMessage(e, ":x: This definitely didn't turn up in the news.");
				return;
			}
			builder.addField("Discarded the following:", Operations.three.toString(), false);
			HandManager.Discard.addAll(Operations.three);
			GameData.txtchnl.sendMessage(builder.build()).complete();
		}
		/*
		 * Space Ability 4
		 */
		if (event==204) {
			ArrayList<Integer>doable = new ArrayList<Integer>();
			ArrayList<Integer>order = new ArrayList<Integer>();
			ArrayList<Integer>values = new ArrayList<Integer>();
			int maxInfRem = 0;
			for (int i=0; i<75; i++) {
				if (MapManager.get(i).support[Common.opp(GameData.dec.sp)]>0) {
					doable.add(i);
					maxInfRem += Math.min(MapManager.get(i).support[Common.opp(GameData.dec.sp)], 2);
				}
			}
			if (maxInfRem<=2) {
				order = doable;
				for (int i : order) {
					values.add(Math.max(-MapManager.get(i).support[Common.opp(GameData.dec.sp)], -2));
				}
			}
			else {
			if (args.length%2!=1) {
				sendMessage(e, ":x: This is not properly formatted.");
				return;
			}
			for (int i=1; i<args.length; i+=2) {
				order.add(MapManager.find(args[i]));
				try{
					values.add(Integer.parseInt(args[i+1]));
				}
				catch (NumberFormatException err){
					sendMessage(e, ":x: This is not properly formatted.");
					return;
				}
			}
			int sum = 0;
			if (!doable.containsAll(order)) {
				sendMessage(e, ":x: This is not properly formatted.");
				return;
			}
			for (int i=0; i<order.size(); i++) {
				if (values.get(i)>=0) {
					sendMessage(e, ":x: This is not properly formatted.");
					return;
				} //no non-negative numbers please
				if (values.get(i)<-2) {
					sendMessage(e, ":x: This is not properly formatted.");
					return;
				}// cannot remove >2 influence from a given country
				if (MapManager.get(order.get(i)).support[Common.opp(GameData.dec.sp)]+values.get(i)<0){
					sendMessage(e, ":x: This is not properly formatted.");
					return;
				} //don't give me negative influence values
				sum += values.get(i);
			}
			if (sum!=-2) {
				sendMessage(e, ":x: This is not properly formatted.");
				return;
			}
			}
			CardEmbedBuilder builder = new CardEmbedBuilder();
			builder
				.setTitle(GameData.dec.sp==0?"Hunger Strike Garners Foreign Sympathy":"Hunger Striking Students Arrested")
				.setColor(Common.spColor(GameData.dec.sp));
			builder.bulkChangeInfluence(order, Common.opp(GameData.dec.sp), values); //remove opponent sps
			GameData.txtchnl.sendMessage(builder.build()).complete();
		}
		if (event==205) {
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
				if (!HandManager.discard(GameData.dec.sp, card)) {
					sendMessage(e, ":x: You don't have this card.");
					return;
				}
				if (CardList.getCard(card).getOps()==0) {
					sendMessage(e, ":x: Scoring cards are not valid discards.");
					return;
				}
				EmbedBuilder builder = new CardEmbedBuilder().setTitle("T-Square Initiative").setDescription("Discarded " + CardList.getCard(card));
				GameData.txtchnl.sendMessage(builder.build()).complete();
				TimeCommand.isCardDiscarded=true;
				Log.writeToLog("Used T-Square Ability 5 to discard " + CardList.getCard(card).getName());
				//draw replacement
				Random random = new Random();
				HandManager.DemHand.add(HandManager.Deck.remove(random.nextInt(HandManager.Deck.size())));
				if(HandManager.Deck.isEmpty()) {
					HandManager.Deck.addAll(HandManager.Discard);
					HandManager.Discard.clear();
				}
			}
			else {
				sendMessage(e, ":x: Give the number of the card you wish to discard.");
				return;
			}
			if (HandManager.Effects.contains(5) && (GameData.phasing()==1)) {
				TimeCommand.trapDone = false;
			}
			else if (GameData.getAR()>14) { //skippable eighth action round from Honecker
				TimeCommand.cardPlayedSkippable = false;
				//if (GameData.phasing()==1) GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", you have an extra action round. You may play a card or pass the turn (TS.play 0).").complete();
				//else GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", you have an extra action round. You may play a card or pass the turn (TS.play 0).").complete();
			}
			else if ((GameData.phasing()==1 && !HandManager.ComHand.isEmpty()) || (GameData.phasing()==0 && !HandManager.DemHand.isEmpty())) { //phasing player must play a card if he isn't in a skippable eighth action round
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
			TimeCommand.prompt();
		}
		if (event==440) {
			ArrayList<Integer> doable = new ArrayList<Integer>();
			ArrayList<Integer> order = new ArrayList<Integer>();
			ArrayList<Integer> values = new ArrayList<Integer>();
			int maxInfRem = 0;
			for (int i=Common.bracket[InflationaryCurrency.target]; i<Common.bracket[InflationaryCurrency.target+1]; i++) {
				if (MapManager.get(i).support[Common.opp(sp)]>0) {
					doable.add(i);
					maxInfRem += MapManager.get(i).support[Common.opp(sp)];
				}
			}
			if (maxInfRem<=2) {
				order = doable;
				for (int i : order) {
					values.add(-MapManager.get(i).support[Common.opp(sp)]);
				}
			}
			else {
			if (args.length%2!=1) return; //each country must associate with a number
			for (int i=1; i<args.length; i+=2) {
				int c = MapManager.find(args[i]);
				if (c==-1) return;
				if (order.indexOf(c)!=-1) return; // no duplicates plox
				order.add(c);
				try{
					values.add(Integer.parseInt(args[i+1]));
				}
				catch (NumberFormatException err){
					return; //this isn't an integer. xP
				}
			}
			int sum = 0;
			if (!doable.containsAll(order)) return;
			for (int i=0; i<order.size(); i++) {
				if (values.get(i)>=0) return; //no non-negative numbers please
				if (values.get(i)<-2) return; // cannot remove >2 influence from a given country
				if (MapManager.get(order.get(i)).support[Common.opp(sp)]+values.get(i)<0) return; //don't give me negative influence values
				sum += values.get(i);
			}
			if (sum!=-2) return; // up to 2 influence may be removed...
			}
			boolean opponentInfluence=false;
			CardEmbedBuilder builder = new CardEmbedBuilder();
			builder
			.setTitle("Skyrocketing Prices in " + Common.countries[InflationaryCurrency.target])
			.setDescription("Inflation rocks the " + InflationaryCurrency.currencies[InflationaryCurrency.target])
			.setColor(Common.spColor(sp))
			.setFooter("GMTBot is nothing more than a game, and these infoboxes do not represent real life happenings. GMTBot may not be held responsible for any financial irresponsibility caused by the misinterpretation of game events as real life news.", null);
			builder.bulkChangeInfluence(order, Common.opp(sp), values); //remove opponent sps
			GameData.txtchnl.sendMessage(builder.build()).complete();
			
			for (int i=Common.bracket[InflationaryCurrency.target]; i<Common.bracket[InflationaryCurrency.target+1]; i++) {
				if (MapManager.get(i).support[Common.opp(sp)]>0) {
					opponentInfluence=true;
					break;
				}
			}
			if(opponentInfluence) {
				GameData.dec=new Decision(Common.opp(sp), 441);
				Common.spChannel(Common.opp(sp)).sendMessage(Common.spRole(Common.opp(sp)).getAsMention()+", "+Common.countries[InflationaryCurrency.target]+" is suffering from inflation. Remedy by discarding a 3 Ops card, or respond with `TS.decide 0` to discard nothing and allow your opponent to conduct a support check.").complete();
			}
			else {
				Common.spChannel(sp).sendMessage("For the oddest reason, you cannot support check in your target region.").complete();
			}
		}
		if (event==441) {
			int card;
			try {
				card = Integer.parseInt(args[1]);
			}
			catch (NumberFormatException err) {
				sendMessage(e, ":x: Give the number of the card you wish to discard, or 0 if you do not wish to do so.");
				return;
			}
			if (card==0) {
				EmbedBuilder builder = new CardEmbedBuilder()
						.setTitle(Common.players[sp]+"s Fail to Address Inflation Crisis")
						.setFooter("GMTBot is nothing more than a game, and these infoboxes do not represent real life happenings. GMTBot may not be held responsible for any financial irresponsibility caused by the misinterpretation of game events as real life news.", null)
						.setColor(Common.spColor(Common.opp(sp)));
				GameData.dec=new Decision(Common.opp(sp), 1);
				GameData.ops=new Operations(Common.opp(sp), CardList.getCard(44).getOpsMod(Common.opp(sp)),false,true,false,1,InflationaryCurrency.target);
				GameData.txtchnl.sendMessage(builder.build()).complete();
			}
			else if (card<=CardList.numberOfCards()) {
				if (CardList.getCard(card).getOpsMod(sp)<3) {
					sendMessage(e, ":x: That won't be enough.");
					return;
				}
				if (!HandManager.discard(sp, card)) {
					sendMessage(e, ":x: We don't have that. At least not at our disposal.");
					return;
				}
				Log.writeToLog(Common.players[sp].substring(0, 3) + " discards "+CardList.getCard(card).getName()+".");

				EmbedBuilder builder = new CardEmbedBuilder()
						.setTitle(Common.players[sp]+"s Fight Inflation Crisis")
						.setDescription("Discarded " + CardList.getCard(card))
						.setFooter("GMTBot is nothing more than a game, and these infoboxes do not represent real life happenings. GMTBot may not be held responsible for any financial irresponsibility caused by the misinterpretation of game events as real life news.", null)
						.setColor(Common.spColor(sp));
				GameData.txtchnl.sendMessage(builder.build()).complete();
			}
			else {
				sendMessage(e, ":x: Give the number of the card you wish to discard.");
				return;
			}
		}
		// TODO more events as enumerated above as they come
		GameData.checkScore(false, false);

		if (GameData.dec.card==event&&GameData.dec.sp==sp) GameData.dec=null; //if the decision is left as is and completes successfully, reset
		if (GameData.dec==null) {
			TimeCommand.eventDone = true;
			if (HandManager.playmode == 'f') {
				TimeCommand.operationsRequired = true;
				GameData.ops = new Operations(GameData.phasing(), CardList.getCard(HandManager.activecard).getOpsMod(GameData.phasing()), true, true, false);
			}
			TimeCommand.prompt();
		}
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("DF.decide","DF.decision","DF.choose");
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

package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import game.GameData;
import logging.Log;
import main.Launcher;
import net.dv8tion.jda.api.EmbedBuilder;
/**
 * The UN Intervention Card.
 * @author adalbert
 *
 */
public class UNIntervention extends Card {
	
	public static int target;

	@Override
	public void onEvent(int sp, String[] args) {
		if (HandManager.effectActive(50)&&sp==0) {
			CardEmbedBuilder builder = new CardEmbedBuilder();
			builder.setTitle("We Will Bury You...")
					.setDescription("UN INTERVENTION!")
					.setColor(Color.blue)
					.setFooter("\"We are happy with our way of life. "
							+ "We recognize its shortcomings and are always trying to improve it. "
							+ "But if challenged, we shall fight to the death to preserve it.\"\n"
							+ "- Norris Poulson, 1959", Launcher.url("people/poulson.png"));
			Log.writeToLog("We Will Bury You Cancelled.");
			HandManager.removeEffect(50);
			GameData.txtchnl.sendMessage(builder.build()).complete();
		}
		if (HandManager.effectActive(60)) {
			CardEmbedBuilder builder = new CardEmbedBuilder();
			builder.setTitle("Khrushchev Embarasses CIA")
				.setDescription("Downed pilot revealed to be alive")
				.setFooter("\"I must tell you a secret. When I made my first report "
						+ "I deliberately did not say that the pilot was alive and well ... "
						+ "and now just look how many silly things the Americans have said.\"\n" + 
						"- Nikita Khrushchev, 1960",Launcher.url("people/khrushchev.png"))
				.setColor(Color.red);
			Log.writeToLog("U-2 Incident: ");
			builder.changeVP(-1);
			HandManager.removeEffect(60);
			GameData.txtchnl.sendMessage(builder.build()).complete();
		}
		EmbedBuilder builder = new CardEmbedBuilder().setTitle("UN INTERVENTION!")
				.setDescription("The UN collectively agrees on something for once")
				.setFooter("\"It is not the Soviet Union or indeed any other big Powers who need the United Nations for their protection. "
						+ "It is all the others.\"\n"
						+ "- Dag Hammarskjöld, 1960", Launcher.url("people/hammarskjold.png"))
				.setColor(sp==0?Color.blue:Color.red)
				.addField("UN Security Council Resolution " + getNumber(), "The event of "+ CardList.getCard(target)+" has been condemned by the UN, and will not occur.", false);
		GameData.txtchnl.sendMessage(builder.build()).complete();
		HandManager.discard(sp, target);
		Log.writeToLog("Event of " + CardList.getCard(target).getName() + " is cancelled.");
		GameData.dec = new Decision(sp, 32);
		GameData.ops = new Operations (sp, CardList.getCard(target).getOpsMod(sp), true, true, true, false, false);
	}

	@Override
	public boolean isPlayable(int sp) {
		if (sp==0) {
			for (Integer i : HandManager.USAHand) {
				if (CardList.getCard(i).getAssociation()==1) return true;
			}
		}
		else {
			for (Integer i : HandManager.SUNHand) {
				if (CardList.getCard(i).getAssociation()==0) return true;
			}
		}
		return false;
	}

	@Override
	public String getId() {
		return "032";
	}

	@Override
	public String getName() {
		return "UN Intervention";
	}

	@Override
	public int getOps() {
		return 1;
	}

	@Override
	public int getEra() {
		return 0;
	}

	@Override
	public int getAssociation() {
		return 2;
	}

	@Override
	public boolean isRemoved() {
		return false;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		if (args.length<2) return false;
		try {
			target = Integer.parseInt(args[1]);
		}
		catch (NumberFormatException e) {
			return false;
		}
		return HandManager.handContains(sp, target) && CardList.getCard(target).isPlayable((sp+1)%2) && CardList.getCard(target).getAssociation()==(sp+1)%2;
	}

	@Override
	public String getDescription() {
		return (HandManager.effectActive(50)?"The USSR is being a bit... threatening. The USSR gets 3 VPs if this isn't played on this Action Round.":"") + (HandManager.effectActive(60)?"Something has happened to that U2. Playing this gives the USSR 1 VP.\n":"") + "Play with an event associated with your opponent; use the operations of your opponent's card without triggering the event. *May not be played as a headline*.";
	}

	@Override
	public String getArguments() {
		return "Event: The card to nullify. It must be in your hand and contain your opponent's event."
				+ "\nDecision: Operations. Must be actual operations.";
	}

	private int getNumber() {
		return 70*(GameData.getTurn()-1) + (int) (Math.random()*70) + 1;
	}
}

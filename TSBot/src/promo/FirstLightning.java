package promo;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import events.Card;
import events.CardEmbedBuilder;
import events.Decision;
import game.GameData;
import main.Launcher;
import net.dv8tion.jda.core.EmbedBuilder;

public class FirstLightning extends Card {

	public static int target;
	@Override
	public void onEvent(int sp, String[] args) {
		EmbedBuilder builder = new CardEmbedBuilder().setTitle("Soviets Test Their First Atomic Bomb")
				.setDescription("Success of Operation \"First Lightning\" shocks Western powers")
				.setFooter("\"...the results will be so overriding [that] it won't be necessary to determine who is to blame for the fact that this work has been neglected in our country.\"\n"
						+ "- Georgiy Nikolayevich Flyorov, 1942", Launcher.url("promo/flyorov.png"))
				.setColor(Color.red)
				.addField("Broken Nuclear Monopoly", "The event of "+ CardList.getCard(target)+" has been condemned by the UN, and will not occur.", false);
		GameData.txtchnl.sendMessage(builder.build()).complete();
		HandManager.discard(1, target);
		GameData.dec = new Decision(1, 116);
		GameData.ops = new Operations (1, CardList.getCard(target).getOpsMod(1), true, true, true, false, false);
	}

	@Override
	public boolean isPlayable(int sp) {
		for (Integer i : HandManager.SUNHand) {
			if (CardList.getCard(i).getAssociation()==0) return true;
		}
		return false;
	}

	@Override
	public String getId() {
		return "116";
	}

	@Override
	public String getName() {
		return "First Lightning";
	}

	@Override
	public int getOps() {
		return 2;
	}

	@Override
	public int getEra() {
		return 0;
	}

	@Override
	public int getAssociation() {
		return 1;
	}

	@Override
	public boolean isRemoved() {
		return true;
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
		return HandManager.handContains(sp, target) && CardList.getCard(target).isPlayable((sp+1)%2) && CardList.getCard(target).getAssociation()==0;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Cancels the event text of a US-affiliated event played at the same time. That event goes into the discard pile, and **the USSR may use that card's Operations Points**. "
				+ "**If this card is played for either the event or the operations (or both), DEFCON decreases by 1 level.**";
	}

	@Override
	public String getArguments() {
		return "The US-affiliated card to cancel the event text of.";
	}

}
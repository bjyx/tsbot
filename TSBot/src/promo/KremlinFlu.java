package promo;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import events.Card;
import events.CardEmbedBuilder;
import events.Decision;
import game.GameData;
//import main.Launcher;

public class KremlinFlu extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Sudden Change in Soviet Leadership")
			.setDescription("Noted political figure not present at annual May Day parade")
			//.setFooter("\"\"\n"
			//		+ "- ",Launcher.url("people/cia.png"))
			.setColor(Color.blue);
		GameData.txtusa.sendMessage(HandManager.getSUNHand()).complete();
		GameData.dec = new Decision(0, 115);
		GameData.ops = new Operations(0, CardList.getCard(115).getOpsMod(0), true, true, true, false, false);
		builder.addField("Kremlin Flu", "The USSR must play a scoring card on the next action round or skip said action round.", false);
		HandManager.addEffect(115);
		GameData.txtchnl.sendMessage(builder.build()).complete();
		GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", you may now conduct operations.").complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "115";
	}

	@Override
	public String getName() {
		return "Kremlin Flu";
	}

	@Override
	public int getOps() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getEra() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAssociation() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isRemoved() {
		return false;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		return "**The US may conduct operations using this card's operations points.** On the next turn, the USSR must play a scoring card; if the USSR doesn't hold one, they forfeit their turn. Tough luck.";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "Event: None.\n"
				+ "Decision (US): Operations.\n";
	}

}

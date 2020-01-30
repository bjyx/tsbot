package yiyo;

import events.Card;
import events.Decision;
import game.GameData;

public class MatchOfTheCentury extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		GameData.dec = new Decision((sp+1)%2, 130);
		if (sp==1) GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", it's you versus Boris Spassky. You may choose to discard a card to gain VP equal to the number of ops on that card or ignore it.").complete();
		if (sp==0) GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", it's you versus Bobby Fischer. You may choose to discard a card to gain VP equal to the number of ops on that card or ignore it.").complete();
}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "130";
	}

	@Override
	public String getName() {
		return "Match of the Century";
	}

	@Override
	public int getOps() {
		return 2;
	}

	@Override
	public int getEra() {
		return 1;
	}

	@Override
	public int getAssociation() {
		return 2;
	}

	@Override
	public boolean isRemoved() {
		return true;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Gain 2 VP. Your opponent may discard a non-scoring card not affiliated with you to gain VP equal to the number of Operations on that card.";
	}

	@Override
	public String getArguments() {
		return "Event: None.\n"
				+ "Decision: The card to discard, or `0` to not discard anything. This cannot be affiliated with the person who played this card.";
	}

}

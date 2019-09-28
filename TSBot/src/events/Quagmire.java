package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import main.Launcher;

public class Quagmire extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("US Bogged Down in Vietnam")
			.setDescription("War drags into stalemate as Tet Offensive dies down")
			.setColor(Color.red)
			.setFooter("\"To say that we are closer to victory today is to believe, in the face of the evidence, "
					+ "the optimists who have been wrong in the past. To suggest we are on the edge of defeat is to yield to unreasonable pessimism. "
					+ "To say that we are mired in stalemate seems the only realistic, yet unsatisfactory, conclusion.\"\n"
					+ "- Walter Cronkite, 1968", Launcher.url("countries/us.png"));
		builder.addField("Ho Chi Minh Trail","The US must discard a card worth at least 2 Ops every action round and roll 1-4 to cancel this effect."
				+(HandManager.removeEffect(106)?"\nThe effects of " + CardList.getCard(106) + " are cancelled.":""),false);
		HandManager.addEffect(42);
		GameData.txtchnl.sendMessage(builder.build());
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "042";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Quagmire";
	}

	@Override
	public int getOps() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public int getEra() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getAssociation() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean isRemoved() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isFormatted(String[] args) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Cancels (but does not prevent) NORAD.\n"
				+ "*The US must discard a card worth at least two OPs every action round and roll a die in lieu of performing an action. If and only if said roll is between 1 and 4, this effect is cancelled. If no cards are available to discard, the US must play a scoring card for the event instead; if the US has no scoring cards, they must skip all action rounds for the rest of the turn.*";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "Event: None.\n"
				+ "Decision: the ID of the card to be discarded/played.";
	}

}

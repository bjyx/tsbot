package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import main.Launcher;

public class StarWars extends Card {

	public static int target;
	
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Reagan announces Strategic Defense Initiative")
			.setDescription("")
			.setFooter("\"It will take years, probably decades of effort on many fronts. "
					+ "There will be failures and setbacks, just as there will be successes and breakthroughs. "
					+ "And as we proceed, we must remain constant in preserving the nuclear deterrent and maintaining a solid capability for flexible response. "
					+ "But isn't it worth every investment necessary to free the world from the threat of nuclear war? We know it is.\"\n"
					+ "- Ronald Reagan, 1983", Launcher.url("people/reagan.png"))
			.setColor(Color.BLUE);
		builder.addField("Brilliant Pebbles", "The USA retrieves " + CardList.getCard(target) + " to play as an event.", false);
		HandManager.getFromDiscard(0, target);
		if (CardList.getCard(target).isRemoved()) HandManager.removeFromGame(0, target);
		else HandManager.discard(0, target);
		GameData.dec = new Decision(CardList.getCard(target).getAssociation()==1?1:0, 85);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return GameData.aheadInSpace()==0;
	}

	@Override
	public String getId() {
		return "085";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Star Wars";
	}

	@Override
	public int getOps() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getEra() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getAssociation() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isRemoved() {
		// TODO Auto-generated method stub
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
		return HandManager.Discard.contains(target) && CardList.getCard(target).isPlayable(0) && CardList.getCard(target).getOps()!=0;
	}

	@Override
	public String getDescription() {
		return "Retrieve any card from the discard pile to play for the event. *Only playable if the US is ahead on the Space Track.*";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "Event: None."
				+ "Decison: Play the event.";
	}

}

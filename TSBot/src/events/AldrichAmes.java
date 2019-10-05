package events;

import java.awt.Color;

import cards.HandManager;
import game.GameData;
import main.Launcher;

public class AldrichAmes extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("CIA Operations compromised")
			.setDescription("Headquarters possibly bugged, CIA says")
			.setFooter("\"He didn't do this for money. He insisted on staying in place to help us.\" \n"
					+ "- A CIA Operative, regarding Dmitri Polyakov",Launcher.url("people/cia.png"))
			.setColor(Color.red);
		GameData.txtssr.sendMessage(HandManager.getUSAHand()).complete();
		builder.addField("Aldrich Ames", "The USSR may now look at the USA's hand. __The USSR may query this hand for the rest of the turn by using `TS.info ames`.__", false);
		GameData.txtchnl.sendMessage(builder.build()).complete();
		if (!HandManager.USAHand.isEmpty()) {
			HandManager.addEffect(98);
			GameData.dec = new Decision(1, 98);
			GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", select a card to remove from the USA's hand.").complete();
		}
		
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "098";
	}

	@Override
	public String getName() {
		return "Aldrich Ames";
	}

	@Override
	public int getOps() {
		return 3;
	}

	@Override
	public int getEra() {
		return 2;
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
		return true;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "The USSR is allowed to see the US's hand for the rest of this turn. The USSR may also remove one card from said hand.";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "Event: None."
				+ "Decision: the ID of the card you want to remove. It must be present in the USA's hand.";
	}

}

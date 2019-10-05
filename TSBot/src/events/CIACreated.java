package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import game.GameData;
import main.Launcher;

public class CIACreated extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("CIA Created")
			.setDescription("The Work of a Nation, the Center of Intelligence")
			.setFooter("\"We had constructed for ourselves a picture of the USSR, "
					+ "and whatever happened had to be made to fit into this picture. "
					+ "Intelligence estimators can hardly commit a more abominable sin.\" \n"
					+ "- Abbot Smith, regarding CIA Operations in Indonesia, 1958",Launcher.url("people/smith.png"))
			.setColor(Color.blue);
		GameData.txtusa.sendMessage(HandManager.getSUNHand()).complete();
		GameData.dec = new Decision(0, 26);
		GameData.ops = new Operations(0, CardList.getCard(26).getOpsMod(0), true, true, true, false, false);
		builder.addField("National Security Act", "The USA may now look at the USSR's hand.", false);
		GameData.txtchnl.sendMessage(builder.build()).complete();
		GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", you may now conduct operations.").complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "026";
	}

	@Override
	public String getName() {
		return "CIA Created";
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
		return 0;
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
		return "Allows the USA to see the Soviet Hand. The USA may then **conduct Operations using the value of this card.**";
	}

	@Override
	public String getArguments() {
		return "Event: None."
				+ "Decision: Operations.";
	}

}

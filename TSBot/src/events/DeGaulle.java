package events;

import java.awt.Color;

import cards.HandManager;
import game.GameData;
import main.Launcher;

public class DeGaulle extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("")
			.setDescription("")
			.setColor(Color.red)
			.setFooter("\"\"\n"
					+ "- Charles de Gaulle, 19XX", Launcher.url("countries/fr.png"));
		builder.changeInfluence(8, 0, -2); //remove 2 American influence in France
		builder.changeInfluence(8, 1, 1); //add 1 USSR influence in France
		builder.addField("France leaves NATO","France can now be couped/realigned by the Soviet Union if under US control.",false);
		HandManager.addEffect(17);
		GameData.txtchnl.sendMessage(builder.build()).complete();

	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "017";
	}

	@Override
	public String getName() {
		return "De Gaulle Leads France";
	}

	@Override
	public int getOps() {
		return 3;
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
	public boolean isFormatted(String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Remove 2 US Influence from France. Add 1 USSR Influence to France. *Cancels the effects of NATO for France only.*";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

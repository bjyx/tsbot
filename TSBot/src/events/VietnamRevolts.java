package events;

import java.awt.Color;

import cards.HandManager;
import game.GameData;

public class VietnamRevolts extends Card {

	@Override
	public void onEvent(String[] args) {
		// TODO Auto-generated method stub
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Dien Bien Phu falls")
			.setDescription("Negotiations now underway in Geneva to decide fate of French Indochina")
			.setFooter("\"The enemy has overrun us. We are blowing up everything. *Vive la France!*\"\n"
					+ "- Unknown radio operator, 1954", "images/countries/fr.png")
			.setColor(Color.red);
		builder.changeInfluence(45, 1, 2); //Vietnam 0/+2
		builder.addField("Viet Minh", "All Soviet Operations exclusively used in Southeast Asia will now have one extra point to use.", false);
		HandManager.addEffect(9);
		GameData.txtchnl.sendMessage(builder.build());
	}

	@Override
	public boolean isPlayable() {
		return true;
	}

	@Override
	public String getId() {
		return "009";
	}

	@Override
	public String getName() {
		return "Vietnam Revolts";
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
	public boolean isFormatted(String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Adds 2 USSR Influence to Vietnam. *For the rest of the turn, the USSR gets +1 Operations Point to any card that uses all of its operations in Southeast Asia.*";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

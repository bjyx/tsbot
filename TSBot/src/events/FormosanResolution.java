package events;

import java.awt.Color;

import cards.HandManager;
import game.GameData;
import main.Launcher;

public class FormosanResolution extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		// TODO Auto-generated method stub
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Congress passes Formosa Resolution")
			.setDescription("US committed to defend the Republic of China from communism")
			.setFooter("\"\"\n"
					+ "- XXXX, 19XX", Launcher.url("countries/XX.png"))
			.setColor(Color.BLUE);
		builder.addField("Mutual Defense Treaty", "Taiwan will count as a battleground for scoring purposes so long as the US controls it.", false);
		HandManager.addEffect(35);
		GameData.txtchnl.sendMessage(builder.build());
	}

	@Override
	public boolean isPlayable(int sp) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return "035";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Formosa Resolution";
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
		return "Taiwan will count as a battleground for scoring purposes only so long as the US controls it. *Cancelled by a US play of `006 China Card`.*";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "None.";
	}

}

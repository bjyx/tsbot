package events;

import java.awt.Color;

import cards.HandManager;
import game.GameData;
import logging.Log;
import main.Launcher;
/**
 * The Formosa Resolution Card.
 * @author adalbert
 *
 */
public class FormosanResolution extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		// TODO Auto-generated method stub
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Congress passes Formosa Resolution")
			.setDescription("US committed to defend the Republic of China from communism")
			.setFooter("\"As long as we have Taiwan, the Communists can never win.\"\n"
					+ "- Chiang Kai-Shek", Launcher.url("people/chiang.png"))
			.setColor(Color.BLUE);
		builder.addField("Mutual Defense Treaty", "Taiwan will count as a battleground for scoring purposes only so long as the US controls it.", false);
		HandManager.addEffect(35);
		Log.writeToLog("Formosa Resolution Active.");
		GameData.txtchnl.sendMessage(builder.build()).complete();
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
	public boolean isFormatted(int sp, String[] args) {
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

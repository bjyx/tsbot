package events;

import java.awt.Color;

import cards.HandManager;
import game.GameData;
import logging.Log;
import main.Launcher;
/**
 * The Prudence Card.
 * @author adalbert
 *
 */
public class Prudence extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		if (sp==0) {
			builder.setTitle("Shevardnadze Urges Caution") //rnged a historical figure
			.setDescription("")
			.setColor(Color.blue)
			.setFooter("\"It is time to realize that neither socialism, nor friendship, nor good-neighborliness, nor respect can be produced by bayonets, tanks or blood.\"\n"
					+ "- Eduard Shevardnadze", Launcher.url("people/shevardnadze.png"));
			builder.addField("Prudence","The Communist subtracts 1 Operations point from any card played for operations for the rest of this turn.",false);
			HandManager.addEffect(81);
			Log.writeToLog("Prudence Active - favors Dem.");
		}
		else {
			builder.setTitle("Bush Urges Caution")
			.setDescription("")
			.setColor(Color.red)
			.setFooter("\"I’m certainly not seen as visionary... But I hope I’m seen as steady and prudent and able.\"\n"
					+ "- George H. W. Bush", Launcher.url("people/bush.png"));
			builder.addField("Prudence","The Democrat subtracts 1 Operations point from any card played for operations for the rest of this turn.",false);
			HandManager.addEffect(80);
			Log.writeToLog("Prudence Active - favors Com.");
		}
		
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "008";
	}

	@Override
	public String getName() {
		return "Prudence";
	}

	@Override
	public int getOps() {
		return 4;
	}

	@Override
	public int getEra() {
		return 0;
	}

	@Override
	public int getAssociation() {
		return 2;
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
		return "The opponent gets a malus of -1 Operation Point to all their cards used for Operations for the rest of this turn, to a minimum of 1.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

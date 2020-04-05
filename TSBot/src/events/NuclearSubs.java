package events;

import java.awt.Color;

import cards.HandManager;
import game.GameData;
import logging.Log;
import main.Launcher;
/**
 * The Nuclear Subs Card.
 * @author adalbert
 *
 */
public class NuclearSubs extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("USS Nautilus Launched")
			.setDescription("Admiral Hyman Rickover to oversee construction of nuclear navy")
			.setColor(Color.blue)
			.setFooter("\"Underway On Nuclear Power.\"\n"
					+ "- Eugene P. Wilkinson, 1955", Launcher.url("people/wilkinson.png"));
		builder.addField("Nuclear Triad","Coups performed by the US will not drop DEFCON for the rest of this turn."
				+ (HandManager.Effects.contains(400)?"\n\n**:warning: The USA is currently dealing with the Cuban Missile Crisis. Any coup conducted by the USA will still lose them the game.**":""),false);
		HandManager.addEffect(41);
		Log.writeToLog("Nuclear Submarines Active.");
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
		return "041";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Nuclear Subs";
	}

	@Override
	public int getOps() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getEra() {
		// TODO Auto-generated method stub
		return 1;
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
		return "*Coups performed by the US will not affect DEFCON for the rest of the turn.*";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "None.";
	}

}

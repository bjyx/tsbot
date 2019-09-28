package events;

import java.awt.Color;

import cards.HandManager;
import game.GameData;
import main.Launcher;

public class BrezhnevDoctrine extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Sovereignty and the International Obligations of Socialist Countries")
			.setDescription("Sergei Kovalev")
			.setColor(Color.red)
			.setFooter("\"In this outward appearance of chaos…there is a certain order. "
					+ "It all began like this in Hungary also, but then came the first and second echelons, "
					+ "and then, finally the social democrats.\"\n"
					+ "- Yuri Andropov, 1968", Launcher.url("countries/su.png"));
		builder.addField("Hard Line","The USSR adds 1 Operations point to any card played for operations. ",false);
		HandManager.addEffect(51);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "051";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Brezhnev Doctrine";
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
		return "The USSR receives one additional Operations Point on every card played by him/her for every purpose for the remainder of this turn.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

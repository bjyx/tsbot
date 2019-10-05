package events;

import java.awt.Color;

import cards.HandManager;
import game.GameData;
import main.Launcher;

public class LADS extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Latin American Death Squads")
			.setDescription("")
			.setFooter("\"If it is necessary to turn the country into a cemetery in order to pacify it, I will not hesitate to do so.\"\n"
					+ "- Carlos Manuel Arana Osorio", Launcher.url("people/arana.png"))
			.setColor(sp==0?Color.blue:Color.red);
		builder.addField("Extrajudicial Actions", "For the rest of the turn, "+(sp==0?"American":"Soviet") + " coups in Latin America get a +1 bonus, while "+(sp==0?"Soviet":"American") + " coups in Latin America get a -1 malus. ", false);
		HandManager.addEffect(690+sp);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "069"; //lol
	}

	@Override
	public String getName() {
		return "Latin American Death Squads";
	}

	@Override
	public int getOps() {
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
		return "For the rest of the turn, the player's coups in Central and South America get a +1 bonus, while the opponent's coups in those regions get a -1 malus.";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "None.";
	}

}

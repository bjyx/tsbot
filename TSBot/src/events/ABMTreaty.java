package events;

import java.awt.Color;

import cards.CardList;
import cards.Operations;
import game.GameData;
import main.Launcher;

public class ABMTreaty extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Anti-Ballistic Missile Treaty")
			.setDescription("Treaty signed in Moscow limits missile defense systems")
			.setColor(sp==0?Color.blue:Color.red)
			.setFooter("\"Modern science and technology have reached a level where "
					+ "there is the grave danger that a weapon even more terrible than nuclear weapons "
					+ "may be developed. The reason and conscience of mankind "
					+ "dictate the need to erect an insuperable barrier "
					+ "to the development of such a weapon.\"\n" + 
					"- Leonid Brezhnev, 1979", Launcher.url("countries/su.png"));
		builder.changeDEFCON(1);
		GameData.txtchnl.sendMessage(builder.build()).complete();
		GameData.ops = new Operations(sp, CardList.getCard(57).getOpsMod(sp), true, true, true, false, false);
		GameData.dec = new Decision(sp, 57);
		if (sp==0) GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", you may now use the " + CardList.getCard(57).getOpsMod(sp) + " Operations points of this card.").complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "057";
	}

	@Override
	public String getName() {
		return "ABM Treaty";
	}

	@Override
	public int getOps() {
		return 4;
	}

	@Override
	public int getEra() {
		return 1;
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
	public boolean isFormatted(String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Increase DEFCON by 1. You may then conduct operations with the point value of this card.";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "Event: None.\n"
				+ "Decision: Like Operations.";
	}

}

package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import commands.TimeCommand;
import game.GameData;
import main.Launcher;

public class Defectors extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		if (GameData.isHeadlinePhase() && GameData.phasing()==0) {
			TimeCommand.hl1=true;
			GameData.txtchnl.sendMessage(new CardEmbedBuilder()
					.setTitle("Top Profile Soviet Officer Defects")
					.setDescription("")
					.setColor(Color.blue)
					.setFooter("\"It was like a bomb on top of everything else.\"\n"
							+ "- William L. M. King, re. Igor Gouzenko, 1945", Launcher.url("people/king.png"))
					.addField("Leaked Intelligence", "The headline "+CardList.getCard(HandManager.headline[1])+" cannot be carried out this turn.", false)
					.build()).complete();
		}
		else if (!GameData.isHeadlinePhase()&&GameData.phasing()==1) {
			GameData.txtchnl.sendMessage(new CardEmbedBuilder()
					.changeVP(1)
					.setTitle("Well Known Soviet Artist Defects")
					.setDescription("")
					.setColor(Color.blue)
					.setFooter("\"It doesn't matter if every ballet is a success or not. The new experience gives me a lot.\"\n"
							+ "- Mikhail Baryshnikov, 1976", Launcher.url("people/baryshnikov.png"))
					.build()).complete();
		}
	}

	@Override
	public boolean isPlayable(int sp) {
		// TODO Auto-generated method stub
		return GameData.phasing()==1||GameData.isHeadlinePhase();
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return "103";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Defectors";
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
		return false;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "If played by the US in the headline phase, cancels the USSR's headline. If played by the USSR during an action round, the US gains 1 VP.";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "None.";
	}

}

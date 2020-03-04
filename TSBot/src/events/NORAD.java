package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import logging.Log;
import main.Launcher;

public class NORAD extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		// TODO Auto-generated method stub
		GameData.txtchnl.sendMessage(new CardEmbedBuilder()
				.addField("Aerospace Defense Command","As long as Canada is under US control, the US may add 1 Influence to any country already containing US Influence upon DEFCON dropping to 2 during an Action Round.",false)
				.setTitle("NORAD Established").setDescription("")
				.setFooter("\"See that sign up here - up here. 'DEFCON.' That indicates our current **def**ense **con**dition. "
						+ "It should read 'DEFCON 5', which means peace. It's still on 4 because of that little stunt you pulled. "
						+ "Actually, if we hadn't caught it in time, it might have gone to DEFCON 1. You know what that means, David? World War III.\"\n"
						+ "- Dr. John McKittrick, *WarGames*",Launcher.url("people/wargames1.png")).setColor(Color.BLUE).build()).complete();
		Log.writeToLog("NORAD Active.");
		HandManager.addEffect(106);
	}

	@Override
	public boolean isPlayable(int sp) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return "106";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "NORAD";
	}

	@Override
	public int getOps() {
		// TODO Auto-generated method stub
		return 3;
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
		return "While Canada is under the control of the US, the US may place 1 Influence into any country already containing US Influence upon DEFCON dropping to 2 during an Action Round. *Cancelled, but not disabled, by `"+ CardList.getCard(42) + "`.*";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "Event: None.\n"
				+ "Decision: A country you would like to drop influence into. ";
	}

}

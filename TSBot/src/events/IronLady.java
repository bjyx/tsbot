package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import logging.Log;
import main.Launcher;
import map.MapManager;

public class IronLady extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Iron Lady Raises Fears")
			.setDescription("Thatcher lambastes Soviet policy for seeking world domination... and Argentina for seeking the Falklands")
			.setColor(Color.blue)
			.setFooter("\"When the demands of war and the dangers to our own people call us to arms—then we British are as we have always been: competent, courageous and resolute.\"\n"
					+ "- Margaret Thatcher, 1983", Launcher.url("people/thatcher.png"));
		builder.changeInfluence(19, 1, -MapManager.get(19).influence[0]); //remove all USSR influence in UK
		builder.changeInfluence(74, 1, 1); //add 1 USSR Influence to Argentina
		builder.addField("Britain Awake",CardList.getCard(7) +" no longer playable for the event.",false);
		HandManager.addEffect(83);
		Log.writeToLog("Iron Lady Active.");
		builder.changeVP(1);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "083";
	}

	@Override
	public String getName() {
		return "The Iron Lady";
	}

	@Override
	public int getOps() {
		return 3;
	}

	@Override
	public int getEra() {
		return 2;
	}

	@Override
	public int getAssociation() {
		return 0;
	}

	@Override
	public boolean isRemoved() {
		return true;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Removes all USSR Influence from the UK... but adds 1 USSR Influence to Argentina. US gains 1 VP. *Disables the event for " + CardList.getCard(7) + ".*";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

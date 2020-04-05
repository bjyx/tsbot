package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import logging.Log;
import main.Launcher;
/**
 * The "An Evil Empire" Card.
 * @author adalbert
 *
 */
public class EvilEmpire extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Ronald Reagan's Rhetoric")
			.setDescription("")
			.setColor(Color.blue)
			.setFooter("\"Let us be aware that while they [the Soviet leadership] preach the supremacy of the state, "
					+ "declare its omnipotence over individual man, and predict its eventual domination of all peoples on the earth, "
					+ "they are the focus of evil in the modern world.\"\n"
					+ "- Ronald Reagan, 1983", Launcher.url("people/reagan.png"));
		builder.changeVP(1);
		builder.addField("Speech to the Evangelical Society",HandManager.removeEffect(59)?"The effects of " + CardList.getCard(59) + " are cancelled.":"The event for " + CardList.getCard(59) + " can no longer be played.",false);
		Log.writeToLog("Evil Empire Active.");
		HandManager.addEffect(97);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getId() {
		return "097";
	}

	@Override
	public String getName() {
		return "\"An Evil Empire\"";
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
		return "The US gains 1 VP. *Cancels/Prevents the effects of Flower Power.*";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "None.";
	}

}

package yiyo;

import java.awt.Color;

import cards.HandManager;
import events.Card;
import events.CardEmbedBuilder;
import game.GameData;
import logging.Log;
import main.Launcher;

public class RedAfrica extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Red Africa")
			.setDescription("Anti-colonial sentiments lead newly-independent nations into Soviet camp")
			.setColor(Color.red)
			.setFooter("\"To us, Communism is as bad as imperialism. What we want is to develop the Kenya Nationalism which helped us to win the struggle against imperialism. We do not want somebody else's nationalism.\"\n"
					+ "- Jomo Kenyatta, 1964", Launcher.url("yiyo/kenyatta.png"));
		builder.addField("Nationalism and Counter-Imperialism","Certain nations will grant VP to the USSR during final scoring if they are controlled by the USSR.",false);
		HandManager.addEffect(137);
		Log.writeToLog("Red Africa Active.");
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "137";
	}

	@Override
	public String getName() {
		return "Red Africa";
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
		return 1;
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
		return "*During Final Scoring, the USSR will gain 1 VP for each of the following countries they control:** \n"
				+ "- Botswana\n"
				+ "- Ivory Coast\n"
				+ "- Kenya\n"
				+ "- Somalia\n"
				+ "- West African States";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

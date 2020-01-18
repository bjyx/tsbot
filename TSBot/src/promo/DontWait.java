package promo;

import java.awt.Color;

import events.Card;
import events.CardEmbedBuilder;
import game.GameData;
import main.Launcher;

public class DontWait extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("\"Don't Wait for the Translation\"")
		.setDescription("Ambassador Stevenson demands answers from Soviet delegation at UN")
		.setColor(Color.blue)
		.setFooter("\"You can answer yes or no. You have denied they exist. I want to know if I understood you correctly. I am prepared to wait for my answer until hell freezes over, if that's your decision. And I am also prepared to present the evidence in this room.\"\n" + 
				"- Adlai E. Stevenson II, 1962",Launcher.url("promo/stevenson.png"));
		builder.changeVP(2);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return GameData.getMilOps(0)<GameData.getMilOps(1);
	}

	@Override
	public String getId() {
		return "118";
	}

	@Override
	public String getName() {
		return "Don't Wait for the Translation";
	}

	@Override
	public int getOps() {
		return 3;
	}

	@Override
	public int getEra() {
		return 1;
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
		return "The US gains 2 VP. *Can only be played if the US is behind the USSR in Military Operations.*";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

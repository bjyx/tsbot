package events;

import java.awt.Color;

import cards.HandManager;
import game.GameData;
import main.Launcher;

public class CulturalRevolution extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Mao Launches Cultural Revolution")
		.setDescription("\"Anti-Communist\" elements purged from the CPC and other institutions")
		.setColor(Color.red)
		.setFooter("\"It is to the advantage of despots to keep people ignorant; it is to our advantage to make them intelligent. We must lead all of them gradually away from ignorance.\"\n" + 
				"- Mao Zedong, 196X",Launcher.url("countries/cn.png"));
		if (HandManager.China%2==0) {
			builder.addField("May 16 Notification", "The China Card has been given to the USSR face-up.", false);
			HandManager.China = 1;
		}
		else {
			builder.changeVP(-1);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return !(GameData.ccw&&HandManager.China==-1);
	}

	@Override
	public String getId() {
		return "058";
	}

	@Override
	public String getName() {
		return "Cultural Revolution";
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
		return 1;
	}

	@Override
	public boolean isRemoved() {
		return true;
	}

	@Override
	public boolean isFormatted(String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		return "If the US has the China Card, the USSR claims it face up; if the USSR has the China Card, they gain 1 VP.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

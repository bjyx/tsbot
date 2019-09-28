package events;

import java.awt.Color;

import cards.HandManager;
import game.GameData;
import main.Launcher;

public class RedScare extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		if (sp==0) {
			builder.setTitle("Purge!")
			.setDescription("Stalinist paranoia leads to arrest of hundreds of Communist Party members")
			.setColor(Color.blue)
			.setFooter("\"\"\n"
					+ "- XXXX, 19XX", Launcher.url("countries/XX.png"));
			builder.addField("Gulag Archipelago","The USSR subtracts 1 Operations point from any card played for operations.",false);
			HandManager.addEffect(311);
		}
		else {
			builder.setTitle("Red Scare!")
			.setDescription("McCarthyist paranoia sows distrust")
			.setColor(Color.red)
			.setFooter("\"\"\n"
					+ "- XXXX, 19XX", Launcher.url("countries/XX.png"));
			builder.addField("Blacklisted","The US subtracts 1 Operations point from any card played for operations.",false);
			HandManager.addEffect(310);
		}
		
		GameData.txtchnl.sendMessage(builder.build());
	}

	@Override
	public boolean isPlayable(int sp) {
		// TODO Auto-generated method stub
		return !(GameData.ccw&&sp==1&&HandManager.China==-1);
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return "031";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Red Scare/Purge";
	}

	@Override
	public int getOps() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public int getEra() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAssociation() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public boolean isRemoved() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFormatted(String[] args) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "The opposing player deducts one Operations Point on every card played by that side for every purpose for the remainder of this turn.";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "None.";
	}

}

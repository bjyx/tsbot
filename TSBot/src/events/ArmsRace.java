package events;

import java.awt.Color;

import game.GameData;
import main.Launcher;

public class ArmsRace extends Card {

	//NB: The original card text says "phasing player". What does that mean?
	@Override
	public void onEvent(int sp, String[] args) {
		// TODO Auto-generated method stub
		CardEmbedBuilder builder = new CardEmbedBuilder();
		boolean isAhead = false;
		boolean hasMetReq = false;
		int x;		//flavor purposes
		if (GameData.getMilOps(sp)>GameData.getMilOps((sp+1)%2)) {
			isAhead = true;
			x = sp;
			if (GameData.getMilOps(x)>=GameData.getDEFCON()) hasMetReq=true;
		}
		else {
			x = (sp+1)%2;
		}
		builder.setTitle((x==0?"USA":"USSR") + (isAhead?" Pulls Ahead in Arms Race":" Stays Ahead in Arms Race"))
			.setDescription(x==0?"Recent research shows Moscow overexaggerated nuclear arsenal size":"Pravda boasts of massive and technologically superior nuclear arsenal")
			.setFooter("\"Imagine a room awash in gasoline, and there are two implacable enemies in that room. One of them has nine thousand matches. The other has seven thousand matches. Each of them is concerned about who's ahead, who's stronger. Well that's the kind of situation we are actually in.\"\n"
					+ "- Carl Sagan, 1983", Launcher.url("people/sagan.png"))
			.setColor(isAhead?(sp==0?Color.blue:Color.red):Color.gray);
		builder.changeVP((x==0?1:-1)*(isAhead?(hasMetReq?3:1):0));
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return "039";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Arms Race";
	}

	@Override
	public int getOps() {
		// TODO Auto-generated method stub
		return 3;
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
		return "If the player of this card has more VPs than his/her opponent, he/she gains 1 VP. An additional 2 VPs is granted if he/she has met the required number of Military Operations, as defined by the DEFCON.";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "None.";
	}

}

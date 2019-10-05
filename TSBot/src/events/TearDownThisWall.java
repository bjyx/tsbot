package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import game.GameData;
import main.Launcher;
import map.MapManager;

public class TearDownThisWall extends Card {

	private static boolean opponentInfluence;
	
	@Override
	public void onEvent(int sp, String[] args) {
		opponentInfluence=false;
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Reagan gives speech at Brandenburg Gate")
			.setDescription("")
			.setFooter("\"We come to Berlin, we American presidents, because it's our duty to speak, in this place, of freedom. But I must confess, we're drawn here by other things as well... most of all, by your courage and determination. \"\n" + 
					"- Ronald Reagan, 1987", Launcher.url("people/reagan.png"))
			.setColor(Color.blue);
		builder.changeInfluence(6, 0, 3);
		builder.addField("The fall of Ostpolitik", HandManager.removeEffect(55)?"The event of " + CardList.getCard(55) + " has been cancelled.":"The event of " + CardList.getCard(55) + " can no longer be played.", false);
		HandManager.addEffect(96);
		GameData.txtchnl.sendMessage(builder.build()).complete();
		
		for (int i=0; i<21; i++) {
			if (MapManager.get(i).influence[(sp+1)%2]>0) {
				opponentInfluence=true;
				break;
			}
		}
		if(opponentInfluence) {
			GameData.ops=new Operations(sp, CardList.getCard(96).getOpsMod(sp), false, true, true, false, true, 96);
			GameData.dec=new Decision(sp, 96);
			GameData.txtusa.sendMessage(GameData.roleusa.getAsMention()+", you may now conduct realignments or a coup on a country of your choosing in Europe.").complete();
		}
		else {
			GameData.txtusa.sendMessage("There is no communism in Europe, for some reason.").complete();
		}
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "096";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "\"Tear Down This Wall\"";
	}

	@Override
	public int getOps() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public int getEra() {
		// TODO Auto-generated method stub
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
		return "Add 3 US Influence to East Germany. **The US may conduct a free coup** or realignments in Europe. *Cancels/prevents the effect of " + CardList.getCard(55) + ".*";
	}

	@Override
	public String getArguments() {
		return "Event: None.\n"
				+ "Decision: Operations.";
	}

}

package events;

import java.awt.Color;

import cards.CardList;
import cards.Operations;
import game.GameData;
import main.Launcher;
import map.MapManager;

public class Junta extends Card {

	private static int target;
	private static boolean opponentInfluence;
	@Override
	public void onEvent(int sp, String[] args) {
		opponentInfluence=false;
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Military Junta Established in " + MapManager.get(target).name)
			.setDescription("New Government Sympathetic to " + (sp==0?"American":"Soviet") + " Interests")
			.setFooter("\"As many people as necessary must die in Argentina so that the country will again be secure.\"\n" + 
					"- Jorge Rafael Videla, 1975", Launcher.url("people/videla.png"))
			.setColor(sp==0?Color.blue:Color.red);
		builder.changeInfluence(target, sp, 2);
		GameData.txtchnl.sendMessage(builder.build()).complete();
		
		for (int i=64; i<84; i++) {
			if (MapManager.get(i).influence[(sp+1)%2]>0) {
				opponentInfluence=true;
				break;
			}
		}
		if(opponentInfluence) {
			GameData.ops=new Operations(sp, CardList.getCard(47).getOpsMod(sp), false, true, true, false, true, 47);
			GameData.dec=new Decision(sp, 47);
			if (sp==0) GameData.txtusa.sendMessage(GameData.roleusa.getAsMention()+", you may now conduct realignments or a coup on a country of your choosing in Latin America.").complete();
			else GameData.txtssr.sendMessage(GameData.rolessr.getAsMention()+", you may now conduct realignments or a coup on a country of your choosing in Latin America.").complete();
		}
		else {
			if (sp==0) GameData.txtusa.sendMessage("Your opponent has not infiltrated Latin America yet.").complete();
			else GameData.txtssr.sendMessage("Your opponent has not infiltrated Latin America yet.").complete();
		}
		
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "047";
	}

	@Override
	public String getName() {
		return "Junta";
	}

	@Override
	public int getOps() {
		return 2;
	}

	@Override
	public int getEra() {
		return 1;
	}

	@Override
	public int getAssociation() {
		return 2;
	}

	@Override
	public boolean isRemoved() {
		return false;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		if (args.length < 2) return false;
		target = MapManager.find(args[1]);
		if (target==-1) return false;
		if (MapManager.get(target).region==7||MapManager.get(target).region==8) return true;
		return false;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Add 2 influence to any country in Central/South America, then conduct realignments or a coup in Central/South America with the Ops value of this card.";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "Event: the country to which you want to add influence.\n"
				+ "Decision: orders for realignments or a coup.";
	}

}

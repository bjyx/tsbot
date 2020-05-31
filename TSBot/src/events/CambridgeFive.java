package events;


import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import logging.Log;
/**
 * The Cambridge Five Card.
 * @author adalbert
 *
 */
public class CambridgeFive extends Card {
	
	public static boolean regions[];

	@Override
	public void onEvent(int sp, String[] args) {
		regions = new boolean[10]; //9 is always false; this is a prophylactic defense against sneaky people who try to game break
		boolean flag = false;
		String a = "";
		for (int i : HandManager.USAHand) {
			if (CardList.getCard(i).getOps()==0) {
				flag = true;
				if (i == 2) {
					regions[0]=true;
					regions[1]=true;
					regions[2]=true;
					a += "Europe\n";
					Log.writeToLog("Scoring card found: Europe.");
				}
				if (i == 3) {
					regions[3]=true;
					a += "Middle East\n";
					Log.writeToLog("Scoring card found: Middle East.");
				}
				if (i == 1) {
					regions[4]=true;
					regions[5]=true;
					a += "Asia\n";
					Log.writeToLog("Scoring card found: Asia.");
				}
				if (i == 38) {
					regions[5]=true;
					a += "Southeast Asia\n";
					Log.writeToLog("Scoring card found: Southeast Asia.");
				}
				if (i == 79) {
					regions[6]=true;
					a += "Africa\n";
					Log.writeToLog("Scoring card found: Africa.");
				}
				if (i == 37) {
					regions[7]=true;
					a += "Central America\n";
					Log.writeToLog("Scoring card found: Central America.");
				}
				if (i == 81) {
					regions[8]=true;
					a += "South America\n";
					Log.writeToLog("Scoring card found: South America.");
				}
			}
		}
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Some Intelligence")
			.setDescription("")
			.setColor(flag?Color.RED:Color.GRAY)
			.addField(flag?"Operations have been compromised in the following regions:":"No secrets to leak. Unfortunate.",a,false);
		GameData.txtchnl.sendMessage(builder.build()).complete();
		if(flag) {
			GameData.dec = new Decision(1, 104);
			GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", place 1 influence in one of the regions revealed to you.").complete();
		}
	}

	@Override
	public boolean isPlayable(int sp) {
		return GameData.getEra()!=2; 
	}

	@Override
	public String getId() {
		return "104";
	}

	@Override
	public String getName() {
		return "The Cambridge Five";
	}

	@Override
	public int getOps() {
		return 2;
	}

	@Override
	public int getEra() {
		return 0;
	}

	@Override
	public int getAssociation() {
		return 1;
	}

	@Override
	public boolean isRemoved() {
		return false;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		if (GameData.getEra()==2) return "The ring has been busted. You may only play this for operations.";
		return "The USSR looks at all of the scoring cards in the US's hand, and gets to place one USSR influence in any country within that region. *Cannot be played during the late war*.";
	}

	@Override
	public String getArguments() {
		return "Event: None. \n"
				+ "Decision: the single country where you want to place influence. This must be within a region listed by the American scoring cards.";
	}

}

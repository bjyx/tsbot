package events;


import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import game.GameData;

public class CambridgeFive extends Card {
	
	public static boolean regions[];

	@Override
	public void onEvent(int sp, String[] args) {
		// TODO Auto-generated method stub
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
				}
				if (i == 3) {
					regions[3]=true;
					a += "Middle East\n";
				}
				if (i == 1) {
					regions[4]=true;
					regions[5]=true;
					a += "Asia\n";
				}
				if (i == 38) {
					regions[5]=true;
					a += "Southeast Asia\n";
				}
				if (i == 79) {
					regions[6]=true;
					a += "Africa\n";
				}
				if (i == 37) {
					regions[7]=true;
					a += "Central America\n";
				}
				if (i == 81) {
					regions[8]=true;
					a += "South America\n";
				}
			}
		}
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Some Intelligence")
			.setDescription("")
			.setColor(flag?Color.RED:Color.GRAY)
			.addField(flag?"Operations have been compromised in the following regions:":"No secrets to leak. Unfortunate.",a,false);
		GameData.dec = new Decision(1, 104);
		GameData.txtchnl.sendMessage(builder.build()).complete();
		GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", place 1 influence in one of the regions revealed to you.").complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		// TODO Auto-generated method stub
		return GameData.getEra()!=2; 
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return "104";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "The Cambridge Five";
	}

	@Override
	public int getOps() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getEra() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAssociation() {
		// TODO Auto-generated method stub
		return 1;
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
		return "The USSR looks at all of the scoring cards in the US's hand, and gets to place one USSR influence in any country within that region. *Cannot be played during the late war*.";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "Event: None. \n"
				+ "Decision: the single country where you want to place influence. This must be within a region enumerated by the American scoring cards.";
	}

}

package events;

import java.awt.Color;

import cards.HandManager;
import cards.Operations;
import game.GameData;
import logging.Log;
import main.Launcher;
/**
 * The "One Small Step..." Card.
 * @author adalbert
 *
 */
public class OneSmallStep extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		int spaceLevel = GameData.getSpace(sp);
		builder.setTitle("Space-Race Comeback")
			.setDescription("Significant strides in technology made in attempt to lead space race")
			.setFooter("\"We choose to go to the moon in this decade and do the other things, "
					+ "not because they are easy, but because they are hard, "
					+ "because that goal will serve to organize and measure the best of our energies and skills, "
					+ "because that challenge is one that we are willing to accept, one we are unwilling to postpone, "
					+ "and one which we intend to win, and the others, too.\"\n"
					+ "- John F. Kennedy, 1962", Launcher.url("people/jfk.png"))
			.setColor(sp==0?Color.blue:Color.red);
		builder.addField("\"One small step for man...", Operations.getSpaceNames(spaceLevel, sp), false);
		builder.addField("\"...one giant leap for mankind.\"", Operations.getSpaceNames(spaceLevel+1, sp),false);
		GameData.addSpace(sp);
		if (GameData.aheadInSpace()==(sp+1)%2) {
			builder.changeVP(-(sp*2-1)*Operations.spaceVP2[spaceLevel]);
		}
		else builder.changeVP(-(sp*2-1)*Operations.spaceVP[spaceLevel]);
		GameData.addSpace(sp);
		if (spaceLevel<3&&GameData.getSpace(sp)>=3&&sp==1&&HandManager.removeEffect(124)) {
			builder.addField("Vostok 1", "The Soviets have acquired the capability to send a man into space. They will no longer receive a -1 bonus to their space race die rolls.", false);
			Log.writeToLog("Laika no longer active.");
		}
		if (spaceLevel<4&&GameData.getSpace(sp)>=4) {
			if (GameData.getSpace((sp+1)%2)<4) {
				Operations.discount = sp;
			}
			else {
				Operations.discount = -1;
			}
		}
		if (spaceLevel<6&&GameData.getSpace(sp)>=6) {
			if (GameData.getSpace((sp+1)%2)<6) {
				Operations.coupReroll = sp;
			}
			else {
				Operations.coupReroll = -1;
			}
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return GameData.aheadInSpace()==(sp+1)%2;
	}

	@Override
	public String getId() {
		return "080";
	}

	@Override
	public String getName() {
		return "\"One Small Step...\"";
	}

	@Override
	public int getOps() {
		// TODO Auto-generated method stub
		return 2;
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
		return "If you are behind on the space race, advance by two spaces. You will only receive the VP award of the second space.";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "None.";
	}

}

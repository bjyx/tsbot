package events;

import java.awt.Color;

import cards.HandManager;
import cards.Operations;
import game.GameData;
import logging.Log;
import main.Launcher;
/**
 * The Captured Scientist Card.
 * @author adalbert
 *
 */
public class Paperclip extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		int spaceLevel = GameData.getSpace(sp);
		builder.setTitle("Operation Paperclip")
			.setDescription("German scientists recruited for " + (sp==0?"American":"Soviet") + " space program")
			.setFooter("\"We knew that we had created a new means of warfare, and the question as to what nation, "
					+ "to what victorious nation we were willing to entrust this brainchild of ours was a moral decision more than anything else. "
					+ "We wanted to see the world spared another conflict such as Germany had just been through, "
					+ "and we felt that only by surrendering such a weapon to people who are guided not by the laws of materialism but by Christianity and humanity "
					+ "could such an assurance to the world be best secured.\"\n"
					+ "- Wernher von Braun, 1945", Launcher.url("people/vonbraun.png"))
			.setColor(sp==0?Color.blue:Color.red);
		builder.addField("Rocketry breakthroughs", Operations.getSpaceNames(spaceLevel, sp),false);
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
		return GameData.getSpace(sp)<8;
	}

	@Override
	public String getId() {
		return "018";
	}

	@Override
	public String getName() {
		return "Operation Paperclip";	
	}

	@Override
	public int getOps() {
		return 1;
	}

	@Override
	public int getEra() {
		return 0;
	}

	@Override
	public int getAssociation() {
		return 2;
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
		return "Advance one space in the Space Race.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

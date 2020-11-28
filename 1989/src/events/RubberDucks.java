package events;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import commands.TimeCommand;
import game.GameData;
import logging.Log;
import main.Common;
import main.Launcher;
import net.dv8tion.jda.api.EmbedBuilder;

public class RubberDucks extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		int spaceLevel = GameData.getT2(sp);
		builder.setTitle("||REDACTED||")
			.setDescription("||REDACTED||")
			.setFooter("\"||DATA EXPUNGED||\"\n"
					+ "- ||REDACTED||, :black_large_square:", Launcher.url("people/black.png"))
			.setColor(sp==0?Color.blue:Color.red);
		GameData.addT2(sp);
		builder.addField("||REDACTED||", Operations.getSpaceNames(spaceLevel, sp),false);
		if (spaceLevel<3&&GameData.getT2(sp)>=3) {
			EmbedBuilder omit = new EmbedBuilder()
					.setTitle("April 26 Editorial")
					.setAuthor(sp==0?"Foreign News":"People's Daily");
			Operations.three = new ArrayList<Integer>();
			for (int i=0; i<3; i++) {
				Operations.three.add(HandManager.Deck.remove(new Random().nextInt(HandManager.Deck.size())));
				if (HandManager.Deck.isEmpty()) {
					HandManager.Deck.addAll(HandManager.Discard);
					HandManager.Discard.clear();
				}
			}
			for (Integer c : Operations.three) {
				omit.addField(""+CardList.getCard(c), CardList.getCard(c).getDescription(), false);
			}
			GameData.dec = new Decision(sp, 203); // 
			Common.spChannel(sp).sendMessage(Common.spRole(sp).getAsMention() + ", here are the next three cards in the deck. Select the one card you wish to keep. (TS.decide **<ID>**").complete();
			Common.spChannel(sp).sendMessage(omit.build()).complete();
		}
		if (spaceLevel<4&&GameData.getT2(sp)>=4) {
			GameData.dec = new Decision(sp, 204); // 
			Common.spChannel(sp).sendMessage(Common.spRole(sp).getAsMention() + ", you may now remove two of your opponent's SPs from the board. (TS.decide <influence>").complete();
		}
		if (spaceLevel<5&&GameData.getT2(sp)>=6) {
			if (GameData.getT2((sp+1)%2)<6) {
				TimeCommand.extraCheck = false; //enables
			}
			else {
				TimeCommand.extraCheck = true; //disables
			}
		}
		if (spaceLevel<7&&GameData.getT2(sp)>=7) {
			if (GameData.getT2((sp+1)%2)<7) {
				Operations.seven = sp;
			}
			else {
				Operations.seven = -1;
			}
		}
		if (spaceLevel<8&&GameData.getT2(sp)>=8) {
			if (GameData.getT2((sp+1)%2)<8) {
				Operations.eight = sp;
			}
			else {
				Operations.eight = -1;
			}
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "085";
	}

	@Override
	public String getName() {
		return "Tank Column/Tank Man";
	}

	@Override
	public int getOps() {
		return 2;
	}

	@Override
	public int getEra() {
		return 2;
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
		return "Advance one space on the T-Square track.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

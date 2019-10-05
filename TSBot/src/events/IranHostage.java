package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import main.Launcher;
import map.MapManager;

public class IranHostage extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Hostages taken in wake of Iranian Revolution")
			.setDescription("American embassy occupied; 52 lives at stake")
			.setColor(Color.red)
			.setFooter("\"Our aim was to object against the American government by going to their embassy and occupying it for several hours. "
					+ "Announcing our objections from within the occupied compound would carry our message to the world in a much more firm and effective way.\"\n"
					+ "- Ebrahim Asgharzadeh, 1962", Launcher.url("people/asgharzadeh.png"));
		builder.changeInfluence(23, 0, -MapManager.get(23).influence[0]); //remove all American influence in Iran
		builder.changeInfluence(23, 1, 2); //add 2 USSR Influence to Iran
		builder.addField("State-sponsored terrorism","If "+ CardList.getCard(92) +" is played against the US, it must now discard two cards.",false);
		HandManager.addEffect(82);
		GameData.txtchnl.sendMessage(builder.build()).complete();

	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "082";
	}

	@Override
	public String getName() {
		return "Iran Hostage Crisis";
	}

	@Override
	public int getOps() {
		return 3;
	}

	@Override
	public int getEra() {
		return 2;
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
	public boolean isFormatted(int sp, String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Remove all US Influence in Iran. Add 2 USSR Influence in Iran. *The US is now forced to discard two cards when targeted by " + CardList.getCard(92) + ".*";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

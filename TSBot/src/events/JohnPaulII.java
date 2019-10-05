package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import main.Launcher;

public class JohnPaulII extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Papal Conclave Elects Karol Josef Wojtyła")
			.setDescription("First Polish Pope to be christened John Paul II")
			.setColor(Color.blue)
			.setFooter("\"They called him from a faraway land—"
					+ "far and yet always close because of our communion in faith "
					+ "and Christian traditions. I was afraid to accept that responsibility, "
					+ "yet I do so in a spirit of obedience to the Lord "
					+ "and total faithfulness to Mary, our most Holy Mother. \"\n"
					+ "- Karol Josef Wojtyła, 1978", Launcher.url("people/jp2.png"));
		builder.changeInfluence(10, 1, -2); //remove 2 USSR influence in Poland
		builder.changeInfluence(10, 0, 1); //add 1 USA influence in Poland
		builder.addField("Catholic clout","Anti-communist sentiment in Poland is building. " + CardList.getCard(101) + " may now be played for the event.",false);
		HandManager.addEffect(68);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "068";
	}

	@Override
	public String getName() {
		return "John Paul II Elected Pope";
	}

	@Override
	public int getOps() {
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
		return "Remove 2 USSR Influence from Poland. Add 1 USA Influence to Poland. *Allows the play of " + CardList.getCard(110) + ".*";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

package events;

import java.awt.Color;

import cards.HandManager;
import game.GameData;
import logging.Log;
import main.Launcher;
/**
 * The Cuban Missile Crisis Card.
 * @author adalbert
 *
 */
public class MissileCrisis extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		// TODO Auto-generated method stub
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle(sp==0?"Turkish Missile Crisis!":"Cuban Missile Crisis!")
		.setDescription(sp==0?"American Missiles Threaten Moscow":"Soviet Missiles in Cuba Within Striking Distance of Washington")
		.setFooter("\"The path we have chosen for the present is full of hazards, as all paths are--but it is the one most consistent with our character and courage as a nation and our commitments around the world. The cost of freedom is always high--and Americans have always paid it. And one path we shall never choose, and that is the path of surrender or submission.\"\n" + 
				"- John F. Kennedy, 1962", Launcher.url("people/jfk.png"))
		.setColor(sp==0?Color.blue:Color.red);
		builder.changeDEFCON(2-GameData.getDEFCON());
		builder.addField("One Minute to Midnight", "**All attempts by " + (sp==0?"the USSR":"the USA") + "to conduct a coup will lose them the game by Thermonuclear War.**", false);
		HandManager.addEffect(400+((sp+1)%2));
		Log.writeToLog("Missile Crisis Active (benefits "+(sp==0?"US":"SU") + ").");
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
		return "040";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Cuban Missile Crisis";
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
		return true;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "**Sets DEFCON to 2**. For the rest of the turn, any coup conducted by your opponent will immediately cause them to lose the game by thermonuclear war. *This effect can be cancelled earlier at any point if the affected opponent removes two influence from a certain country: the USSR must do so from Cuba, and the USA can select one of West Germany or Turkey.*";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "Event: None.\n"
				+ "Decision: The word `resolve` and a country. For the USSR, must write a valid alias for Cuba. For the USA, the country can be either West Germany or Turkey. May be performed at any moment.";
	}

}

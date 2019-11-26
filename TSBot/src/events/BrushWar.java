package events;

import java.awt.Color;

import cards.HandManager;
import game.GameData;
import main.Launcher;
import map.MapManager;

public class BrushWar extends Card {

	private static int target;
	
	@Override
	public void onEvent(int sp, String[] args) {
		int die = (int) (Math.random()*6+1);
		int mod = die;
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.addMilOps(sp,  3);
		String adjacents = "";
		for (int i : MapManager.get(target).adj) {
			if (MapManager.get(i).isControlledBy()==(sp+1)%2) {
				mod--;
				adjacents += MapManager.get(i);
			}
		}
		builder.addField("Insurgency in " + MapManager.get(target).name, "Roll: :" + numbers[die] + (mod==die?":":(": - " + adjacents)), false);
		if (mod>=3) {
			builder.setTitle("Insurgents overthrow government in " + MapManager.get(target).name)
				.setDescription("New government aligns closely with " + (sp==0?"American":"Soviet") + " interests")
				.setFooter("\"The people colonized by Abyssinia will be free. "
						+ "Eritrea will be free, and they cannot refuse to let them be free. "
						+ "Western Somalia will be free, and they cannot refuse to grant it freedom. "
						+ "The numerous Abo will be free because this is history, and no one can prevent the sunshine from reaching us.\"\n"
						+ "- Siad Barre, 1978",Launcher.url("people/barre.png"))
				.setColor(sp==0?Color.blue:Color.red);
			builder.changeInfluence(target, sp, MapManager.get(target).influence[(sp+1)%2]);
			builder.changeInfluence(target, (sp+1)%2, -MapManager.get(target).influence[(sp+1)%2]);
			builder.changeVP(sp==0?1:-1);
		}
		else {
			builder.setTitle("Insurgency Repulsed")
				.setDescription("Brush war resolved with minor political changes")
				.setFooter("\"Henceforth we will tackle our enemies that come face to face with us and we will not be stabbed in from behind by internal foes... "
						+ "To this end, we will arm the allies and comrades of the broad masses without giving respite to reactionaries, "
						+ "and avenge the blood of our comrades double - and triple - fold.\"\n"
						+ "- Mengistu Haile Mariam, 1977",Launcher.url("people/mengistu.png"))
				.setColor(sp==0?Color.red:Color.blue);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "036";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Brush War";
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
		return false;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		if (args.length<2) return false;
		target = MapManager.find(args[1]);
		if (MapManager.get(target).stab>2) return false;
		if (MapManager.get(target).region<=2 && MapManager.get(target).isControlledBy()==0 && sp==1 && HandManager.effectActive(21)) return false; //NATO lol
		return true;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Target a single country with at most 2 stability. Add 3 Military Operations. Roll a die and subtract the number of countries neighboring the target under your opponent's control. On a roll of 3-6, gain 1 VP and replace all opposing influence in the country with your own.";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "The country to target, which must have at most 2 stability.";
	}

}

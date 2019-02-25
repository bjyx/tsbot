package map;

import java.util.Arrays;
import java.util.List;

import game.GameData;

public class Austria extends Country {

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Austria";
	}

	@Override
	public int getRegion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getISO3166() {
		// TODO Auto-generated method stub
		return "at";
	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return Arrays.asList("austria","at","aut","österreich","osterreich");
	}

	@Override
	public int getStab() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public List<Integer> getAdj() {
		// TODO Auto-generated method stub
		return Arrays.asList(6,10,11,19);//dd, hu, it, de
	}

	@Override
	public boolean isBattleground() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return "Like Germany, Austria was divided between Western and Soviet occupation zones after World War II; unlike Germany, Austria was able to avoid a Cold War partition, in part due to doubts that its Socialist leader Karl Renner could become a Soviet Puppet. It regained full independence on the 15th of May, 1955, by the Austrian State Treaty, which also swore it to permanent neutrality.";
	}

	@Override
	public String getLeader() {
		if (isControlledBy()==1) {
			if (GameData.getTurn()<=2) return "Adolf Schärf";
			if (GameData.getTurn()<=5) return "Bruno Pittermann";
			if (GameData.getTurn()<=9) return "Bruno Kreisky";
			return "Fred Sinowatz";
		}
		else if (isControlledBy()==0){
			if (GameData.getTurn()<=1) return "Leopold Figi";
			if (GameData.getTurn()<=3) return "Julius Raab";
			if (GameData.getTurn()<=4) return "Alfons Gorbach";
			if (GameData.getTurn()<=6) return "Josef Klaus";
			if (GameData.getTurn()<=7) return "Karl Schleinzer";
			if (GameData.getTurn()<=8) return "Josef Taus";
			return "Alois Mock";
		}
		else {
			if (GameData.getTurn()<=1) return "Leopold Figi";
			if (GameData.getTurn()<=3) return "Julius Raab";
			if (GameData.getTurn()<=4) return "Alfons Gorbach";
			if (GameData.getTurn()<=6) return "Josef Klaus";
			if (GameData.getTurn()<=9) return "Bruno Kreisky";
			return "Fred Sinowatz";
		}
	}

}

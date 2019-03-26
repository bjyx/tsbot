package map;

import java.util.ArrayList;

public class MapManager {
	public static ArrayList<Country> map;
	
	public static void initialize() {//literally list every damn country here.
		map.add(new Austria());
		//and so forth.
	}
	public static int find(String alias) {
		for (Country c : map) {
			if (c.getAliases().contains(alias)) return c.getID();
		}
		return -1;
	}
}

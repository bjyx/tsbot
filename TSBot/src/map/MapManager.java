package map;

import java.util.List;

public class MapManager {
	public static List<Country> map;
	
	public static void initialize() {//literally list every damn country here.
		map.add(new Austria());
	}
	public Country find(String alias) {
		for (Country c : map) {
			if (c.getAliases().contains(alias)) return c;
		}
		return null;
	}
}

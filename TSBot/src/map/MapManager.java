package map;

import java.util.List;

public class MapManager {
	public static List<Country> map;
	
	public static void initialize() {//literally list every damn country here.
		map.add(new Austria());
	}
}

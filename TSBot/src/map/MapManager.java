package map;

import java.util.ArrayList;

import game.GameData;

public class MapManager {
	private static ArrayList<Country> map;
	
	public static void initialize() {//literally list every damn country here.
		map = new ArrayList<Country>();
		//Europe - 0
		map.add(new Country(
				"Austria", 
				0, 
				"at", 
				4, 
				false, 
				new String[] {"austria", "at", "aut", "österreich", "osterreich"}, 
				new Integer[] {6, 10, 11, 19}, //hu, it, dd, de
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Benelux", 
				1, 
				"be", 
				3, 
				false, 
				new String[] {"benelux", "belgium", "bel", "be", "belgië", "belgie", "belgique", "netherlands", "nld","nl","nederland","luxembourg","lux","lu","luxemburg","letzebuerg","lëtzebuerg"}, 
				new Integer[] {18, 19}, //uk, de
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Bulgaria", 
				2, 
				"bg", 
				4, 
				false, 
				new String[] {"bulgaria", "bg", "bgr", "balgariya", "българия"}, 
				new Integer[] {9, 17}, //gr, tr
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Canada", 
				1, 
				"ca", 
				4, 
				false, 
				new String[] {"canada", "ca", "can"}, 
				new Integer[] {18, 84}, //uk, us
				"", 
				new int[] {2,0}
				));
		map.add(new Country(
				"Czechoslovakia", 
				2, 
				"cz", 
				3, 
				false, 
				new String[] {"czechoslovakia", "cs", "csk", "československo", "ceskoslovensko","cshh"}, //note - not including Czechia or Slovakia for obvious divisive reasons, but uses cz (Czechia only) for flag
				new Integer[] {6, 10, 13}, //hu, pl, dd
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Denmark", 
				1, 
				"dk", 
				3, 
				false, 
				new String[] {"denmark", "dk", "dnk", "danmark"}, 
				new Integer[] {16, 19}, //de, se
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"East Germany", 
				2, 
				"dd", 
				3, 
				true, 
				new String[] {"eastgermany", "dd", "ddr", "east germany"}, 
				new Integer[] {0,4,13,19}, //at, cs, pl, de
				"", 
				new int[] {0,3}
				));
		map.add(new Country(
				"Finland", 
				0, 
				"fi", 
				4, 
				false, 
				new String[] {"finland", "fi", "fin", "suomi"}, 
				new Integer[] {16, 85}, //se, su
				"", 
				new int[] {0,1}
				));
		map.add(new Country(
				"France", 
				1, 
				"fr", 
				3, 
				true, 
				new String[] {"france", "fr", "fra"}, 
				new Integer[] {11, 15, 18, 19, 46}, //it, es, uk, de, dz
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Greece", 
				1, 
				"gr", 
				2, 
				false, 
				new String[] {"greece", "gr", "grc", "hellas", "Ελλάς"}, 
				new Integer[] {2,11,17,20}, //bg, it, tr, yu
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Hungary", 
				2, 
				"hu", 
				3, 
				false, 
				new String[] {"hungary", "hu", "hun", "magyarorszag", "magyarország"}, 
				new Integer[] {0,4,14,20}, //at, cz, ro, yu
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Italy", 
				1, 
				"it", 
				2, 
				true, 
				new String[] {"italy", "it", "ita", "italia"}, 
				new Integer[] {0,8,9,15,20}, //at, fr, gr, es, yu
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Norway", 
				1, 
				"no", 
				4, 
				false, 
				new String[] {"norway", "no", "nor", "norge"}, 
				new Integer[] {16, 18}, //se, uk
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Poland", 
				2, 
				"pl", 
				3, 
				true,
				new String[] {"poland", "pl", "pol", "polska"}, 
				new Integer[] {4, 6, 85}, //cs, dd, su
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Romania", 
				2, 
				"ro", 
				3, 
				false, 
				new String[] {"romania", "rou", "ro"}, 
				new Integer[] {10, 17, 20, 85}, //hu, tr, yu, su
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Spain/Portugal", 
				1, 
				"es::flag_pt", 
				2, 
				false, 
				new String[] {"spainportugal", "es", "esp", "españa", "espana", "spain", "portugal", "pr", "prt"}, 
				new Integer[] {8, 11, 53}, //fr, it, ma
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Sweden", 
				1, 
				"se", 
				4, 
				false, 
				new String[] {"sweden", "se", "swe", "sverige"}, 
				new Integer[] {5, 7, 12}, //dk, fi, no
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Turkey", 
				1, 
				"tr", 
				2, 
				false, 
				new String[] {"turkey", "tr", "tur", "turkiye", "türkiye"}, 
				new Integer[] {2, 9, 14, 30}, //bg, gr, ro, sy
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"United Kingdom", 
				1, 
				"gb", 
				5, 
				false, 
				new String[] {"uk", "gbr", "gb", "unitedkingdom", "britain"}, 
				new Integer[] {1, 3, 8, 12}, //be, ca, fr, no
				"", 
				new int[] {5,0}
				));
		map.add(new Country(
				"West Germany", 
				1, 
				"de", 
				4, 
				true, 
				new String[] {"westgermany", "de", "deu", "brd"}, 
				new Integer[] {0, 1, 5, 6, 8}, //at, be, dk, dd, fr
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Yugoslavia", 
				2, 
				"yu", 
				3, 
				false, 
				new String[] {"yugoslavia", "yu", "yug", "yuhh", "jugoslavija"}, 
				new Integer[] {9, 10, 11, 14}, //gr, hu, it, ro
				"", 
				new int[] {0,0}
				));
		//Middle East - 21
		map.add(new Country(
				"Egypt", 
				3, 
				"eg", 
				2, 
				true, 
				new String[] {"egypt", "egy", "eg", "misr", "masr"}, 
				new Integer[] {25, 28, 59}, //il, ly, sd
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Gulf States", 
				3, 
				"kw", 
				3, 
				false, 
				new String[] {"gulfstates", "ae", "are", "unitedarabemirates", "imarat", "uae", "oman","uman","omn","om","qatar","qat","qa","bahrain","bhr","bahrayn","bh","kuwait","kwt","kw"}, 
				new Integer[] {24, 29}, //iq, sa
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Iran", 
				3, 
				"ir", 
				2, 
				true, 
				new String[] {"iran", "irn", "ir", "persia"}, 
				new Integer[] {24, 31, 40}, //iq, af, pk
				"", 
				new int[] {1,0}
				));
		map.add(new Country(
				"Iraq", 
				3, 
				"iq", 
				3, 
				true, 
				new String[] {"iraq", "irq", "iq"}, 
				new Integer[] {22, 23, 26, 29}, //ae, ir, jo, sa
				"", 
				new int[] {0,1}
				));
		map.add(new Country(
				"Israel", 
				3, 
				"il", 
				4, 
				true, 
				new String[] {"israel", "isr", "il", "yisrael", "israil"}, 
				new Integer[] {21, 26, 27, 30}, //eg, jo, lb, sy
				"", 
				new int[] {1,0}
				));
		map.add(new Country(
				"Jordan", 
				3, 
				"jo", 
				2, 
				false, 
				new String[] {"jordan", "jor", "jo", "urdunn"}, 
				new Integer[] {24, 25, 27, 29}, //iq, il, lb, sa
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Lebanon", 
				3, 
				"lb", 
				1, 
				false, 
				new String[] {"lebanon", "lbn", "lb", "lubnan"}, 
				new Integer[] {25, 26, 30}, //il, jo, sy
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Libya", 
				3, 
				"ly", 
				2, 
				true, 
				new String[] {"libya", "lby", "ly", "libiyyah"}, 
				new Integer[] {21, 60}, //eg, tn
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Saudi Arabia", 
				3, 
				"sa", 
				3, 
				true, 
				new String[] {"saudiarabia", "sau", "sa", "saudiyah", "ksa"}, 
				new Integer[] {22, 24, 26}, //ae, iq, jo
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Syria", 
				3, 
				"sy", 
				2, 
				false, 
				new String[] {"syria", "syr", "sy", "suriyah"}, 
				new Integer[] {17, 25, 27}, //tr, il, lb
				"", 
				new int[] {0,1}
				));
		//Asia - 31
		map.add(new Country(
				"Afghanistan", 
				4, 
				"af", 
				2, 
				false, 
				new String[] {"afghanistan", "afg", "af", "afganestan"}, 
				new Integer[] {23, 40, 85}, //ir, pk, su
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Australia", 
				4, 
				"au", 
				4, 
				false, 
				new String[] {"australia", "aus", "au"}, 
				new Integer[] {38}, //my
				"", 
				new int[] {4,0}
				));
		map.add(new Country(
				"Burma/Myanmar", 
				5, 
				"bu", //codes for the flag of the Union of Burma (six stars, blue canton, red field) instead of the current flag
				2, 
				false, 
				new String[] {"burma", "bur", "bu", "bumm", "myanmar", "mmr", "mm"}, 
				new Integer[] {34, 37}, //in, la
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"India", 
				4, 
				"in", 
				3, 
				true, 
				new String[] {"india", "ind", "in", "bharat"}, 
				new Integer[] {33, 40}, //bu, pk
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Indonesia", 
				5, 
				"id", 
				1, 
				false, 
				new String[] {"indonesia", "idn", "id"}, 
				new Integer[] {38, 41}, //my, ph
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Japan", 
				4, 
				"jp", 
				4, 
				true, 
				new String[] {"japan", "jpn", "jp", "nihon", "nippon"}, 
				new Integer[] {41, 42, 43, 84}, //ph, kr, tw, us
				"", 
				new int[] {1,0}
				));
		map.add(new Country(
				"Laos/Cambodia", 
				5, 
				"la::flag_kh", 
				1, 
				false, 
				new String[] {"laoscambodia", "lao", "la", "laos", "cambodia", "kampucie", "kh", "khm"}, 
				new Integer[] {33,44,45}, //bu, th, vn
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Malaysia", 
				5, 
				"my", 
				2, 
				false, 
				new String[] {"malaysia", "mys", "my"}, 
				new Integer[] {32, 35, 44}, //au, id, th
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"North Korea", 
				4, 
				"kp", 
				3, 
				true, 
				new String[] {"northkorea", "dprk", "prk", "kp","choson","bukhan","bukchoson"}, 
				new Integer[] {42, 85}, //kr, su
				"", 
				new int[] {0,3}
				));
		map.add(new Country(
				"Pakistan", 
				4, 
				"pk", 
				2, 
				true, 
				new String[] {"pakistan", "pak", "pk"}, 
				new Integer[] {23, 31, 34}, //ir, af, in
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Philippines", 
				5, 
				"ph", 
				2, 
				false, 
				new String[] {"philippines", "phl", "ph", "pilipinas"}, 
				new Integer[] {35, 36}, //id, jp
				"", 
				new int[] {1,0}
				));
		map.add(new Country(
				"South Korea", 
				4, 
				"kr",
				3, 
				true, 
				new String[] {"southkorea", "kor", "kr", "hanguk","namhan","namjoson"}, 
				new Integer[] {36, 39, 43}, //jp, pk, tw
				"", 
				new int[] {1,0}
				));
		map.add(new Country(
				"Taiwan", 
				4, 
				"tw", 
				3, 
				false, 
				new String[] {"taiwan", "tw", "twn"}, 
				new Integer[] {36, 42}, //jp, kr
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Thailand", 
				5, 
				"th", 
				2, 
				true, 
				new String[] {"thailand", "tha", "th", "thai", "siam"}, 
				new Integer[] {37, 38, 45}, //la, my, vn
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Vietnam", 
				5, 
				"vn", 
				2, 
				false, 
				new String[] {"vietnam", "vnm", "vn", "vdr", "vd"}, 
				new Integer[] {37, 44}, //la, th
				"", 
				new int[] {0,0}
				));
		//Africa - 46
		map.add(new Country(
				"Algeria", 
				6, 
				"dz", 
				2, 
				true, 
				new String[] {"algeria", "dza", "dz", "dzayir", "jazair"}, 
				new Integer[] {8, 53, 56, 60}, //fr, ma, td, tn
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Angola", 
				6, 
				"ao", 
				1, 
				true, 
				new String[] {"angola", "ago", "ao"}, 
				new Integer[] {48, 58, 62}, //bw, za, zr
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Botswana", 
				6, 
				"bw", 
				2, 
				false, 
				new String[] {"botswana", "bwa", "bw"}, 
				new Integer[] {47, 58, 63}, //ao, za, zw
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Cameroon", 
				6, 
				"cm", 
				1, 
				false, 
				new String[] {"cameroon", "cmr", "cm"}, 
				new Integer[] {54, 62}, //ng, zr
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Ethiopia", 
				6, 
				"et", 
				1, 
				false, 
				new String[] {"ethiopia", "eth", "et", "ityoppya"}, 
				new Integer[] {57, 59}, //so, sd
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Ivory Coast", 
				6, 
				"ci", 
				2, 
				false, 
				new String[] {"ivorycoast", "civ", "ci", "cotedivoire"}, 
				new Integer[] {54, 61}, //ng, ml
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Kenya", 
				6, 
				"ke", 
				2, 
				false, 
				new String[] {"kenya", "ken", "ke"}, 
				new Integer[] {55, 57}, //mz, so
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Morocco", 
				6, 
				"ma", 
				3, 
				false, 
				new String[] {"morocco", "mar", "ma", "magrib"}, 
				new Integer[] {15, 46, 61}, //es, dz, ml
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Nigeria", 
				6, 
				"ng", 
				1, 
				true, 
				new String[] {"nigeria", "nga", "ng"}, 
				new Integer[] {49, 51, 56}, //cm, ci, td
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Southeast African States", 
				6, 
				"mz", 
				1, 
				false, 
				new String[] {"seafricanstates", "seafrica", "southeastafricanstates", "southeastafrica", "mozambique", "moz", "mz"}, 
				new Integer[] {52, 63}, //ke, zw
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Saharan States", 
				6, 
				"td", //chad
				1, 
				false, 
				new String[] {"saharanstates", "sahara", "chad", "tcd", "td"}, 
				new Integer[] {46, 54}, //dz, ng
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Somalia", 
				6, 
				"so", 
				2, 
				false, 
				new String[] {"somalia", "som", "so", "soomaaliya", "sumal"}, 
				new Integer[] {50, 52}, //et, ke
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"South Africa", 
				6, 
				"za", 
				3, 
				false, 
				new String[] {"southafrica", "zaf", "za", "suidafrika"}, 
				new Integer[] {47, 48}, //ao, bw
				"", 
				new int[] {1,0}
				));
		map.add(new Country(
				"Sudan", 
				6, 
				"sd", 
				1, 
				false, 
				new String[] {"sudan", "sdn", "sd"}, 
				new Integer[] {21, 50}, //eg, et
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Tunisia", 
				6, 
				"tn", 
				2, 
				false, 
				new String[] {"tunisia", "tun", "tn", "tunis"}, 
				new Integer[] {28, 46}, //ly, dz
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"West African States", 
				6, 
				"ml", 
				2, 
				false, 
				new String[] {"westafricanstates", "westafrica", "mli", "mali", "ml"}, 
				new Integer[] {51, 53}, //ci, ma
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Zaire", 
				6, 
				"zr", 
				1, 
				true, 
				new String[] {"zaire", "zar", "zr", "drc", "cod", "cd"}, 
				new Integer[] {47, 49, 63}, //ao, cm, zw
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Zimbabwe", 
				6, 
				"zw", 
				1, 
				false, 
				new String[] {"zimbabwe", "zwe", "zw"}, 
				new Integer[] {48, 55, 62}, //bw, mz, zr
				"", 
				new int[] {0,0}
				));
		//Central America - 64
		map.add(new Country(
				"Costa Rica", 
				7, 
				"cr", 
				3, 
				false, 
				new String[] {"costarica", "cri", "cr"}, 
				new Integer[] {70, 72, 73}, //hn, ni, pa
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Cuba", 
				7, 
				"cu", 
				3, 
				true, 
				new String[] {"cuba", "cub", "cu"}, 
				new Integer[] {69, 72, 84}, //ht, ni, us
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Dominican Republic", 
				7, 
				"do", 
				1, 
				false, 
				new String[] {"dominicanrepublic", "dom", "do"}, 
				new Integer[] {69}, //ht
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"El Salvador", 
				7, 
				"sv", 
				1, 
				false, 
				new String[] {"elsalvador", "slv", "sv"}, 
				new Integer[] {68, 70}, //gt, hn
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Guatemala", 
				7, 
				"gt", 
				1, 
				false, 
				new String[] {"guatemala", "gtm", "gt"}, 
				new Integer[] {67, 70, 71}, //sv, hn, mx
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Haiti", 
				7, 
				"ht", 
				1, 
				false, 
				new String[] {"haiti", "hti", "ht", "ayiti", "haïti"}, 
				new Integer[] {65, 66}, //cu, do
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Honduras", 
				7, 
				"hn", 
				2, 
				false, 
				new String[] {"honduras", "hnd", "hn"}, 
				new Integer[] {64, 67, 68, 72}, //cr, sv, gt, ni
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Mexico", 
				7, 
				"mx", 
				2, 
				true, 
				new String[] {"mexico", "mex", "mx", "méxico"}, 
				new Integer[] {68, 84}, //gt, us
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Nicaragua", 
				7, 
				"ni", 
				1, 
				false, 
				new String[] {"nicaragua", "nic", "ni"}, 
				new Integer[] {64, 65, 70}, //cr, cu, hn
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Panama", 
				7, 
				"pa", 
				2, 
				true, 
				new String[] {"panama", "pan", "pa"}, 
				new Integer[] {64, 78}, //cr, co
				"", 
				new int[] {1,0}
				));
		//South America - 74
		map.add(new Country(
				"Argentina", 
				8, 
				"ar", 
				2, 
				true, 
				new String[] {"argentina", "arg", "ar"}, 
				new Integer[] {77, 80, 82}, //cl, py, uy
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Bolivia", 
				8, 
				"bo", 
				2, 
				false, 
				new String[] {"bolivia", "bol", "bo"}, 
				new Integer[] {80, 81}, //py, pe
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Brazil", 
				8, 
				"br", 
				2, 
				true, 
				new String[] {"brazil", "bra", "br", "brasil"}, 
				new Integer[] {82, 83}, //uy, ve
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Chile", 
				8, 
				"cl", 
				3, 
				true, 
				new String[] {"chile", "chl", "cl"}, 
				new Integer[] {74, 81}, //ar, pe
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Colombia", 
				8, 
				"co", 
				1, 
				false, 
				new String[] {"colombia", "col", "co"}, 
				new Integer[] {73, 79, 83}, //pa, ec, ve
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Ecuador", 
				8, 
				"ec", 
				2, 
				false, 
				new String[] {"ecuador", "ecu", "ec"}, 
				new Integer[] {78, 81}, //co, pe
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Paraguay", 
				8, 
				"py", 
				2, 
				false, 
				new String[] {"paraguay", "pry", "py"}, 
				new Integer[] {74, 75, 82}, //ar, bo, uy
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Peru", 
				8, 
				"pe", 
				2, 
				false, 
				new String[] {"peru", "per", "pe"}, 
				new Integer[] {75, 77, 79}, //bo, cl, ec
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Uruguay", 
				8, 
				"uy", 
				2, 
				false, 
				new String[] {"uruguay", "ury", "uy"}, 
				new Integer[] {74, 76, 80}, //ar, br, py
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Venezuela", 
				8, 
				"ve", 
				2, 
				true, 
				new String[] {"venezuela", "ven", "ve"}, 
				new Integer[] {76, 78}, //br, co
				"", 
				new int[] {0,0}
				));
		//Superpowers - 84 - really only relevant in case I need flags for Brush War
		//TODO set up precautions in case someone tries funny business in the superpowers :P
		map.add(new Country(
				"United States of America", 
				9, 
				"us", 
				7, 
				false, 
				new String[] {"unitedstates", "usa", "us","america","democrat", "capitalist"}, 
				new Integer[] {3, 36, 65, 71}, //ca, jp, cu, mx
				"", 
				new int[] {2147483647,-2147483648} //don't even bother...
				));
		map.add(new Country(
				"Union of Soviet Socialist Republics", 
				9, 
				"su", 
				7, 
				false, 
				new String[] {"unionofsovietsocialistrepublics", "sun", "su","sovietunion","ussr", "sovetskysoyuz","sssr","communist","ссср", "soviet"}, 
				new Integer[] {7, 13, 14, 31, 39}, //fi, pl, ro, af, kp
				"", 
				new int[] {-2147483648,2147483647} //don't even bother...
				));
		map.add(new Country(
				"China", 
				9, 
				"cn", 
				3, 
				false, 
				new String[] {"china", "chn", "cn","prc","zhongguo", "zhonghuarenmingongheguo","peoplesrepublicofchina"}, 
				new Integer[] {85}, //su
				"", 
				new int[] {0,GameData.ccw?0:3} //If you're the US you probably can't put things here anyways
				));
	}
	public static Country get(int id) {
		return map.get(id);
	}
	public static int find(String alias) {
		for (Country c : map) {
			if (c.aliases.contains(alias)) return c.id;
		}
		return -1;
	}
	public static void lateWarMap() {
		map.clear();
		//Europe - 0
		map.add(new Country(
				"Austria", 
				0, 
				"at", 
				4, 
				false, 
				new String[] {"austria", "at", "aut", "österreich", "osterreich"}, 
				new Integer[] {6, 10, 11, 19}, //hu, it, dd, de
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Benelux", 
				1, 
				"be", 
				3, 
				false, 
				new String[] {"benelux", "belgium", "bel", "be", "belgië", "belgie", "belgique", "netherlands", "nld","nl","nederland","luxembourg","lux","lu","luxemburg","letzebuerg","lëtzebuerg"}, 
				new Integer[] {18, 19}, //uk, de
				"", 
				new int[] {3,0}
				));
		map.add(new Country(
				"Bulgaria", 
				2, 
				"bg", 
				4, 
				false, 
				new String[] {"bulgaria", "bg", "bgr", "balgariya", "българия"}, 
				new Integer[] {9, 17}, //gr, tr
				"", 
				new int[] {0,3}
				));
		map.add(new Country(
				"Canada", 
				1, 
				"ca", 
				4, 
				false, 
				new String[] {"canada", "ca", "can"}, 
				new Integer[] {18, 84}, //uk, us
				"", 
				new int[] {2,0}
				));
		map.add(new Country(
				"Czechoslovakia", 
				2, 
				"cz", 
				3, 
				false, 
				new String[] {"czechoslovakia", "cs", "csk", "československo", "ceskoslovensko","cshh"}, //note - not including Czechia or Slovakia for obvious divisive reasons, but uses cz (Czechia only) for flag
				new Integer[] {6, 10, 13}, //hu, pl, dd
				"", 
				new int[] {0,3}
				));
		map.add(new Country(
				"Denmark", 
				1, 
				"dk", 
				3, 
				false, 
				new String[] {"denmark", "dk", "dnk", "danmark"}, 
				new Integer[] {16, 19}, //de, se
				"", 
				new int[] {3,0}
				));
		map.add(new Country(
				"East Germany", 
				2, 
				"dd", 
				3, 
				true, 
				new String[] {"eastgermany", "dd", "ddr", "east germany"}, 
				new Integer[] {0,4,13,19}, //at, cs, pl, de
				"", 
				new int[] {0,3}
				));
		map.add(new Country(
				"Finland", 
				0, 
				"fi", 
				4, 
				false, 
				new String[] {"finland", "fi", "fin", "suomi"}, 
				new Integer[] {16, 85}, //se, su
				"", 
				new int[] {1,2}
				));
		map.add(new Country(
				"France", 
				1, 
				"fr", 
				3, 
				true, 
				new String[] {"france", "fr", "fra"}, 
				new Integer[] {11, 15, 18, 19, 46}, //it, es, uk, de, dz
				"", 
				new int[] {3,1}
				));
		map.add(new Country(
				"Greece", 
				1, 
				"gr", 
				2, 
				false, 
				new String[] {"greece", "gr", "grc", "hellas", "Ελλάς"}, 
				new Integer[] {2,11,17,20}, //bg, it, tr, yu
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Hungary", 
				2, 
				"hu", 
				3, 
				false, 
				new String[] {"hungary", "hu", "hun", "magyarorszag", "magyarország"}, 
				new Integer[] {0,4,14,20}, //at, cz, ro, yu
				"", 
				new int[] {0,3}
				));
		map.add(new Country(
				"Italy", 
				1, 
				"it", 
				2, 
				true, 
				new String[] {"italy", "it", "ita", "italia"}, 
				new Integer[] {0,8,9,15,20}, //at, fr, gr, es, yu
				"", 
				new int[] {2,0}
				));
		map.add(new Country(
				"Norway", 
				1, 
				"no", 
				4, 
				false, 
				new String[] {"norway", "no", "nor", "norge"}, 
				new Integer[] {16, 18}, //se, uk
				"", 
				new int[] {4,0}
				));
		map.add(new Country(
				"Poland", 
				2, 
				"pl", 
				3, 
				true,
				new String[] {"poland", "pl", "pol", "polska"}, 
				new Integer[] {4, 6, 85}, //cs, dd, su
				"", 
				new int[] {0,3}
				));
		map.add(new Country(
				"Romania", 
				2, 
				"ro", 
				3, 
				false, 
				new String[] {"romania", "rou", "ro"}, 
				new Integer[] {10, 17, 20, 85}, //hu, tr, yu, su
				"", 
				new int[] {1,3}
				));
		map.add(new Country(
				"Spain/Portugal", 
				1, 
				"es::flag_pr", 
				2, 
				false, 
				new String[] {"spainportugal", "es", "esp", "españa", "espana", "spain", "portugal", "pr", "prt"}, 
				new Integer[] {8, 11, 53}, //fr, it, ma
				"", 
				new int[] {1,0}
				));
		map.add(new Country(
				"Sweden", 
				1, 
				"se", 
				4, 
				false, 
				new String[] {"sweden", "se", "swe", "sverige"}, 
				new Integer[] {5, 7, 12}, //dk, fi, no
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Turkey", 
				1, 
				"tr", 
				2, 
				false, 
				new String[] {"turkey", "tr", "tur", "turkiye", "türkiye"}, 
				new Integer[] {2, 9, 14, 30}, //bg, gr, ro, sy
				"", 
				new int[] {2,0}
				));
		map.add(new Country(
				"United Kingdom", 
				1, 
				"gb", 
				5, 
				false, 
				new String[] {"uk", "gbr", "gb", "unitedkingdom", "britain"}, 
				new Integer[] {1, 3, 8, 12}, //be, ca, fr, no
				"", 
				new int[] {5,0}
				));
		map.add(new Country(
				"West Germany", 
				1, 
				"de", 
				4, 
				true, 
				new String[] {"westgermany", "de", "deu", "brd"}, 
				new Integer[] {0, 1, 5, 6, 8}, //at, be, dk, dd, fr
				"", 
				new int[] {5,1}
				));
		map.add(new Country(
				"Yugoslavia", 
				2, 
				"yu", 
				3, 
				false, 
				new String[] {"yugoslavia", "yu", "yug", "yuhh", "jugoslavija"}, 
				new Integer[] {9, 10, 11, 14}, //gr, hu, it, ro
				"", 
				new int[] {1,2}
				));
		//Middle East - 21
		map.add(new Country(
				"Egypt", 
				3, 
				"eg", 
				2, 
				true, 
				new String[] {"egypt", "egy", "eg", "misr", "masr"}, 
				new Integer[] {25, 28, 59}, //il, ly, sd
				"", 
				new int[] {1,0}
				));
		map.add(new Country(
				"Gulf States", 
				3, 
				"ae", 
				3, 
				false, 
				new String[] {"gulfstates", "ae", "are", "unitedarabemirates", "imarat", "uae", "oman","uman","omn","om","qatar","qat","qa","bahrain","bhr","bahrayn","bh","kuwait","kwt","kw"}, 
				new Integer[] {24, 29}, //iq, sa
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Iran", 
				3, 
				"ir", 
				2, 
				true, 
				new String[] {"iran", "irn", "ir", "persia"}, 
				new Integer[] {24, 31, 40}, //iq, af, pk
				"", 
				new int[] {2,0}
				));
		map.add(new Country(
				"Iraq", 
				3, 
				"iq", 
				3, 
				true, 
				new String[] {"iraq", "irq", "iq"}, 
				new Integer[] {22, 23, 26, 29}, //ae, ir, jo, sa
				"", 
				new int[] {0,3}
				));
		map.add(new Country(
				"Israel", 
				3, 
				"il", 
				4, 
				true, 
				new String[] {"israel", "isr", "il", "yisrael", "israil"}, 
				new Integer[] {21, 26, 27, 30}, //eg, jo, lb, sy
				"", 
				new int[] {4,0}
				));
		map.add(new Country(
				"Jordan", 
				3, 
				"jo", 
				2, 
				false, 
				new String[] {"jordan", "jor", "jo", "urdunn"}, 
				new Integer[] {24, 25, 27, 29}, //iq, il, lb, sa
				"", 
				new int[] {2,2}
				));
		map.add(new Country(
				"Lebanon", 
				3, 
				"lb", 
				1, 
				false, 
				new String[] {"lebanon", "lbn", "lb", "lubnan"}, 
				new Integer[] {25, 26, 30}, //il, jo, sy
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Libya", 
				3, 
				"ly", 
				2, 
				true, 
				new String[] {"libya", "lby", "ly", "libiyyah"}, 
				new Integer[] {21, 60}, //eg, tn
				"", 
				new int[] {0,2}
				));
		map.add(new Country(
				"Saudi Arabia", 
				3, 
				"sa", 
				3, 
				true, 
				new String[] {"saudiarabia", "sau", "sa", "saudiyah", "ksa"}, 
				new Integer[] {22, 24, 26}, //ae, iq, jo
				"", 
				new int[] {2,0}
				));
		map.add(new Country(
				"Syria", 
				3, 
				"sy", 
				2, 
				false, 
				new String[] {"syria", "syr", "sy", "suriyah"}, 
				new Integer[] {17, 25, 27}, //tr, il, lb
				"", 
				new int[] {0,3}
				));
		//Asia - 31
		map.add(new Country(
				"Afghanistan", 
				4, 
				"af", 
				2, 
				false, 
				new String[] {"afghanistan", "afg", "af", "afganestan"}, 
				new Integer[] {23, 40, 85}, //ir, pk, su
				"", 
				new int[] {0,2}
				));
		map.add(new Country(
				"Australia", 
				4, 
				"au", 
				4, 
				false, 
				new String[] {"australia", "aus", "au"}, 
				new Integer[] {38}, //my
				"", 
				new int[] {4,0}
				));
		map.add(new Country(
				"Burma/Myanmar", 
				5, 
				"bu", //codes for the flag of the Union of Burma (six stars, blue canton, red field) instead of the current flag
				2, 
				false, 
				new String[] {"burma", "bur", "bu", "bumm", "myanmar", "mmr", "mm"}, 
				new Integer[] {34, 37}, //in, la
				"", 
				new int[] {0,1}
				));
		map.add(new Country(
				"India", 
				4, 
				"in", 
				3, 
				true, 
				new String[] {"india", "ind", "in", "bharat"}, 
				new Integer[] {33, 40}, //bu, pk
				"", 
				new int[] {0,3}
				));
		map.add(new Country(
				"Indonesia", 
				5, 
				"id", 
				1, 
				false, 
				new String[] {"indonesia", "idn", "id"}, 
				new Integer[] {38, 41}, //my, ph
				"", 
				new int[] {1,0}
				));
		map.add(new Country(
				"Japan", 
				4, 
				"jp", 
				4, 
				true, 
				new String[] {"japan", "jpn", "jp", "nihon", "nippon"}, 
				new Integer[] {41, 42, 43, 84}, //ph, kr, tw, us
				"", 
				new int[] {4,0}
				));
		map.add(new Country(
				"Laos/Cambodia", 
				5, 
				"la::flag_kh", 
				1, 
				false, 
				new String[] {"laoscambodia", "lao", "la", "laos", "cambodia", "kampucie", "kh", "khm"}, 
				new Integer[] {33,44,45}, //bu, th, vn
				"", 
				new int[] {0,2}
				));
		map.add(new Country(
				"Malaysia", 
				5, 
				"my", 
				2, 
				false, 
				new String[] {"malaysia", "mys", "my"}, 
				new Integer[] {32, 35, 44}, //au, id, th
				"", 
				new int[] {3,1}
				));
		map.add(new Country(
				"North Korea", 
				4, 
				"kp", 
				3, 
				true, 
				new String[] {"northkorea", "dprk", "prk", "kp","choson","bukhan","bukchoson"}, 
				new Integer[] {42, 85}, //kr, su
				"", 
				new int[] {0,3}
				));
		map.add(new Country(
				"Pakistan", 
				4, 
				"pk", 
				2, 
				true, 
				new String[] {"pakistan", "pak", "pk"}, 
				new Integer[] {23, 31, 34}, //ir, af, in
				"", 
				new int[] {2,0}
				));
		map.add(new Country(
				"Philippines", 
				5, 
				"ph", 
				2, 
				false, 
				new String[] {"philippines", "phl", "ph", "pilipinas"}, 
				new Integer[] {35, 36}, //id, jp
				"", 
				new int[] {3,1}
				));
		map.add(new Country(
				"South Korea", 
				4, 
				"kr",
				3, 
				true, 
				new String[] {"southkorea", "kor", "kr", "hanguk","namhan","namjoson"}, 
				new Integer[] {36, 39, 43}, //jp, pk, tw
				"", 
				new int[] {3,0}
				));
		map.add(new Country(
				"Taiwan", 
				4, 
				"tw", 
				3, 
				false, 
				new String[] {"taiwan", "tw", "twn"}, 
				new Integer[] {36, 42}, //jp, kr
				"", 
				new int[] {3,0}
				));
		map.add(new Country(
				"Thailand", 
				5, 
				"th", 
				2, 
				true, 
				new String[] {"thailand", "tha", "th", "thai", "siam"}, 
				new Integer[] {37, 38, 45}, //la, my, vn
				"", 
				new int[] {2,0}
				));
		map.add(new Country(
				"Vietnam", 
				5, 
				"vn", 
				2, 
				false, 
				new String[] {"vietnam", "vnm", "vn", "vdr", "vd"}, 
				new Integer[] {37, 44}, //la, th
				"", 
				new int[] {0,5}
				));
		//Africa - 46
		map.add(new Country(
				"Algeria", 
				6, 
				"dz", 
				2, 
				true, 
				new String[] {"algeria", "dza", "dz", "dzayir", "jazair"}, 
				new Integer[] {8, 53, 56, 60}, //fr, ma, td, tn
				"", 
				new int[] {0,2}
				));
		map.add(new Country(
				"Angola", 
				6, 
				"ao", 
				1, 
				true, 
				new String[] {"angola", "ago", "ao"}, 
				new Integer[] {48, 58, 62}, //bw, za, zr
				"", 
				new int[] {1,3}
				));
		map.add(new Country(
				"Botswana", 
				6, 
				"bw", 
				2, 
				false, 
				new String[] {"botswana", "bwa", "bw"}, 
				new Integer[] {47, 58, 63}, //ao, za, zw
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Cameroon", 
				6, 
				"cm", 
				1, 
				false, 
				new String[] {"cameroon", "cmr", "cm"}, 
				new Integer[] {54, 62}, //ng, zr
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Ethiopia", 
				6, 
				"et", 
				1, 
				false, 
				new String[] {"ethiopia", "eth", "et", "ityoppya"}, 
				new Integer[] {57, 59}, //so, sd
				"", 
				new int[] {0,1}
				));
		map.add(new Country(
				"Ivory Coast", 
				6, 
				"ci", 
				2, 
				false, 
				new String[] {"ivorycoast", "civ", "ci", "cotedivoire"}, 
				new Integer[] {54, 61}, //ng, ml
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Kenya", 
				6, 
				"ke", 
				2, 
				false, 
				new String[] {"kenya", "ken", "ke"}, 
				new Integer[] {55, 57}, //mz, so
				"", 
				new int[] {2,0}
				));
		map.add(new Country(
				"Morocco", 
				6, 
				"ma", 
				3, 
				false, 
				new String[] {"morocco", "mar", "ma", "magrib"}, 
				new Integer[] {15, 46, 61}, //es, dz, ml
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Nigeria", 
				6, 
				"ng", 
				1, 
				true, 
				new String[] {"nigeria", "nga", "ng"}, 
				new Integer[] {49, 51, 56}, //cm, ci, td
				"", 
				new int[] {1,0}
				));
		map.add(new Country(
				"Southeast African States", 
				6, 
				"mz", 
				1, 
				false, 
				new String[] {"seafricanstates", "seafrica", "southeastafricanstates", "southeastafrica", "mozambique", "moz", "mz"}, 
				new Integer[] {52, 63}, //ke, zw
				"", 
				new int[] {0,2}
				));
		map.add(new Country(
				"Saharan States", 
				6, 
				"td", //chad
				1, 
				false, 
				new String[] {"saharanstates", "sahara", "chad", "tcd", "td"}, 
				new Integer[] {46, 54}, //dz, ng
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Somalia", 
				6, 
				"so", 
				2, 
				false, 
				new String[] {"somalia", "som", "so", "soomaaliya", "sumal"}, 
				new Integer[] {50, 52}, //et, ke
				"", 
				new int[] {2,0}
				));
		map.add(new Country(
				"South Africa", 
				6, 
				"za", 
				3, 
				false, 
				new String[] {"southafrica", "zaf", "za", "suidafrika"}, 
				new Integer[] {47, 48}, //ao, bw
				"", 
				new int[] {2,1}
				));
		map.add(new Country(
				"Sudan", 
				6, 
				"sd", 
				1, 
				false, 
				new String[] {"sudan", "sdn", "sd"}, 
				new Integer[] {21, 50}, //eg, et
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Tunisia", 
				6, 
				"tn", 
				2, 
				false, 
				new String[] {"tunisia", "tun", "tn", "tunis"}, 
				new Integer[] {28, 46}, //ly, dz
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"West African States", 
				6, 
				"ml", 
				2, 
				false, 
				new String[] {"westafricanstates", "westafrica", "mli", "mali", "ml"}, 
				new Integer[] {51, 53}, //ci, ma
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Zaire", 
				6, 
				"zr", 
				1, 
				true, 
				new String[] {"zaire", "zar", "zr", "drc", "cod", "cd"}, 
				new Integer[] {47, 49, 63}, //ao, cm, zw
				"", 
				new int[] {1,0}
				));
		map.add(new Country(
				"Zimbabwe", 
				6, 
				"zw", 
				1, 
				false, 
				new String[] {"zimbabwe", "zwe", "zw"}, 
				new Integer[] {48, 55, 62}, //bw, mz, zr
				"", 
				new int[] {0,1}
				));
		//Central America - 64
		map.add(new Country(
				"Costa Rica", 
				7, 
				"cr", 
				3, 
				false, 
				new String[] {"costarica", "cri", "cr"}, 
				new Integer[] {70, 72, 73}, //hn, ni, pa
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Cuba", 
				7, 
				"cu", 
				3, 
				true, 
				new String[] {"cuba", "cub", "cu"}, 
				new Integer[] {69, 72, 84}, //ht, ni, us
				"", 
				new int[] {0,3}
				));
		map.add(new Country(
				"Dominican Republic", 
				7, 
				"do", 
				1, 
				false, 
				new String[] {"dominicanrepublic", "dom", "do"}, 
				new Integer[] {69}, //ht
				"", 
				new int[] {1,0}
				));
		map.add(new Country(
				"El Salvador", 
				7, 
				"sv", 
				1, 
				false, 
				new String[] {"elsalvador", "slv", "sv"}, 
				new Integer[] {68, 70}, //gt, hn
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Guatemala", 
				7, 
				"gt", 
				1, 
				false, 
				new String[] {"guatemala", "gtm", "gt"}, 
				new Integer[] {67, 70, 71}, //sv, hn, mx
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Haiti", 
				7, 
				"ht", 
				1, 
				false, 
				new String[] {"haiti", "hti", "ht", "ayiti", "haïti"}, 
				new Integer[] {65, 66}, //cu, do
				"", 
				new int[] {1,0}
				));
		map.add(new Country(
				"Honduras", 
				7, 
				"hn", 
				2, 
				false, 
				new String[] {"honduras", "hnd", "hn"}, 
				new Integer[] {64, 67, 68, 72}, //cr, sv, gt, ni
				"", 
				new int[] {2,0}
				));
		map.add(new Country(
				"Mexico", 
				7, 
				"mx", 
				2, 
				true, 
				new String[] {"mexico", "mex", "mx", "méxico"}, 
				new Integer[] {68, 84}, //gt, us
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Nicaragua", 
				7, 
				"ni", 
				1, 
				false, 
				new String[] {"nicaragua", "nic", "ni"}, 
				new Integer[] {64, 65, 70}, //cr, cu, hn
				"", 
				new int[] {1,0}
				));
		map.add(new Country(
				"Panama", 
				7, 
				"pa", 
				2, 
				true, 
				new String[] {"panama", "pan", "pa"}, 
				new Integer[] {64, 78}, //cr, co
				"", 
				new int[] {2,0}
				));
		//South America - 74
		map.add(new Country(
				"Argentina", 
				8, 
				"ar", 
				2, 
				true, 
				new String[] {"argentina", "arg", "ar"}, 
				new Integer[] {77, 80, 82}, //cl, py, uy
				"", 
				new int[] {2,0}
				));
		map.add(new Country(
				"Bolivia", 
				8, 
				"bo", 
				2, 
				false, 
				new String[] {"bolivia", "bol", "bo"}, 
				new Integer[] {80, 81}, //py, pe
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Brazil", 
				8, 
				"br", 
				2, 
				true, 
				new String[] {"brazil", "bra", "br", "brasil"}, 
				new Integer[] {82, 83}, //uy, ve
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Chile", 
				8, 
				"cl", 
				3, 
				true, 
				new String[] {"chile", "chl", "cl"}, 
				new Integer[] {74, 81}, //ar, pe
				"", 
				new int[] {3,0}
				));
		map.add(new Country(
				"Colombia", 
				8, 
				"co", 
				1, 
				false, 
				new String[] {"colombia", "col", "co"}, 
				new Integer[] {73, 79, 83}, //pa, ec, ve
				"", 
				new int[] {2,1}
				));
		map.add(new Country(
				"Ecuador", 
				8, 
				"ec", 
				2, 
				false, 
				new String[] {"ecuador", "ecu", "ec"}, 
				new Integer[] {78, 81}, //co, pe
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Paraguay", 
				8, 
				"py", 
				2, 
				false, 
				new String[] {"paraguay", "pry", "py"}, 
				new Integer[] {74, 75, 82}, //ar, bo, uy
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Peru", 
				8, 
				"pe", 
				2, 
				false, 
				new String[] {"peru", "per", "pe"}, 
				new Integer[] {75, 77, 79}, //bo, cl, ec
				"", 
				new int[] {2,1}
				));
		map.add(new Country(
				"Uruguay", 
				8, 
				"uy", 
				2, 
				false, 
				new String[] {"uruguay", "ury", "uy"}, 
				new Integer[] {74, 76, 80}, //ar, br, py
				"", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Venezuela", 
				8, 
				"ve", 
				2, 
				true, 
				new String[] {"venezuela", "ven", "ve"}, 
				new Integer[] {76, 78}, //br, co
				"", 
				new int[] {2,0}
				));
		//Superpowers - 84 - really only relevant in case I need flags for Brush War/realignments
		//TODO set up precautions in case someone tries funny business in the superpowers :P
		map.add(new Country(
				"United States of America", 
				9, 
				"us", 
				7, 
				false, 
				new String[] {"unitedstates", "usa", "us","america","democrat", "capitalist"}, 
				new Integer[] {3, 36, 65, 71}, //ca, jp, cu, mx
				"", 
				new int[] {2147483647,-2147483648} //don't even bother...
				));
		map.add(new Country(
				"Union of Soviet Socialist Republics", 
				9, 
				"su", 
				7, 
				false, 
				new String[] {"unionofsovietsocialistrepublics", "sun", "su","sovietunion","ussr", "sovetskysoyuz","sssr","communist","ссср", "soviet"}, 
				new Integer[] {7, 13, 14, 31, 39}, //fi, pl, ro, af, kp
				"", 
				new int[] {-2147483648,2147483647} //don't even bother...
				));
		map.add(new Country(
				"China", 
				9, 
				"cn", 
				3, 
				false, 
				new String[] {"china", "chn", "cn","prc","zhongguo", "zhonghuarenmingongheguo","peoplesrepublicofchina"}, 
				new Integer[] {85}, //su
				"", 
				new int[] {-2147483648,3} //If you're the US you probably can't put things here anyways
				));
	}
}

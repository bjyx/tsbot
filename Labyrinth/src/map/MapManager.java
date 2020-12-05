package map;

import java.util.ArrayList;
import java.util.Arrays;

import game.GameData;

//import readwrite.ReadWrite;
/**
 * Handles the map for the game.
 * @author adalbert
 *
 */
public class MapManager {
	/**
	 * The map used in the game.
	 */
	private static ArrayList<Country> map;
	/**
	 * The capital of the caliphate, should there be one (otherwise, -1).
	 */
	public static int caliph = -1;
	/**
	 * The number of troops that are "off map".
	 */
	public static int offmap = 0;
	/**
	 * Generates the state of the map as it is at setup. And yes, that means creating every damn country individually. <br>
	 * A list:
	 * US	0
	 * CA	1
	 * GB	2
	 * ES	3
	 * FR	4
	 * BE	5
	 * DE	6
	 * DK	7
	 * IT	8
	 * PL	9 <br>
	 * RS	10
	 * RU	11
	 * GE	12
	 * TR	13
	 * SY	14
	 * LB	15
	 * IL	16
	 * JO	17
	 * IQ	18
	 * SA	19 <br>
	 * YE	20
	 * AE	21
	 * IR	22
	 * KZ	23
	 * AF	24
	 * PK	25
	 * IN	26
	 * ID	27
	 * CN	28
	 * TH	29 <br>
	 * PH	30
	 * SO	31
	 * KE	32
	 * SD	33
	 * EG	34
	 * LY	35
	 * DZ	36
	 * MA	37
	 * ML	38
	 * NG	39
	 */
	public static void initialize(int scenario) {
		map = new ArrayList<Country>();
		//Start at US - 0
		map.add(new Country(
				"United States", //0
				2, 1, 0, false,
				new String[] {"unitedstates","usa","us","america","unitedstatesofamerica"},
				new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 30},
				"",
				"us"
				));
		map.add(new Country(
				"Canada", //1
				2, 1, 0, false,
				new String[] {"canada","can","ca"},
				new Integer[] {0, 2, 3, 4, 5, 6, 7, 8, 9},
				"",
				"ca"
				));
		map.add(new Country(
				"United Kingdom", //2
				2, 1, 3, false,
				new String[] {"unitedkingdom","uk","gbr","gb","greatbritain"},
				new Integer[] {0, 1, 3, 4, 5, 6, 7, 8, 9},
				"",
				"gb"
				));
		map.add(new Country(
				"Spain", //3
				2, 1, 2, false,
				new String[] {"spain","esp","es","espana"},
				new Integer[] {0, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 13, 15, 35, 36, 37}, //schengen borders: us, ca, gb, rs, ru, tr, lb, ly, dz, ma
				"",
				"es"
				));
		map.add(new Country(
				"France", //4
				2, 1, 2, false,
				new String[] {"france","fra","fr"},
				new Integer[] {0, 1, 2, 3, 5, 6, 7, 8, 9, 10, 11, 13, 15, 35, 36, 37}, //schengen borders: us, ca, gb, rs, ru, tr, lb, ly, dz, ma
				"",
				"fr"
				));
		map.add(new Country(
				"Benelux", //5
				2, 1, 0, false,
				new String[] {"benelux","belgium","bel","be","belgique","belgie","luxembourg","luxemburg","letzebuerg","lux","lu","nederland","netherlands","nld","nl"},
				new Integer[] {0, 1, 2, 3, 4, 6, 7, 8, 9, 10, 11, 13, 15, 35, 36, 37}, //schengen borders: us, ca, gb, rs, ru, tr, lb, ly, dz, ma
				"",
				"be"
				));
		map.add(new Country(
				"Germany", //6
				2, 1, 0, false,
				new String[] {"germany","deu","de","deutschland"},
				new Integer[] {0, 1, 2, 3, 4, 5, 7, 8, 9, 10, 11, 13, 15, 35, 36, 37}, //schengen borders: us, ca, gb, rs, ru, tr, lb, ly, dz, ma
				"",
				"de"
				));
		map.add(new Country(
				"Scandinavia", //7
				2, 1, 0, false,
				new String[] {"scandinavia","dnk","dk","denmark","danmark","sweden","sverige","swe","se","norway","norge","nor","no"},
				new Integer[] {0, 1, 2, 3, 4, 5, 6, 8, 9, 10, 11, 13, 15, 35, 36, 37}, //schengen borders: us, ca, gb, rs, ru, tr, lb, ly, dz, ma
				"",
				"dk" //selected because Danish Cartoons
				));
		map.add(new Country(
				"Italy", //6
				2, 1, 0, false,
				new String[] {"italy","ita","it","italia"},
				new Integer[] {0, 1, 2, 3, 4, 5, 6, 7, 9, 10, 11, 13, 15, 35, 36, 37}, //schengen borders: us, ca, gb, rs, ru, tr, lb, ly, dz, ma
				"",
				"it"
				));
		map.add(new Country(
				"Eastern Europe", //9
				2, 1, 0, false,
				new String[] {"easterneurope","eeu","eeurope","poland","polska","pol","pl"}, //to simplify this one, only Poland is counted
				new Integer[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11, 13, 15, 35, 36, 37}, //schengen borders: us, ca, gb, rs, ru, tr, lb, ly, dz, ma
				"",
				"pl"
				));
		map.add(new Country(
				"Serbia", //10
				2, 1, 0, false,
				new String[] {"serbia","srb","rs","srbija"},
				new Integer[] {3, 4, 5, 6, 7, 8, 9, 11, 13},
				"",
				"rs"
				));
		map.add(new Country(
				"Russia", //11
				2, 2, 0, false,
				new String[] {"russia","rus","ru","rossiya"},
				new Integer[] {3, 4, 5, 6, 7, 8, 9, 10, 12, 13, 23},
				"",
				"ru"
				));
		map.add(new Country(
				"Caucasus", //12
				2, 2, 0, false,
				new String[] {"caucasus","geo","ge","georgia","sakartvelo"}, //again, only georgia here for brevity, and also because Azerbaijan doesn't fit in a non-muslim region
				new Integer[] {11, 13, 22, 23},
				"",
				"ge"
				));
		map.add(new Country(
				"Turkey", //13
				1, 0, 2, false,
				new String[] {"turkey","tur","tr","turkiye"}, 
				new Integer[] {3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 18, 22}, //schengen, rs, ru, ge, sy, iq, ir
				"",
				"tr"
				));
		map.add(new Country(
				"Syria", //14
				0, 0, 2, false,
				new String[] {"syria","syr","sy","suriyah"}, 
				new Integer[] {13, 15, 17, 18},
				"",
				"sy"
				));
		map.add(new Country(
				"Lebanon", //15
				1, 0, 1, false,
				new String[] {"lebanon","lbn","lb","lubnan"}, 
				new Integer[] {3, 4, 5, 6, 7, 8, 9, 14, 16},
				"",
				"lb"
				));
		map.add(new Country(
				"Israel", //16
				2, 1, 0, false,
				new String[] {"israel","isr","il","yisrael"}, 
				new Integer[] {15, 17, 34},
				"",
				"il"
				));
		map.get(16).setPosture(1); //Israel is always hard-postured
		map.add(new Country(
				"Jordan", //17
				0, 0, 1, false,
				new String[] {"jordan","jor","jo","urdun"}, 
				new Integer[] {14, 16, 18, 19},
				"",
				"jo"
				));
		map.add(new Country(
				"Iraq", //18
				1, 0, 3, true,
				new String[] {"iraq","irq","iq"},
				new Integer[] {13, 14, 17, 19, 21, 22},
				"",
				"iq"
				));
		map.add(new Country(
				"Saudi Arabia", //19
				1, 0, 3, true,
				new String[] {"saudiarabia","sau","sa","ksa"}, //is there a shorthand for Saudi Arabia in Arabic?
				new Integer[] {17, 18, 20, 21},
				"",
				"sa"
				));
		map.add(new Country(
				"Yemen", //20
				1, 0, 1, false,
				new String[] {"yemen","yem","ye","yaman"}, 
				new Integer[] {19, 31},
				"",
				"ye"
				));
		map.add(new Country(
				"Gulf States", //21
				1, 0, 3, true,
				new String[] {"gulfstates","kwt","kw","kuwait","om","omn","oman","uman","qatar","qat","qa","uae","are","ae","emirates","imarat","bahrain","bhr","bh"}, //kuwait + qatar + uae + oman + bahrain
				new Integer[] {18, 19, 22, 25},
				"",
				"bh" //Bahrain chosen mainly for the Shi'a-Sunni conflict and Arab Spring demonstrations
				));
		map.add(new Country(
				"Iran", //22
				3, 2, 2, true, //starts special
				new String[] {"iran","irn","ir"},
				new Integer[] {13, 18, 21, 23, 24, 25},
				"",
				"ir"
				));
		map.add(new Country(
				"Central Asia", //23
				0, 0, 2, false,
				new String[] {"centralasia","kaz","kz","kazakhstan","qazaqstan", "uzbekistan","ozbekiston","uzb","uz"}, //Kazakhstan figures in most heavily, but there's also the Uzbek union thing to contend with
				new Integer[] {11, 12, 22, 24, 28},
				"",
				"kz"
				));
		map.add(new Country(
				"Afghanistan", //24
				1, 0, 1, false, 
				new String[] {"afghanistan","afg","af"},
				new Integer[] {22, 23, 25},
				"",
				"af"
				));
		map.add(new Country(
				"Pakistan", //25
				1, 0, 2, false, 
				new String[] {"pakistan","pak","pk"},
				new Integer[] {21, 22, 24, 26, 27},
				"",
				"pk"
				));
		map.add(new Country(
				"India", //26
				2, 1, 0, false, 
				new String[] {"india","ind","in","bharat"},
				new Integer[] {25, 27},
				"",
				"in"
				));
		map.add(new Country(
				"Indonesia/Malaysia", //27
				0, 0, 3, true, 
				new String[] {"indonesia","ido","id","malaysia","mys","my"},
				new Integer[] {25, 26, 29, 30},
				"",
				"id"
				));
		map.add(new Country(
				"China", //28
				2, 2, 0, false, 
				new String[] {"china","chn","cn","zhongguo"},
				new Integer[] {23, 29},
				"",
				"cn"
				));
		map.add(new Country(
				"Thailand", //29
				2, 2, 0, false, 
				new String[] {"thailand","tha","th","thai"},
				new Integer[] {27, 28, 30},
				"",
				"th"
				));
		map.add(new Country(
				"Philippines", //30
				2, 2, 3, false, 
				new String[] {"philippines","phl","ph","pilipinas"},
				new Integer[] {27, 29},
				"",
				"ph"
				));
		map.add(new Country(
				"Somalia", //31
				0, 0, 1, false, 
				new String[] {"somalia","som","so","soomaaliya","sumal"},
				new Integer[] {20, 32, 33},
				"",
				"so"
				));
		map.add(new Country(
				"Kenya/Tanzania", //32
				2, 2, 0, false, 
				new String[] {"kenya","ken","ke","tanzania","tza","tz"},
				new Integer[] {31, 33},
				"",
				"ke"
				));
		map.add(new Country(
				"Sudan", //33
				0, 0, 1, true, 
				new String[] {"sudan","sdn","sd"},
				new Integer[] {31, 32, 34, 35},
				"",
				"sd"
				));
		map.add(new Country(
				"Egypt", //34
				0, 0, 3, false, 
				new String[] {"egypt","egy","eg","masr","misr"},
				new Integer[] {16, 33, 35},
				"",
				"eg"
				));
		map.add(new Country(
				"Libya", //35
				0, 0, 1, true,
				new String[] {"libya","lby","ly","libiya"}, 
				new Integer[] {3, 4, 5, 6, 7, 8, 9, 33, 34, 36},
				"",
				"ly"
				));
		map.add(new Country(
				"Algeria/Tunisia", //36
				0, 0, 2, true,
				new String[] {"algeria","dza","dz","jazair","tunisia","tunis","tun","tn"}, 
				new Integer[] {3, 4, 5, 6, 7, 8, 9, 35, 37},
				"",
				"dz"
				));
		map.add(new Country(
				"Morocco", //37
				0, 0, 2, false,
				new String[] {"morocco","mar","ma","maghrib","maroc"}, 
				new Integer[] {3, 4, 5, 6, 7, 8, 9, 36},
				"",
				"ma"
				));
		if (scenario > 3) { //AWK/FW only
			if (scenario != 10 && scenario != 11 && scenario != 15 && scenario != 16) map.get(14).religion = 1; //sy is shia-mix at the start of awakening and forever war only
			map.get(32).adj.add(39);
			map.get(33).adj.add(39);
			map.get(36).adj.add(38);
			map.get(37).adj.add(38);
			map.add(new Country(
					"Mali", //38
					0, 0, 1, false,
					new String[] {"mali","mli","ml"}, 
					new Integer[] {36, 37, 39},
					"",
					"ml"
					));
			map.add(new Country(
					"Nigeria", //39
					2, 3, 2, true, //non-muslim, poor, 2 resource (exception hardcoded)
					new String[] {"nigeria","nga","ng"}, 
					new Integer[] {38, 32, 33},
					"",
					"ng"
					));
		}
		//end - 39
		//scenario-specific setup
		switch (scenario) {
		case 0://let's roll!
			//us hard
			map.get(0).setPosture(1);
			//ly: poor adv
			map.get(35).setPosture(3);
			map.get(35).setGovernance(3);
			//sy: fair adv
			map.get(14).setPosture(3);
			map.get(14).setGovernance(2);
			//iq: poor adv
			map.get(18).setPosture(3);
			map.get(18).setGovernance(3);
			//sa: poor ally, 2 troops
			map.get(19).setPosture(1);
			map.get(19).setGovernance(3);
			map.get(19).editUnits(-2, 2);
			//bh: fair ally, 2 troops
			map.get(21).setPosture(1);
			map.get(21).setGovernance(2);
			map.get(21).editUnits(-2, 2);
			//pk: fair neut
			map.get(25).setPosture(2);
			map.get(25).setGovernance(2);
			//af: ir, 4 sleepers
			map.get(24).setGovernance(4);
			map.get(24).setPosture(3);
			map.get(24).editUnits(1, 4);
			//so: besieged
			map.get(31).br = true;
			break;
		case 1: //call me al
			//us soft
			map.get(0).setPosture(-1);
			//ly: poor adv
			map.get(35).setPosture(3);
			map.get(35).setGovernance(3);
			//sy: fair adv
			map.get(14).setPosture(3);
			map.get(14).setGovernance(2);
			//iq: poor adv
			map.get(18).setPosture(3);
			map.get(18).setGovernance(3);
			//sa: poor ally, 2 troops
			map.get(19).setPosture(1);
			map.get(19).setGovernance(3);
			map.get(19).editUnits(-2, 2);
			//bh: fair ally, 2 troops
			map.get(21).setPosture(1);
			map.get(21).setGovernance(2);
			map.get(21).editUnits(-2, 2);
			//pk: fair neut
			map.get(25).setPosture(2);
			map.get(25).setGovernance(2);
			//af: ir, 4 sleepers
			map.get(24).setGovernance(4);
			map.get(24).setPosture(3);
			map.get(24).editUnits(1, 4);
			//so: besieged
			map.get(31).br = true;
			break;
		case 2: //anaconda
			//us hard
			map.get(0).setPosture(1);
			map.get(0).adj = new ArrayList<Integer>(Arrays.asList(1)); //patriot
			//ly: poor adv
			map.get(35).setPosture(3);
			map.get(35).setGovernance(3);
			//sy: fair adv
			map.get(14).setPosture(3);
			map.get(14).setGovernance(2);
			//iq: poor adv
			map.get(18).setPosture(3);
			map.get(18).setGovernance(3);
			//sa: poor ally, 2 troops
			map.get(19).setPosture(1);
			map.get(19).setGovernance(3);
			map.get(19).editUnits(-2, 2);
			//bh: fair ally, 2 troops
			map.get(21).setPosture(1);
			map.get(21).setGovernance(2);
			map.get(21).editUnits(-2, 2);
			//pk: poor ally, 1 cell
			map.get(25).setPosture(1);
			map.get(25).setGovernance(3);
			map.get(25).editUnits(1, 1);
			//af: rc poor ally, 6 troops, 1 sleeper
			map.get(24).setGovernance(3);
			map.get(24).setPosture(1);
			map.get(24).editUnits(-2, 6);
			map.get(24).editUnits(1, 1);
			map.get(24).rc = 2;
			//so: besieged
			map.get(31).br = true;
			//kz: poor ally
			map.get(23).setPosture(1);
			map.get(23).setGovernance(3);
			//setup phase required for something
			break;
		case 3: //mission accomplished?
			//us hard
			map.get(0).setPosture(1);
			map.get(0).adj = new ArrayList<Integer>(Arrays.asList(1)); //patriot
			//ly: poor adv
			map.get(35).setPosture(3);
			map.get(35).setGovernance(3);
			//sy: fair adv, 1 cell
			map.get(14).setPosture(3);
			map.get(14).setGovernance(2);
			map.get(14).editUnits(1, 1);
			//iq: rc poor ally, 6T3C
			map.get(18).setPosture(1);
			map.get(18).setGovernance(3);
			map.get(18).editUnits(-2, 6);
			map.get(18).editUnits(1, 3);
			map.get(18).rc = 2;
			//ir: 1 cell
			map.get(22).editUnits(1, 1);
			//sa: poor ally, 1 cell
			map.get(19).setPosture(1);
			map.get(19).setGovernance(3);
			map.get(19).editUnits(1, 1);
			//bh: fair ally, 2 troops
			map.get(21).setPosture(1);
			map.get(21).setGovernance(2);
			map.get(21).editUnits(-2, 2);
			//pk: fair ally, 1 cell
			map.get(25).setPosture(1);
			map.get(25).setGovernance(2);
			map.get(25).editUnits(1, 1);
			//af: rc poor ally, 5 troops, 1 sleeper
			map.get(24).setGovernance(3);
			map.get(24).setPosture(1);
			map.get(24).editUnits(-2, 5);
			map.get(24).editUnits(1, 1);
			map.get(24).rc = 2;
			//so: besieged
			map.get(31).br = true;
			//kz: fair neutral
			map.get(23).setPosture(2);
			map.get(23).setGovernance(2);
			//id: fair neutral, 1 cell
			map.get(27).setPosture(2);
			map.get(27).setGovernance(2);
			map.get(27).editUnits(1, 1);
			//ph: soft, 2 troops, 1 cell
			map.get(30).setPosture(-1);
			map.get(24).editUnits(-2, 2);
			map.get(24).editUnits(1, 1);
			//gb: hard
			map.get(2).setPosture(1);
			//schengen: roll all, reroll one hard
			map.get(3).testCountry();
			map.get(4).testCountry();
			map.get(5).testCountry();
			map.get(6).testCountry();
			map.get(7).testCountry();
			map.get(8).testCountry();
			map.get(9).testCountry();
			//auto-rerolls one hard schengen country in optimal order
			if (GameData.player==2) {
				for (int i=3; i<=9; i++) {
					if (map.get(i).getPosture()==1) {
						map.get(i).rollCountry();
						break;
					}
				}
			}
			break;
		case 4: //awakening
			//us soft
			map.get(0).setPosture(-1);
			//dz: poor neutral, 1 Awakening
			map.get(36).setPosture(2);
			map.get(36).setGovernance(3);
			map.get(36).spring[0]=1;
			//iq: poor ally, 2T1C
			map.get(18).setPosture(1);
			map.get(18).setGovernance(3);
			map.get(18).editUnits(-2, 2);
			map.get(18).editUnits(1, 1);
			//bh: fair ally, 2T
			map.get(21).setPosture(1);
			map.get(21).setGovernance(2);
			map.get(21).editUnits(-2, 2);
			//pk: fair neutral, 2C
			map.get(25).setPosture(2);
			map.get(25).setGovernance(2);
			map.get(25).editUnits(1, 2);
			//af: rc poor ally, 6T2C
			map.get(24).setGovernance(3);
			map.get(24).setPosture(1);
			map.get(24).editUnits(-2, 6);
			map.get(24).editUnits(1, 2);
			map.get(24).rc = 2;
			//gb: hard
			map.get(2).setPosture(1);
			//fr: hard
			map.get(4).setPosture(1);
			//be: soft
			map.get(5).setPosture(-1);
			break;
		case 5: //alt awakening
			//us hard
			map.get(0).setPosture(1);
			//dz: poor neutral, 1 Awakening
			map.get(36).setPosture(2);
			map.get(36).setGovernance(3);
			map.get(36).spring[0]=1;
			//iq: poor ally, 2T1C
			map.get(18).setPosture(1);
			map.get(18).setGovernance(3);
			map.get(18).editUnits(-2, 2);
			map.get(18).editUnits(1, 1);
			//bh: fair ally, 2T
			map.get(21).setPosture(1);
			map.get(21).setGovernance(2);
			map.get(21).editUnits(-2, 2);
			//pk: fair neutral, 2C
			map.get(25).setPosture(2);
			map.get(25).setGovernance(2);
			map.get(25).editUnits(1, 2);
			//af: rc poor ally, 6T2C
			map.get(24).setGovernance(3);
			map.get(24).setPosture(1);
			map.get(24).editUnits(-2, 6);
			map.get(24).editUnits(1, 2);
			map.get(24).rc = 2;
			//gb: hard
			map.get(2).setPosture(1);
			//fr: hard
			map.get(4).setPosture(1);
			//be: soft
			map.get(5).setPosture(-1);
			//ru: soft
			map.get(11).setPosture(-1);
			break;
		case 6: //arab spring = awakening
			//us soft
			map.get(0).setPosture(-1);
			//dz: poor neutral, 1 Awakening
			map.get(36).setPosture(2);
			map.get(36).setGovernance(3);
			map.get(36).spring[0]=1;
			//iq: poor ally, 2T1C
			map.get(18).setPosture(1);
			map.get(18).setGovernance(3);
			map.get(18).editUnits(-2, 2);
			map.get(18).editUnits(1, 1);
			//bh: fair ally, 2T
			map.get(21).setPosture(1);
			map.get(21).setGovernance(2);
			map.get(21).editUnits(-2, 2);
			//pk: fair neutral, 2C
			map.get(25).setPosture(2);
			map.get(25).setGovernance(2);
			map.get(25).editUnits(1, 2);
			//af: rc poor ally, 6T2C
			map.get(24).setGovernance(3);
			map.get(24).setPosture(1);
			map.get(24).editUnits(-2, 6);
			map.get(24).editUnits(1, 2);
			map.get(24).rc = 2;
			//gb: hard
			map.get(2).setPosture(1);
			//fr: hard
			map.get(4).setPosture(1);
			//be: soft
			map.get(5).setPosture(-1);
			break;
		case 7: //status of forces
			//us soft
			map.get(0).setPosture(-1);
			//iq: poor neutral, 1M2C, 1A1R
			map.get(18).setPosture(2);
			map.get(18).setGovernance(3);
			map.get(18).editUnits(-1, 1);
			map.get(18).editUnits(1, 2);
			map.get(18).spring = new int[] {1, 1};
			//bh: fair ally, 2T, 1A
			map.get(21).setPosture(1);
			map.get(21).setGovernance(2);
			map.get(21).editUnits(-2, 2);
			map.get(21).spring[0] = 1;
			//pk: poor neutral, 1C, 1R
			map.get(25).setPosture(2);
			map.get(25).setGovernance(2);
			map.get(25).editUnits(1, 1);
			map.get(25).spring[1] = 1;
			//af: rc poor ally, 6T2C
			map.get(24).setGovernance(3);
			map.get(24).setPosture(1);
			map.get(24).editUnits(-2, 6);
			map.get(24).editUnits(1, 2);
			map.get(24).rc = 2;
			//sy: poor neut, 2C, 1A1R
			map.get(14).setPosture(2);
			map.get(14).setGovernance(3);
			map.get(14).editUnits(1, 2);
			map.get(14).spring = new int[] {1, 1};
			//eg: fair neut, 1A1R
			map.get(34).setPosture(2);
			map.get(34).setGovernance(2);
			map.get(34).spring = new int[] {1, 1};
			//ly: fair ally
			map.get(35).setPosture(1);
			map.get(35).setGovernance(2);
			//gb: hard
			map.get(2).setPosture(1);
			//fr: hard
			map.get(4).setPosture(1);
			//be: soft
			map.get(5).setPosture(-1);
			break;
		case 8: //isil
			//us hard
			map.get(0).setPosture(1);
			//iq: poor neutral, 2M2C, 1A1R
			map.get(18).setPosture(2);
			map.get(18).setGovernance(3);
			map.get(18).editUnits(-1, 2);
			map.get(18).editUnits(2, 3);
			map.get(18).cw = true;
			//bh: fair ally, 2T, 1A
			map.get(21).setPosture(1);
			map.get(21).setGovernance(2);
			map.get(21).editUnits(-2, 2);
			//pk: poor ally, cadre
			map.get(25).setPosture(1);
			map.get(25).setGovernance(2);
			map.get(25).editUnits(0, 1);
			//af: fair ally, 2T1C
			map.get(24).setGovernance(2);
			map.get(24).setPosture(1);
			map.get(24).editUnits(-2, 2);
			map.get(24).editUnits(1, 1);
			//sy: cw fair neut, 3M4C, 1A1R
			map.get(14).setPosture(2);
			map.get(14).setGovernance(2);
			map.get(14).cw = true;
			map.get(14).editUnits(-1, 3);
			map.get(14).editUnits(2, 4);
			caliph = 14; //raqqa
			//ng: poor neutral, 2 cells
			map.get(39).religion = 0; //set to muslim
			map.get(39).setPosture(2);
			map.get(39).setGovernance(3);
			map.get(39).editUnits(1, 2);
			offmap = 3;
			//gb: hard
			map.get(2).setPosture(1);
			//fr: hard
			map.get(4).setPosture(1);
			//be: hard
			map.get(5).setPosture(1);
			break;
		case 9: //campaign awk
			//us hard
			map.get(0).setPosture(1);
			//ly: poor adv
			map.get(35).setPosture(3);
			map.get(35).setGovernance(3);
			//sy: fair adv
			map.get(14).setPosture(3);
			map.get(14).setGovernance(2);
			//iq: poor adv
			map.get(18).setPosture(3);
			map.get(18).setGovernance(3);
			//sa: poor ally, 2 troops
			map.get(19).setPosture(1);
			map.get(19).setGovernance(3);
			map.get(19).editUnits(-2, 2);
			//bh: fair ally, 2 troops
			map.get(21).setPosture(1);
			map.get(21).setGovernance(2);
			map.get(21).editUnits(-2, 2);
			//pk: fair neut
			map.get(25).setPosture(2);
			map.get(25).setGovernance(2);
			//af: ir, 4 sleepers
			map.get(24).setGovernance(4);
			map.get(24).setPosture(3);
			map.get(24).editUnits(1, 4);
			//so: besieged
			map.get(31).br = true;
			break;
		case 10: //campaign awk althist
			//us hard
			map.get(0).setPosture(1);
			//ly: poor adv
			map.get(35).setPosture(3);
			map.get(35).setGovernance(3);
			//sy: fair adv
			map.get(14).setPosture(3);
			map.get(14).setGovernance(2);
			//iq: poor adv
			map.get(18).setPosture(3);
			map.get(18).setGovernance(3);
			//sa: poor ally, 2 troops
			map.get(19).setPosture(1);
			map.get(19).setGovernance(3);
			map.get(19).editUnits(-2, 2);
			//bh: fair ally, 2 troops
			map.get(21).setPosture(1);
			map.get(21).setGovernance(2);
			map.get(21).editUnits(-2, 2);
			//pk: fair neut
			map.get(25).setPosture(2);
			map.get(25).setGovernance(2);
			//af: ir, 4 sleepers
			map.get(24).setGovernance(4);
			map.get(24).setPosture(3);
			map.get(24).editUnits(1, 4);
			//so: besieged
			map.get(31).br = true;
			break;
		case 11: //fall of isil
			//us hard
			map.get(0).setPosture(1);
			//iq: poor neutral, 2M3C; advisors
			map.get(18).setPosture(2);
			map.get(18).setGovernance(3);
			map.get(18).editUnits(-1, 2);
			map.get(18).editUnits(2, 3);
			map.get(18).cw = true;
			map.get(18).advisors = 1;
			//bh: fair ally, 2T
			map.get(21).setPosture(1);
			map.get(21).setGovernance(2);
			map.get(21).editUnits(-2, 2);
			//af: poor ally, 2T1C
			map.get(24).setGovernance(3);
			map.get(24).setPosture(1);
			map.get(24).editUnits(-2, 2);
			map.get(24).editUnits(1, 1);
			//sy: cw fair neut, 3M4C, 1A1R
			map.get(14).setPosture(2);
			map.get(14).setGovernance(2);
			map.get(14).cw = true;
			map.get(14).editUnits(-1, 2);
			map.get(14).editUnits(2, 4);
			caliph = 14; //raqqa
			//ng: poor ally, 1 cells
			map.get(39).religion = 0; //set to muslim
			map.get(39).setPosture(1);
			map.get(39).setGovernance(3);
			map.get(39).editUnits(1, 1);
			//be: hard
			map.get(5).setPosture(1);
			break;
		case 12: //trump
			//us hard
			map.get(0).setPosture(1);
			//iq: poor neutral, 2M3C; advisors
			map.get(18).setPosture(2);
			map.get(18).setGovernance(3);
			map.get(18).editUnits(-1, 2);
			map.get(18).editUnits(1, 3);
			map.get(18).cw = true;
			//dz: good neut
			map.get(36).setPosture(2);
			map.get(36).setGovernance(1);
			//ly: poor adv
			map.get(35).setPosture(3);
			map.get(35).setGovernance(3);
			map.get(35).cw = true;
			map.get(35).editUnits(1, 1);
			//lb: poor adv
			map.get(15).setPosture(3);
			map.get(15).setGovernance(3);
			map.get(15).editUnits(1, 1);
			//bh: fair ally, 2T
			map.get(21).setPosture(1);
			map.get(21).setGovernance(2);
			map.get(21).editUnits(-2, 2);
			//af: poor ally, 2T1M1C
			map.get(24).setGovernance(3);
			map.get(24).setPosture(1);
			map.get(24).editUnits(-2, 2);
			map.get(24).editUnits(-1, 1);
			map.get(24).editUnits(1, 1);
			map.get(24).spring[1] = 1;
			//sy: cw fair neut, 2M1C, 1A1R
			map.get(14).setPosture(2);
			map.get(14).setGovernance(2);
			map.get(14).cw = true;
			map.get(14).editUnits(-1, 2);
			map.get(14).editUnits(1, 1);
			map.get(14).advisors = 1;
			//ir: islamist, 3 cell
			map.get(22).religion = 1; //set to muslim
			map.get(22).setPosture(3);
			map.get(22).setGovernance(4);
			map.get(22).editUnits(1, 3);
			//ng: soft, 1 cells
			map.get(39).setPosture(-1);
			map.get(39).editUnits(1, 1);
			//ke: hard, cadre
			map.get(32).setPosture(1);
			map.get(32).editUnits(0, 1);
			//pk: 2M
			map.get(25).editUnits(-1, 2);
			break;
		case 13: //hillary
			//us soft
			map.get(0).setPosture(-1);
			//iq: poor neutral, 2M3C; advisors
			map.get(18).setPosture(2);
			map.get(18).setGovernance(3);
			map.get(18).editUnits(-1, 2);
			map.get(18).editUnits(1, 3);
			map.get(18).cw = true;
			//dz: good neut
			map.get(36).setPosture(2);
			map.get(36).setGovernance(1);
			//ly: poor adv
			map.get(35).setPosture(3);
			map.get(35).setGovernance(3);
			map.get(35).cw = true;
			map.get(35).editUnits(1, 1);
			//lb: poor adv
			map.get(15).setPosture(3);
			map.get(15).setGovernance(3);
			map.get(15).editUnits(1, 1);
			//bh: fair ally, 2T
			map.get(21).setPosture(1);
			map.get(21).setGovernance(2);
			map.get(21).editUnits(-2, 2);
			//af: poor ally, 2T1M1C
			map.get(24).setGovernance(3);
			map.get(24).setPosture(1);
			map.get(24).editUnits(-2, 2);
			map.get(24).editUnits(-1, 1);
			map.get(24).editUnits(1, 1);
			map.get(24).spring[1] = 1;
			//sy: cw fair neut, 2M1C, 1A1R
			map.get(14).setPosture(2);
			map.get(14).setGovernance(2);
			map.get(14).cw = true;
			map.get(14).editUnits(-1, 2);
			map.get(14).editUnits(1, 1);
			map.get(14).advisors = 1;
			//ir: islamist, 3 cell
			map.get(22).religion = 1; //set to muslim
			map.get(22).setPosture(3);
			map.get(22).setGovernance(4);
			map.get(22).editUnits(1, 3);
			//ng: soft, 1 cells
			map.get(39).setPosture(-1);
			map.get(39).editUnits(1, 1);
			//ke: hard, cadre
			map.get(32).setPosture(1);
			map.get(32).editUnits(0, 1);
			//pk: 2M
			map.get(25).editUnits(-1, 2);
			//ru: hard
			map.get(11).setPosture(1);
			break;
		case 14:
			//us hard
			map.get(0).setPosture(1);
			//ly: poor adv
			map.get(35).setPosture(3);
			map.get(35).setGovernance(3);
			//sy: fair adv
			map.get(14).setPosture(3);
			map.get(14).setGovernance(2);
			//iq: poor adv
			map.get(18).setPosture(3);
			map.get(18).setGovernance(3);
			//sa: poor ally, 2 troops
			map.get(19).setPosture(1);
			map.get(19).setGovernance(3);
			map.get(19).editUnits(-2, 2);
			//bh: fair ally, 2 troops
			map.get(21).setPosture(1);
			map.get(21).setGovernance(2);
			map.get(21).editUnits(-2, 2);
			//pk: fair neut
			map.get(25).setPosture(2);
			map.get(25).setGovernance(2);
			//af: ir, 4 sleepers
			map.get(24).setGovernance(4);
			map.get(24).setPosture(3);
			map.get(24).editUnits(1, 4);
			//so: besieged
			map.get(31).br = true;
			break;
		case 15:
			//us hard
			map.get(0).setPosture(1);
			//ly: poor adv
			map.get(35).setPosture(3);
			map.get(35).setGovernance(3);
			//sy: fair adv
			map.get(14).setPosture(3);
			map.get(14).setGovernance(2);
			//iq: poor adv
			map.get(18).setPosture(3);
			map.get(18).setGovernance(3);
			//sa: poor ally, 2 troops
			map.get(19).setPosture(1);
			map.get(19).setGovernance(3);
			map.get(19).editUnits(-2, 2);
			//bh: fair ally, 2 troops
			map.get(21).setPosture(1);
			map.get(21).setGovernance(2);
			map.get(21).editUnits(-2, 2);
			//pk: fair neut
			map.get(25).setPosture(2);
			map.get(25).setGovernance(2);
			//af: ir, 4 sleepers
			map.get(24).setGovernance(4);
			map.get(24).setPosture(3);
			map.get(24).editUnits(1, 4);
			//so: besieged
			map.get(31).br = true;
			break;
		default:
			break;
		}
		
	}
	/**
	 * Provides the country with the given ID.
	 * @param id is... well...
	 * @return a Country.
	 */
	public static Country get(int id) {
		return map.get(id);
	}
	/**
	 * Searches for a country with the provided alias. 
	 * @param alias is... well...
	 * @return the ID of the country with the given alias. If no country has this alias, it provides -1. 
	 */
	public static int find(String alias) {
		for (Country c : map) {
			if (c.aliases.contains(alias)) return c.id;
		}
		return -1;
	}
	
	/**
	 * Searches for a country with the provided alias. 
	 * @param alias is... well...
	 * @return the ID of the country with the given alias. If no country has this alias, it provides -1. 
	 */
	public static int length() {
		return map.size();
	}
	/**
	 * Sets up the board in a custom manner, as dictated by the provided string. 
	 * @param s describes the board state.
	 * @return true.
	 */
	/*public static boolean customSetup(String s) {
		for (int i=0; i<75; i++) {
			int us = ReadWrite.undoParser(s.charAt(2*i));
			int su = ReadWrite.undoParser(s.charAt(2*i+1));
			MapManager.get(i).support[0]=us;
			MapManager.get(i).support[1]=su;
		}
		return true;
	}*/
	/**
	 * 
	 * @return The sum of the postures of the rest of the world multiplied by the US's posture. 
	 */
	public static int GWOT() {
		int x = 0; 
		for (int i=1; i<length(); i++) { //aka not america
			if (get(i).religion==2) x += get(i).getPosture();
		}
		return x*get(0).getPosture();
	}
	/**
	 * Calculates the extent of the caliphate.
	 */
	public static ArrayList<Integer> caliphate() {
		ArrayList<Integer> c = new ArrayList<Integer>(Arrays.asList(caliph));
		for (Integer i : c) {
			for (Integer j : map.get(i).adj) {
				if (!c.contains(j) && map.get(j).religion<=1 && (map.get(j).cw||map.get(j).rc!=0||map.get(j).getGovernance()==4)) {
					c.add(j);
				}
			}
		}
		return c;
	}
}

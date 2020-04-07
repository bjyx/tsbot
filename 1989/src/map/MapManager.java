package map;

import java.util.ArrayList;

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
	 * Generates the state of the map as it is before setup (Rule 3.2/3.3). And yes, that means creating every damn country individually. <br>
	 * A list:
	 * BER	0
DRS	1
ERF	2
HAL	3
KMS	4
LPZ	5
MGD	6
RST	7
SCW	8
DDC	9 <br>
DDU	10
DDW	11

BLY	12
BYD	13
GDA	14
KTW	15
KRK	16
LDZ	17
LUB	18
PZN	19 <br>
SZC	20
WSZ	21
WRO	22
PLC	23
PLU	24
PLW	25

BRT	26
BRN	27
CBD	28
KOS	29 <br>
OST	30
PLZ	31
PRA	32
PRE	33
CSC	34
CSU	35
CSW	36

BUD	37
DBR	38
GYR	39 <br>
MSK	40
PCS	41
SZG	42
SZK	43
SZM	44
TTB	45
HUC	46
HUU	47
HUW	48

BRA	49 <br>
BUC	50
CNP	51
CON	52
CRA	53
GLT	54
HRG	55
IAS	56
PLO	57
TGM	58
TMS	59 <br>
ROC	60
ROU	61
ROW	62

BRG	63
PLE	64
PLV	65
RAZ	66
RUS	67
SLV	68
SOF	69 <br>
STZ	70
VAR	71
BGC	72
BGU	73
BGW	74

	 */
	public static void initialize() {
		map = new ArrayList<Country>();
		//East Germany - 0
		map.add(new Country(
				"Berlin", 
				0, 
				3, //bureaucrat
				true, 
				new String[] {"ber","berlin", "ostberlin", "eastberlin"}, 
				new Integer[] {5, 7, 8, 10},
				"The divided capital of Germany, Berlin was at the heart of numerous confrontations during the Cold War, having weathered a blockade, the construction of the Berlin Wall, and the standoff at Checkpoint Charlie."
				+ " Two years ago, Reagan delivered a bombastic speech at the gate on the other side of the city commanding Gorbachev to \"Tear down this wall!\"... a sentiment itself echoed by many East Berliners. Will the wall fall?", 
				new int[] {0,2}, 
				"ber"
				));
		map.add(new Country(
				"Dresden", 
				0, 
				2, //elite
				true, 
				new String[] {"drs","dresden"}, 
				new Integer[] {4, 5, 22, 31},
				"", 
				new int[] {0,2}, 
				"drs"
				));
		map.add(new Country(
				"Erfurt", 
				0, 
				1, //farmer
				false, 
				new String[] {"erf","erfurt"}, 
				new Integer[] {6},
				"", 
				new int[] {0,0}, 
				"erf"
				));
		map.add(new Country(
				"Halle", 
				0, 
				0, //worker
				false, 
				new String[] {"hal","halle"}, 
				new Integer[] {4, 5, 6},
				"", 
				new int[] {0,0}, 
				"hal"
				));
		map.add(new Country(
				"Karl-Marx-Stadt", 
				0, 
				0, //worker
				true, 
				new String[] {"kms","karlmarxstadt","chemnitz"}, 
				new Integer[] {1, 3, 5, 6},
				"", 
				new int[] {0,0}, 
				"kms"
				));
		map.add(new Country(
				"Leipzig", 
				0, 
				0, //worker
				true, 
				new String[] {"lpz","leipzig"}, 
				new Integer[] {0, 1, 3, 4},
				"", 
				new int[] {0,0}, 
				"lpz"
				));
		map.add(new Country(
				"Magdeburg", 
				0, 
				0, //worker
				true, 
				new String[] {"mgd","magdeburg"}, 
				new Integer[] {2,3,4},
				"", 
				new int[] {0,0}, 
				"mgd"
				));
		map.add(new Country(
				"Rostock", 
				0, 
				0, //worker
				false, 
				new String[] {"rst","rostock"}, 
				new Integer[] {0, 8},
				"", 
				new int[] {0,0}, 
				"rst"
				));
		map.add(new Country(
				"Schwerin", 
				0, 
				0, //worker
				false, 
				new String[] {"scw","schwerin"}, 
				new Integer[] {0, 7},
				"", 
				new int[] {0,0}, 
				"scw"
				));
		map.add(new Country(
				"German Lutheran Church", 
				0, 
				6, //church
				true, 
				new String[] {"ddc","lutheran","dchurch","gchurch"}, 
				new Integer[] {5},
				"", 
				new int[] {0,0}, 
				"ddc"
				));
		map.add(new Country(
				"Walter Ulbricht Academy", 
				0, 
				5, //uni
				false, 
				new String[] {"ddu","ulbricht"}, 
				new Integer[] {0, 11},
				"", 
				new int[] {0,0}, 
				"ddu"
				));
		map.add(new Country(
				"German Writers", 
				0, 
				4, //writers
				false, 
				new String[] {"ddw","gwriters"}, 
				new Integer[] {10},
				"", 
				new int[] {0,0}, 
				"ddw"
				));
		// Poland - 12
		map.add(new Country(
				"Białystok", 
				1, 
				1, //farmer
				false, 
				new String[] {"bly","bialystok"}, 
				new Integer[] {14, 18, 21},
				"", 
				new int[] {0,0}, 
				"bly"
				));
		map.add(new Country(
				"Bydgoszcz", 
				1, 
				2, //elite
				false, 
				new String[] {"byd","bydgoszcz"}, 
				new Integer[] {14, 21},
				"", 
				new int[] {0,1}, 
				"byd"
				));
		map.add(new Country(
				"Gdańsk", 
				1, 
				0, //worker
				true, 
				new String[] {"gda","gdansk"}, 
				new Integer[] {12, 13, 19, 20, 21},
				"", 
				new int[] {1,0}, 
				"gda"
				));
		map.add(new Country(
				"Katowice", 
				1, 
				0, //worker
				false, 
				new String[] {"ktw","katowice"}, 
				new Integer[] {16, 22, 23, 30},
				"", 
				new int[] {0,0}, 
				"ktw"
				));
		map.add(new Country(
				"Kraków", 
				1, 
				0, //worker
				true, 
				new String[] {"krk","krakow"}, 
				new Integer[] {15, 17, 23, 24},
				"", 
				new int[] {1,0}, 
				"krk"
				));
		map.add(new Country(
				"Łódź", 
				1, 
				0, //worker
				true, 
				new String[] {"ldz","lodz"}, 
				new Integer[] {16, 18, 21, 23},
				"", 
				new int[] {0,0}, 
				"ldz"
				));
		map.add(new Country(
				"Lublin", 
				1, 
				1, //farmer
				false, 
				new String[] {"lub","lublin"}, 
				new Integer[] {12, 17, 21},
				"", 
				new int[] {0,1}, 
				"lub"
				));
		map.add(new Country(
				"Poznań", 
				1, 
				0, //worker
				true, 
				new String[] {"pzn","poznan"}, 
				new Integer[] {14, 20, 22, 23},
				"", 
				new int[] {0,0}, 
				"pzn"
				));
		map.add(new Country(
				"Szczecin", 
				1, 
				0, //worker
				false, 
				new String[] {"szc","szczecin"}, 
				new Integer[] {14, 19},
				"", 
				new int[] {0,0}, 
				"szc"
				));
		map.add(new Country(
				"Warszawa", 
				1, 
				3, //politik
				true, 
				new String[] {"wsz","warszawa","warsaw"}, 
				new Integer[] {12, 13, 14, 18, 17},
				"", 
				new int[] {0,1}, 
				"wsz"
				));
		map.add(new Country(
				"Wrocław", 
				1, 
				0, //worker
				true, 
				new String[] {"wro","wroclaw"}, 
				new Integer[] {1, 19, 15, 23},
				"", 
				new int[] {0,0}, 
				"wro"
				));
		map.add(new Country(
				"Polish Catholic Church", 
				1, 
				6, //church
				false, 
				new String[] {"plc","pchurch","pcatholic"}, 
				new Integer[] {15, 16, 17, 19, 22},
				"", 
				new int[] {5,0}, 
				"plc"
				));
		map.add(new Country(
				"Jagiellonian U", 
				1, 
				5, //uni
				false, 
				new String[] {"plu","jagiellonian"}, 
				new Integer[] {16, 25},
				"", 
				new int[] {0,0}, 
				"plu"
				));
		map.add(new Country(
				"Polish Writers", 
				1, 
				4, //writer
				false, 
				new String[] {"plw","pwriters"}, 
				new Integer[] {24},
				"", 
				new int[] {0,0}, 
				"plw"
				));
		//Czechoslovakia - 26
		map.add(new Country(
				"Bratislava", 
				2, 
				0, //worker
				true, 
				new String[] {"brt","bratislava"}, 
				new Integer[] {27, 30, 34},
				"", 
				new int[] {0,0}, 
				"brt"
				));
		map.add(new Country(
				"Brno", 
				2, 
				0, //worker
				true, 
				new String[] {"brn","brno"}, 
				new Integer[] {26, 30, 32},
				"", 
				new int[] {0,1}, 
				"brn"
				));
		map.add(new Country(
				"České Budějovice", 
				2, 
				0, //worker
				false, 
				new String[] {"cbd","budejovice","ceskebudejovice"}, 
				new Integer[] {31, 32},
				"", 
				new int[] {0,0}, 
				"cbd"
				));
		map.add(new Country(
				"Košice", 
				2, 
				1, //farmer
				false, 
				new String[] {"kos","kosice"}, 
				new Integer[] {33, 38},
				"", 
				new int[] {0,0}, 
				"kos"
				));
		map.add(new Country(
				"Ostrava", 
				2, 
				0, //worker
				true, 
				new String[] {"ost","ostrava"}, 
				new Integer[] {26, 27, 34, 15},
				"", 
				new int[] {0,0}, 
				"ost"
				));
		map.add(new Country(
				"Plzen", 
				2, 
				2, //elite
				false, 
				new String[] {"plz","plzen"}, 
				new Integer[] {1, 28, 32},
				"", 
				new int[] {0,2}, 
				"plz"
				));
		map.add(new Country(
				"Praha", 
				2, 
				3, //pol
				true, 
				new String[] {"pra","praha","prague"}, 
				new Integer[] {27, 28, 31, 35},
				"", 
				new int[] {0,2}, 
				"pra"
				));
		map.add(new Country(
				"Prešov", 
				2, 
				1, //farmer
				false, 
				new String[] {"presov","pre"}, 
				new Integer[] {34, 29},
				"", 
				new int[] {0,0}, 
				"pre"
				));
		map.add(new Country(
				"Czechoslovak Catholic Church", 
				2, 
				6, //church
				true, 
				new String[] {"csc","ccatholic","cchurch"}, 
				new Integer[] {26, 30, 33},
				"", 
				new int[] {1,0}, 
				"csc"
				));
		map.add(new Country(
				"Charles University", 
				2, 
				5, //uni
				true, 
				new String[] {"csu","charles","cstudents","cuni"}, 
				new Integer[] {32, 36},
				"", 
				new int[] {0,0}, 
				"csu"
				));
		map.add(new Country(
				"Bratislava", 
				2, 
				4, //writer
				true, 
				new String[] {"csw","cwriters","havel","kundera"}, 
				new Integer[] {35},
				"", 
				new int[] {2,0}, 
				"csw"
				));
		//Hungary - 37
		map.add(new Country(
				"Budapest", 
				3, 
				3,
				true, 
				new String[] {"budapest","bud"}, 
				new Integer[] {40, 42, 45, 47},
				"", 
				new int[] {1,0}, 
				"bud"
				));
		map.add(new Country(
				"Debrecen", 
				3, 
				0,
				true, 
				new String[] {"dbr","debrecen"}, 
				new Integer[] {29, 40},
				"", 
				new int[] {0,0}, 
				"dbr"
				));
		map.add(new Country(
				"Győr", 
				3, 
				0,
				false, 
				new String[] {"gyr","gyor"}, 
				new Integer[] {43, 44, 45, 46},
				"", 
				new int[] {0,0}, 
				"gyr"
				));
		map.add(new Country(
				"Miskolc", 
				3, 
				0,
				true, 
				new String[] {"msk","miskolc"}, 
				new Integer[] {37, 38, 45},
				"", 
				new int[] {0,0}, 
				"msk"
				));
		map.add(new Country(
				"Pécs", 
				3, 
				1,
				false, 
				new String[] {"pcs","pecs"}, 
				new Integer[] {42},
				"", 
				new int[] {0,0}, 
				"pcs"
				));
		map.add(new Country(
				"Szeged", 
				3, 
				1,
				true, 
				new String[] {"szg","szeged"}, 
				new Integer[] {41, 37, 59},
				"", 
				new int[] {1,0}, 
				"szg"
				));
		map.add(new Country(
				"Székesfehérvár", 
				3, 
				0,
				false, 
				new String[] {"szk","szekesfehervar"}, 
				new Integer[] {37, 39, 44},
				"", 
				new int[] {1,0}, 
				"szk"
				));
		map.add(new Country(
				"Szombathely", 
				3, 
				2,
				false, 
				new String[] {"smb","szombathely"}, 
				new Integer[] {39, 43, 46},
				"", 
				new int[] {0,1}, 
				"smb"
				));
		map.add(new Country(
				"Tatabánya", 
				3, 
				0,
				false, 
				new String[] {"ttb","tatabanya"}, 
				new Integer[] {37,39,40},
				"", 
				new int[] {0,0}, 
				"ttb"
				));
		map.add(new Country(
				"Hungarian Catholic Church", 
				3, 
				6,
				false, 
				new String[] {"huc","hchurch","hcatholic"}, 
				new Integer[] {39, 44},
				"", 
				new int[] {1,0}, 
				"huc"
				));
		map.add(new Country(
				"Eötvös Loránd University", 
				3, 
				5,
				false, 
				new String[] {"huu","eotvoslorand","huni","hstudents"}, 
				new Integer[] {37, 48},
				"", 
				new int[] {0,0}, 
				"huu"
				));
		map.add(new Country(
				"Hungarian Writers", 
				3, 
				4,
				false, 
				new String[] {"huw","hwriters"}, 
				new Integer[] {47},
				"", 
				new int[] {0,0}, 
				"huw"
				));
		//Romania - 49
		map.add(new Country(
				"Brașov", 
				4, 
				0,
				true, 
				new String[] {"bra","brasov"}, 
				new Integer[] {50, 56, 57},
				"", 
				new int[] {0,0}, 
				"bra"
				));
		map.add(new Country(
				"București", 
				4, 
				3,
				true, 
				new String[] {"buc","bucuresti","bucharest"}, 
				new Integer[] {49, 51, 52, 53, 57},
				"", 
				new int[] {0,2}, 
				"buc"
				));
		map.add(new Country(
				"Cluj-Napoca", 
				4, 
				2,
				true, 
				new String[] {"cnp","cluj","clujnapoca"}, 
				new Integer[] {50, 59, 60, 61},
				"", 
				new int[] {0,2}, 
				"cnp"
				));
		map.add(new Country(
				"Constanța", 
				4, 
				0,
				false, 
				new String[] {"constanta","con"}, 
				new Integer[] {50, 54, 71},
				"", 
				new int[] {0,0}, 
				"con"
				));
		map.add(new Country(
				"Craiova", 
				4, 
				1,
				false, 
				new String[] {"cra","craiova"}, 
				new Integer[] {50, 59, 60},
				"", 
				new int[] {0,0}, 
				"cra"
				));
		map.add(new Country(
				"Galați", 
				4, 
				0,
				true, 
				new String[] {"glt","galati"}, 
				new Integer[] {52, 57, 56},
				"", 
				new int[] {0,0}, 
				"glt"
				));
		map.add(new Country(
				"Harghita/Covasna", 
				4, 
				7,
				false, 
				new String[] {"hrg","harghita","covasna"}, 
				new Integer[] {58},
				"", 
				new int[] {0,0}, 
				"hrg"
				));
		map.add(new Country(
				"Iași", 
				4, 
				0,
				true, 
				new String[] {"iasi","ias"}, 
				new Integer[] {58, 49, 54},
				"", 
				new int[] {0,0}, 
				"ias"
				));
		map.add(new Country(
				"Ploiești", 
				4, 
				0,
				false, 
				new String[] {"plo","ploiesti"}, 
				new Integer[] {50, 49, 54},
				"", 
				new int[] {0,0}, 
				"plo"
				));
		map.add(new Country(
				"Târgu Mureș", 
				4, 
				1,
				false, 
				new String[] {"tgm","targumures"}, 
				new Integer[] {55,56},
				"", 
				new int[] {0,0}, 
				"tgm"
				));
		map.add(new Country(
				"Timișoara", 
				4, 
				0,
				true, 
				new String[] {"timisoara","tms"}, 
				new Integer[] {42, 51, 53},
				"", 
				new int[] {0,0}, 
				"tms"
				));
		map.add(new Country(
				"Romanian Orthodox Church", 
				4, 
				6,
				false, 
				new String[] {"roc","rchurch","rorthodox"}, 
				new Integer[] {51, 53},
				"", 
				new int[] {0,0}, 
				"roc"
				));
		map.add(new Country(
				"Babeș-Bolyai University", 
				4, 
				5,
				false, 
				new String[] {"rou","babesbolyai","rstudents","runi"}, 
				new Integer[] {51, 62},
				"", 
				new int[] {0,0}, 
				"rou"
				));
		map.add(new Country(
				"Romanian Writers", 
				4, 
				4,
				false, 
				new String[] {"row","rwriters"}, 
				new Integer[] {61},
				"", 
				new int[] {0,0}, 
				"row"
				));
		//Bulgaria - 63
		map.add(new Country(
				"Burgas", 
				5, 
				0,
				true, 
				new String[] {"brg","burgas"}, 
				new Integer[] {67, 70, 71},
				"", 
				new int[] {0,0}, 
				"brg"
				));
		map.add(new Country(
				"Pleven", 
				5, 
				1,
				false, 
				new String[] {"ple","pleven"}, 
				new Integer[] {69},
				"", 
				new int[] {0,0}, 
				"ple"
				));
		map.add(new Country(
				"Plovdiv", 
				5, 
				0,
				true, 
				new String[] {"plv","plovdiv"}, 
				new Integer[] {68, 69},
				"", 
				new int[] {0,0}, 
				"plv"
				));
		map.add(new Country(
				"Razgrad", 
				5, 
				7,
				false, 
				new String[] {"raz","razgrad"}, 
				new Integer[] {67},
				"", 
				new int[] {0,0}, 
				"raz"
				));
		map.add(new Country(
				"Ruse", 
				5, 
				0,
				true, 
				new String[] {"rus","ruse"}, 
				new Integer[] {66,70,71,72},
				"", 
				new int[] {0,0}, 
				"rus"
				));
		map.add(new Country(
				"Sliven", 
				5, 
				1,
				false, 
				new String[] {"slv","sliven"}, 
				new Integer[] {65},
				"", 
				new int[] {0,0}, 
				"slv"
				));
		map.add(new Country(
				"Sofia", 
				5, 
				3,
				true, 
				new String[] {"sof","sofia"}, 
				new Integer[] {64, 65, 70, 72, 73},
				"", 
				new int[] {0,2}, 
				"sof"
				));
		map.add(new Country(
				"Stara Zagora", 
				5, 
				2,
				false, 
				new String[] {"stz","starazagora"}, 
				new Integer[] {69, 63, 67},
				"", 
				new int[] {0,1}, 
				"brg"
				));
		map.add(new Country(
				"Varna", 
				5, 
				0,
				true, 
				new String[] {"var","varna"}, 
				new Integer[] {52, 63, 67},
				"", 
				new int[] {0,0}, 
				"brg"
				));
		map.add(new Country(
				"Bulgarian Orthodox Church", 
				5, 
				6,
				false, 
				new String[] {"bgc","bchurch","borthodox"}, 
				new Integer[] {67, 69},
				"", 
				new int[] {0,0}, 
				"bgc"
				));
		map.add(new Country(
				"Sofia University", 
				5, 
				5,
				true, 
				new String[] {"bgu","sofiau","buni","bstudents"}, 
				new Integer[] {69, 74},
				"", 
				new int[] {0,0}, 
				"bgu"
				));
		map.add(new Country(
				"Bulgarian Writers", 
				5, 
				4,
				true, 
				new String[] {"bgw","bwriters"}, 
				new Integer[] {73},
				"", 
				new int[] {0,0}, 
				"bgw"
				));
		//end - 75
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
}

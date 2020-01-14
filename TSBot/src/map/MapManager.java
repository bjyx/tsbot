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
				"Like Germany, Austria was divided between Western and Soviet occupation zones after World War II; "
				+ "unlike Germany, Austria was able to avoid a Cold War partition, "
				+ "in part due to doubts that its Socialist leader Karl Renner could become a Soviet Puppet. "
				+ "It regained full independence on the 15th of May, 1955, by the Austrian State Treaty, which also swore it to permanent neutrality.", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Benelux", //Belgium's flag is employed on official TS boards.
				1, 
				"be", 
				3, 
				false, 
				new String[] {"benelux", "belgium", "bel", "be", "belgië", "belgie", "belgique", "netherlands", "nld","nl","nederland","luxembourg","lux","lu","luxemburg","letzebuerg","lëtzebuerg"}, 
				new Integer[] {18, 19}, //uk, de
				"The region collectively representing the countries of Belgium, the Kingdom of the Netherlands, and Luxembourg. "
				+ "All three of these nations were liberated by the Western Powers during World War II; "
				+ "they were among the founding members of NATO and the European Coal and Steel Community, later the European Union.", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Bulgaria", 
				2, 
				"bg", 
				3, 
				false, 
				new String[] {"bulgaria", "bg", "bgr", "balgariya", "българия"}, 
				new Integer[] {9, 17}, //gr, tr
				"A left-wing uprising in 1944 toppled the monarchy formerly reigning over Bulgaria, "
				+ "eventually culminating in a one-party state under Georgi Dimitrov. "
				+ "By the time the country was passed to Todor Zhivkov in 1949, "
				+ "it was a working appendage of the Soviet Union, following its opinion in almost all cases.", 
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
				"Canada largely took the stance of its southern neighbor on foreign policy. "
				+ "In 1957, Canada and the US formed the North American Air Defense Command, or NORAD. "
				+ "Canada was among the founding nations of NATO.", 
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
				"Unlike in other Soviet-controlled countries, Czechoslovakia was allowed a free election after its liberation, "
				+ "one that the local communist party won by a large margin. "
				+ "Czechoslovakia would be the target of a Soviet coup d'état following a brief consideration for accepting funds from the Marshall Plan; "
				+ "the only non-communist left afterwards was found dead two weeks later, and the country turned into a satellite state.", 
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
				"Despite not being a major target for communist operations, Denmark was one of the founding nations of NATO.", 
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
				"As talks over reunifying an occupied Germany stalled after the deaths of Stalin and Beria, "
				+ "the communist occupation zone was converted into the German Democratic Republic. "
				+ "This state was primarily subservient to Soviet wishes, especially the Honecker government, "
				+ "which rolled back any semblance of liberalism from the previous government under Walter Ulbricht.", 
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
				"While allied with Germany, Finland did not receive the same brutal treatment as the rest of the Axis Powers that fell to the USSR. "
				+ "While ostensibly neutral, the Soviets stuck a hand in the nation's politics (something that President Kekkonen used to great effect), "
				+ "and the country's politics would generally avoid statements disparaging the Soviet Union, a phenomenon that the West German press would dub \"Finlandization\".", 
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
				"While ostensibly aligned with the west, France primarily pursued a policy of \\\"national independence\\\" from the two sides of the war. "
				+ "It was a founding member of NATO, but withdrew from it under Charles de Gaulle, and restored relations with West Germany in order to create a European counterweight to the two major blocs, "
				+ "even going as far as starting a nuclear program that led it to become the fourth nuclear power.", 
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
				"Greece emerged from World War II in the midst of a civil war between communist forces and the anti-communist government. "
				+ "It and neighboring Turkey were the impetus for the creation of the Truman Doctrine, "
				+ "which provided for American aid to any nations struggling against communism. In the end, the government won out, and Greece joined NATO in 1952.", 
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
				"Hungary, much like Romania and Bulgaria, was converted into a Soviet satellite state at the conclusion of World War II. "
				+ "Under Mátyás Rákosi, the nation molded itself into a smaller version of the repressive side of Soviet Russia, "
				+ "with around six hundred thousand Hungarians interned in prison camps. Stalin's death would herald his fall, "
				+ "leading to a more liberal premier, Imre Nagy, taking power.", 
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
				"Italy became a republic after a referendum on the monarchy. Fears of a communist government led to a landslide victory by the Christian Democrats, "
				+ "led by one Alcide de Gaspari. Despite the inclusion of the Italian Communist Party in his coalition, Italy was able to accept Marshall Plan money, "
				+ "and joined NATO as a founding member.", 
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
				"While not a major battleground between the Soviets and the US, Norway was one of the founding members of NATO in 1949. It was also the origin of the first UN Secretary General, Trygve Lie.", 
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
				"At the Yalta Conference during World War II, Stalin promised to hold free elections in Poland, a promise he reneged on; "
				+ "the communists had almost total control over the country. In 1946, a people's referendum was held on three questions; "
				+ "with the government manipulating the ballots, all three motions in this \\\"Three Times Yes\\\" referendum—"
				+ "abolishing the Senate, land reform, and the extension of the Polish border to the Oder-Neisse Line—passed.", 
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
				"Fraudulent elections and a subsequent forced abdication by Michael I led to a communist regime in Romania, "
				+ "led by Gheorghe Gheorghiu-Dej. The time of his premiership was marked by the draining of resources from his country to supply the Soviet Union, "
				+ "via the so-called \\\"SovRoms\\\". After his death in 1965, he was succeeded by Nicolae Ceaușescu, whose regime is noted primarily for its human rights violations.", 
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
				"The Francoist regime in Spain was politically isolated after World War II, to the point that it could not join the United Nations (until 1955). "
				+ "On the other hand, despite the undertones of dictatorship in the Estado Novo regime, Portugal became a founding member of NATO. "
				+ "Spain would later join NATO after the death of Franco and the restoration of democracy.", 
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
				"While officially neutral in the Cold War (i.e. part of neither NATO nor the Warsaw Pact), Sweden kept strong ties with the United States and other western governments, "
				+ "and accepted aid from the Marshall Plan. It was also the home country of Dag Hammarskjöld, the UN Secretary-General who died en route to the Congo, "
				+ "and Olof Palme, the Prime Minister with notable anti-apartheid sympathies, mysteriously murdered in 1986.", 
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
				"Turkey joined the Allies at the closing stage of World War II as a symbolic move to gain entry into the United Nations. "
				+ "Pressure to open its straits to Soviet shipping led it to join NATO in 1952; from there, "
				+ "the US could place intermediate-range missiles that could target major Russian cities, a point of contention during the missile crisis ten years later.", 
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
				"Master of a world-spanning empire before World War II, the end of the war left the country severely in debt; "
				+ "this, combined with the rising tide of nationalism, made decolonization unavoidable. "
				+ "The UK worked closely with the United States to stem communism, being a founding member of NATO and the Western European Union. "
				+ "It is also a permanent member of the United Nations Security Council, and the third country to detonate a thermonuclear weapon.", 
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
				"After 'Germany as a whole' was split into occupation zones, the three in the west were merged to create West Germany, "
				+ "one thing among many that led the Soviets to blockade Berlin. The new nation then joined NATO in 1955, "
				+ "leading the Communists to create the Warsaw Pact. West Germany never held official relations with any states that officially recognized East Germany besides the USSR under the Hallstein Doctrine until Willy Brandt took the chancellorship.", 
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
				"While Yugoslavia adopted a communist form of government, its leader, Josip Broz Tito, "
				+ "under the argument that Yugoslavia largely liberated itself with partisans rather than relying heavily on the Red Army, "
				+ "opted to steer an independent course, starting a period of chilly relations with the USSR known as the Informbiro period. "
				+ "With this split, Western aid started flowing into Yugoslavia, though Tito declined an invitation to join NATO.", 
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
				"A coup d'état in 1952, in part due to lasting British influence, forced King Farouk to abdicate, thus putting Egypt under military rule. "
				+ "The first such ruler, Muhammad Naguib, lasted for little over a year before being ousted by Gamal Abdel Nasser, "
				+ "who quickly started working on removing Western influences. In doing so, he nationalized the Suez Canal, inducing a failed joint Anglo-French invasion.", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Gulf States", //which will use Kuwait for all cosmetic purposes, thank opec
				3, 
				"kw", 
				3, 
				false, 
				new String[] {"gulfstates", "ae", "are", "unitedarabemirates", "imarat", "uae", "oman","uman","omn","om","qatar","qat","qa","bahrain","bhr","bahrayn","bh","kuwait","kwt","kw"}, 
				new Integer[] {24, 29}, //iq, sa
				"A region collectively representing the minor states on the Persian Gulf, primarily Kuwait, a founding member of OPEC.", 
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
				"The nationalization of Iran's oil industry, while a popular move with Iranians, did not sit well with the US and the UK, "
				+ "who organized Operation Ajax to depose then-Prime Minister Mohammad Mosaddegh. After that coup, Shah Reza Pahlavi became increasingly despotic; "
				+ "his overthrow in 1979 heralded the arrival of an Islamic Republic that then distanced itself from the United States, instigating a 444-day long hostage crisis.", 
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
				"While independent before World War II, Iraq experienced a coup d'état in 1941, leading to fears that it would cut the Allies from vital oil supplies, "
				+ "and Britain occupied the country until 1947. In 1958, another coup d'état established a republic under Abd al-Karim Qasim, who  was himself overthrown in 1963. "
				+ "After that came the 17 July Revolution, leading to the rule of the Ba'ath party in Iraq under Saddam Hussein.", 
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
				"Originally to be partitioned evenly between Arabs and Jews, the land of the former Mandatory Palestine quickly became a center of conflict, "
				+ "being the focus of between four and seven wars depending on how they are counted. Western support for Israel only managed to further polarize the Arab states into the Soviet camp.", 
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
				"Upon independence from Britain, Jordan joined other Arab States in invading the newly-formed state of Israel; following this war, Jordan controlled the West Bank, "
				+ "and formally annexed this territory in 1950, a territory that would later be lost to Israel in the Six-Day War. "
				+ "Although it briefly united with the Kingdom of Iraq in the Arab Federation, that union was short-lived, as the 14 July Revolution deposed the Iraqi King Faisal II.", 
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
				"Lebanon was granted formal independence in the middle of World War II by the Free French government. "
				+ "Like other Arab States, Lebanon participated in the 1948 Arab-Israeli War. "
				+ "In 1958, an insurrection broke out over a desire to have Lebanon join the United Arab Republic (Egypt + Syria); "
				+ "due to American intervention (Operation Blue Bat), this insurrection was put down.", 
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
				"With Italy's defeat in World War II, its colony in Libya was granted full independence as a kingdom led by King Idris I. "
				+ "The Kingdom soon became wealthy after the discovery of oil reserves in 1959. Resentment of the king soon built up, "
				+ "and the country was destabilized after a coup in 1969; the new leader, Muammar Gaddafi, moved to create a socialist republic.", 
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
				"While an Arab state opposed to the existence of Israel, the presence of a Cold War-style rivalry with Nasser's Egypt allowed Saudi Arabia to develop close relations with the United States. "
				+ "As one of the largest oil producers in the world, however, it could control oil prices at will, which it did against Western countries that supported Israel in the Yom Kippur War.", 
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
				"Instability followed Syria after its participation in the Arab-Israeli War, with four coups in immediate succession. "
				+ "As a result of the Suez Crisis, Syria aligned itself with the Soviet Union. In 1958, Syria joined Egypt in the United Arab Republic, "
				+ "though it seceded three years later. The instability following this led Syria to be taken over by the Ba'ath Party, soon to be led by Hafez al-Assad.", 
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
				"Afghanistan's unique position was able to net plenty of investment from both Soviet and American interests. "
				+ "The Kingdom was turned into a Republic after a bloodless coup in 1973, "
				+ "but Pakistani involvement likely caused another coup after that, allowing the People's Democratic Party of Afghanistan to take power in the Saur Revolution. "
				+ "The assassination of the president after that coup led to a costly Soviet invasion, during which the US supplied billions to anti-communist mujahideen.", 
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
				"Australia largely cooperated with the US on its policies towards communist countries. In 1955, Australia, along with New Zealand, joined the Southeast Asia Treaty Organization, an effort to combat Communist China's influence in the area.", 
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
				"Burma (Myanmar) started as a republic after independence from Britain in 1948. In 1962, a coup led by Ne Win took over the country, "
				+ "and almost every aspect of society was nationalised under the 'Burmese Way to Socialism'. Interestingly, a year before, "
				+ "the Burmese U Thant was appointed as the Secretary-General of the United Nations; "
				+ "his death and subsequent lack of a state funeral was a point of contention in the various student protests in Burma.", 
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
				"India's independence and subsequent partition in 1948 created two nations increasingly hostile to each other. They fought a total of four wars, with many minor skirmishes in between, "
				+ "over border disputes and a Pakistani exclave that would later become Bangladesh. On the internal front, India had had to consistently deal with the Maoist Naxalites, "
				+ "as well as interreligious conflict.", 
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
				"Japan's surrender prompted Indonesia to declare its independence from the Dutch, who relented after four years of conflict. "
				+ "The new country quickly drifted towards a \"guided democracy\" (in effect authoritarianism) under Sukarno, "
				+ "who brutally suppressed the country's communist party after an attempted coup on 30 September 1965. "
				+ "He was succeeded by Suharto, whose New Order administration received significant support from the US.", 
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
				"Japan was occupied by the United States after World War II, during which the occupiers rebuilt the nation and in general aided the transition towards a democracy. "
				+ "Even after the end of occupation, a mutual defense pact provided Japan with protection from potential communist influences and the United States with a springboard in Asia.", 
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
				"After independence from France, ideological conflict dominated the landscapes of these two nations. Laos quickly devolved into civil war between royalist forces and the communist Pathet Lao. "
				+ "On the other hand, an invasion of Cambodia by the US during the Vietnam War to close the Ho Chi Minh Trail destabilized the Khmer Republic, allowing the brutally repressive Khmer Rouge to take control.", 
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
				"As a British colony, Malaysia was the subject of many communist insurgencies. Two attempts by the Malayan Communist Party to take over the country, "
				+ "known collectively as the Malayan Emergency, failed. A separate insurgency in the eastern province of Sarawak similarly ended with the dissolution of the communist party involved. "
				+ "Opposition to the formation of Malaysia also led non-communist Indonesia to invade it in the *Konfrontasi*.", 
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
				"After Japan gave up its possession of Korea, it was split along the 38th parallel into a communist north, led by Kim Il-sung, and an anticommunist south. "
				+ "Kim successfully resisted Soviet and Chinese efforts to replace him with someone more favorable to their interests in the August Faction incident; "
				+ "Chinese troops withdrew in 1958, allowing North Korea effective independence.", 
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
				"India's independence and subsequent partition in 1948 created two nations increasingly hostile to each other. They fought a total of four wars, "
				+ "with many minor skirmishes in between, over border disputes and a Pakistani exclave that would later become Bangladesh. "
				+ "A small element of leftism was present in Pakistani democracy in the form of the Pakistan Peoples Party, "
				+ "an element that would be provocative influence for a coup against it in 1977.", 
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
				"After being officially made independent by the US, the Philippines quickly experienced a rebellion made by the communist Hukbalahap, "
				+ "one that was quickly put down by President Ramon Magsaysay. The country passed into the hands of Ferdinand Marcos in 1965; "
				+ "after a probably fraudulent election, Marcos was overthrown in the People Power Revolution.", 
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
				"After Japan gave up its possession of Korea, it was split along the 38th parallel into a communist north and an anticommunist south, led by Syngman Rhee. "
				+ "After an attempted unification war that ended in a stalemate, Rhee went autocratic; "
				+ "allegations of a rigged election for Vice President incited the April Revolution that forced his resignation, "
				+ "eventually allowing Park Chung-hee to rise to power.", 
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
				"The government of the Republic of China fled to Taipei after a costly civil war with the Communist forces that founded the People's Republic of China in 1949. "
				+ "While initially swearing off intervention in the conflict, the United States, upon the outbreak of the Korean War, "
				+ "declared it in its best interests to extend protection to the island, eventually signing defense treaties with it.", 
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
				"Thailand became an ally of the West after joining the Southeast Asia Treaty Organization in 1954, "
				+ "participating in the Vietnam War on the side of the South. A bloody coup in 1976 brought about a military regime that further amplified the ongoing communist insurgency in the nation.", 
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
				"Like Korea, Vietnam was split in half (albeit at the 17th parallel) after defeating France at Dien Bien Phu, with promises to hold elections to reunite the country; "
				+ "that never happened. American involvement in the conflict between the two halves was mostly limited to \"advisors\" until the Gulf of Tonkin Incident, "
				+ "in which North Vietnamese ships shot at the *USS Maddox*. This war would spill over into neighboring Laos and Cambodia.", 
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
				"Algeria, a French colony since around 1830, started clamoring for its independence after World War II. A massacre in Sétif was the tipping point, "
				+ "and the Algerian War began between the French and the socialist FLN, with atrocities on both sides. De Gaulle eventually agreed to end the conflict with the Évian Accords."
				+ "The independent Algeria immediately suffered a coup d'etat by Houari Boumédiène, turning the country increasingly authoritarian.", 
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
				"Unlike most countries, Portugal and its Estado Novo refused to release its remaining colonies. Open hostilities in the Baixa do Cassanje revolt "
				+ "boiled over into a full-blown independence war in Angola, with four separate movements, the FNLA, the UNITA, the MPLA, and the FLEC (fighting for the liberation of the exclave Cabinda), "
				+ "battling the Portuguese until the post-Carnation Revolution Portuguese pulled out under the Alvor Agreement. The socialist MPLA then turned on the other two in the Angolan Civil War, eventually winning by 1992.", 
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
				"Formerly the Bechuanaland Protectorate, Botswana gained independence from the United Kingdom in 1964. "
				+ "Botswanan politics was mostly uneventful save for where it interacted with the heavily-apartheid-favoring South African government, which refused to allow Seretse Khama to rule due to his interracial marriage.", 
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
				"As a French colony, Cameroun started agitating for independence via the UPC, and was granted it without a bloody independence war in 1960. The first president, Ahmadou Ahidjo, resigned in favor of his Vice President Paul Biya, "
				+ "who has ruled Cameroon in an authoritarian manner since then, despite a coup in 1984 believed to have been orchestrated by Ahidjo himself.", 
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
				"After surviving two Italian invasions, the Ethiopian Empire annexed Italian Eritrea, first in a federation and then outright in 1962, "
				+ "initiating the Eritrean struggle for independence that would continue through the end of the Cold War. "
				+ "Its Emperor, Haile Selassie, would be deposed by the Marxist Derg, allowing it to receive Soviet aid against a Somalian incursion in the Ogaden War. "
				+ "It also marred the country with a Red Terror and a famine.", 
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
				"Like most French colonies in Africa, the Ivory Coast obtained its independence in 1960. Electing Félix Houphouët-Boigny as its first president, "
				+ "the country stayed considerably conservative, unlike the other nations within the region, which embraced a leftist, anti-western stance."
				+ "The country stayed stable throughout the Cold War.", 
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
				"After anti-colonial agitation resulting from the Mau Mau Rebellion, Kenya was granted independence from the United Kingdom in 1963. "
				+ "Its first leader was Jomo Kenyatta, whose party, the KANU, would become the only party in the nation until a splinter created the socialist KPU. "
				+ "Kenyatta's successor Daniel arap Moi turned the country authoritarian and ruled until 2002.", 
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
				"A French protectorate, Morocco gained its independence in 1956. While not a meaningful front during the Cold War, "
				+ "Morocco did have interactions with neighboring Algeria in the Sand Wars, as well as Spain, "
				+ "from which it obtained the Rif, Sidi Ifni, and two-thirds of the Spanish Sahara, a point of contention with both Mauritania and the Polisario Front.", 
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
				"Nigeria's early history as an independent republic was tumultuous. Only three years into its existence, "
				+ "several coup d'etats occurred in rapid succession, culminating in the rise of Yakubu Gowon."
				+ "The mistreatment of Nigeria's Igbo peoples created secessionist sentiment, "
				+ "with Emeka Ojukwu declaring the independence of the Republic of Biafra and initiating the Nigerian Civil War. "
				+ "The Nigerian side obtained support from both the USSR and the USA.", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Southeast African States", // uses Mozambique due to Portuguese Empire Collapses
				6, 
				"mz", 
				1, 
				false, 
				new String[] {"seafricanstates", "seafrica", "southeastafricanstates", "southeastafrica", "mozambique", "moz", "mz"}, 
				new Integer[] {52, 63}, //ke, zw
				"Although called \"Southeast African States\", in practice the region represents the single country of Mozambique. "
				+ "Like Angola, Mozambique was a Portuguese colony, and Portugal's Estado Novo government refused to release its colonies. "
				+ "Tensions boiled over into the Mozambican War of Independence, with the FRELIMO eventually winning the country's independence. "
				+ "Two years later, Mozambique became embroiled in a civil war, with the RENAMO fighting the socialist FRELIMO's efforts to establish a one-party state.", 
				new int[] {0,0}
				));
		map.add(new Country(
				"Saharan States", //uses Chad for cosmetic purposes; thank Libya
				6, 
				"td", //chad
				1, 
				false, 
				new String[] {"saharanstates", "sahara", "chad", "tcd", "td"}, 
				new Integer[] {46, 54}, //dz, ng
				"A representation of states formerly comprising French Africa. Of particular note here is Chad, which was involved in conflict with Libya over the Aouzou Strip during the Toyota War.", 
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
				"Although Italy was on the losing side of World War II, it was allowed to retain its portion of Somaliland as a UN Trust Territory until 1960, "
				+ "when it would merge with British Somaliland and obtain independence as Somalia. A coup d'etat in 1969 led to the rise of the socialist Siad Barre. "
				+ "Barre invaded the Ogaden region of Ethiopia in 1977, expecting Soviet aid in his endeavor; as the Soviets supported newly-communist Ethiopia, however, Barre turned to the United States.", 
				new int[] {0,0}
				));
		map.add(new Country(
				"South Africa", 
				6, 
				"za", 
				3, 
				true, 
				new String[] {"southafrica", "zaf", "za", "suidafrika"}, 
				new Integer[] {47, 48}, //ao, bw
				"South Africa, dominated by the Afrikaner National Party in 1948, instituted a policy of apartheid, essentially state-sponsored segregation, "
				+ "including the removal of Africans to various Bantustans. The UN declared apartheid a crime against humanity in 1966, and suspended South Africa from the organization in 1974—its relations with other countries, especially the rest of Africa, also deteriorated. "
				+ "Insurrection became commonplace, and police action even more so, with events like the Sharpeville Massacre and the Soweto Uprising marring the era. "
				+ "They also made attempts to destabilize countries believed to be sponsoring anti-apartheid movements.", 
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
				"Originally a condominium between Egypt and the United Kingdom, Sudan gained independence in 1956 after a transition period. Economic difficulties plagued the country at first, "
				+ "and the general Ibrahim Abboud initiated a bloodless coup d'etat that allowed him to rule for six years. After a brief period of parliamentarism, Gaafar Nimeiry took the reins in another coup, "
				+ "and would rule for another six-year period, with a brief interruption leading to rule by the Sudanese Communist Party. All the while, agitation in the south led to the breakout of the Sudanese Civil Wars, "
				+ "which would result in the independence of South Sudan in 2011.", 
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
				"After Tunisia was released from its French protectorate in 1956, the Neo Destour (New Constitution) Party and its leader Habib Bourguiba came to dominate the nation for the next thirty years. "
				+ "The country did not dabble in socialism at first, but the Neo Destour did turn socialist for a period of six years, complete with an industrialization plan—public opposition forced its cessation.", 
				new int[] {0,0}
				));
		map.add(new Country(
				"West African States", //I honestly just picked a random country for this one.
				6, 
				"ml", 
				2, 
				false, 
				new String[] {"westafricanstates", "westafrica", "mli", "mali", "ml"}, 
				new Integer[] {51, 53}, //ci, ma
				"A representation of the nations formerly comprising French West Africa.", 
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
				"Formerly the Belgian Congo, the Republic of Congo(-Léopoldville) obtained its independence in 1960, and promptly fell into chaos as the provinces of Katanga and Kasai broke away, starting the Congo Crisis. "
				+ "The new leader, Patrice Lumumba, was able to garner aid from the Soviet Union; with American support, Joseph-Désiré Mobutu launched a coup that led to the downfall of Lumumba's government, and would go on to reunify the country. "
				+ "Mobutu formally seized power in 1965, renaming most of the nation and turning Zaire into a one-party state.", 
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
				"As the process of decolonization was proceeding and African-majority governments took power in former British colonies, Southern Rhodesia unilaterally declared independence under Ian Smith, a move left unrecognized by most nations. "
				+ "The new nation was immediately faced with the Bush War, where it fought the ZANU and the socialist ZAPU, backed by Mozambique and Zambia respectively. The war ended with the Lancaster House Agreement, where Rhodesia accepted majority rule. "
				+ "The head of the ZANU, Robert Mugabe, became the prime minister of the now-internationally recognized nation of Zimbabwe, and would eventually rise to the presidency, staying in power until 2017. ", 
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
		map.add(new Country(
				"United States of America", 
				9, 
				"us", 
				7, //cannot coup. ever. 14 > 6 [die] + 6 [Vietnam-busted China card is the maximum ops number on any country] + 1 [Brezhnev or Containment, even though it doesn't apply to the China Card]
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
				new int[] {3,0}
				));
		map.add(new Country(
				"Bulgaria", 
				2, 
				"bg", 
				3, 
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
				new int[] {0,0}
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
				"es::flag_pt", 
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
				true, 
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
				new int[] {0,3} //If you're the US you probably can't put things here anyways
				));
	}
}

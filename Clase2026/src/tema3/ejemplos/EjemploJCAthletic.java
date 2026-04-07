package tema3.ejemplos;

import java.util.*;

public class EjemploJCAthletic {

	private static final Object[][] GOLES_COPA = {
		{ "1ªronda", "1/11/23",	"Rubí-Athletic", "1-2", "Adu Ares", 50 },
		{ "1ªronda", "1/11/23",	"Rubí-Athletic", "1-2", "Adu Ares", 56 },
		{ "2ªronda", "7/12/23",	"Cayón-Athletic", "0-3", "Villalibre", 21 },
		{ "2ªronda", "7/12/23",	"Cayón-Athletic", "0-3", "Villalibre", 25 },
		{ "2ªronda", "7/12/23",	"Cayón-Athletic", "0-3", "Nico Williams", 84 },
		{ "dieciseisavos", "7/1/24", "Eibar-Athletic", "0-3", "Villalibre", 16 },
		{ "dieciseisavos", "7/1/24", "Eibar-Athletic", "0-3", "Muniain", 32 },
		{ "dieciseisavos", "7/1/24", "Eibar-Athletic", "0-3", "Villalibre", 39 },
		{ "octavos", "16/1/24",	"Athletic-Alavés", "2-0", "Villalibre", 28 },
		{ "octavos", "16/1/24",	"Athletic-Alavés", "2-0", "Villalibre", 60 },
		{ "cuartos", "24/1/24",	"Athletic-Barcelona", "4-2", "Guruzeta", 0 },
		{ "cuartos", "24/1/24",	"Athletic-Barcelona", "4-2", "Sancet", 48 },
		{ "cuartos", "24/1/24",	"Athletic-Barcelona", "4-2", "Iñaki Williams", 106 },
		{ "cuartos", "24/1/24",	"Athletic-Barcelona", "4-2", "Nico Williams", 120 },
		{ "semifinal", "7/2/24",	"Atlético-Athletic", "0-1", "Berenguer", 24 },
		{ "semifinal", "29/2/24",	"Athletic-Atlético", "3-0", "Iñaki Williams", 12 },
		{ "semifinal", "29/2/24",	"Athletic-Atlético", "3-0", "Nico Williams", 41 },
		{ "semifinal", "29/2/24",	"Athletic-Atlético", "3-0", "Guruzeta", 60 },
		{ "final", "6/4/24",	"Athletic-Mallorca", "1-1 (4-2)", "Sancet", 49 }
	};

	private static ArrayList<String> cargaGoleadores() {
		ArrayList<String> ret = new ArrayList<>();
		for (int i=0; i<GOLES_COPA.length; i++) {
			ret.add( (String) GOLES_COPA[i][4] );
		}
		return ret;
	}

	private static ArrayList<String> cargaPartidos() {
		ArrayList<String> ret = new ArrayList<>();
		for (int i=0; i<GOLES_COPA.length; i++) {
			ret.add( (String) GOLES_COPA[i][2] );
		}
		return ret;
	}

	private static ArrayList<String> goleadores;
	
	public static void main(String[] args) {
		pruebaJCStrings();
	}
	
	private static void pruebaJCStrings() {
		goleadores = cargaGoleadores();
		System.out.println( goleadores );
		
		// Linkedlist
		LinkedList<String> lista2 = new LinkedList<>();
		for (String jugador : goleadores) {
			lista2.add( jugador );
		}
		if (lista2.contains( "Sancet" )) {
			System.out.println( "Sancet está en la lista" );
		}
		System.out.println( lista2 );

		// Sets
		HashSet<String> conjuntoHash = new HashSet<String>();
		for (String jugador : goleadores) {
			conjuntoHash.add( jugador );
		}
		System.out.println( conjuntoHash );
		for (String j : conjuntoHash) {
			System.out.println( "  " + j );
		}
		if (conjuntoHash.contains( "Sancet" )) {
			System.out.println( "Sancet está en el conjunto" );
		}
		
		TreeSet<String> conjuntoTree = new TreeSet<>();
		for (String jugador : goleadores) {
			conjuntoTree.add( jugador );
			System.out.println( "  " + conjuntoTree );
		}
		System.out.println( conjuntoTree );
		if (conjuntoTree.contains( "Sancet" )) {
			System.out.println( "Sancet está en el conjunto" );
		}
		
		// Y cómo es un hash por dentro?
		HashSet<Jugador> conjJugs = new HashSet<>();
		for (String jugador : goleadores) {
			conjJugs.add( new Jugador(jugador) );
			System.out.println( " * " + conjJugs );
		}
		System.out.println( conjJugs );
		
		// Y cómo es un Tree por dentro?
		TreeSet<Jugador> conjTreeJugs = new TreeSet<>();
		for (String jugador : goleadores) {
			conjTreeJugs.add( new Jugador(jugador) );
			System.out.println( " t " + conjTreeJugs );
		}
		
		// Qué podríamos querer hacer con un mapa teniendo goleadores?
		HashMap<String,Integer> mapaConteoGoles = new HashMap<>();  // 1.- Inicializar el mapa
		// 2.- Tratamiento o carga del mapa
		for (String jugador : goleadores) {
			if (!mapaConteoGoles.containsKey(jugador)) {
				mapaConteoGoles.put( jugador, 1 ); // new Integer(1) ); --> recordemos que el Integer es inmutable
			} else {
				// mapaConteoGoles.get(jugador)++  No se puede porque integer es inmutable
				int golesHastaAhora = mapaConteoGoles.get( jugador );
				golesHastaAhora++;
				mapaConteoGoles.replace( jugador, golesHastaAhora );  // put funciona igual que replace si la clave existe
			}
			System.out.println( mapaConteoGoles );
		}
		System.out.println( mapaConteoGoles );
		// Reprogramado con enteros mutables
		TreeMap<String,EnteroMutable> mapaConteoGoles2 = new TreeMap<>();  // 1.- Inicializar el mapa
		// 2.- Carga con if (existe / no existe la clave)
		for (String jugador : goleadores) {
			if (!mapaConteoGoles2.containsKey(jugador)) {
				mapaConteoGoles2.put( jugador, new EnteroMutable(1) ); // este sí es mutable
			} else {
				// mapaConteoGoles2.get(jugador).inc1();
				// De otra manera
				EnteroMutable em = mapaConteoGoles2.get(jugador);
				em.inc1();
			}
			System.out.println( mapaConteoGoles );
		}
		System.out.println( mapaConteoGoles );
		// 3.- Búsquedas -> get
		// 4.- Recorridos -> keySet() las claves / values() los valores
		for (String clave : mapaConteoGoles2.keySet()) {
			System.out.println( "Jugador " + clave + " - goles " + mapaConteoGoles2.get(clave) );
		}
		
		// Lista de partidos en los que se ha metido gol?
		HashMap<String,ArrayList<String>> mapaPartidos = new HashMap<>();
		ArrayList<String> partidos = cargaPartidos();
		for (int i=0; i<goleadores.size(); i++) {
			String jugador = goleadores.get(i);
			String partido = partidos.get(i);
//			if (!mapaPartidos.containsKey(jugador)) {
//				mapaPartidos.put( jugador, new ArrayList<>() );
//				mapaPartidos.get( jugador ).add( partido );
//			} else {
//				mapaPartidos.get( jugador ).add( partido );
//			}
			if (!mapaPartidos.containsKey(jugador)) {
				mapaPartidos.put( jugador, new ArrayList<>() );
			}
			mapaPartidos.get( jugador ).add( partido );
		}
		System.out.println( mapaPartidos );
		for (String jugador : mapaPartidos.keySet()) {
			System.out.println( jugador + " - partidos " + mapaPartidos.get(jugador) );
		}
	}
	
	
}

package tema3.ejemplos;

import java.util.*;

/** Clase de ejemplo de uso de Java Collections
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public class EjemploJC {
	public static void main(String[] args) {
		// pruebasJCconStrings();
		pruebasJCconClasesPropias();
	}

	private static String[] peliculasOscars2023 = {
			"Oppenheimer", "Oppenheimer", "Oppenheimer", "Pobres criaturas",   // 4 principales
			"Oppenheimer", "Los que se quedan",  // Act secundarios
			"La zona de interés",   // Internacional
			"Anatomía de una caída",  // Guión original
			"American fiction", // Guión adaptado
			"Oppenheimer", "Oppenheimer", "La zona de interés", "Oppenheimer",  // Fotografía, montaje, sonido, banda sonora
			"Pobres criaturas", "Pobres criaturas", "Pobres criaturas",  // Producción, vestuario, maquillaje
			"Godzilla: minus one",  // Efectos visuales
			"Barbie",  // Canción
			"El chico y la garza", // Animación
			"20 días en Mariupol", // Documental
			"La maravillosa historia de Henry Sugar", "The last repair shop", "War is over" // Cortos
		};
	
	private static String[] premioOscar2023 = {
	        "Mejor película", "Mejor dirección", "Mejor actor", "Mejor actriz",
	        "Mejor actor de reparto", "Mejor actriz de reparto",
	        "Mejor película internacional",
	        "Mejor guión original",
	        "Mejor guión adaptado",
	        "Mejor fotografía", "Mejor montaje", "Mejor sonido", "Mejor banda sonora",
	        "Mejor diseño de producción", "Mejor vestuario", "Mejor maquillaje y peluquería",
	        "Mejores efectos visuales",
	        "Mejor canción original",
	        "Mejor película de animación",
	        "Mejor documental",
	        "Mejor cortometraje de ficción", "Mejor cortometraje documental", "Mejor cortometraje de animación"
	};
		
	private static void pruebasJCconStrings() {
		// TODO
		// ArrayList:
		ArrayList<String> al = new ArrayList<>();
		for (String peli : peliculasOscars2023) {
			al.add( peli );
		}
		System.out.println( "AL: " + al );
		if (al.contains( "Oppenheimer") ) {
			System.out.println( "Oppenheimer existe en AL" );
		}
		
		LinkedList<String> ll = new LinkedList<>();
		for (String peli : peliculasOscars2023) {
			ll.add( peli );
		}
		System.out.println( "LL: " + ll );
		if (ll.contains( "Oppenheimer" ) ) {
			System.out.println( "Oppenheimer existe en LL" );
		}
		
		HashSet<String> hs = new HashSet<>();
		for (String peli : peliculasOscars2023) {
			hs.add( peli );
			System.out.println( "  " + hs );
		}
		System.out.println( "HS: " + hs );
		if (hs.contains( "Oppenheimer" ) ) {
			System.out.println( "Oppenheimer existe en HS" );
		}
		// No hs.get(n)  !!!!
		
		
		TreeSet<String> ts = new TreeSet<>();
		for (String peli : peliculasOscars2023) {
			ts.add( peli );
			System.out.println( "  " + ts );
		}
		System.out.println( "TS: " + ts );
		if (ts.contains( "Oppenheimer" ) ) {
			System.out.println( "Oppenheimer existe en TS" );
		}
	}
	
	private static void pruebasJCconClasesPropias() {
		HashSet<Pelicula> hs = new HashSet<>();
		for (String peli : peliculasOscars2023) {
			hs.add( new Pelicula( peli, 2023 ) );
			System.out.println( "  " + hs );
		}
		System.out.println( "HS: " + hs );
		if (hs.contains( new Pelicula( "Oppenheimer", 2023) ) ) {
			System.out.println( "Oppenheimer existe en HS" );
		}
		
		TreeSet<Pelicula> ts = new TreeSet<>();
		for (String peli : peliculasOscars2023) {
			ts.add( new Pelicula( peli, 2023 ) );
			System.out.println( "  " + ts );
		}
		System.out.println( "TS: " + ts );
		if (ts.contains( new Pelicula( "Oppenheimer", 2023) ) ) {
			System.out.println( "Oppenheimer existe en TS" );
		}
		
		// Mapas
		
		// Contador de oscars
		// Clave: nombre de peli (String) >> Valor: Integer (contador)
		// (Podría ser peli la clave porque la tenemos preparada)
		
		// 1. Crear el mapa
		HashMap<String,Integer> contadoresPelis = new HashMap<>();
		
		// 2. Alimentar el mapa
		for (String peli : peliculasOscars2023) {
			// Existe o no la clave?
			if (!contadoresPelis.containsKey( peli )) {
				// La primera vez que llega una clave
				contadoresPelis.put( peli, new Integer(1) );
			} else {
				// Las segundas, terceras, enésimas... veces que entra la clave
				// contadoresPelis.get( peli ).inc();  // No es MUTABLE el Integer
				int contador = contadoresPelis.get(peli);
				contador++;
				contadoresPelis.remove(peli);
				contadoresPelis.put(peli, contador);
			}
		}
		
		// 3. Uso o recorrido del mapa
		for (String nombre : contadoresPelis.keySet()) {
			int contador = contadoresPelis.get(nombre);
			System.out.println( nombre + " --> " + contador );
		}
		
		
	}
	
}

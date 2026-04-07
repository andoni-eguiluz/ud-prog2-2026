package tema3.ejercicios;

import java.util.*;

/** Ejercicio de Java Collections
 * Preparado para sacar equipos del calendario de partidos de la liga de fútbol desde una web
 * Utilizado para practicar con Java Collections
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public class SacarEquiposFutbol {

	public static void main(String[] args) {
		// Saca el código html del calendario de liga que coincide con una parte de los nombres de equipos
		ArrayList<String> l = ProcesaURLs.buscaEnWeb( "https://www.marca.com/futbol/primera-division/calendario.html", 
				"<img src=\"https://e00-marca.uecdn.es/assets/", 
				"iso-8859-15" );
		procesa( l );
	}

	// Método para hacer pruebas de Java Collections con los equipos de la liga de fútbol
	private static void pruebasDeJC( String equipo ) {
		System.out.println( equipo );  // Comentar esta línea cuando hayas resuelto el ejercicio
		// Resolver los siguientes puntos
		// 1.- Meter todos los equipos tal cual aparecen en una lista y visualizarla
		// 2.- Meter todos los equipos tal cual aparecen en una lista SOLO si no estaban ya en ella. Visualizarla
		// 3.- Meter todos los equipos en un conjunto SIN ORDEN. Visualizarlos
		// 3b.- Lo mismo en lugar de con string con una clase Equipo (que contenga el nombre). hashCode + equals
		// 4.- Meter todos los equipos en un conjunto CON ORDEN (alfabético). Visualizarlos
		// 4b.- Lo mismo ordenando AL REVÉS, en lugar de con string con una clase Equipo (que contenga el nombre). comparable
		// 5.- Usar un mapa para contar el número de veces que aparece cada equipo (Integer)
		// 6.- Usar un mapa para contar el número de veces que aparece cada equipo (Entero MUTABLE)
		// 7.- Usar un mapa para sacar una lista de todos los enfrentamientos de cada equipo
		// 8.- Usar un mapa para sacar una lista de todos los enfrentamientos de cada equipo (con objeto Partido)
	}
	
	private static void cierre() {
		System.out.println( "** FIN **" );
	}
	
	private static void procesa( ArrayList<String> lHtml ) {
		// System.out.println( "Html encontrado:" );
//		for (String html : lHtml) {
//			System.out.println( html );
//		}
		// Proceso de cada nombre de equipo
		System.out.println();
		System.out.println( "Equipos encontrados:" );
		for (String html : lHtml) {
			String equipo = sacaEquipoDeHtml( html );
			// System.out.println( equipo );
			pruebasDeJC( equipo );
		}
		cierre();
	}
	
	private static String sacaEquipoDeHtml( String html ) {
		// Buscar esta parte: alt="Barcelona"/>   (o cualquier otro equipo)
		int posi1 = html.indexOf( "alt=\"" );
		int posi2 = html.indexOf( "\"", posi1+5 );
		// System.out.println( posi1 + " - " + posi2 );
		String equipo = html.substring( posi1+5, posi2 );
		return equipo;
	}

}

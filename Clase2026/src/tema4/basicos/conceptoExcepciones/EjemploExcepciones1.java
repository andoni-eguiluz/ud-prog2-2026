package tema4.basicos.conceptoExcepciones;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/** Clase de ejemplo para entender excepciones sencillas
 */
public class EjemploExcepciones1 {
	public static void main(String[] args) {
		System.out.println( "Inicio del programa" );
		excepcionNoGestionada();
		System.out.println( "Punto entre llamadas" );
		excepcionGestionada();
		System.out.println( "Fin del programa" );
	}
	
	private static void excepcionNoGestionada() {
		int i = 5;
		int j = 0;
		System.out.println( i + "/" + j + "=" + (i/j) );
	}

	private static void excepcionGestionada() {
		// TODO Descomentar estas líneas
//		Scanner leer = new Scanner( new File( "prueba.txt" ) );
//		System.out.println( "Contenidos del fichero prueba.txt" );
//		while (leer.hasNextLine()) {
//			System.out.println( leer.nextLine() );
//		}
//		leer.close();
	}
	
}

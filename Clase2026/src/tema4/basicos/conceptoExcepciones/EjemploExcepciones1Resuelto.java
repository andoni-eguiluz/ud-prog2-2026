package tema4.basicos.conceptoExcepciones;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/** Clase de ejemplo para entender excepciones sencillas
 */
public class EjemploExcepciones1Resuelto {
	public static void main(String[] args) {
		try {
			System.out.println( "Inicio del programa" );
			excepcionNoGestionada( 5, 0 );
		} catch (ArithmeticException e) {
			System.out.println( "Ha habido error en división" );
			// e.printStackTrace();
		} catch (NullPointerException e) {
			// Gestión
			e.printStackTrace();
		}
		System.out.println( "Punto entre llamadas" );
		try {
			excepcionGestionada();
		} catch (FileNotFoundException e) {
			System.out.println( "Fichero no encontrado" );
		}
		System.out.println( "Fin del programa" );
	}
	
	/** Calcular una división y sacar el resultado a pantalla
	 * @param i	Dividendo
	 * @param j	Divisor
	 * @throws ArithmeticException	Producida si el divisor es cero
	 */
	private static void excepcionNoGestionada( int i, int j ) throws ArithmeticException {
		System.out.println( i + "/" + j + "=" + (i/j) );
		// si hubiera try/catch no pondría throws
	}

	private static void excepcionGestionada() throws FileNotFoundException {
		// TODO Descomentar estas líneas
		Scanner leer = new Scanner( new File( "prueba.txt" ) );
		System.out.println( "Contenidos del fichero prueba.txt" );
		while (leer.hasNextLine()) {
			System.out.println( leer.nextLine() );
		}
		leer.close();
	}
	
}

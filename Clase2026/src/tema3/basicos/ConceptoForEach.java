package tema3.basicos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/** Concepto y ejemplos de bucle for each
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class ConceptoForEach {
	public static void main(String[] args) {
		ejemploArray();
		ejemploArrayList();
		ejemploLoQueHace();
		ejemploLoQueNoHace();
		ejemploIterable();
	}
	
	private static void ejemploArray() {
		// Un for each permite recorrer un array. Lo recorre entero, aunque parte esté "vacío" (por ejemplo nulls)
		int[] aInts = { 1, 2, 3, 4 };
		Integer[] aIntegers = { new Integer(1), new Integer(2), null, null, null };
		System.out.print( "Array de ints: ");
		for (int i : aInts) {
			System.out.print( i + "  " );
		}
		System.out.println();
		System.out.print( "Array de integers con nulls: ");
		for (Integer i : aIntegers) {
			System.out.print( i + "  " );
		}
		System.out.println();
	}
	
	private static void ejemploArrayList() {
		// Un for each permite recorrer un arraylist, y en general cualquier Collection
		ArrayList<String> lStrings = new ArrayList<String>( Arrays.asList( "a", "b", "c" ) );
		System.out.print( "Arraylist de strings: ");
		for (String s : lStrings) {
			System.out.print( s + "  " );
		}
	}
	
	private static void ejemploLoQueHace() {
		int[] aInts = { 1, 2, 3, 4, 5 };
		System.out.print( "El recorrido... ");
		for (int num : aInts) {
			System.out.print( num + "  " );
		}
		System.out.println();
		System.out.println( "- Es siempre en orden ascendente" );
		System.out.println( "- Es completo" );
		System.out.print( "- Se puede parar con un break;  " );
		for (int num : aInts) {
			System.out.print( num + "  " );
			if (num == 3) break;
		}
		System.out.println();
	}
	
	private static void ejemploLoQueNoHace() {
		System.out.println( "Y lo que no se puede es:");
		System.out.println( "- Recorrer al revés o de cualquier otra manera que no sea secuencial" );
		System.out.println( "- Recuperar el índice" );
		System.out.println( "  - Por tanto no se pueden recorrer dos arrays a la vez" );

		System.out.println( "- Modificar la estructura según se recorre (puede provocar funcionamiento parcial o errores)" );
		ArrayList<String> lStrings = new ArrayList<String>( Arrays.asList( "a", "b", "c" ) );
		System.out.print( "Arraylist de strings: ");
		for (String s : lStrings) {
			System.out.print( s + "  " );
			// if (s.equals("a")) lStrings.remove(s);  // Si se quitan los comentarios da error de ejecución
			// if (s.equals("c")) lStrings.add(0,"j");  // Si se quitan los comentarios da error de ejecución
		}
		System.out.println();
	}

	// Hay dos interfaces en Java que permiten definir estructuras de datos que pueden ser
	// recorridas con un for each: Iterator e Iterable.
	// Iterator es la estructura en sí misma y tiene que tener métodos hasNext() y next()
	// Iterable simplemente devuelve un iterator
	private static void ejemploIterable() {
		System.out.print( "Ejemplo con iterable: ");
		// Pueden ser dos clases independientes
		EjemploIterable estructuraRecorrible = new EjemploIterable();
		for (String string : estructuraRecorrible) {
			System.out.print( string + "  " );
		}
		System.out.println();
		// O la misma clase puede implementar iterator e iterable:
		System.out.print( "Otro ejemplo: ");
		Iterable<String> otraEstructura = new EjemploIteratorEIterable();
		for (String string : otraEstructura) {
			System.out.print( string + "  " );
		}
	}
	
}

class EjemploIterable implements Iterable<String> {
	@Override
	public Iterator<String> iterator() {
		return new EjemploIterator();
	}
}

class EjemploIterator implements Iterator<String> {
	private String[] ejemploStrings = { "A", "B", "C", "D" };
	private int posicion;
	EjemploIterator() {
		posicion = 0;
	}
	@Override
	public boolean hasNext() {
		return (posicion<ejemploStrings.length);
	}
	@Override
	public String next() {
		return ejemploStrings[posicion++];  // El ++ primero devuelve su valor y después suma
	}
}


class EjemploIteratorEIterable implements Iterator<String>, Iterable<String> {
	private String[] ejemploStrings = { "A2", "B2", "C2", "D2" };
	private int posicion;
	EjemploIteratorEIterable() {
		posicion = 0;
	}
	@Override
	public boolean hasNext() {
		return (posicion<ejemploStrings.length);
	}
	@Override
	public String next() {
		return ejemploStrings[posicion++];  // El ++ primero devuelve su valor y después suma
	}
	@Override
	public Iterator<String> iterator() {
		posicion = 0;  // Reinicia la iteración
		return this;
	}
}

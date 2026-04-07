package tema3.javaCollections;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/** Ejemplo de algunas utilidades de Java Collections: intercambio, ordenación, inversión, búsqueda...
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public class PruebasUtilidadesJavaCollections {
	public static void main(String[] args) {
		ArrayList<Peli> l = new ArrayList<Peli>();
		// Facilitar las listas de valores
		inicializarValores( l );
		// Ordenación
		ordenar( l );
		// Otras utilidades
		otras( l );
	}
	
	private static void otras( ArrayList<Peli> l ) {
		Peli p1 = new Peli( "Green Book", 2018 );
		Peli p2 = new Peli( "La Favorita", 2018 );
		System.out.println();
		Collections.sort( l );  // Ordenar una lista
		System.out.println( l );
		Collections.reverse( l );  // Invertir una lista
		System.out.println( l );
		Collections.shuffle( l );  // Desordenar aleatoriamente una lista
		System.out.println( l );
		// Invertir el 0 y el 2
		Peli temp = l.get( 0 );
		l.set( 0, l.get(2) );
		l.set( 2, temp );
		System.out.println( l );
		Collections.swap( l, 0, 2 );  // Intercambio de elementos en una lista
		System.out.println( l );
		
		// Búsqueda binaria - solo en estructuras de comparables YA ORDENADAS
		Collections.sort( l );
		System.out.println();
		System.out.println( l );
		int res1 = Collections.binarySearch( l, p1 );
		int res2 = Collections.binarySearch( l, p2 );
		System.out.println( res1 );
		System.out.println( res2 );
		// Positivo n -> está en la posición n-1
		// Negativo -n -> se podría insertar en la posición (n-1)
	}
	
	private static void ordenar( ArrayList<Peli> l ) {
		Collections.sort( l );
		System.out.println( l );
		// Ordenar de distintas maneras?
		Comparator<Peli> comparador = new Comparador1();
		Collections.sort( l, comparador );
		System.out.println( l );
		Comparator<Peli> comparador2 = new Comparador2();
		Collections.sort( l, comparador2 );
		System.out.println( l );
		// l.sort( comparador2 );
	}
	
	private static void inicializarValores( ArrayList<Peli> l ) {
		Peli p1 = new Peli( "Avengers: EndGame", 2019 );
		Peli p2 = new Peli( "Green Book", 2018 );
		Peli p3 = new Peli( "Roma", 2018 );
		// Inicialización array
		Peli[] ap = new Peli[] { p1, p2, p3 };
		// Inicialización collection
		// Manera 1
		l.add( p1 );
		l.add( p2 );
		l.add( p3 );
		// Manera 2
		l.addAll( Arrays.asList( ap ) );
		// Manera 3
		l.addAll( Arrays.asList( p1, p2, p3 ) );
		System.out.println( l );
	}
}

// CLASES DE EJEMPLO PARA PRUEBAS DE LAS UTILIDADES DE Java Collections

class Peli extends Object implements Comparable<Peli>, Serializable {
	private static final long serialVersionUID = 1L;  // Número de versión de este objeto (desde el punto de vista de la serialización)
	private String nombre;
	private int anyo;
	// private Genero genero;  // Si tuviéramos otros objetos tienen que implementar también Serializable

	public Peli(String nombre) {
		super();
		this.nombre = nombre;
		this.anyo = 0;
	}
	
	public Peli(String nombre, int anyo) {
		this.nombre = nombre;
		this.anyo = anyo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getAnyo() {
		return anyo;
	}

	public void setAnyo(int anyo) {
		this.anyo = anyo;
	}

	@Override
	public int hashCode() {
		return nombre.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Peli)) return false;
		return nombre.equals( ((Peli)obj).nombre ); 
	}

	@Override
	public String toString() {
		return nombre + " (" + anyo + ")";
	}

	@Override
	public int compareTo(Peli o) {
		return nombre.compareTo( o.nombre );
	}
	
	
	// ========================================= 
	//  Métodos para gestión de pelis en fichero de texto
	// ========================================= 
	
	/** Método de conversión a string para escritura a fichero de texto
	 * @return
	 */
	public String toStringAFichero() {
		return nombre + "\t" + anyo;
	}
	
	/** Constructor indirecto que devuelve un objeto Peli partiendo de un string formateado
	 * con el nombre de la peli un tabulador y el año de la peli
	 * @param linea
	 * @return
	 */
	public static Peli leerDeLinea( String linea ) {
		int posi = linea.indexOf( "\t" );
		int anyo = Integer.parseInt( linea.substring( posi+1 ) );
		Peli peli = new Peli( linea.substring( 0, posi ), anyo );
		return peli;
	}	
	
}

/** Comparador de Pelis
 * @author andoni.eguiluz at deusto.es
 */
class Comparador1 implements Comparator<Peli> {
	@Override
	public int compare(Peli arg0, Peli arg1) {
		return -arg0.getNombre().compareTo( arg1.getNombre() );
	}

}

/** Comparador de Pelis
 * @author andoni.eguiluz at deusto.es
 */
class Comparador2 implements Comparator<Peli> {
	@Override
	public int compare(Peli arg0, Peli arg1) {
		if (arg0.getAnyo()!=arg1.getAnyo()) {
			return arg0.getAnyo() - arg1.getAnyo(); // negativo si 1<2, positivo si 1>2, 0 si 1==2
		}
		return arg0.getNombre().compareTo( arg1.getNombre() );
	}

}
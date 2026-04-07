package tema2.ejemplos;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ComparacionYOrdenacion {
	public static void main(String[] args) {
		comoFuncionaElSort();
		comoCompararNuestrasClases();
	}
	
	private static void comoCompararNuestrasClases() {
		ArrayList<Persona> listaPersonas = new ArrayList<>(
			Arrays.asList( new Persona( "a", "a", "1a", "a@a" ),
				new Persona( "z", "z", "4z", "z@z" ),
				new Persona( "c", "c", "15c", "c@c" ),
				new Persona( "a", "c", "103c", "ac@ac" ),
				new Persona( "b", "b", "3b", "b@b" )
			)
		);
		System.out.println( listaPersonas );
		Collections.sort( listaPersonas );
		listaPersonas.sort( null );
		System.out.println( listaPersonas );
		// Cómo ordenamos por dni?
		// Con un comparator
		ComparadorPorDNI comp = new ComparadorPorDNI();
		listaPersonas.sort( comp );
		System.out.println( listaPersonas );
		listaPersonas.sort( new ComparadorPorDNI2() );
		System.out.println( listaPersonas );
		
	}
	
	private static void comoFuncionaElSort() {
		ArrayList<String> listaNombres = new ArrayList<>();
		listaNombres.add( "Andoni" );
		listaNombres.add( "Emilio" );
		listaNombres.add( "Iratxe" );
		listaNombres.addAll( Arrays.asList(
			new String[] { "Alazne", "alberto", "Xabat" }
		));
		listaNombres.addAll( Arrays.asList(
			"Lucas", "Marta", "Jaime"
		));
		System.out.println( listaNombres );
		Collections.sort( listaNombres );
		System.out.println( listaNombres );
		listaNombres.sort( null );
		System.out.println( listaNombres );
		
		ArrayList<Point> listaPuntos = new ArrayList<>(
			Arrays.asList( new Point(2,4), new Point(4,2), new Point(-3,5) )
		);
		System.out.println( listaPuntos );
		// Collections.sort( listaPuntos );  // No funciona ¿por qué? "Not applicable"
		// listaPuntos.sort( null );  // No funciona error en ejecución "ClassCastException"
		// Pero con comparator sí:
		listaPuntos.sort( new ComparadorPuntos() );
		System.out.println( listaPuntos );
	}
}

package tema1c.ejemplos.ajedrez;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

import tema1c.ejemplos.ajedrez.datos.*;
import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class PruebasInterfaces {
	public static void main(String[] args) {
		// Pruebas de ordenación con Comparable
		ArrayList<Integer> l = new ArrayList<>();
		l.add( 7 );
		l.add( -3 );
		l.add( 2 );
		Collections.sort( l );
		l.sort( null );
		System.out.println( l );
		ArrayList<Point> lP = new ArrayList<>();
		lP.add( new Point( 5, 3 ) );
		lP.add( new Point( 5, 2 ) );
		lP.add( new Point( -5, 3 ) );
		lP.add( new Point( 0, 0 ) );
		// lP.sort( null );   No se puede -- point no es comparable
		System.out.println( lP );
		
		ArrayList<Object> lPiezas = new ArrayList<Object>();
		VentanaGrafica v = new VentanaGrafica( 300, 300, "Prueba" );
		Tablero tab = new Tablero( 0, 0, 300, 300, v );
		lPiezas.add( new Alfil( 3, 2, Color.BLANCA, tab ) );
		lPiezas.add( new Caballo( 4, 2, Color.BLANCA, tab ) );
		lPiezas.add( new Dama( 3, 5, Color.BLANCA, tab ) );
		lPiezas.add( new Rey( 7, 1, Color.BLANCA, tab ) );
		// lPiezas.add( 5 );  Si añadiéramos un Integer, no se puede ordenar con Pieza  (Pieza no es comparable con Integer)
		System.out.println( lPiezas );
		lPiezas.sort( null );
		System.out.println( lPiezas );
		for (Pieza p : tab.getPiezas()) {   // TODO: for (Pieza p : tab)
			System.out.println( p );
		}
	}
}

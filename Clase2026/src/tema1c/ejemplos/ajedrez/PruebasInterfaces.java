package tema1c.ejemplos.ajedrez;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import tema1c.ejemplos.ajedrez.datos.*;
import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class PruebasInterfaces {
	
	public static void main(String[] args) throws Exception {
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
		v.acaba();
		
		
		// Prueba Iterable
		VentanaGrafica v2 = new VentanaGrafica( 600, 402, "Tablero iterable" );
		Tablero tablero = new Tablero( 100, 0, 400, 400, v2 );
		tablero.crearYColocarPiezas();
		tablero.dibujar();

		// Originalmente no funciona pero al hacer Iterable el tablero, sí
		for (Pieza p : tablero) {
			System.out.println( p );
		}
		
		// Prueba Comparable  [comparable y ordenable son lo mismo]
		ArrayList<Pieza> listaPiezas = new ArrayList<>( tablero.getPiezas() );
		Collections.sort( listaPiezas );
		System.out.println( listaPiezas );

		// Prueba Comparator
		listaPiezas.sort( new ComparadorPiezas() );
		System.out.println( listaPiezas );
		
		// Prueba Serializable - Reloj
		Reloj r = new Reloj( 50, 50, 50, v2 );
		r.dibujar();

		// Desde aquí solo funciona si hacemos throws Exception en el main (lo veremos)
		ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream( "test.dat" ) );
		oos.writeObject( r );  // TODO Necesita que el reloj sea Serializable... y luego que la ventana sea transient
		oos.close();

		v2.espera( 5000 );
		r.setY( 250 );
		r.actualizar( 5 );
		v2.borra();
		r.dibujar();

		v2.espera( 5000 );
		ObjectInputStream ois = new ObjectInputStream( new FileInputStream( "test.dat" ) );
		Reloj r2 = (Reloj) ois.readObject();  // TODO Necesita que también ElementoVisual (madre) sea serializable
		ois.close();
		v2.borra();
		// r2.setVentana( v2 ); // TODO Esto hace falta porque si la ventana es transient no se recupera al leer del fichero
		r2.dibujar();
		
	}

	// ---------- CLASE INTERNA ------------
	// Clase local - interna - también podría ser externa
	// static es porque va dentro de una clase que no pretende crear objetos - si es externa no hace falta
	static class ComparadorPiezas implements Comparator<Pieza> {
		@Override
		public int compare(Pieza o1, Pieza o2) {
			// Ejemplo de otro criterio de ordenación: Ordenamos por distancia a la esquina 0,0
			double distOrigen1 = Math.sqrt( o1.getX()*o1.getX() + o1.getY()*o1.getY() );
			double distOrigen2 = Math.sqrt( o2.getX()*o2.getX() + o2.getY()*o2.getY() );
			if (distOrigen1 < distOrigen2) {
				return -1;
			} else if (distOrigen1 < distOrigen2) {
				return +1;
			} else {
				return 0;
			}
		}
	}

}


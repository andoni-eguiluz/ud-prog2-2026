package tema1b.ejemplos;

import java.awt.Color;
import java.util.ArrayList;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class PruebaFiguritas {
	public static void main(String[] args) {
		VentanaGrafica v = new VentanaGrafica( 600, 400, "Prueba" );
		Circulo c1 = new Circulo( 50, Color.BLUE, 200, 100 );
		c1.dibujar(v);
		Rectangulo r1 = new Rectangulo( 0, 0, 200, 100, Color.GREEN, 2.5f );
		r1.dibujar(v);
		System.out.println( c1 );
		System.out.println( r1 );
		Circulo c2 = new Circulo( 50, Color.BLUE, 200, 100 );
		System.out.println( c1.equals(r1) );
		System.out.println( c1.equals(c2) );
		// Polimorfismo de datos
		Circulo c3 = c2;
		// Rectangulo r3 = c2;  NO
		Figura f3 = c2;
		Object o3 = c2;
		System.out.println( c2.getRadio() );
		// System.out.println( f3.getRadio() );  NO
		if (f3 instanceof Circulo) {
			System.out.println( ((Circulo)f3).getRadio() );
		}
		ArrayList<Circulo> lC = new ArrayList<>();
		lC.add( c1 );
		lC.add( c2 );
		lC.add( c3 );
		ArrayList<Figura> lF = new ArrayList<>();
		lF.add( c1 );
		lF.add( r1 );
		System.out.println( lF ); // Polimorfismo de código
		for (Figura f : lF) {
			f.dibujar(v);  // Polimorfismo de código
		}
	}
}

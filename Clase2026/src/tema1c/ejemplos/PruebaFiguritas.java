package tema1c.ejemplos;

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
		ArrayList<Figura> lF = new ArrayList<>();
		lF.add( c1 );
		lF.add( r1 );
		lF.add( new Flecha( 100, 100, 200, 200) );
		lF.add( new Imagen( 200, 200, "sonic.png", 1.00) );
		lF.add( new ImagenEscalada( 300, 300, "dama-blanca.png", 150, 150 ) );
		Imagen i = ImagenEscalada.crear( 450, 170, "bubble.png", 640, 627, v );
		lF.add( i );
		System.out.println( lF ); // Polimorfismo de código
		Flecha f2 = new Flecha( 100, 100, 200, 200);
		f2.rotar( Math.PI/8 );
		f2.setColor( Color.MAGENTA );
		lF.add( f2 );
		for (Figura f : lF) {
			f.dibujar(v);  // Polimorfismo de código
		}
		// Bucle de rotación
		while (!v.estaCerrada()) {
			v.borra();
			for (Figura f : lF) {
				if (f instanceof Rotable) {
					((Rotable)f).rotar( 0.1 );
				}
			}
			for (Figura f : lF) {
				f.dibujar(v);
			}
			v.espera( 40 );
		}
	}
	
}

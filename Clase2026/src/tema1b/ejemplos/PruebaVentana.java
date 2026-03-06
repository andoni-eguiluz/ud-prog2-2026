package tema1b.ejemplos;

import java.awt.Color;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class PruebaVentana {
	public static void main(String[] args) {
		VentanaGrafica v = new VentanaGrafica( 600, 400, "Prueba" );
		v.dibujaCirculo(300, 200, 50, 2.5f, new Color(  254, 100, 0 ) );
		v.espera( 10000 );
		v.dibujaFlecha( 100, 50, 250, 100, 2.5f );
		v.espera( 1000 );
		v.acaba();
	}
}

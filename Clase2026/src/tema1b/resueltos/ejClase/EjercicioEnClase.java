package tema1b.resueltos.ejClase;

import java.awt.Font;
import java.awt.Point;
import java.util.Random;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Ejercicio en clase resuelto
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class EjercicioEnClase {

	// Juego
	private static GrupoFiguras figuras;
	private static final long ESPERA_ENTRE_FRAMES = 20;  // 50 fps
	private static VentanaGrafica ventana;
	private static Random random = new Random();
	
	/** Método principal del juego
	 * @param args	No utilizado
	 */
	public static void main(String[] args) {
		initVentana();
		juego();
	}
	
	private static void initVentana() {
		ventana = new VentanaGrafica( 800, 600, "Ejercicio en clase" );
		ventana.setMensajeFont( new Font( "Arial", Font.PLAIN, 24 ) );
		ventana.setDibujadoInmediato(false);
	}
	
	private static void juego() {
		figuras = new GrupoFiguras();
		// Bucle de juego (sistema de tiempo real)
		while (!ventana.estaCerrada()) {
			// Input - qué hace el usuario
			Point hayClick = ventana.getRatonClicado();
			// Mecánica: crear figura
			if (hayClick!=null) {
				if (ventana.isBotonIzquierdo()) {
					int tam = random.nextInt(21) + 20;
					Cuadrado c = new Cuadrado( tam, hayClick.x, hayClick.y ); 
					c.setVelXY( 0, 100 );
					figuras.add( c ); 
				} else if (ventana.isBotonDerecho()) {
					int tam = random.nextInt(26) + 15;
					Hexagono h = new Hexagono( hayClick.x, hayClick.y, tam ); 
					h.setVelXY( 100, 0 );
					figuras.add( h ); 
				}
			}

			// Mecánica: movimiento de círculos
			figuras.mover( ESPERA_ENTRE_FRAMES );
			
			// Dibujado
			ventana.borra();
			figuras.dibujar( ventana );
			ventana.repaint();
			
			// Ciclo de espera del bucle de juego de tiempo real
			ventana.espera( ESPERA_ENTRE_FRAMES );
		}
	}
	
}

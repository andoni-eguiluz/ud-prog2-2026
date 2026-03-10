package tema1b.resueltos.ej1b7.previo;

import java.awt.Font;
import java.awt.Point;
import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Ejercicio 2a.7 (última versión del 1.13 preparada para modificar con herencia)
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class ClickerV3 {

	// Círculos
	private static final long TIEMPO_MAXIMO_PUNTOS = 2000;
	private static final long PUNTUACION_CLICK_CIRCULO_MUERTO = -10000;
	private static final long PUNTUACION_CLICK_FUERA_ELEMENTO = -1000;
	private static final long ESPERA_ENTRE_CIRCULOS = 750;  // milis entre círculo y círculo
	private static final int NUM_MAXIMO_CIRCULOS = 30; // máximo número de círculos (si hay más se acaba el juego)
	private static GrupoCirculos circulos;
	// Ladrillos
	private static GrupoRectangulos rectangulos;
	private static final int ANCH_LADRILLO = 100;
	private static final int ALT_LADRILLO = 40;
	private static final long ESPERA_ENTRE_LADRILLOS = 2000;  // milis entre ladrillo y ladrillo
	private static final long TIEMPO_MAXIMO_LADRILLO = 5000;  // milis de vida de ladrillo
	private static final int PUNTUACION_CLICK_LADRILLO = -5000;  // puntos de menos por ladrillo
	// Juego
	private static final long ESPERA_ENTRE_FRAMES = 20;  // 50 fps
	private static VentanaGrafica ventana;
	private static int puntos;
	
	/** Método principal del juego
	 * @param args	No utilizado
	 */
	public static void main(String[] args) {
		initVentana();
		juego();
	}
	
	private static void initVentana() {
		ventana = new VentanaGrafica( 800, 600, "Clicker (ej. 1.13)" );
		ventana.setMensajeFont( new Font( "Arial", Font.PLAIN, 24 ) );
		ventana.setDibujadoInmediato(false);
	}
	
	private static void juego() {
		circulos = new GrupoCirculos();
		long tiempoUltimoCirculo = 0; // Tiempo de salida de círculos (el primero aparece nada más empezar)
		rectangulos = new GrupoRectangulos();
		long tiempoUltimoLadrillo = System.currentTimeMillis(); // Tiempo de salida de ladrillos (el primero espera unos segundos)
		puntos = 0;
		
		// Bucle de juego (sistema de tiempo real)
		while (!ventana.estaCerrada()) {
			// Input - qué hace el usuario
			Point hayClick = ventana.getRatonClicado();

			// Update - qué mecánicas calcula el juego
			// Mecánica: click en círculo o ladrillo - puntuación acorde al click
			hayClickEnAlgunCirculoOLadrillo( circulos, hayClick );
			// Mecánica: aparición de nuevos círculos (de acuerdo al tiempo)
			if (System.currentTimeMillis() - tiempoUltimoCirculo > ESPERA_ENTRE_CIRCULOS) {
				circulos.add( new Circulo( ventana ) );
				tiempoUltimoCirculo = System.currentTimeMillis();
			}
			// Mecánica: Aparición y desaparición de ladrillos (de acuerdo al tiempo)
			if (System.currentTimeMillis() - tiempoUltimoLadrillo > ESPERA_ENTRE_LADRILLOS) {
				rectangulos.add( new Rectangulo( ANCH_LADRILLO,  ALT_LADRILLO, ventana ) );
				tiempoUltimoLadrillo = System.currentTimeMillis();
			}
			for (int i=rectangulos.size()-1; i>=0; i--) {  // ¿por qué al revés? (Observa lo que ocurriría si no fuera así)
				Rectangulo rect = rectangulos.get(i);
				if (rect.getTiempoVida() > TIEMPO_MAXIMO_LADRILLO) {
					rectangulos.remove(i);
				}
			}
			// Mecánica: fin de juego por número de círculos
			if (circulos.size() > NUM_MAXIMO_CIRCULOS) {
				ventana.setMensaje( "Puntos: " + puntos + " JUEGO FINALIZADO - Nº MÁXIMO DE CÍRCULOS" );
				break;  // Sale del bucle de juego
			} else if (circulos.size() > NUM_MAXIMO_CIRCULOS - 5) {
				ventana.setMensaje( "Puntos: " + puntos + " ¡Atención! Ya hay " + circulos.size() + " círculos en juego" );
			}

			// Mecánica: movimiento de círculos
			circulos.mover( ESPERA_ENTRE_FRAMES );
			// Mecánica: rebote en bordes
			circulos.choqueBordes( ventana );
			
			// Dibujado
			ventana.borra();
			circulos.dibujar( ventana );
			rectangulos.dibujar( ventana );
			ventana.repaint();
			
			// Ciclo de espera del bucle de juego de tiempo real
			ventana.espera( ESPERA_ENTRE_FRAMES );
		}
	}
	
	private static Circulo hayClickEnAlgunCirculoOLadrillo( GrupoCirculos circulos, Point hayClick ) {
		if (hayClick!=null) {
			// Chequeo de ladrillo:
			Rectangulo ladrilloConClick = rectangulos.encuentraRectanguloEnPunto(hayClick);
			if (ladrilloConClick!=null) {
				puntos += PUNTUACION_CLICK_LADRILLO;
				ventana.setMensaje( "Puntos: " + puntos );
				rectangulos.remove( ladrilloConClick );
			} else { // Solo se chequea el click en círculo si no ha ocurrido un click en ladrillo
				// Chequeo de círculo
				Circulo circuloConClick = circulos.encuentraCirculoEnPunto(hayClick);
				if (circuloConClick==null) {  // Ningún círculo pulsado
					puntos += PUNTUACION_CLICK_FUERA_ELEMENTO;
					ventana.setMensaje( "Puntos: " + puntos );
				} else {
					long tiempo = circuloConClick.getTiempoVida();
					if (tiempo <= TIEMPO_MAXIMO_PUNTOS) {
						puntos += (int) (TIEMPO_MAXIMO_PUNTOS - tiempo);  // Puntuación = milisegundos "salvados" del círculo
					} else {
						puntos += PUNTUACION_CLICK_CIRCULO_MUERTO;
					}
					ventana.setMensaje( "Puntos: " + puntos );
					circulos.remove( circuloConClick );
				}
			}
		}			
		return null;
	}
	
}

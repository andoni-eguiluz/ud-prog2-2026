package tema1b.ejemplos.bolasYBloques;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Clase principal de animación de las bolas y bloques rebotando (ejercicio 1.11)
 * MODIFICADA USANDO HERENCIA (clases Bola - Bloque)
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class MainBolas {
	
	private static VentanaGrafica v;
	
	// Lógica de juego
	private static GrupoObjetosAnimacion gBolas = new GrupoObjetosAnimacion();
	private static GrupoObjetosAnimacion gBloques = new GrupoObjetosAnimacion();
	private static int milisEntreFrames = 10;    // Milisegundos entre frames
	private static boolean pausa = false;        // Pausa del juego
	private static boolean avanceFot = false;    // Avance de un fotograma
	private static Point ultRatonPulsado = null; // Última pulsación de ratón
	
	/** Método principal
	 * @param args	No utilizado
	 */
	public static void main(String[] args) {
		v = new VentanaGrafica( 1000, 600, "Juego de bolas", Color.WHITE );
		// v.getJFrame().setLocation( 2000, 100 );  // Si se tienen dos pantallas
		v.setMensaje( "<Esc> para acabar, <+> para añadir bola");
		gBolas.add( new Bola( 521, 300, 15, -320, -480, Color.GREEN, Color.CYAN ) );
		gBloques.add( new Bloque( 400, 300, 100, 20, 100, 20, Color.BLUE, Color.YELLOW ) );
		gBloques.add( new Bloque( 500, 200, 200, 40, -50, 100, Color.BLACK, Color.WHITE ) );
		buclePrincipal();
	}

	private static void buclePrincipal() {
		v.setDibujadoInmediato( false );
		int tecla = v.getCodUltimaTeclaTecleada();
		while (tecla != KeyEvent.VK_ESCAPE && !v.estaCerrada()) {
			// Input y proceso
			procesaInputTeclado( tecla );
			procesaRaton();
			// Movimiento
			if (!pausa || avanceFot) { // Gestión del movimiento solo si no está en pausa, o solo un fotograma
				// 1. Movimiento de bolas
				gBolas.mover( milisEntreFrames );
				// Choque con bordes
				gBolas.choqueBordes( v, milisEntreFrames );
				// Choque entre bolas
				gBolas.choqueEntreObjetos( v, milisEntreFrames );
				// Corrección bordes
				gBolas.correccionBordes( v );
				
				// 2. Movimiento de bloques
				gBloques.mover( milisEntreFrames );
				// Choque con bordes
				gBloques.choqueBordes( v, milisEntreFrames );
				// Choque entre bloques
				gBloques.choqueEntreObjetos( v, milisEntreFrames );
				// Corrección bordes
				gBloques.correccionBordes( v );
				
				// Choque de bloques y bolas
				gBloques.choqueConOtrosObjetos( v, milisEntreFrames, gBolas );
			}
			avanceFot = false;
			// Dibujado
			v.borra();  // Quitar para ver diferencia en vez de borrar las bolas
			v.dibujaImagen( "UD-roller.jpg", v.getAnchura()/2, v.getAltura()/2, v.getAnchura(), v.getAltura(), 1.0, 0.0, 1.0f );
			gBolas.dibujar( v );
			gBloques.dibujar( v );
			v.repaint();
			// Mensaje de uso e info de energía
			double energia = 0.000000001 * gBolas.getEnergia();  // Disminuimos la escala para que sea más fácil visualizarla
			v.setMensaje( "<Esc> para acabar, <+> para añadir bola, <P> para pausa, <Alt-click> para borrar bola. Energía total: " + String.format( "%.4f", energia ) );
			// Ciclo de espera
			v.espera( milisEntreFrames );
			// Reinicio de bucle
			tecla = v.getCodUltimaTeclaTecleada();
		}
		v.acaba();
	}
	
	// Procesa el input del juego de teclado
	private static void procesaInputTeclado( int tecla ) {
		if (tecla == KeyEvent.VK_PLUS) {  // Añadir bola aleatoria con +
			boolean hayChoques;
			do {
				hayChoques = false;
				Bola bola3 = new Bola(v);  // Crea bola aleatoria
				gBolas.add( bola3 );
			} while (hayChoques);
		} else if (tecla == KeyEvent.VK_P) {  // P --> Pausa 
			pausa = !pausa;
		} else if (tecla == KeyEvent.VK_SPACE) {  // Espacio --> Avance de un fotograma 
			avanceFot = true;
		} 
		// Teclas pulsadas continuas
		if (v.isTeclaPulsada(KeyEvent.VK_UP)) {  // Cursor arriba -> aumenta velocidad Y hacia arriba del bloque controlable en 10 píxels / segundo
			gBloques.get(0).setVelY( gBloques.get(0).getVelY() - 10 );
		} else if (v.isTeclaPulsada(KeyEvent.VK_DOWN)) {  // Cursor arriba -> aumenta velocidad Y hacia abajo del bloque controlable en 10 píxels / segundo
			gBloques.get(0).setVelY( gBloques.get(0).getVelY() + 10 );
		} else if (v.isTeclaPulsada(KeyEvent.VK_LEFT)) {  // Cursor arriba -> aumenta velocidad X hacia izquierda del bloque controlable en 10 píxels / segundo
			gBloques.get(0).setVelX( gBloques.get(0).getVelX() - 10 );
		} else if (v.isTeclaPulsada(KeyEvent.VK_RIGHT)) {  // Cursor arriba -> aumenta velocidad X hacia derecha del bloque controlable en 10 píxels / segundo
			gBloques.get(0).setVelX( gBloques.get(0).getVelX() + 10 );
		}
	}
	
	// Procesa el ratón para click+alt
	private static void procesaRaton() {
		Point click = v.getRatonPulsado();
		if (click!=null && !click.equals(ultRatonPulsado)) {  // Si la pulsación es nueva
			if (!v.isTeclaPulsada( KeyEvent.VK_ALT)) {  // Solo se hace algo si se pulsa a la vez que Alt
				return;
			}
			// Búsqueda para borrar bola
			for (int i=0; i<gBolas.size(); i++) {
				Bola bola = (Bola) gBolas.get(i);
				if (bola.contienePunto( click )) {
					int posicionBola = gBolas.buscar(bola);
					if (posicionBola!=-1) {  // Se encuentra - borrar la bola y acabar el bucle
						gBolas.remove(posicionBola);
						break;
					}
				}
			}
			ultRatonPulsado = click;
		}
	}
	
}

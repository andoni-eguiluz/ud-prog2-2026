package tema1b.resueltos.brickBreaker;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Programa para revisar visualmente cómo funcionan cada una de las funciones físicas de bolas y bloques
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class TestVisualDeFisicas {
	
	private static VentanaGrafica v;
	
	// Lógica de juego
	private static GrupoObjetosMoviles gObjetos = new GrupoObjetosMoviles();
	private static int milisEntreFrames = 10;    // Milisegundos entre frames
	private static boolean avanceFot = false;    // Avance de un fotograma
	private static boolean pausa = true;         // Pausa del juego
	private static Point ultRatonPulsado = null; // Última pulsación de ratón
	private static ObjetoMovil ultObjetoArrastrado; // Último objeto al que se ha hecho drag
	private static Color colorColision = Color.MAGENTA;
	
	/** Método principal
	 * @param args	No utilizado
	 */
	public static void main(String[] args) {
		v = new VentanaGrafica( 1000, 600, "Chequeo de físicas", Color.WHITE );
		// v.getJFrame().setLocation( 2000, 100 );  // Si se tienen dos pantallas
		v.setMensaje( "<Esc> para acabar, <P> para quitar pausa, drag mueve objeto, ctrl+drag cambia velocidad");
		ObjetoMovil obj = new Bola( 521, 300, 15, -160, -240, Color.GREEN, Color.CYAN );
		obj.setVisuVel( true );
		gObjetos.add( obj );
		obj = new Bola( 300, 200, 45, 100, 200, Color.PINK, Color.LIGHT_GRAY );
		obj.setVisuVel( true );
		gObjetos.add( obj );
		obj = new Bloque( 400, 300, 100, 20, 100, 20, Color.BLUE, Color.YELLOW );
		obj.setVisuVel( true );
		gObjetos.add( obj );
		obj = new Bloque( 500, 200, 200, 40, -50, 100, Color.BLACK, Color.WHITE );
		obj.setVisuVel( true );
		gObjetos.add( obj );
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
				// 1. Movimiento de objetos
				gObjetos.mover( milisEntreFrames );
				// Choque con bordes
				gObjetos.choqueBordes( v, milisEntreFrames );
				// Choque entre bolas
				gObjetos.choqueEntreObjetos( v, milisEntreFrames );
				// Corrección bordes
				gObjetos.correccionBordes( v );
			}
			avanceFot = false;
			// Dibujado
			v.borra();  // Quitar para ver diferencia en vez de borrar las bolas
			gObjetos.dibujar( v );
			// Dibujado de colisiones
			for (int i=0; i<gObjetos.size(); i++) {
				ObjetoMovil o1 = gObjetos.get(i);
				for (int j=0; j<gObjetos.size(); j++) {
					ObjetoMovil o2 = gObjetos.get(j);
					if (o1 != o2) {
						Polar choquePolar = o1.vectorChoqueConObjeto(o2);
						if (choquePolar!=null) {
							Point2D choque = choquePolar.toPoint();
							v.dibujaFlecha( o1.getX(), o1.getY(), o1.getX()+choque.getX(), o1.getY()+choque.getY(), 2.0f, colorColision, 10 );
						}
					}
				}
			}
			v.repaint();
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
				gObjetos.add( bola3 );
			} while (hayChoques);
		} else if (tecla == KeyEvent.VK_P) {  // P --> Pausa 
			pausa = !pausa;
		} else if (tecla == KeyEvent.VK_SPACE) {  // Espacio --> Avance de un fotograma 
			avanceFot = true;
		} 
		// Teclas pulsadas continuas
		if (v.isTeclaPulsada(KeyEvent.VK_UP)) {  // Cursor arriba -> aumenta velocidad Y hacia arriba del bloque controlable en 10 píxels / segundo
			gObjetos.get(0).setVelY( gObjetos.get(0).getVelY() - 10 );
		} else if (v.isTeclaPulsada(KeyEvent.VK_DOWN)) {  // Cursor arriba -> aumenta velocidad Y hacia abajo del bloque controlable en 10 píxels / segundo
			gObjetos.get(0).setVelY( gObjetos.get(0).getVelY() + 10 );
		} else if (v.isTeclaPulsada(KeyEvent.VK_LEFT)) {  // Cursor arriba -> aumenta velocidad X hacia izquierda del bloque controlable en 10 píxels / segundo
			gObjetos.get(0).setVelX( gObjetos.get(0).getVelX() - 10 );
		} else if (v.isTeclaPulsada(KeyEvent.VK_RIGHT)) {  // Cursor arriba -> aumenta velocidad X hacia derecha del bloque controlable en 10 píxels / segundo
			gObjetos.get(0).setVelX( gObjetos.get(0).getVelX() + 10 );
		}
	}
	
	// Procesa el ratón para click+alt
	private static void procesaRaton() {
		Point click = v.getRatonPulsado();
		if (click!=null && !click.equals(ultRatonPulsado)) {  // Si la pulsación es nueva
			if (ultRatonPulsado!=null) {
				if (ultObjetoArrastrado==null) {
					ultObjetoArrastrado = gObjetos.getObjetoEnPunto( click );
				}
				if (ultObjetoArrastrado!=null) {  // Drag del objeto si está en el punto del ratón
					if (v.isTeclaPulsada( KeyEvent.VK_CONTROL)) {  // Con control = cambio de velocidad
						ultObjetoArrastrado.setVelX( ultObjetoArrastrado.getVelX() + click.x - ultRatonPulsado.x );
						ultObjetoArrastrado.setVelY( ultObjetoArrastrado.getVelY() + click.y - ultRatonPulsado.y );
					} else {  // Sin control = cambio de posición
						ultObjetoArrastrado.setX( ultObjetoArrastrado.getX() + click.x - ultRatonPulsado.x );
						ultObjetoArrastrado.setY( ultObjetoArrastrado.getY() + click.y - ultRatonPulsado.y );
					}
				}
			}
			ultRatonPulsado = click;
		} else if (click==null) {
			ultRatonPulsado = null;
			ultObjetoArrastrado = null;
		}
	}
	
}

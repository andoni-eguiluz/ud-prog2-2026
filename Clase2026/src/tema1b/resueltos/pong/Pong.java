package tema1b.resueltos.pong;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Juego de pong con herencia
 * V2: 
 * - Incorpora multibola
 * - Incorpora puntuaciones (clase Puntuación TAMBIÉN hija de Figura), una por cada jugador
 * - Una estructura polimórfica con todas las figuras del juego (palas, bolas, puntuaciones)
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class Pong {

	// Constantes para mecánicas del juego
	private static final int NUM_BOLAS = 10;       // Número de bolas
	private static final long MSGS_POR_FRAME = 20; // 20 msgs por frame = 50 frames por segundo aprox
	private static final double RADIO_BOLA = 15;   // Radio de la pelota
	private static final double VEL_MIN = 300;     // Velocidad inicial mínima (300 píxels por segundo)
	private static final double VEL_RANGO = 200;   // Rango de velocidad mínima (de VEL_MIN a VEL_MIN + 200 píxels por segundo)
	private static final double VEL_PALA = 500;    // Velocidad de desplazamiento de las palas
	private static boolean debugChoques = false;   // Activar si se quiere "depurar" en pantalla los choques (con parada del juego en cada choque-rebote y líneas de referencia de lo que ocurre en cada choque) - variable porque se puede cambiar interactivamente por teclado
	private static double velocidadVista = 1.0;    // Velocidad de vista (para ver en fast o slow motion)
	private static final int Y_PUNTUACIONES = 40;  // V2: Y de las puntuaciones en pantalla
	private static final int TAM_PUNTUACION = 40;  // V2: Tamaño del texto de puntuaciones
	// Constantes para inicialización de arena de juego
	private static final int ANCHO_VENTANA = 1000;
	private static final int ALTO_VENTANA = 800;
	private static final int X_INICIAL_BOLA = 500;
	private static final int Y_INICIAL_BOLA = 400;
	private static final int ANCHO_PALA = 20;
	private static final int ALTO_PALA = 100;
	private static final int DIST_PALA_A_BORDE_LATERAL = 60;
	private static final Color COLOR_PALA_1 = Color.GREEN;
	private static final Color COLOR_PALA_2 = Color.BLUE;
	private static final Color COLOR_BOLA = Color.MAGENTA;
	
	// Variables para mecánicas del juego
	private static double velocidadJuego = 1.0;         // 1.0 = tiempo real. Cuando mayor, más rápido pasa el tiempo y viceversa 
	
	/** Método principal de Pong
	 * @param args	No utilizado
	 */
	public static void main(String[] args) {
		Pong juego = new Pong();
		juego.jugar();
	}
	
	// =================================
	// ATRIBUTOS Y MÉTODOS DE INSTANCIA (no static)
	// =================================
	
	private VentanaGrafica vent;        // Ventana del juego
	private ArrayList<Circulo> bolas;   // Bolas del juego (tiene una pero está preparado para tener varias)
	private Rectangulo pala1;           // Pala izquierda del juego (jugador 1)
	private Rectangulo pala2;           // Pala derecha del juego (jugador 2)
	private ArrayList<Figura> objsPong; // V2: Todos los objetos del juego
	private Puntuacion punt1;           // Puntuación del jugador 1
	private Puntuacion punt2;           // Puntuación del jugador 2

	/** Lanza una ventana gráfica e inicia el juego interactivo de Pong en ella
	 */
	public void jugar() {
		init();
		juego();
	}
	
	// Inicialización de juego: creación de ventana, bola y palas
	private void init() {
		Random random = new Random();
		vent = new VentanaGrafica( ANCHO_VENTANA, ALTO_VENTANA, "Juego de pong" );
		// vent.getJFrame().setLocation(2000,20); // TODO Quitar (Solo si se quiere ver en la segunda ventana (a la derecha))
		objsPong = new ArrayList<Figura>();  // V2: Lista de todos los objetos (polimórfica)
		// Las bolas (V2: Varias)
		bolas = new ArrayList<Circulo>();
		for (int i=0; i<NUM_BOLAS; i++) {
			Circulo bola = new Circulo( RADIO_BOLA, X_INICIAL_BOLA, Y_INICIAL_BOLA, COLOR_BOLA );
			bola.setVX( random.nextDouble() * VEL_RANGO * 2 - VEL_RANGO );
			bola.setVX( bola.getVX()<0 ? bola.getVX()-VEL_MIN : bola.getVX()+VEL_MIN ); // Velocidad x (aleatoria entre -500 y +500 px/seg, al menos 300)
			bola.setVY( random.nextDouble() * VEL_RANGO * 2 - VEL_RANGO );  // Velocidad y (aleatoria entre -200 y +200)
			bolas.add( bola );
			objsPong.add( bola ); // V2
		}
		// Las palas
		pala1 = new Rectangulo( ANCHO_PALA, ALTO_PALA, DIST_PALA_A_BORDE_LATERAL, vent.getAltura()/2, COLOR_PALA_1 );
		pala2 = new Rectangulo( ANCHO_PALA, ALTO_PALA, vent.getAnchura()-DIST_PALA_A_BORDE_LATERAL, vent.getAltura()/2, COLOR_PALA_2 );
		objsPong.add( pala1 ); // V2
		objsPong.add( pala2 ); // V2
		// Info de juego
		vent.setMensaje( "Usa W/S y cursores para mover las palas. +/-=cambio velocidad (con Ctrl cambio vel.vista). d=modo depuración" );
		// V2: Puntuaciones
		punt1 = new Puntuacion( DIST_PALA_A_BORDE_LATERAL, Y_PUNTUACIONES, COLOR_PALA_1, TAM_PUNTUACION );
		punt2 = new Puntuacion( vent.getAnchura()-DIST_PALA_A_BORDE_LATERAL, Y_PUNTUACIONES, COLOR_PALA_2, TAM_PUNTUACION );
		objsPong.add( punt1 );
		objsPong.add( punt2 );
	}
	
	// Bucle de juego/movimiento
	private void juego() {
		vent.setDibujadoInmediato( false );  // Preparación del dibujado con doble buffer (primero se dibuja todo y se pinta al final en pantalla con .repaint())
		while (!vent.estaCerrada()) {
			// Manejo de teclado
			gestionTeclado();
			// Movimiento de la bola
			for (Circulo bola : bolas) {  // V2
				bola.mueve( MSGS_POR_FRAME/1000.0 * velocidadJuego );
			}
			// Movimiento de las palas
			pala1.mueve( MSGS_POR_FRAME/1000.0 * velocidadJuego );
			pala2.mueve( MSGS_POR_FRAME/1000.0 * velocidadJuego );
			// Dibujado en modo depuración
			if (debugChoques) { // En modo depuración, dibuja palas y vector de velocidad para ver "pantalla de choque"
				vent.borra(); 
				for (Circulo bola : bolas) {  // V2
					bola.dibuja( vent );
					vent.dibujaFlecha( bola.getX(), bola.getY(), bola.getX()+bola.getVX()/4, bola.getY()+bola.getVY()/4, 1.0f, Color.orange, 20 );
				}
				pala1.dibuja( vent );
				pala2.dibuja( vent );
			}
			ArrayList<Circulo> bolasAQuitar = new ArrayList<Circulo>();
			for (Circulo bola : bolas) {  // V2: Hacerlo por cada bola
				// Control de salida de pantalla
				if (bola.seSaleEnHorizontal( vent )) { // V2: Esto es GOL
					// bola.setVX( -bola.getVX() );
					// if (debugChoques) { vent.dibujaFlecha( bola.getX(), bola.getY(), bola.getX()+bola.getVX()/4, bola.getY()+bola.getVY()/4, 1.0f, Color.green, 20 ); }  // En modo depuración, dibuja el cambio de velocidad tras choque
					bolasAQuitar.add( bola );
					if (bola.getX()-bola.getRadio()<=0) {  // Gol del jugador 2
						punt2.inc();
					} else {  // Gol del jugador 1
						punt1.inc();
					}
					continue; // No hace falta hacer más cosas en el bucle con esta pelota
				}
				if (bola.seSaleEnVertical( vent )) {
					bola.setVY( -bola.getVY() );
					if (debugChoques) { vent.dibujaFlecha( bola.getX(), bola.getY(), bola.getX()+bola.getVX()/4, bola.getY()+bola.getVY()/4, 1.0f, Color.green, 20 ); }  // En modo depuración, dibuja el cambio de velocidad tras choque
				}
				// Choque y cálculo de rebote bola y palas
				boolean hayChoque = Fisica.hayChoque( pala1, bola );
				if (hayChoque) { 
					Fisica.aplicaReboteCirculoElastico( pala1, bola, MSGS_POR_FRAME/1000.0 * velocidadJuego ); 
				} else {
					hayChoque = Fisica.hayChoque( pala2, bola );
					if (hayChoque) {
						Fisica.aplicaReboteCirculoElastico( pala2, bola, MSGS_POR_FRAME/1000.0 * velocidadJuego );
					}
				}
				if (hayChoque && debugChoques) {  // En modo depuración, dibuja los elementos y espera a click para observar visualmente cada choque
					bola.dibuja( vent );
					vent.dibujaFlecha( bola.getX(), bola.getY(), bola.getX()+bola.getVX()/4, bola.getY()+bola.getVY()/4, 1.0f, Color.green, 20 );
					vent.repaint();
					while (!vent.estaCerrada() && vent.getRatonPulsado()==null) vent.espera( MSGS_POR_FRAME ); // Espera al ratón pulsado sin hacer nada
					while (!vent.estaCerrada() && vent.getRatonPulsado()!=null) vent.espera( MSGS_POR_FRAME ); // Espera al ratón soltado sin hacer nada		
				}
			}
			for (Circulo bola : bolasAQuitar) {
				bolas.remove( bola ); // Quita la bola de las bolas...
				objsPong.remove( bola ); // Y de los objetos de juego generales
			}
			// Dibujado
			dibujaTodo();
			// Ciclo de espera en cada bucle
			vent.espera( (long) (MSGS_POR_FRAME/velocidadVista) );
		}
	}
	
	// Dibuja toda la ventana por cada fotograma
	private void dibujaTodo() {
		vent.borra();
		dibujaBordes();
		for (Figura objeto : objsPong) {  // V2
			objeto.dibuja( vent );
		}
		if (debugChoques) { // En modo depuración, dibuja vector de velocidad de las bolas
			for (Circulo bola : bolas) {  // V2
				vent.dibujaFlecha( bola.getX(), bola.getY(), bola.getX()+bola.getVX()/4, bola.getY()+bola.getVY()/4, 1.0f, Color.magenta, 20 ); 
			}  
		}
		vent.repaint();
		// V2: Última parte del ejercicio 3.3: sacar a consola
		double sumaX = 0;
		double sumaY = 0;
		double sumaRadio = 0;
		int numBolas = 0;
		for (Figura figura : objsPong) {
			// sacar a consola en cada fotograma la coordenada media de todo nuestro juego 
			sumaX += figura.getX();
			sumaY += figura.getY();
			// calcula el radio medio de todas las bolas
			if (figura instanceof Circulo) {
				sumaRadio += ((Circulo)figura).getRadio();
				numBolas++;
			}
		}
		System.out.print( "Coordenada media: (" + sumaX/objsPong.size() + "," + sumaY/objsPong.size() + ")" );
		System.out.println( " - Radio medio de las bolas: " + sumaRadio/numBolas );
	}
	
	// Gestiona el teclado de control de juego
	private void gestionTeclado() {
		if (vent.isTeclaPulsada( KeyEvent.VK_PLUS ) & vent.isControlPulsado()) {
			velocidadVista *= 1.05;
			vent.setMensaje( "Nueva velocidad de vista: " + velocidadVista );
		} else if (vent.isTeclaPulsada( KeyEvent.VK_MINUS ) & vent.isControlPulsado()) {
			velocidadVista /= 1.05;
			vent.setMensaje( "Nueva velocidad de vista: " + velocidadVista );
		} else if (vent.isTeclaPulsada( KeyEvent.VK_PLUS )) {
			velocidadJuego *= 1.05;
			vent.setMensaje( "Nueva velocidad de juego: " + velocidadJuego );
		} else if (vent.isTeclaPulsada( KeyEvent.VK_MINUS )) {
			velocidadJuego /= 1.05;
			vent.setMensaje( "Nueva velocidad de juego: " + velocidadJuego );
		}
		// Movimiento jugador 1
		if (vent.isTeclaPulsada( KeyEvent.VK_W )) {
			pala1.setVY( -VEL_PALA );
		} else if (vent.isTeclaPulsada( KeyEvent.VK_S )) {
			pala1.setVY( VEL_PALA );
		} else {
			pala1.setVY( 0 );
		}
		// Movimiento jugador 2
		if (vent.isTeclaPulsada( KeyEvent.VK_UP )) {
			pala2.setVY( -VEL_PALA );
		} else if (vent.isTeclaPulsada( KeyEvent.VK_DOWN )) {
			pala2.setVY( VEL_PALA );
		} else {
			pala2.setVY( 0 );
		}
		// Modo debug
		if (vent.getCodUltimaTeclaTecleada() == KeyEvent.VK_D ) {  // Con este método solo se detecta la D UNA VEZ en cada pulsación (si no se detectaría varias veces por segundo)
			debugChoques = !debugChoques;
		}
	}
	
	private void dibujaBordes() {
		vent.dibujaRect( 0, 0, vent.getAnchura(), vent.getAltura(), 0.5f, Color.black );
		vent.dibujaLinea( vent.getAnchura()/2, 0, vent.getAnchura()/2, vent.getAltura(), 0.5f, Color.black );
	}
	

}

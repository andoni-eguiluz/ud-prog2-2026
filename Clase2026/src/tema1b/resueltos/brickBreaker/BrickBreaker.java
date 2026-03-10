package tema1b.resueltos.brickBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Clase para crear ventanas con animaciones de bolas y bloques rebotando
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class BrickBreaker {
	
	/** Método principal - prueba con una instancia particular de la animación
	 * @param args	No utilizado
	 */
	public static void main(String[] args) {
		BrickBreaker brickBreaker = new BrickBreaker(1000, 600, "Brick Breaker" );
		brickBreaker.init();
		brickBreaker.buclePrincipal();
	}
	
	// Atributos base
	private VentanaGrafica v;
	private GrupoObjetosMoviles bolas = new GrupoObjetosMoviles();
	private GrupoObjetosMoviles elementosJuego = new GrupoObjetosMoviles();
	private Bloque pala;
	// Lógica general
	private boolean bolasChocan = true;
	// Lógica de juego
	private int milisEntreFrames = 10;    // Milisegundos entre frames
	private boolean pausa = false;        // Pausa del juego
	private boolean avanceFot = false;    // Avance de un fotograma
	private Point ultRatonPulsado = null; // Última pulsación de ratón
	private int puntuacion;        // Puntuación del juego
	private TextoInfo textoPuntuacion;    // Puntuación del juego en pantalla
	
	
	/** Crea una nueva animación de bolas y bloques con ventana propia
	 * @param ancho	Anchura de la ventana gráfica
	 * @param alto	Altura de la ventana gráfica
	 * @param titulo	Título de la ventana
	 */
	public BrickBreaker( int ancho, int alto, String titulo ) {
		v = new VentanaGrafica( ancho, alto, titulo, Color.WHITE );
		// v.getJFrame().setLocation( 2000, 100 );  // Si se tienen dos pantallas
		v.setMensaje( "<Esc> para acabar, <P> para pausa, <Esp> para fotograma");
		puntuacion = 0;
		// (Para este juego las bolas no chocan entre sí)
		setBolasChocan( false );
	}
	
	/** Inicializa un juego de prueba de brickbreaker
	 */
	public void init() {
		for (int i=0; i<8; i++) {
			bolas.add( new Bola( 750 + i*30, 280 + i*45, 15, -360, -540, Color.GREEN, new Color( i*30, 255 - i*30, 255 ) ) );
		}
		// Añadimos los elementos del juego: pala, ladrillos, powerups
		pala = new Bloque( 500, 585, 150, 20, 0, 0, Color.BLUE, Color.CYAN );
		elementosJuego.add( pala );
		for (int i=0; i<3; i++) {
			BloqueRompible br = new BloqueRompible( i*200+50, 200, 98, 40, 0, 0, Color.ORANGE, Color.YELLOW, 10 );
			elementosJuego.add( br );
			br = new BloqueRompible( i*200+150, 200, 98, 40, 0, 0, Color.BLACK, Color.WHITE, 10 );
			elementosJuego.add( br );
		}
		ArrayList<BloqueRompible> bloquesPowerup = new ArrayList<>();
		for (int i=0; i<3; i++) {
			BloqueRompible bl = new BloqueRompible( i*200+250, 60, 98, 40, 0, 0, Color.RED, Color.GREEN, 100 );
			elementosJuego.add( bl );
			bloquesPowerup.add( bl );
			bl = new BloqueRompible( i*200+350, 60, 98, 40, 0, 0, Color.MAGENTA, Color.GRAY, 100 );
			elementosJuego.add( bl );
			bloquesPowerup.add( bl );
		}
		PowerupRompeLadrillos powerUp = new PowerupRompeLadrillos( 50, 60, 20, 5000 );  // 5 segundos
		powerUp.setBloquesAfectados( bloquesPowerup );
		elementosJuego.add( powerUp );
		textoPuntuacion = new TextoInfo( 940, 50, 100, 60 );
		elementosJuego.add( textoPuntuacion );
		elementosJuego.add( new GraficoDecorativo( 940,  500,  80, 110,  "UD-blue.png" ) );  // Logotipo
	}
	
	/** Cambia la configuración de choque de bolas
	 * @param on
	 */
	public void setBolasChocan( boolean on ) {
		bolasChocan = on;
	}

	private void buclePrincipal() {
		v.setDibujadoInmediato( false );
		Rectangle rectJuego = new Rectangle( v.getAnchura(), v.getAltura() + 100 );  // Rectángulo de juego es mayor en altura para que las bolas desaparezcan por abajo
		int tecla = v.getCodUltimaTeclaTecleada();
		long tiempoReal = System.currentTimeMillis();
		int tiempoEntreFrames = milisEntreFrames;
		while (tecla != KeyEvent.VK_ESCAPE && !v.estaCerrada()) {
			// Input y proceso
			procesaInputTeclado( tecla );
			procesaRaton();
			// Movimiento
			if (!pausa || avanceFot) { // Gestión del movimiento solo si no está en pausa, o solo un fotograma
				// 1. Movimiento de bolas
				bolas.mover( tiempoEntreFrames );
				// Choque con bordes
				bolas.choqueBordes( rectJuego, tiempoEntreFrames );
				// Choque entre bolas
				if (bolasChocan) {
					bolas.choqueEntreObjetos( v, tiempoEntreFrames );
				}
				// Corrección bordes
				bolas.correccionBordes( rectJuego );
				// Quitar bolas que se pierden por abajo
				for (int i=bolas.size()-1; i>=0; i--) {  // Iteración al revés porque vamos a ir borrando
					Bola b = (Bola) bolas.get(i);
					if (b.getRectangulo().getMinY() > v.getAltura()) {
						bolas.remove(i);
					}
				}
				
				// 2. Actualización de elementos (aunque no se muevan algunos tienen animación)
				elementosJuego.mover( tiempoEntreFrames );
				// Choque del bloque manejable - para la pala en el borde
				if (pala.chocaBordeHorizontal(v)) {
					pala.setVelX( 0 );
					// Fisica.calcChoqueConBorde( rectJuego, pala, tiempoEntreFrames );  // Esto haría el rebote
					if (pala.getRectangulo().getMinX() < 0) {  // Izquierda
						pala.setX( pala.getAncho()/2.0 );
					} else if (pala.getRectangulo().getMaxX() > v.getAnchura()){  // Derecha
						pala.setX( v.getAnchura() - pala.getAncho()/2.0 - 1);
					}
				}
				// Eliminar los bloques que estén muertos y se haya acabado su animación de muerte
				for (int i=elementosJuego.size()-1; i>=0; i--) {  // Iteración al revés porque vamos a ir borrando
					if (elementosJuego.get(i) instanceof BloqueRompible) {
						BloqueRompible b = (BloqueRompible) elementosJuego.get(i);
						if (!b.estaVivo()) {
							elementosJuego.remove(i);
						}
					} else if (elementosJuego.get(i) instanceof PowerupRompeLadrillos) {
						PowerupRompeLadrillos powerup = (PowerupRompeLadrillos) elementosJuego.get(i);
						if (!powerup.estaVivo()) {
							elementosJuego.remove(i);
						}
					}
				}
				
				// Choque de bloques y bolas
				int incPuntuacion = bolas.choqueMultipleConOtrosObjetos( v, tiempoEntreFrames, elementosJuego );
				puntuacion += incPuntuacion;
			}
			avanceFot = false;
			// Dibujado
			v.borra();  // Quitar para ver diferencia en vez de borrar las bolas
			v.dibujaImagen( "UD-roller.jpg", v.getAnchura()/2, v.getAltura()/2, v.getAnchura(), v.getAltura(), 1.0, 0.0, 1.0f );
			bolas.dibujar( v );
			textoPuntuacion.setTexto( puntuacion + "" );
			elementosJuego.dibujar( v );
			if (bolas.size()==0) { // Lógica de final de juego
				v.setMensaje( "¡Final de partida! Has conseguido " + puntuacion + " puntos." );
				v.dibujaTextoCentrado( 0, 0, v.getAnchura(), v.getAltura(), "THE END!", new Font( "Arial", Font.BOLD, 128 ), Color.BLUE );
				v.dibujaTextoCentrado( 0, 0, v.getAnchura(), v.getAltura(), "THE END!", new Font( "Arial", Font.BOLD, 120 ), Color.WHITE );
			}
			v.repaint();
			// Ciclo de espera
			if (tiempoEntreFrames<milisEntreFrames) {
				v.espera( milisEntreFrames-tiempoEntreFrames ); // Pausa correspondiente al tiempo que falta por esperar
			} else {
				v.espera( 3 );  // Pausa muy pequeña
			}
			tiempoEntreFrames = (int) (System.currentTimeMillis() - tiempoReal);
			tiempoReal = System.currentTimeMillis();
			
			// Reinicio de bucle
			tecla = v.getCodUltimaTeclaTecleada();
		}
		v.acaba();
	}
	
	// Procesa el input del juego de teclado
	private void procesaInputTeclado( int tecla ) {
		if (tecla == KeyEvent.VK_PLUS) {  // Añadir bola aleatoria con +
			boolean hayChoques;
			do {
				hayChoques = false;
				Bola bola3 = new Bola(v);  // Crea bola aleatoria
				bolas.add( bola3 );
			} while (hayChoques);
		} else if (tecla == KeyEvent.VK_P) {  // P --> Pausa 
			pausa = !pausa;
		} else if (tecla == KeyEvent.VK_SPACE) {  // Espacio --> Avance de un fotograma 
			avanceFot = true;
		} 
		// Teclas pulsadas continuas
		if (v.isTeclaPulsada(KeyEvent.VK_LEFT)) {  // Cursor izquierda -> aumenta velocidad X hacia izquierda del bloque controlable en 10 píxels / segundo
			if (pala.getVelX() > -3000) {  // Velocidad máxima
				pala.setVelX( pala.getVelX() - 300 );
				// pala.setVelX( -3000 );
			}
		} else if (v.isTeclaPulsada(KeyEvent.VK_RIGHT)) {  // Cursor derecha -> aumenta velocidad X hacia derecha del bloque controlable en 10 píxels / segundo
			if (pala.getVelX() < 3000) {  // Velocidad máxima
				pala.setVelX( pala.getVelX() + 300 );
				// pala.setVelX( 3000 );
			}
		} else {
			if (pala.getVelX()>0) {
				pala.setVelX( pala.getVelX() - 800 );
				if (pala.getVelX()<0) {
					pala.setVelX( 0.0 );
				}
			} else if (pala.getVelX()<0) {
				pala.setVelX( pala.getVelX() + 800 );
				if (pala.getVelX()>0) {
					pala.setVelX( 0.0 );
				}
			}
		}
	}
	
	// Procesa el ratón para click+alt
	private void procesaRaton() {
		Point click = v.getRatonPulsado();
		if (click!=null && !click.equals(ultRatonPulsado)) {  // Si la pulsación es nueva
			if (!v.isTeclaPulsada( KeyEvent.VK_ALT)) {  // Solo se hace algo si se pulsa a la vez que Alt
				return;
			}
			// Búsqueda para borrar bola
			for (int i=0; i<bolas.size(); i++) {
				Bola bola = (Bola) bolas.get(i);
				if (bola.contienePunto( click )) {
					int posicionBola = bolas.buscar(bola);
					if (posicionBola!=-1) {  // Se encuentra - borrar la bola y acabar el bucle
						bolas.remove(posicionBola);
						break;
					}
				}
			}
			ultRatonPulsado = click;
		}
	}
	
}

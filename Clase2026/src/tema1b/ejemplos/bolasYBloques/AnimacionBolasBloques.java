package tema1b.ejemplos.bolasYBloques;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Clase para crear ventanas con animaciones de bolas y bloques rebotando
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class AnimacionBolasBloques {
	
	/** Método principal - prueba con una instancia particular de la animación
	 * @param args	No utilizado
	 */
	public static void main(String[] args) {
		AnimacionBolasBloques anim1 = new AnimacionBolasBloques(1000, 600, "Brick Breaker" );
		anim1.init();
		// (Para este juego las bolas no chocan entre sí)
		anim1.setBolasChocan( false );
		anim1.buclePrincipal();
	}
	
	// Atributos base
	private VentanaGrafica v;
	private GrupoObjetosAnimacion gBolas = new GrupoObjetosAnimacion();
	private GrupoObjetosAnimacion gBloques = new GrupoObjetosAnimacion();
	// Lógica general
	private boolean bolasChocan = true;
	// Lógica de juego
	private int milisEntreFrames = 10;    // Milisegundos entre frames
	private boolean pausa = false;        // Pausa del juego
	private boolean avanceFot = false;    // Avance de un fotograma
	private Point ultRatonPulsado = null; // Última pulsación de ratón
	
	/** Crea una nueva animación de bolas y bloques con ventana propia
	 * @param ancho	Anchura de la ventana gráfica
	 * @param alto	Altura de la ventana gráfica
	 * @param titulo	Título de la ventana
	 */
	public AnimacionBolasBloques( int ancho, int alto, String titulo ) {
		v = new VentanaGrafica( ancho, alto, titulo, Color.WHITE );
		v.setMensaje( "<Esc> para acabar, <+> para añadir bola, <P> para pausa, <Esp> para fotograma");
	}
	
	/** Inicializa un ejemplo de animación con unos cuantos bloques y bolas rebotando
	 */
	public void init() {
		for (int i=0; i<8; i++) {
			gBolas.add( new Bola( 750 + i*30, 280 + i*45, 15, -1200, -1800, Color.GREEN, new Color( i*30, 255 - i*30, 255 ) ) );
		}
		for (int i=0; i<3; i++) {
			gBloques.add( new Bloque( i*200+50, 200, 98, 40, 0, 0, Color.BLUE, Color.YELLOW ) );
			gBloques.add( new Bloque( i*200+150, 200, 98, 40, 0, 0, Color.BLACK, Color.WHITE ) );
		}
		for (int i=0; i<3; i++) {
			gBloques.add( new Bloque( i*200+250, 60, 98, 40, 0, 0, Color.RED, Color.GREEN ) );
			gBloques.add( new Bloque( i*200+350, 60, 98, 40, 0, 0, Color.MAGENTA, Color.ORANGE ) );
		}
	}
	
	/** Cambia la configuración de choque de bolas
	 * @param on
	 */
	public void setBolasChocan( boolean on ) {
		bolasChocan = on;
	}

	private void buclePrincipal() {
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
				if (bolasChocan) {
					gBolas.choqueEntreObjetos( v, milisEntreFrames );
				}
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
				gBolas.choqueMultipleConOtrosObjetos( v, milisEntreFrames, gBloques );
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
	private void procesaInputTeclado( int tecla ) {
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
	private void procesaRaton() {
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

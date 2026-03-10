package tema1b.resueltos.runnerNaves;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Juego runner ejercicio 
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class RunnerNaves {

	private static final long MSGS_POR_FRAME = 20;    // 20 msgs por frame = 50 frames por segundo aprox
	private static final double RADIO_NAVE = 25;      // Radio de la nave (círculo imaginario)
	private static final int TAM_NAVE = 50;           // Píxel de tamaño de cada nave
	private static final double RADIO_MIN_ASTER = 25; // Radio mínimo del asteroide (círculo imaginario)
	private static final double RADIO_MAX_ASTER = 50; // Radio máximo del asteroide (círculo imaginario)
	private static final double VEL_NAVES = 500;      // Velocidad de desplazamiento de las naves
	private static final double INC_VEL_NAVES = 50;   // Cada nave es un poquito más rápida que la anterior
	private static final int NUM_NAVES = 5;           // Número de naves del juego
	private static final int DIST_NAVES = 60;         // Píxels de distancia entre naves

	private static final int[] CANAL = { 100, 200, 300, 400, 500 };  // Píxels y de cada canal

	private static double VEL_MIN_ASTER = 200;        // Velocidad mínima del asteroide (píxels por segundo)
	private static double VEL_MAX_ASTER = 300;        // Velocidad máxima del asteroide (píxels por segundo)
	private static double PROB_NUEVO_AST = 0.02;      // Probabilidad de que aparezca un nuevo asteroide cada frame (2%)
	private static double VEL_JUEGO = 1.0;            // 1.0 = tiempo real. Cuando mayor, más rápido pasa el tiempo y viceversa 
	private static Random random;                     // Generador de aleatorios para creación de asteroides
	
	public static void main(String[] args) {
		RunnerNaves juego = new RunnerNaves();
		juego.jugar();
	}
	
	// =================================
	// ATRIBUTOS Y MÉTODOS DE INSTANCIA (no static)
	// =================================
	
	private VentanaGrafica vent;                  // Ventana del juego
	private ArrayList<ObjetoEspacial> elementos;  // Elementos del juego (tanto naves como asteroides)
	private ArrayList<Nave> naves;                // Naves del juego (solo las naves)
	private long tiempoNivel;                     // Tiempo inicial del nivel actual (milisegundos)
	
	public void jugar() {
		random = new Random();
		vent = new VentanaGrafica( 1200, 600, "Runner de naves" );
		crearNaves();
		mover();
	}

	// Crea las 5 naves de inicio
	private void crearNaves() {
		int posNave = TAM_NAVE;
		elementos = new ArrayList<>();
		naves = new ArrayList<>();
		for (int numNave=0; numNave<NUM_NAVES; numNave++) {
			Nave nave = new Nave( posNave, CANAL[2] );  // Nueva nave en canal intermedio
			nave.setCanal( 2 );
			elementos.add( nave );
			naves.add( nave );
			posNave += DIST_NAVES;
		}
	}
	
	// Bucle de movimiento
	private void mover() {
		tiempoNivel = System.currentTimeMillis();
		vent.setDibujadoInmediato( false );
		while (!vent.estaCerrada() && naves.size()>0) {
			// Manejo de teclado
			gestionTeclado();
			// Creación aleatoria de nuevo asteroide
			if (random.nextDouble()<PROB_NUEVO_AST) {
				creaNuevoAsteroide();
			}
			// Movimiento de todos los elementos
			for (ObjetoEspacial objeto : elementos) {
				objeto.mueve( MSGS_POR_FRAME/1000.0 * VEL_JUEGO );
			}
			// Comprobación de parada de naves (ver si han llegado a su canal)
			for (Nave nave : naves) {
				miraSiEstaEnSuCanal(nave);
			}
			// Control de salida de pantalla de los asteroides
			for (int i=elementos.size()-1; i>=0; i--) {
				ObjetoEspacial oe = elementos.get(i);
				if (oe instanceof Asteroide) {
					Asteroide a = (Asteroide) oe;
					if (a.seSalePorLaIzquierda( vent )) {
						elementos.remove( a );
					}
				}
			}
			// Choque naves con asteroides (todos con todos)
			for (int i=naves.size()-1; i>=0; i--) {  // OJO: Como se pueden borrar hay que recorrerlo en sentido inverso
				ObjetoEspacial oe = naves.get(i);
				if (oe instanceof Nave) {
					Nave nave = (Nave) oe;
					boolean choque = false;
					for (ObjetoEspacial oe2 : elementos) {
						if (oe2 instanceof Asteroide) {
							Asteroide a = (Asteroide) oe2;
							if (hayChoque(nave, a)) {
								choque = true;
								break; // Acabar bucle (no hace falta mirar más colisiones)
							}
						}
					}
					if (choque) {
						elementos.remove( nave );  // Fuera nave
						naves.remove( nave );  // Da igual aquí naves.remove( i );
					}
				}
			}
			// Dibujado
			vent.borra();
			dibujaBordes();
			for (ObjetoEspacial oe : elementos) {
				oe.dibuja( vent );
			}
			vent.repaint();
			// Ciclo de espera en cada bucle
			vent.espera( MSGS_POR_FRAME );
			// Subida de nivel
			subeNivel();
		}
	}
	
	private void gestionTeclado() {
		if (vent.isTeclaPulsada( KeyEvent.VK_PLUS )) {
			VEL_JUEGO *= 1.05;
			vent.setMensaje( "Nueva velocidad de juego: " + VEL_JUEGO );
		}
		if (vent.isTeclaPulsada( KeyEvent.VK_MINUS )) {
			VEL_JUEGO /= 1.05;
			vent.setMensaje( "Nueva velocidad de juego: " + VEL_JUEGO );
		}
		// Movimiento jugador - mira a ver si están subiendo o bajando...
		boolean estanSubiendo = false;
		boolean estanBajando = false;
		for (Nave nave : naves) {
			if (nave.isSubiendo()) estanSubiendo = true;
			if (nave.isBajando()) estanBajando = true;
		}
		if (!estanSubiendo && !estanBajando) {  // Si están bajando o subiendo las naves siguen su camino hasta que lleguen al canal
			// Si ni están subiendo ni bajando entonces...
			if (vent.isTeclaPulsada( KeyEvent.VK_UP )) {
				double velocidad = -VEL_NAVES;
				for (Nave nave : naves) {
					if (nave.getCanal()>0) {  // Si no está en el canal superior
						nave.setVY( velocidad );
						nave.setSubiendo( true );
						nave.setCanal( nave.getCanal()-1 );
					}
					velocidad -= INC_VEL_NAVES;
				}
			} else if (vent.isTeclaPulsada( KeyEvent.VK_DOWN )) {
				double velocidad = +VEL_NAVES;
				for (Nave nave : naves) {
					if (nave.getCanal()<CANAL.length-1) {  // Si no está en el canal inferior
						nave.setVY( velocidad );
						nave.setBajando( true );
						nave.setCanal( nave.getCanal()+1 );
					}
					velocidad += INC_VEL_NAVES;
				}
			}
		}
	}
	
	private void dibujaBordes() {
		int distCanales = (CANAL[1] - CANAL[0]) / 2;
		vent.dibujaRect( 0, distCanales, vent.getAnchura(), 2.0*CANAL.length*distCanales, 1.5f, Color.black );
		for (int canal=0; canal<CANAL.length-1; canal++) {
			int vertical = CANAL[canal] + distCanales;
			vent.dibujaLinea( 0, vertical, vent.getAnchura(), vertical, 0.5f, Color.blue );
		}
	}
	
	private void creaNuevoAsteroide() {
		double radioAleatorio = random.nextDouble() * (RADIO_MAX_ASTER - RADIO_MIN_ASTER ) + RADIO_MIN_ASTER;
		int canalAleatorio = random.nextInt( CANAL.length );
		Asteroide asteroide = new Asteroide( radioAleatorio, vent.getAnchura() + radioAleatorio, CANAL[canalAleatorio] );
		double velAleatoria = random.nextDouble() * (VEL_MAX_ASTER - VEL_MIN_ASTER ) + VEL_MIN_ASTER;
		asteroide.setVX( -velAleatoria );
		elementos.add( asteroide );
	}
	
	// Choque entre nave y asteroide (simplificado con envolventes círculos)
	private boolean hayChoque( Nave nave, Asteroide asteroide ) {
		double dist = Fisica.distancia(nave.getX(), nave.getY(), asteroide.getX(), asteroide.getY() );
		return (dist < RADIO_NAVE + asteroide.getRadio());
	}
	
	// Determina si una nave ha llegado ya a su canal y la para si es el caso
	private void miraSiEstaEnSuCanal( Nave nave ) {
		if (nave.isSubiendo()) {
			if (nave.getY() <= CANAL[nave.getCanal()]) {
				nave.setY( CANAL[nave.getCanal()]);
				nave.setVY( 0.0 );
				nave.setSubiendo( false );
			}
		} else if (nave.isBajando()) {
			if (nave.getY() >= CANAL[nave.getCanal()]) {
				nave.setY( CANAL[nave.getCanal()]);
				nave.setVY( 0.0 );
				nave.setBajando( false );
			}
		}
	}
	
	// Sube el nivel cada 15 segundos
	private void subeNivel() {
		if (System.currentTimeMillis() - tiempoNivel > 15000) {
			PROB_NUEVO_AST += 0.01;  // Sube un 1% la probabilidad de asteroide
			VEL_MAX_ASTER += 10;     // Sube 10 píxels por segundo la velocidad máxima de asteroide
			tiempoNivel = System.currentTimeMillis();
		}
	}

}

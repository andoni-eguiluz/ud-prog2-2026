package tema1c.resueltos.runnerNaves;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Juego runner ejercicio mejorado
 * Con las colisiones codificadas como interfaz
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class RunnerNaves {

	private static final long MSGS_POR_FRAME = 20;    // 20 msgs por frame = 50 frames por segundo aprox
	private static final int TAM_NAVE = 50;           // Píxel de tamaño de cada nave
	private static final double RADIO_MIN_ASTER = 25; // Radio mínimo del asteroide (círculo imaginario)
	private static final double RADIO_MAX_ASTER = 50; // Radio máximo del asteroide (círculo imaginario)
	private static final double VEL_Y_NAVES = 500;    // Velocidad (vertical) de desplazamiento de las naves
	private static final double VEL_X_NAVES = 400;    // Velocidad (horizontal) de desplazamiento de las naves
	private static final int X_MIN_NAVES = 0;         // Mínima x por la izquierda para las naves
	private static final int X_MAX_NAVES = 800;       // Máxima x por la derecha para las naves
	private static final double INC_VEL_NAVES = 50;   // Cada nave es un poquito más rápida que la anterior
	private static final int NUM_NAVES = 5;           // Número de naves del juego
	private static final int DIST_NAVES = 60;         // Píxels de distancia entre naves
	private static final long MILIS_NIVEL = 15000;    // Milisegundos de paso de nivel
	private static final double PRB_AST_NIVEL = 0.01; // Subida de probabilidad (suma) de aparición de asteroide por cada subida de nivel
	private static final double PRB_BON_NIVEL = 0.8;  // Disminución (producto) de probabilidad de aparición de asteroide por cada subida de nivel
	private static final double VEL_AST_NIVEL = 10.0; // Subida de velocidad máxima de asteroide por cada subida de nivel

	private static final int[] CANAL = { 100, 200, 300, 400, 500 };  // Píxels y de cada canal

	private static double VEL_MIN_ASTER = 200;        // Velocidad mínima del asteroide (píxels por segundo)
	private static double VEL_MAX_ASTER = 300;        // Velocidad máxima del asteroide (píxels por segundo)
	private static double PROB_NUEVO_AST = 0.02;      // Probabilidad de que aparezca un nuevo asteroide cada frame (2%)
	private static double PROB_NUEVO_BONUS = 0.02;    // Probabilidad de que aparezca un nuevo bonus cada frame (%)
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
	private ArrayList<ObjetoEspacial> elementos;  // Elementos del juego (tanto naves como asteroides, como fondos como bonus...)
	private ArrayList<Nave> naves;                // Naves del juego (solo las naves)
	private long tiempoNivel;                     // Tiempo inicial del nivel actual (milisegundos)
	private int puntuacion;                       // Variable para puntuación
	private long anteriorTiempoPuntuacion;        // Variable utilizada para gestionar el tiempo, asociada a la puntuación

	
	public void jugar() {
		puntuacion = 0;
		anteriorTiempoPuntuacion = System.currentTimeMillis();
		System.out.println( String.format( "Inicio juego. Asteroide %1$1.1f%% - Bonus %2$1.1f%%", PROB_NUEVO_AST*100.0, PROB_NUEVO_BONUS*100.0 ) );
		random = new Random();
		vent = new VentanaGrafica( 1200, 600, "Runner de naves" );
		crearFondos();
		crearNaves();
		mover();
	}

	// Crea los 3 fondos decorativos
	private void crearFondos() {
		elementos = new ArrayList<>();
		Fondo fondo1 = new Fondo( "img/UD-roller.jpg", 0, 333, 1.0, 0.35f, 1000 );   // Imagen 1000x666 píxels
		Fondo fondo2 = new Fondo( "img/UD-roller.jpg", 1000, 333, 1.0, 0.35f, 1000 );  // Todas seguidas una a la derecha de la otra
		Fondo fondo3 = new Fondo( "img/UD-roller.jpg", 2000, 333, 1.0, 0.35f, 1000 );
		fondo1.setVX( -200 );
		fondo2.setVX( -200 );
		fondo3.setVX( -200 );
		elementos.add( 0, fondo1 );
		elementos.add( 1, fondo2 );
		elementos.add( 2, fondo3 );  // Los tres primeros elementos son los fondos (se dibujan al principio de todo)
	}

	// Crea las 5 naves de inicio
	private void crearNaves() {
		int posNave = TAM_NAVE;
		naves = new ArrayList<>();
		for (int numNave=0; numNave<NUM_NAVES; numNave++) {
			Nave nave = new Nave( posNave, CANAL[2] );  // Nueva nave en canal intermedio
			nave.setCanal( 2 );
			elementos.add( nave );
			naves.add( nave );
			posNave += DIST_NAVES;
		}
	}
	
	// Bucle de movimiento (bucle principal de juego)
	private void mover() {
		long tiempoInicial = System.currentTimeMillis();
		tiempoNivel = System.currentTimeMillis();
		vent.setDibujadoInmediato( false );
		while (!vent.estaCerrada() && naves.size()>0) {
			gestionTecladoYMovtoNaves();       // Manejo de teclado
			creacionNuevosElementos();         // Creación procedural de elementos
			movimientoElementos();             // Movimiento y rotación de todos los elementos
			controlDesaparicion();             // Control de desapariciones de los elementos explotados
			controlLimitesMovimiento();        // Controles de límites de movimiento de todos los elementos
			controlDeChoques();                // Control de choques de todas las naves con el resto de elementos
			gestionPuntuacion();               // Gestión de puntuación
			dibujadoElementos(tiempoInicial);  // Dibujado del fotograma completo
			vent.espera( MSGS_POR_FRAME );     // Ciclo de espera en cada bucle
			subeNivel();                       // Subida de nivel (cuando proceda)
		}
	}

	// Toda la gestión de teclado y su lógica asociada en el juego - movimiento de la nave
	private void gestionTecladoYMovtoNaves() {
		if (vent.isTeclaPulsada( KeyEvent.VK_PLUS )) {
			VEL_JUEGO *= 1.05;
			vent.setMensaje( "Nueva velocidad de juego: " + VEL_JUEGO );
		}
		if (vent.isTeclaPulsada( KeyEvent.VK_MINUS )) {
			VEL_JUEGO /= 1.05;
			vent.setMensaje( "Nueva velocidad de juego: " + VEL_JUEGO );
		}
		// Movimiento vertical jugador - mira a ver si están subiendo o bajando...
		boolean estanSubiendo = false;
		boolean estanBajando = false;
		for (Nave nave : naves) {
			if (nave.isSubiendo()) estanSubiendo = true;
			if (nave.isBajando()) estanBajando = true;
		}
		if (!estanSubiendo && !estanBajando) {  // Si están bajando o subiendo las naves siguen su camino hasta que lleguen al canal
			// Si ni están subiendo ni bajando entonces...
			if (vent.isTeclaPulsada( KeyEvent.VK_UP )) {
				double velocidad = -VEL_Y_NAVES;
				for (Nave nave : naves) {
					if (nave.getCanal()>0) {  // Si no está en el canal superior
						nave.setVY( velocidad );
						nave.setCanal( nave.getCanal()-1 );  // Canal al que va
					}
					velocidad -= INC_VEL_NAVES;
				}
			} else if (vent.isTeclaPulsada( KeyEvent.VK_DOWN )) {
				double velocidad = +VEL_Y_NAVES;
				for (Nave nave : naves) {
					if (nave.getCanal()<CANAL.length-1) {  // Si no está en el canal inferior
						nave.setVY( velocidad );
						nave.setCanal( nave.getCanal()+1 );  // Canal al que va
					}
					velocidad += INC_VEL_NAVES;
				}
			}
		}
		// Movimiento horizontal jugador
		double velX = 0.0;
		if (vent.isTeclaPulsada( KeyEvent.VK_LEFT )) {
			velX = -VEL_X_NAVES;  // Velocidad hacia la izquierda (negativa)
		} else if (vent.isTeclaPulsada( KeyEvent.VK_RIGHT )) {
			velX = VEL_X_NAVES;  // Velocidad hacia la derecha (positiva)
		}
		// Comprobación de parada en los extremos
		if (naves.get(0).getX() <= X_MIN_NAVES && velX<0) velX = 0.0;
		if (naves.get(naves.size()-1).getX() >= X_MAX_NAVES && velX>0) velX = 0.0;
		for (Nave nave : naves) {
			nave.setVX( velX );
		}
	}

	// Creación de nuevos elementos (en función de los valores aleatorios)
	private void creacionNuevosElementos() {
		// Creación aleatoria de nuevo asteroide
		if (random.nextDouble()<PROB_NUEVO_AST) {
			creaNuevoAsteroide();
		}
		// Creación aleatoria de nuevo bonus
		if (random.nextDouble()<PROB_NUEVO_BONUS) {
			creaNuevoBonus();
		}
	}
	
	// Movimiento y rotación del fotograma de todos los elementos
	private void movimientoElementos() {
		for (ObjetoEspacial objeto : elementos) {
			objeto.mueve( MSGS_POR_FRAME/1000.0 * VEL_JUEGO );
		}
		// Rotación de los bonus y los asteroides
		for (ObjetoEspacial objeto : elementos) {
			if (objeto instanceof Rotable) {  // El interfaz funciona como una clase abstracta
				((Rotable)objeto).rota( 0.1 );
			}
		}
	}

	// Controles de límites de movimiento de todos los elementos
	private void controlLimitesMovimiento() {
		// Comprobación de parada de naves (ver si han llegado a su canal)
		for (Nave nave : naves) {  // Se podría hacer tb for (Objeto o : elementos) if (o instanceof Nave) ...
			nave.miraSiEstaEnSuCanal( CANAL );  // Se lleva la lógica a la nave: si está en su canal, se le para para que no siga subiendo/bajando
		}
		// Control de salida de pantalla de los asteroides o bonus o fondos
		for (int i=elementos.size()-1; i>=0; i--) {
			ObjetoEspacial oe = elementos.get(i);
			if (oe instanceof Salible && ((Salible)oe).seSalePorLaIzquierda(vent)) {
				((Salible)oe).sal( elementos );
			}
		}
	}

    // Control de desapariciones de los elementos explotados
	private void controlDesaparicion() {
		// Explosión de naves o asteroides
		for (int i=naves.size()-1; i>=0; i--) {
			ObjetoEspacial oe = naves.get(i);
			if (oe instanceof Explotable && ((Explotable)oe).haExplotado()) {  // Polimorfismo: cualquier objeto que puede explotar
				elementos.remove( oe );  // Fuera elemento explotado
				naves.remove( oe );      // Si no es una nave, remove no hace nada
			}
		}
	}
	
	// Control de choques de todas las naves con el resto de elementos
	private void controlDeChoques() {
		for (Nave nave : naves) {
			if (!nave.estaExplotando()) {  // Si ya está explotando no se miran los choques
				boolean cogeBonus = false;
				ArrayList<Bonus> lBonusChocan = new ArrayList<Bonus>();
				for (ObjetoEspacial oe2 : elementos) {
					if (!(oe2 instanceof Nave) && oe2 instanceof Chocable) {  // Polimorfismo para cualquier objeto chocable, sea cual sea (Asteroide o Bonus), pero no entre naves
						Chocable choc = (Chocable) oe2;
						if (nave.getEnvolvente().chocaCon( ((Chocable) oe2).getEnvolvente() )) {  // Hay choque!
							// Puntua cada asteroide que destrozamos
							if (choc instanceof Asteroide && !((Asteroide)choc).estaExplotando()) {
								puntuacion += Math.round( ((Asteroide)choc).getRadio()/5.0 );  // 5 puntos por cada 5 píxels de radio del asteroide
							}
							nave.chocaCon(choc); // La lógica del choque se lleva al método de la clase Nave
							// El asteroide no se quita directamente, solo después de la explosión
							if (choc instanceof Bonus) { // El bonus sí se quita directamente
								cogeBonus = true;
								lBonusChocan.add( (Bonus)choc );
							}
						}
					}
				}
				// Choque de nave con bonus
				if (cogeBonus) {  // Se hace fuera del bucle anterior porque no se puede modificar una estructura dentro de un for each
					for (Bonus b : lBonusChocan) elementos.remove( b );  // Quitar los bonus que chocan
				}
			}
		}
	}

	// Gestión de puntuación
	private void gestionPuntuacion() {
		// Diez puntos por cada segundo que pasa cada nave en el sistema
		if (System.currentTimeMillis() - anteriorTiempoPuntuacion > 1000) {  // Ha pasado un segundo
			anteriorTiempoPuntuacion = System.currentTimeMillis();
			for (Nave nave : naves) {
				if (!nave.estaExplotando()) {
					puntuacion += 10;
				}
			}
		}
	}
	
	// Dibujado de todos los elementos en el fotograma
	private void dibujadoElementos( long tiempoInicial ) {
		vent.borra();
		int fondos = 0;
		for (ObjetoEspacial oe : elementos) {
			oe.dibuja( vent );
			fondos++;
			if (fondos==3) {  // Entre los fondos y el resto de los objetos dibuja los bordes de los canales
				dibujaBordes();
			}
		}
		vent.repaint();
		vent.setMensaje( "PUNTUACIÓN: " + puntuacion + ". Tiempo de juego: " + (System.currentTimeMillis() - tiempoInicial)/1000 + " segundos." );
	}
	
	
	// Dibuja los bordes y los canales
	private void dibujaBordes() {
		int distCanales = (CANAL[1] - CANAL[0]) / 2;
		vent.dibujaRect( 0, distCanales, vent.getAnchura(), 2.0*CANAL.length*distCanales, 1.5f, Color.black );
		for (int canal=0; canal<CANAL.length-1; canal++) {
			int vertical = CANAL[canal] + distCanales;
			vent.dibujaLinea( 0, vertical, vent.getAnchura(), vertical, 0.5f, Color.blue );
		}
	}

	// Crea un asteroide nuevo
	private void creaNuevoAsteroide() {
		double radioAleatorio = random.nextDouble() * (RADIO_MAX_ASTER - RADIO_MIN_ASTER ) + RADIO_MIN_ASTER;
		int canalAleatorio = random.nextInt( CANAL.length );
		Asteroide asteroide = new Asteroide( radioAleatorio, vent.getAnchura() + radioAleatorio, CANAL[canalAleatorio] );
		double velAleatoria = random.nextDouble() * (VEL_MAX_ASTER - VEL_MIN_ASTER ) + VEL_MIN_ASTER;
		asteroide.setVX( -velAleatoria );
		elementos.add( 3, asteroide );  // Se añade al principio para que las naves sean lo último que se dibujen -los fondos son lo primero-
	}
	
	// Crea un bonus nuevo
	private void creaNuevoBonus() {
		double tiempo = random.nextDouble() * 5; // Entre 0 y 5 segundos de protección - podríamos ponerlo configurable
		double radio = tiempo*10; // Proporcional al tiempo del bonus
		int canalAleatorio = random.nextInt( CANAL.length );
		Bonus bonus = new Bonus( radio, vent.getAnchura() + radio, CANAL[canalAleatorio], tiempo );
		double velAleatoria = random.nextDouble() * (VEL_MAX_ASTER - VEL_MIN_ASTER ) + VEL_MIN_ASTER;
		bonus.setVX( -velAleatoria );
		elementos.add( bonus );
	}
	
	// Sube el nivel cada N segundos (mayor dificultad)
	private void subeNivel() {
		if (System.currentTimeMillis() - tiempoNivel > MILIS_NIVEL) {
			PROB_NUEVO_AST += PRB_AST_NIVEL;     // Sube un x% la probabilidad de asteroide
			PROB_NUEVO_BONUS *= PRB_BON_NIVEL;   // Se baja un x% la probabilidad de nuevo bonus
			VEL_MAX_ASTER += VEL_AST_NIVEL;      // Sube 10 píxels por segundo la velocidad máxima de asteroide
			tiempoNivel = System.currentTimeMillis();  // Nueva cuenta de tiempo
			System.out.println( String.format( "Nuevo nivel. Asteroide %1$1.1f%% - Bonus %2$1.1f%%", PROB_NUEVO_AST*100.0, PROB_NUEVO_BONUS*100.0 ) );
		}
	}

}

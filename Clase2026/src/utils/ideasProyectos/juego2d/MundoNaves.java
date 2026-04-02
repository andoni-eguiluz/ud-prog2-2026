package utils.ideasProyectos.juego2d;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import utils.ideasProyectos.juego2d.datos.Asteroide;
import utils.ideasProyectos.juego2d.datos.Nave;
import utils.ideasProyectos.juego2d.datos.NaveTripulada;
import utils.ideasProyectos.juego2d.datos.ObjetoMovil;
import utils.ideasProyectos.juego2d.utils.Fisica;
import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class MundoNaves {
	private ArrayList<ObjetoMovil> objetos;
	private VentanaGrafica ventana;
	
	private static long MILIS_POR_MOVIMIENTO = 16;
	private static long MILIS_ENTRE_MOVTOS = 16;
	private static boolean PAUSA = false;
	private static boolean VER_CHOQUES = false;
	
	public MundoNaves() {
		objetos = new ArrayList<ObjetoMovil>();
		ventana = new VentanaGrafica( 1000, 800, "MundoNaves" );
	}
	
	public ArrayList<ObjetoMovil> getObjetos() {
		return objetos;
	}
	
	public VentanaGrafica getVentana() {
		return ventana;
	}
	
	public boolean addObjeto( ObjetoMovil objeto ) {
		if (objeto.getNombre()==null || objeto.getNombre().isEmpty()) objeto.setNombre( "" + objetos.size() );
		objetos.add( objeto );
		return true;
	}
	

	public static void main(String[] args) {
		Fisica.setGravedad( false );
		crearYMoverMundo();
	}
	
	private static void crearYMoverMundo() {
		MundoNaves mundo = new MundoNaves();
		mundo.init();
		mundo.crearMundoTest( 1 );
		mundo.moverMundo();
	}
	
	private void init() {
		ventana.anyadeBoton( "Reinicio test 1", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ejecutaSeguro( new Runnable() {
					@Override
					public void run() {
						crearMundoTest( 1 );
					}
				} );
			}
		});
//		ventana.anyadeBoton( "Reinicio test 2", new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				ObjetoEspacial.DIBUJAR_VELOCIDAD = false;
//				VER_CHOQUES = true;
//				ventana.setMensaje( "Parar cálculo en choques ON" );
//				crearMundoTest( 2 );
//			}
//		});
		ventana.anyadeBoton( "Test", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ejecutaSeguro( new Runnable() {
					@Override
					public void run() {
						test( objetos );
					}
				} );
			}
		});
		ventana.anyadeBoton( "Cargar", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ejecutaSeguro( new Runnable() {
					@Override
					public void run() {
						cargarDeFichero( objetos );
					}
				} );
			}
		});
		ventana.anyadeBoton( "Guardar", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ejecutaSeguro( new Runnable() {
					@Override
					public void run() {
						guardarAFichero( objetos );
					}
				} );
			}
		});
	}
	
		private static Random random = new Random();
		public static int cont = 0;

		private transient NaveTripulada navePrincipal = null;
		private transient Runnable run = null;
		private transient int tipoInit = 0;
	// Crea objetos de test en el mundo
	private void crearMundoTest( int tipoTest ) {
		tipoInit = tipoTest;
		objetos.clear();
		if (tipoInit==1) {  // Unas cuantas naves chocando y moviéndose
			MundoNaves.this.addObjeto( new Nave( 200, 100, 20, 100, 100, Color.red ) );  // Pone un nombre secuencial ... "1", "2"... y así sucesivamente
			MundoNaves.this.addObjeto( new Nave( 200, 400, 20, -100, -50, Color.red ) );
			MundoNaves.this.addObjeto( new Nave( 300, 350, 28, -40, 45, Color.blue ) );
			MundoNaves.this.addObjeto( new Nave( 420, 550, 28, 420, 550, Color.blue ) );
			MundoNaves.this.addObjeto( new Nave( 500, 120, 28, 530, 180, Color.blue ) );
			MundoNaves.this.addObjeto( new Nave( 600, 280, 28, 690, 300, Color.blue ) );
			MundoNaves.this.addObjeto( new Nave( 700, 500, 14, 630, 450, Color.green ) );
			MundoNaves.this.addObjeto( new Asteroide( 350, 300, 150, Color.magenta ) );
			MundoNaves.this.addObjeto( new Asteroide( 700, 130, 80, Color.magenta ) );
			navePrincipal = new NaveTripulada( 600, 500, 40, 600, 500, Color.magenta );
			MundoNaves.this.addObjeto( navePrincipal );
		} else if (tipoInit==2) {  // Dos naves grandes que van a chocar
			MundoNaves.this.addObjeto( new Nave( 300, 100, 100, 1900, 420, Color.blue ) );
			MundoNaves.this.addObjeto( new Nave( 565, 120, 100, -215, 560, Color.magenta ) );
		}
	}
	
		// Método privado para ejecutar un código sin interferir con el ciclo de ejecución del juego
		private void ejecutaSeguro( Runnable miRun ) {
			if (!running)
				miRun.run();  // Si no está ejecutándose el juego, se inicia
			else
				run = miRun;  // Si ya está ejecutándose el juego se almacena el trabajo y el propio juego lo lanzará cuando sea seguro
		}
		
		private boolean running = false;
	// Permitiendo interacción con el ratón para crear naves y/o lanzarlas en diagonal
	private void moverMundo() {
		running = true;
		primerClick = null;
		ultimoClick = null;
		objetoClickado = null;
		VentanaGrafica v = this.getVentana();
		PAUSA = true;
		ventana.setMensaje( "Pausa ON. Pulsa P para iniciar");
		while (!v.estaCerrada()) {  // hasta que se cierre la ventana
			// 0.- Cambiar parámetros con posibles indicaciones de teclado
			procesarTeclado();
			// 1.- Chequear posible interacción de ratón
			procesarRaton(v);
			// 2.- Hacer movimiento de los objetos en el lapso de tiempo ocurrido
			moverObjetos(v);
			// 3.- Calcular y corregir choques en el mundo
			corregirMovimiento(v);
			// 4.- Dibujado explícito de todos los objetos
			dibujadoMundo(v);
			// 5.- Ciclo de espera hasta la siguiente iteración
			this.getVentana().espera( MILIS_ENTRE_MOVTOS );
			// 6.- Posible trabajo de reinicialización que esté pendiente
			if (run!=null) { run.run(); run = null; }
		}
		running = false;
	}

	// 0.- Cambiar parámetros con posibles indicaciones de teclado
	private void procesarTeclado() {
		int tecla = ventana.getCodUltimaTeclaTecleada(); 
		if (tecla==KeyEvent.VK_V) {
			ObjetoMovil.DIBUJAR_VELOCIDAD = !ObjetoMovil.DIBUJAR_VELOCIDAD;
			ventana.setMensaje( "Dibujar velocidad " + (ObjetoMovil.DIBUJAR_VELOCIDAD ? "ON" : "OFF") );
		} else if (tecla==KeyEvent.VK_P) {
			PAUSA = !PAUSA;
			ventana.setMensaje( "Pausa " + (PAUSA ? "ON" : "OFF") );
		} else if (tecla==KeyEvent.VK_C) {
			VER_CHOQUES = !VER_CHOQUES;
			ventana.setMensaje( "Dibujar y parar cálculo en choques " + (VER_CHOQUES ? "ON" : "OFF") );
		} else if (tecla==KeyEvent.VK_PLUS) {
			if (MILIS_POR_MOVIMIENTO<132) {
				MILIS_POR_MOVIMIENTO = MILIS_POR_MOVIMIENTO * 2;
				if (MILIS_POR_MOVIMIENTO >= MILIS_ENTRE_MOVTOS)
					ventana.setMensaje( "Tiempo visualización x" + (1.0 * MILIS_POR_MOVIMIENTO / MILIS_ENTRE_MOVTOS) );
				else 
					ventana.setMensaje( "Tiempo visualización /" + (1.0 * MILIS_ENTRE_MOVTOS / MILIS_POR_MOVIMIENTO) );
			}
		} else if (tecla==KeyEvent.VK_MINUS) {
			if (MILIS_POR_MOVIMIENTO>1) {
				MILIS_POR_MOVIMIENTO = MILIS_POR_MOVIMIENTO / 2;
				if (MILIS_POR_MOVIMIENTO >= MILIS_ENTRE_MOVTOS)
					ventana.setMensaje( "Tiempo visualización x" + (1.0 * MILIS_POR_MOVIMIENTO / MILIS_ENTRE_MOVTOS) );
				else 
					ventana.setMensaje( "Tiempo visualización /" + (1.0 * MILIS_ENTRE_MOVTOS / MILIS_POR_MOVIMIENTO) );
			}
		}
		// Pulsaciones activas
		tecla = ventana.getCodTeclaQueEstaPulsada(); 
		if (ventana.isTeclaPulsada( KeyEvent.VK_LEFT )) {
			navePrincipal.gira( -Math.PI/20 );
		} else if (ventana.isTeclaPulsada( KeyEvent.VK_RIGHT )) {
			navePrincipal.gira( Math.PI/20 );
		} else if (ventana.isTeclaPulsada( KeyEvent.VK_UP )) {
			navePrincipal.acelera( 10 );
		} else if (ventana.isTeclaPulsada( KeyEvent.VK_DOWN )) {
			navePrincipal.acelera( -10 );
		}
	}

		private Point primerClick = null;
		private Point ultimoClick = null;
		private ObjetoMovil objetoClickado = null;
	// 1.- Chequear posible interacción de ratón
	private void procesarRaton(VentanaGrafica v) {
		Point clickRaton = v.getRatonPulsado();
		if (clickRaton==null) {
			if (primerClick!=null && ultimoClick!=null && objetoClickado!=null && !primerClick.equals(ultimoClick)) { // Ha habido un drag sobre un objeto
				// Aplicar fuerza a objeto
				objetoClickado.setVelocidadX( (ultimoClick.x - primerClick.x)*10.0 );
				objetoClickado.setVelocidadY( (ultimoClick.y - primerClick.y)*10.0 );
			} else if (primerClick!=null && ultimoClick!=null && objetoClickado==null && !primerClick.equals(ultimoClick)) {  // No hay drag. Creación de objeto nuevo
				double radio = primerClick.distance( ultimoClick );
				if (radio >= 5) {  // Por debajo de 10 píxels no se considera
					Color color = Color.blue;
					switch (random.nextInt( 3 )) {
						case 0: {
							color = Color.red;
							break;
						}
						case 1: {
							color = Color.green;
							break;
						}
					}
					Nave nave = new Nave( primerClick.x, primerClick.y, 20, ultimoClick.x, ultimoClick.y, color );
					if (this.addObjeto( nave ))
						nave.dibuja( this.getVentana() );
				}
			}
			primerClick = null;
		} else {
			if (primerClick==null) {
				primerClick = clickRaton;
				ultimoClick = null;
				objetoClickado = null;
				for (ObjetoMovil objeto : this.getObjetos()) {
						if (objeto!=null) {
						if (objeto.contieneA(primerClick)) {
							objetoClickado = objeto;
							break;
						}
					}
				}
			} else {
				ultimoClick = clickRaton;
			}
		}
	}
	
	// 2.- Hacer movimiento de los objetos en el lapso de tiempo ocurrido
	private void moverObjetos(VentanaGrafica v) {
		if (!PAUSA) {
			for (ObjetoMovil objeto : this.getObjetos()) {
				if (objeto != null) {  // Ojo, solo con los objetos que haya!
					// Se mueve el objeto
					objeto.mueveUnPoco( v, MILIS_POR_MOVIMIENTO, false );  // Método para movimiento con influencia de gravedad   (sin dibujado)
				}
			}
		}
	}

	// 3.- Calcular y corregir choques en el mundo
	private void corregirMovimiento(VentanaGrafica v) {
		if (!PAUSA) {
			boolean hayChoques;
			int numIteraciones = 0;
			do { 
				numIteraciones++;
				hayChoques = false;
				// 3a.- Comprobamos choques con los límites de la ventana
				for (ObjetoMovil objeto : this.getObjetos()) {
					if (objeto != null) {  // Ojo, solo con los objetos que haya!
						// Choque lateral
						int choque = objeto.chocaConBorde( v );
						// System.out.println( choque );
						if ((choque & 0b0001) != 0 && objeto.getVelocidadX()<0) { // Choque izquierda
							objeto.rebotaIzquierda( 1.0 );  // Rebota al 100% -sale hacia la derecha-
							objeto.corrigeChoqueLateral( v, false );
							hayChoques = true;
							reboteEnBorde( objeto, 0 );
						} else if ((choque & 0b0010) != 0 && objeto.getVelocidadX()>0) {  // Choque derecha
							objeto.rebotaDerecha( 1.0 );  // Rebota al 100% -sale hacia la izquierda-
							objeto.corrigeChoqueLateral( v, false );
							hayChoques = true;
							reboteEnBorde( objeto, 1 );
						}
						// Choque en vertical
						if (choque>=8) {  // Abajo
							hayChoques = true;
							if (objeto.getVelocidadY()>0) objeto.corrigeChoqueVertical( v, false );
							objeto.rebotaAbajo( 1.0 );
							objeto.corrigeChoqueVertical( v, false );
							reboteEnBorde( objeto, 3 );
						} else if (choque>=4) {  // Arriba
							hayChoques = true;
							if (objeto.getVelocidadY()<0) objeto.corrigeChoqueVertical( v, false );
							objeto.rebotaArriba( 1.0 );
							objeto.corrigeChoqueVertical( v, false );
							reboteEnBorde( objeto, 2 );
						}
					}
				}
				// 3b.- Comprobamos choques entre objetos
				// Probamos todas con todas (salen rebotadas en la dirección del choque)
				for (int i=0; i<this.getObjetos().size(); i++) {
					ObjetoMovil objeto = this.getObjetos().get(i); 
					if (objeto != null) {
						for (int j=i+1; j<this.getObjetos().size(); j++) {
							ObjetoMovil objeto2 = this.getObjetos().get(j); 
							if (objeto2 != null) {
								if (objeto.chocaConObjeto( objeto2 )!=null) {
									procesaChoque( objeto, objeto2 );
								}
							}
						}
					}
				}
			} while (hayChoques && numIteraciones<=3);
		}
	}

	// 4.- Dibujado explícito de todos los objetos
	private void dibujadoMundo(VentanaGrafica v) {
		// Dibujado de mundo
		this.getVentana().borra();  // Borra todo
		for (ObjetoMovil objeto : this.getObjetos()) {  // Y dibuja de nuevo todos los objetos
			if (objeto != null) {
				objeto.dibuja( this.getVentana() );
			}
		}
		// Feedback visual de interacciones
		if (primerClick!=null && ultimoClick!=null) {
			if (objetoClickado!=null) {  // Se está queriendo imprimir velocidad a un objeto
				ventana.dibujaFlecha( primerClick.getX(), primerClick.getY(), ultimoClick.getX(), ultimoClick.getY(), 1.0f, Color.orange, 25 );
			} else {  // Se está queriendo crear un objeto
				ventana.dibujaCirculo( primerClick.getX(), primerClick.getY(), 20, 1.0f, Color.orange );
				ventana.dibujaFlecha( primerClick.getX(), primerClick.getY(), ultimoClick.getX(), ultimoClick.getY(), 1.0f, Color.orange, 25 );
			}
		}
		if (!PAUSA) trasCadaFotograma( this.getObjetos() );
	}
	
	// Métodos de lógica de la animación
	
	// Se ejecuta en cada choque y recibe los objetos que chocan
	private void procesaChoque( ObjetoMovil objeto, ObjetoMovil objeto2 ) {
		double milis = MILIS_POR_MOVIMIENTO;
		// Aplica velocidad de choque en función de las masas (el que tiene masa más grande se ve menos afectado y viceversa)
		Fisica.calcChoqueEntreObjetos(ventana, objeto, objeto2, milis, VER_CHOQUES );
		if (VER_CHOQUES) {  // Espera a pulsación de ratón
			if (ventana.getRatonPulsado()==null) { // Si el ratón no está pulsado...
				while (ventana.getRatonPulsado()==null && !ventana.estaCerrada()) {}  // Espera a pulsación...
				while (ventana.getRatonPulsado()!=null && !ventana.estaCerrada()) {}  // ...y suelta
			}
		}
		if (objeto==navePrincipal || objeto2==navePrincipal) {
			ObjetoMovil chocado = objeto;
			if (objeto==navePrincipal) chocado = objeto2;
			navePrincipal.cambiaEnergia( - chocado.getArea()/20 );
			ventana.setMensaje( "Choque: Energía = " + navePrincipal.getEnergia() );
			if (navePrincipal.getEnergia()<0) {
				objetos.remove( navePrincipal );
				ventana.setMensaje( "Juego terminado! Has perdido" );
			}
		}
	}

	// Se ejecuta tras cada fotograma y recibe todos los objetos en pantalla
	private void trasCadaFotograma( ArrayList<ObjetoMovil> listaObjetos ) {
		
	}
	
	// Se ejecuta tras rebotar en un borde de la ventana un objeto.
	// Código de borde: 0-Izquierda 1-Derecha 2-Arriba 3-Abajo
	private void reboteEnBorde( ObjetoMovil objeto, int codigoBorde ) {
		// TODO
	}
	
	// Se ejecuta al pulsar el botón de test y recibe todos los objetos en pantalla
	private void test( ArrayList<ObjetoMovil> listaObjetos ) {
		// TODO
	}
	public void cargarDeFichero( ArrayList<ObjetoMovil> listaObjetos ) {
		// TODO
	}

	public void guardarAFichero( ArrayList<ObjetoMovil> listaObjetos ) {
		// TODO
	}

	
}

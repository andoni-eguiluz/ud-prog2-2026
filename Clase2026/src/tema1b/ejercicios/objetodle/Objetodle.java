package tema1b.ejercicios.objetodle;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Clase para definir juegos objetodles, como ventanas gráficas con la lógica que corresponde al wordle
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class Objetodle extends VentanaGrafica {

	private Opciones opciones;
	private int numElementos;
	private int numIntentos;
	private int pixelsMargen = 8;
	private int altoTeclado = 200;
	private int xTablero;    // x de inicio de tablero
	private int yTablero;    // y de inicio de tablero
	private int altoIntento; // alto en pixels de cada fila de intento
	private int yTeclado;    // y de inicio de teclado
	private int tamElemento; // alto y ancho en pixels de cada elemento del Objetodle
	private int tamTecla;  // alto y ancho en pixels de cada tecla inferior
	private Point ultimoClick; 
	
	// Lógica de wordle
	private Combi combiSecreta;
	private int numIntentoActual;
	private int eltoActual;
	private Combi intentoActual;
	private ArrayList<Combi> intentosHechos;
	private boolean finExito;
	private boolean finFracaso;
	
	public Objetodle( Opciones opciones ) {
		this( 500, 800, "Objetodle", opciones, 5, 6 );
	}
	
	public Objetodle(int anchura, int altura, String titulo, Opciones opciones, int numElementos, int numIntentos ) {
		super(anchura, altura, titulo);
		this.opciones = opciones;
		this.numElementos = numElementos;
		this.numIntentos = numIntentos;
		yTeclado = getAltura() - altoTeclado;
		tamTecla = (altoTeclado-4*pixelsMargen) / 4;
		int numTeclasPorFila = getAnchura() / (tamTecla + pixelsMargen);
		while (4*numTeclasPorFila < opciones.size()) {
			tamTecla--;
			numTeclasPorFila = getAnchura() / (tamTecla + pixelsMargen);
		}
		yTablero = pixelsMargen;
		altoIntento = (yTeclado - yTablero - pixelsMargen) / numIntentos;
		int posibleAncho = (getAnchura() - (numElementos+1) * pixelsMargen) / numElementos;
		tamElemento = Math.min( altoIntento, posibleAncho ); 
		xTablero = (getAnchura() - (numElementos-1)*pixelsMargen - numElementos*tamElemento) / 2;
	}

	/** Bucle de ejecución principal
	 */
	public void run() {
		combiSecreta = new Combi(opciones, numElementos);
		System.out.println( "Solución: " + combiSecreta.getCombinacion() );
		numIntentoActual = 0;
		eltoActual = 0;
		intentoActual = new Combi(numElementos);
		intentosHechos = new ArrayList<>();
		finExito = false;
		finFracaso = false;
		dibujaTablero();
		while (!estaCerrada() && !finExito && !finFracaso ) {
			input();
			dibujaTablero();
			espera(50);
		}
	}
	
	private void input() {
		Point click = getRatonPulsado();
		if (click==null) {
			ultimoClick = null;
		} else if (click!=ultimoClick) {  // Click en sitio nuevo
			ultimoClick = click;
			for (Elemento tecla : opciones.getOpciones()) {
				if (tecla.estaPuntoDentro( click )) {
					procesaTecla( tecla );
					break;
				}
			}
		}
	}
	
	private void procesaTecla( Elemento tecla ) {
		Elemento intento = tecla.duplicar();
		intento.setTam( tamElemento );
		intento.setPosi( xDeCasilla( intentoActual.getNumActualElementos() ), yDeFila( numIntentoActual) );
		intentoActual.add( intento );
		System.out.println( "Añadido elemento " + tecla + " al intento actual." );
		if (intentoActual.estaCompletada()) {  // Se ha llenado una línea de intento - procesarlo
			intentosHechos.add( intentoActual );
			boolean exito = intentoActual.comparaConSolucion( combiSecreta );
			if (exito) {
				finExito = true;
				setMensaje( "Juego acabado con éxito!" );
				return;
			}
			numIntentoActual++;
			System.out.println( "Empezando nuevo intento en fila " + numIntentoActual );
			if (numIntentoActual >= numIntentos) {
				finFracaso = true;
				setMensaje( "Juego acabado con fracaso!" );
				return;
			}
			intentoActual = new Combi( numElementos );
		}
	}
	
	private void dibujaTablero() {
		// Dibujo de teclado
		dibujaLinea( 0, yTeclado, getAnchura(), yTeclado, 2f, Color.BLACK );
		int indiceTecla = 0;
		int yLinea = yTeclado + pixelsMargen;
		ArrayList<Elemento> lTeclas = new ArrayList<Elemento>( opciones.getOpciones() );
		do {
			int numTeclasPorFila = getAnchura() / (tamTecla + pixelsMargen);
			if (numTeclasPorFila > lTeclas.size()) {
				numTeclasPorFila = lTeclas.size();
			}
			int xLinea = (getAnchura() - (numTeclasPorFila * (tamTecla + pixelsMargen))) / 2;
			for (int i=0; i<numTeclasPorFila; i++) {
				Elemento e = lTeclas.remove(0);
				e.setObjetodle( this );
				e.setPosi( xLinea, yLinea );
				e.setTam( tamTecla );
				e.dibuja();
				xLinea = xLinea + (tamTecla + pixelsMargen);
			}
			yLinea += (tamTecla + pixelsMargen);
		} while (!lTeclas.isEmpty());
		// Dibujo de tablero superior de juego
		for (int indIntento=0; indIntento < numIntentos; indIntento++) {
			for (int indElto=0; indElto < numElementos; indElto++) {
				dibujaRect( xDeCasilla(indElto), yDeFila(indIntento), tamElemento, tamElemento, 5f, Color.BLACK );
			}
		}
		// Dibujo de intentos ya hechos
		for (Combi combi : intentosHechos) {
			for (Elemento el : combi.getCombinacion()) {
				el.dibuja();
			}
		}
		// Dibujo de intento en curso
		for (Elemento el : intentoActual.getCombinacion()) {
			el.dibuja();
		}
	}
	
	private int xDeCasilla( int columna ) {
		return xTablero + columna * (tamElemento + pixelsMargen);
	}

	private int yDeFila( int fila ) {
		return yTablero + fila * (tamElemento + pixelsMargen);
	}
	
}

package tema1c.resueltos.objetodle;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class Objetodle extends VentanaGrafica {

	/** Valores de modo de juego: ALEATORIO (combinación random), PALABRAS_CORRECTAS (lista de palabras permitidas), NUMEROS_IMPARES (números solo impares)
	 */
	public static enum Modo { ALEATORIO, PALABRAS_CORRECTAS, NUMEROS_IMPARES };

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
	private Combi intentoActual;
	private ArrayList<Combi> intentosHechos;
	private boolean finExito;
	private boolean finFracaso;
	// Combinaciones correctas (si existen)
	ArrayList<String> combinacionesOk;
	Modo modo = Modo.ALEATORIO;  // Por defecto
	// Mouseover
	private SensibleAlRaton teclaConMouseOver;
	// Teclado diferenciado de opciones
	private ArrayList<Elemento> teclado;
	
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
		// Teclado y teclas especiales
		teclado = new ArrayList<>( opciones.getOpciones() );
		teclado.add( new TeclaBackspace() );
	}

	/** Cambia el modo de funcionamiento de objetodle según el modo indicado
	 * @param modo	Modo de juego (con los valores de {@link Modo}
	 */
	public void setModo( Modo modo ) {
		this.modo = modo;
	}
	
	/** Modifica las combinaciones que son correctas (si se quieren limitar las palabras aceptadas)
	 * @param combisCorrectas	Lista de combinaciones correctas
	 */
	public void setCombinacionesCorrectas( List<String> combisCorrectas ) {
		combinacionesOk = new ArrayList<>( combisCorrectas );
	}
	
	/** Devuelve el intento actual
	 * @return	Intento en curso
	 */
	public Combi getIntentoActual() {
		return intentoActual;
	}

	/** Bucle de ejecución principal
	 */
	public void run() {
		if (combinacionesOk!=null && modo==Modo.PALABRAS_CORRECTAS) {
			combiSecreta = new CombiPalabrasCorrectas(opciones, numElementos, combinacionesOk );
		} else if (modo==Modo.NUMEROS_IMPARES) {
			combiSecreta = new CombiNumerosImpares(opciones, numElementos);
		} else {
			combiSecreta = new Combi(opciones, numElementos);
		}
		System.out.println( "Solución: " + combiSecreta.getCombinacion() );
		numIntentoActual = 0;
		if (modo==Modo.PALABRAS_CORRECTAS) {
			intentoActual = new CombiPalabrasCorrectas(numElementos);
		} else if (modo==Modo.NUMEROS_IMPARES) {
			intentoActual = new CombiNumerosImpares(numElementos);
		} else {
			intentoActual = new Combi(numElementos);
		}

		intentosHechos = new ArrayList<>();
		finExito = false;
		finFracaso = false;
		dibujaTablero();
		while (!estaCerrada() && !finExito && !finFracaso ) {
			boolean hayInput = input();
			if (hayInput) {  // Si hay input hay que redibujar el tablero
				dibujaTablero();
			} else {  // Si no hay input redibujar solo los objetos animables
				dibujaAnimacion();
			}
			espera(50);
		}
	}
	
	private boolean input() {
		boolean hayInput = false;
		// Proceso de ratón
		Point click = getRatonPulsado();
		if (click==null) {
			ultimoClick = null;
		} else if (click!=ultimoClick) {  // Click en sitio nuevo
			ultimoClick = click;
			for (Elemento tecla : teclado) {
				if (tecla.estaPuntoDentro( click )) {
					procesaTecla( tecla );
					hayInput = true;
					break;
				}
			}
		}
		// Proceso de teclado (equivalente a hacer click con el ratón)
		int codTecla = getCodUltimaTeclaTecleada();
		if (codTecla!=0) {
			char teclaPulsada = (char) codTecla;  // El código es el ASCII del carácter, pero ojo, las letras en mayúsculas
			for (Elemento tecla : teclado) {
				if ((tecla.aCaracter() + "").toUpperCase().equals( teclaPulsada+"" )) {  // Si coincide el carácter de la tecla-elemento con la tecla-teclado pulsada
					procesaTecla( tecla );
					hayInput = true;
					break;
				}
			}
		}
		// Proceso de mouse over
		Point mouse = getRatonMovido();
		if (mouse!=null) {
			for (Elemento tecla : teclado) {
				if (tecla instanceof SensibleAlRaton) {
					SensibleAlRaton teclaSensible = (SensibleAlRaton) tecla;
					if (teclaSensible.estaPuntoDentro( mouse )) {
						teclaConMouseOver = teclaSensible;
						hayInput = true;
						break;
					}
				}
			}
		}
		return hayInput;
	}
	
	private void procesaTecla( Elemento tecla ) {
		if (tecla instanceof TeclaEspecial) {
			((TeclaEspecial)tecla).procesaTecla( this );
		} else {
			Elemento intento = tecla.duplicar();
			intento.setTam( tamElemento );
			intento.setPosi( xDeCasilla( intentoActual.getNumActualElementos() ), yDeFila( numIntentoActual) );
			intentoActual.add( intento );
			if (intentoActual.estaCompletada()) {  // Se ha llenado una línea de intento - procesarlo
				// Gestión de combinaciones correctas (según el modo de juego)
				if (modo==Modo.PALABRAS_CORRECTAS) {
					CombiPalabrasCorrectas cpc = (CombiPalabrasCorrectas) intentoActual;
					cpc.setCombinacionesCorrectas( combinacionesOk );
					if (!cpc.intentoCorrecto()) {  // No es correcto el intento - reinicio
						setMensaje( "La palabra " + cpc.aString() + " no es correcta." );
						intentoActual.clear();
						return;
					}
				} else if (modo==Modo.NUMEROS_IMPARES) {
					CombiNumerosImpares cni = (CombiNumerosImpares) intentoActual;
					if (!cni.intentoCorrecto()) {  // No es correcto el intento - reinicio
						setMensaje( "El número " + cni.aString() + " no es correcta." );
						intentoActual.clear();
						return;
					}
				}
				intentosHechos.add( intentoActual );
				boolean exito = intentoActual.comparaConSolucion( combiSecreta );
				if (exito) {
					finExito = true;
					setMensaje( "Juego acabado con éxito!" );
					return;
				}
				numIntentoActual++;
				if (numIntentoActual >= numIntentos) {
					finFracaso = true;
					setMensaje( "Juego acabado con fracaso!" );
					return;
				}
				if (modo==Modo.PALABRAS_CORRECTAS) {
					intentoActual = new CombiPalabrasCorrectas(numElementos);
				} else if (modo==Modo.NUMEROS_IMPARES) {
					intentoActual = new CombiNumerosImpares(numElementos);
				} else {
					intentoActual = new Combi(numElementos);
				}
			}
		}
	}
	
	private void dibujaTablero() {
		borra();
		dibujoTeclado();
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
	
	private void dibujaAnimacion() {
		// Dibujo de animaciones de intentos ya hechos
		for (Combi combi : intentosHechos) {
			for (Elemento el : combi.getCombinacion()) {
				if (el instanceof Animable) {
					((Animable)el).dibuja();
				}
			}
		}
	}
	
	private void dibujoTeclado() {
		dibujaLinea( 0, yTeclado, getAnchura(), yTeclado, 2f, Color.BLACK );
		int yLinea = yTeclado + pixelsMargen;
		ArrayList<Elemento> lTeclas = new ArrayList<Elemento>( teclado );
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
		// Mouse over
		if (teclaConMouseOver != null) {
			teclaConMouseOver.dibujaInverso();
		}
	}
	
	private int xDeCasilla( int columna ) {
		return xTablero + columna * (tamElemento + pixelsMargen);
	}

	private int yDeFila( int fila ) {
		return yTablero + fila * (tamElemento + pixelsMargen);
	}
	
	

}

package tema3.basicos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import tema3.basicos.utils.PruebaEstructurasPersonaVentana;
import utils.ventanas.ventanaBitmap.VentanaGrafica;
import utils.ventanas.ventanaConsola.ConsolaVentana;

/** Clase de exploración de Java Collections
 * Visualización de comportamiento de hash
 * Visualización de comportamiento de tree
 * Tiempo de ejecución de las estructuras al tener tamaño MUY GRANDE
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public class ExploracionJC {

	private static final int TAMANYO = 700_000; 
	private static final int VECES = 1_000;  // Para repetir muchas veces un proceso para poder medir diferencias 
	private static final Font FONT = new Font( "Arial", Font.PLAIN, 24 ); 
	private static final Font FONT_INDS = new Font( "Arial", Font.PLAIN, 14 ); 
	private static long time;  // Para medición de tiempo
	
	public static void main(String[] args) {
		JFrame f = new JFrame( "Vista de estructuras" );
		f.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		f.setSize( 320, 200 );
		String[] datos = { "Explorar hash", "Explorar tree", "BST balanceado y no", "Tiempos collections", "Dif. visual de tiempos" };
		JList<String> lista = new JList<>( datos );
		f.add( lista, BorderLayout.CENTER );
		lista.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount()>1) {
					(new Thread() {
						public void run() {
							if ("Explorar hash".equals(lista.getSelectedValue())) {
								visualHash();
							} else if ("Explorar tree".equals(lista.getSelectedValue())) {
								visualizacionBST();
							} else if ("Tiempos collections".equals(lista.getSelectedValue())) {
								ConsolaVentana.lanzarConsolaOutEnVentana();
								pruebasTiemposJC();
							} else if ("Dif. visual de tiempos".equals(lista.getSelectedValue())) {
								PruebaEstructurasPersonaVentana.main(null);
							} else if ("BST balanceado y no".equals(lista.getSelectedValue())) {
								ExploracionJC.BST.visualizarBSTs();
							}
						}
					}).start();
				}
			}
		});
		f.setVisible( true );
	}
	
	private static void visualHash() {
		VentanaGrafica vent = new VentanaGrafica( 1200, 600, "Visualización de hashsets" );
		vent.setDibujadoInmediato( false );
		HashSet<String> hashset = new HashSet<>();
		vent.setMensaje( "Pulsa tecla para avanzar" );
		for (int i=0; i<250; i++) {
			if (vent.estaCerrada()) {
				break;
			}
			String valorNuevo = i + "-" + ((int) (Math.random()*1000));
			hashset.add( valorNuevo );  // String aleatorio siempre distinto
			if (i < 50) {  // A partir de 50 ya va en automático
				dibujaEstructuraHash( vent, hashset, true, valorNuevo );
				vent.espera( 250 );
				while (vent.getCodTeclaQueEstaPulsada()==0) {
					vent.espera( 10 );
				}
			} else {
				vent.setMensaje( " " );
				dibujaEstructuraHash( vent, hashset, false, null );
				vent.espera( 100 );
			}
		}
		vent.acaba();
	}
		// Método de dibujado simbólico de una estructura de tabla hash de una variable hashset
		// Utiliza reflectividad (el hashset no expone sus atributos, hay que hacer "trampitas" para acceder a ellos)
		private static void dibujaEstructuraHash( VentanaGrafica vent, HashSet<String> hashset, boolean dibujaEstructura, String valorNuevo ) {
			vent.borra();
			int y = 200;
			int xInicio = 10;
			int xFin = vent.getAnchura() - 10;
			try {
				// Código de acceso interno particular a la estructura de un HashSet
				Field field = hashset.getClass().getDeclaredField("map");
				field.setAccessible( true );
				Object mapa = field.get( hashset );
				Field field2 = mapa.getClass().getDeclaredField("table");
				field2.setAccessible( true );
				Object[] arrayNodosHash = (Object[]) field2.get( mapa );
				int tamanyoHashtable = arrayNodosHash.length;
				double anchoCasilla = 1.0 * (xFin-xInicio) / tamanyoHashtable;
				vent.dibujaTextoCentrado( 5, 5, vent.getAnchura(), 30, "Hash con " + hashset.size() + " elementos, tamaño de tabla " + tamanyoHashtable , FONT, Color.BLACK );
				if (dibujaEstructura) {
					vent.dibujaLinea( xInicio, y-anchoCasilla, xFin, y-anchoCasilla, 1f, Color.GRAY );
					for (int i=0; i<tamanyoHashtable; i++) {
						vent.dibujaTextoCentrado( xInicio + i*anchoCasilla, y-anchoCasilla-30, anchoCasilla, 30, "" + i, FONT_INDS, Color.BLACK );
						vent.dibujaLinea( xInicio + i*anchoCasilla, y-anchoCasilla, xInicio + i*anchoCasilla, y+anchoCasilla, 1f, Color.GRAY );
					}
					vent.dibujaLinea( xInicio + tamanyoHashtable*anchoCasilla, y-anchoCasilla, xInicio + tamanyoHashtable*anchoCasilla, y+anchoCasilla, 1f, Color.GRAY );
				}
				for (int i=0; i<tamanyoHashtable; i++) {
					// System.out.print( " [" + i + "] " + arrayNodosHash[i] );
					int elementos = 0;
					if (arrayNodosHash[i]!=null) {
						Object nodo = arrayNodosHash[i];
						double saltoY = anchoCasilla/2.0*1.5;
						int num = 0;
						do {
							elementos++;
							Field field3 = nodo.getClass().getDeclaredField("next");
							Field fieldValor = nodo.getClass().getDeclaredField("key");
							Field fieldHash = nodo.getClass().getDeclaredField("hash");
							field3.setAccessible( true );
							Object nodoAnt = nodo;
							nodo = field3.get( nodo );
							if (valorNuevo!=null) {
								fieldValor.setAccessible( true );
								fieldHash.setAccessible( true );
								Object valor = fieldValor.get( nodoAnt );
								int hashCode = (Integer) fieldHash.get( nodoAnt );
								if (valor!=null && valor instanceof String) {
									double xCentro = xInicio + anchoCasilla*i + anchoCasilla/2;
									vent.dibujaTextoCentrado( xCentro-anchoCasilla/2, y-anchoCasilla/2+saltoY*num, anchoCasilla, anchoCasilla, (String)valor, FONT, (num==0 ? Color.BLUE : Color.RED), true );
									if (((String)valor).equals(valorNuevo)) {
										vent.dibujaTextoCentrado( xCentro-anchoCasilla/2-100, 400, anchoCasilla+200, 40, "" + hashCode, FONT, Color.GREEN, true );
										vent.dibujaTextoCentrado( xCentro-anchoCasilla/2-100, 440, anchoCasilla+200, 40, "r" + tamanyoHashtable + ": " + (hashCode%tamanyoHashtable), FONT, Color.GREEN, true );
									}
								}
								num++;
							}
						} while (nodo!=null);
						// System.out.println( "  colisiones: " + (elementos-1) );
					} else {
						// System.out.println();
					}
					// La posición i tiene "elementos" elementos
					if (elementos > 0) {
						double radio = anchoCasilla/2;
						double saltoY = radio*1.5;
						if (radio < 1) radio = 1;
						double xCentro = xInicio + anchoCasilla*i + anchoCasilla/2;
						vent.dibujaCirculo( xCentro, y, radio, 1f, Color.BLUE );
						for (int j=1; j<elementos; j++) {
							vent.dibujaCirculo( xCentro, y + saltoY*j, radio, 1f, Color.RED );
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			vent.repaint();
		}

	private static void pruebasTiemposJC() {
		
		initTiempo();
		ArrayList<String> arraylist = new ArrayList<>();
		for (int i=0; i<TAMANYO; i++) {
			arraylist.add( "Dato" + i );
		}
		sacaTiempo( "Cargar " + TAMANYO + " strings diferentes en un arraylist" );
		
		initTiempo();
		LinkedList<String> linkedlist = new LinkedList<>();
		for (int i=0; i<TAMANYO; i++) {
			linkedlist.add( "Dato" + i );
		}
		sacaTiempo( "Cargar " + TAMANYO + " strings diferentes en un linkedlist" );

		initTiempo();
		HashSet<String> hashset = new HashSet<>();
		for (int i=0; i<TAMANYO; i++) {
			hashset.add( "Dato" + i );
		}
		sacaTiempo( "Cargar " + TAMANYO + " strings diferentes en un hashset" );
		
		initTiempo();
		TreeSet<String> treeset = new TreeSet<>();
		for (int i=0; i<TAMANYO; i++) {
			treeset.add( "Dato" + i );
		}
		sacaTiempo( "Cargar " + TAMANYO + " strings diferentes en un treeset" );
		
		initTiempo();
		HashSet<String> hashset2 = new HashSet<>();
		for (int i=0; i<TAMANYO; i++) {
			hashset2.add( "Dato" + (i%100) );
		}
		sacaTiempo( "Cargar " + TAMANYO + " strings -solo 100 diferentes- en un hashset" );
		
		initTiempo();
		TreeSet<String> treeset2 = new TreeSet<>();
		for (int i=0; i<TAMANYO; i++) {
			treeset2.add( "Dato" + (i%100) );
		}
		sacaTiempo( "Cargar " + TAMANYO + " strings -solo 100 diferentes- en un treeset" );

		System.out.println();
		initTiempo();
		arraylist = new ArrayList<>();
		for (int i=0; i<TAMANYO; i++) {
			arraylist.add( 0, "Dato" + i );
		}
		sacaTiempo( "Cargar " + TAMANYO + " strings en arraylist insertando por el inicio" );
		
		initTiempo();
		linkedlist = new LinkedList<>();
		for (int i=0; i<TAMANYO; i++) {
			linkedlist.add( 0, "Dato" + i );
		}
		sacaTiempo( "Cargar " + TAMANYO + " strings en linkedlist insertando por el inicio" );

		System.out.println();
		initTiempo();
		boolean busq = false;
		for (int i=0; i<VECES; i++) {
			busq = busq || arraylist.contains( "Dato no existente" );
		}
		sacaTiempo( "Buscar " + VECES + " veces string no existente en un arraylist de tamaño " + TAMANYO + "? " + busq );
		
		initTiempo();
		for (int i=0; i<VECES; i++) {
			busq = busq || linkedlist.contains( "Dato no existente" );
		}
		sacaTiempo( "Buscar " + VECES + " veces string no existente en un linkedlist de tamaño " + TAMANYO + "? " + busq );
		
		initTiempo();
		for (int i=0; i<VECES; i++) {
			busq = busq || hashset.contains( "Dato no existente" );
		}
		sacaTiempo( "Buscar " + VECES + " veces string no existente en un hashset de tamaño " + hashset.size() + "? " + busq );
		
		initTiempo();
		for (int i=0; i<VECES; i++) {
			busq = busq || treeset.contains( "Dato no existente" );
		}
		sacaTiempo( "Buscar " + VECES + " veces string no existente en un treeset de tamaño " + treeset.size() + "? " + busq );
		
		initTiempo();
		for (int i=0; i<VECES; i++) {
			busq = busq || hashset2.contains( "Dato no existente" );
		}
		sacaTiempo( "Buscar " + VECES + " veces string no existente en un hashset de tamaño " + hashset2.size() + "? " + busq );
		
		initTiempo();
		for (int i=0; i<VECES; i++) {
			busq = busq || treeset2.contains( "Dato no existente" );
		}
		sacaTiempo( "Buscar " + VECES + " veces string no existente en un treeset de tamaño " + treeset2.size() + "? " + busq );
		
		System.out.println();
		int numDatoMedio = TAMANYO/2;
		initTiempo();
		busq = true;
		for (int i=0; i<VECES; i++) {
			busq = busq && arraylist.contains( "Dato" + numDatoMedio );
		}
		sacaTiempo( "Buscar " + VECES + " veces string existente en un arraylist de tamaño " + TAMANYO + "? " + busq );
		
		initTiempo();
		for (int i=0; i<VECES; i++) {
			busq = busq && linkedlist.contains( "Dato" + numDatoMedio );
		}
		sacaTiempo( "Buscar " + VECES + " veces string existente en un linkedlist de tamaño " + TAMANYO + "? " + busq );
		
		initTiempo();
		for (int i=0; i<VECES; i++) {
			busq = busq && hashset.contains( "Dato1" );
		}
		sacaTiempo( "Buscar " + VECES + " veces string existente en un hashset de tamaño " + hashset.size() + "? " + busq );
		
		initTiempo();
		for (int i=0; i<VECES; i++) {
			busq = busq && treeset.contains( "Dato1" );
		}
		sacaTiempo( "Buscar " + VECES + " veces string existente en un treeset de tamaño " + treeset.size() + "? " + busq );
		
		initTiempo();
		for (int i=0; i<VECES; i++) {
			busq = busq && hashset2.contains( "Dato1" );
		}
		sacaTiempo( "Buscar " + VECES + " veces string existente en un hashset de tamaño " + hashset2.size() + "? " + busq );
		
		initTiempo();
		for (int i=0; i<VECES; i++) {
			busq = busq && treeset2.contains( "Dato1" );
		}
		sacaTiempo( "Buscar " + VECES + " veces string existente en un treeset de tamaño " + treeset2.size() + "? " + busq );
	}
	
	private static void initTiempo() {
		time = System.nanoTime();
	}
	
	private static void sacaTiempo( String descripcion ) {
		System.out.println( String.format( "%90s: %,14d nsegs.", "Tiempo " + descripcion, (System.nanoTime() - time) ) );
	}
	
	
	//----------------------------------------------------------
	//----------------------------------------------------------
	// Idea visual de BST 
	//----------------------------------------------------------
	//----------------------------------------------------------
	
	private static int OFFSET_X = 25;
	private static int ANCHO_NODO = 30;
	private static int ALTO_NODO = 16;
	private static int NUM_NODOS = 31;
	private static int Y_PARTIDA = 300;
	private static int Y_POR_NIVEL = 30;
	private static Font TIPO_LETRA = new Font( "Arial", Font.PLAIN, 12 );
	
	public static void visualizacionBST() {
		// Creación de ventana gráfica
		final VentanaGrafica v = new VentanaGrafica( 1000, 800, "Concepto de BST - Búsqueda binaria" );
		v.setDibujadoInmediato( false );
		// Creación de árbol equilibrado con posición plana
		final BST<NumeroConPosicion> bst = new BST<NumeroConPosicion>();
		init( bst, NUM_NODOS, ANCHO_NODO );
		// Evento de redimensión de ventana
		v.addComponentListener( new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				visualizaBST( bst, v, ANCHO_NODO, ALTO_NODO );
			}
		});
		// Visualización de árbol
		visualizaBST( bst, v, ANCHO_NODO, ALTO_NODO );
		// Animación
		// 1.- Construye la primera lista de nodos a animar (el del medio)
		ArrayList<NumeroConPosicion> nodosAnimAnts = new ArrayList<NumeroConPosicion>();
		ArrayList<NumeroConPosicion> nodosAnim = new ArrayList<NumeroConPosicion>();
		NumeroConPosicion ncp = bst.get( new NumeroConPosicion(NUM_NODOS/2, 0, 0) );
		nodosAnim.add( ncp );
		do {
			System.out.println( "Animando los nodos " + nodosAnim );
			System.out.println( "    (Esperando a pulsación de barra espaciadora)" );
			while (!v.isTeclaPulsada( KeyEvent.VK_SPACE ));
			// 2.- Anima los nodos indicados
			for (int i=0; i<Y_POR_NIVEL; i++) {
				for (NumeroConPosicion nodo : nodosAnim) nodo.y--;
				for (NumeroConPosicion nodo : nodosAnimAnts) nodo.y--;
				visualizaBST( bst, v, ANCHO_NODO, ALTO_NODO );
				v.espera( 25 );
			}
			// 3.- Calcula los próximos nodos a animar (los referenciados por los anteriores)
			int espacio = nodosAnim.get(0).numero / 2;
			if (espacio<1) { // Si los nodos son los últimos ya no se animan
				nodosAnim.clear();
			} else {
				ArrayList<NumeroConPosicion> nuevos = new ArrayList<>();
				for (NumeroConPosicion nodo : nodosAnim) {
					ncp = bst.get( new NumeroConPosicion(nodo.numero-espacio-1, 0, 0) );
					nuevos.add( ncp );
					ncp = bst.get( new NumeroConPosicion(nodo.numero+espacio+1, 0, 0) );
					nuevos.add( ncp );
				}
				nodosAnimAnts.addAll( nodosAnim );
				nodosAnim = nuevos;
			}
		} while (!nodosAnim.isEmpty());  // Hasta que los nodos a animar sean los últimos
	}

	private static void init( BST<NumeroConPosicion> bst, int tam, int anchNodo ) {
		initRec( bst, anchNodo, 0, tam-1 );  // Inicializa un árbol equilibradamente - insertando siempre antes el punto medio
	}
		private static void initRec( BST<NumeroConPosicion> bst, int anchNodo, int desde, int hasta ) {
			if (desde>hasta) {
				// Caso base vacío
			} else if (desde==hasta) {
				bst.insertar( new NumeroConPosicion( desde, anchNodo*desde, Y_PARTIDA ) );  // Caso base inserción hoja
			} else {
				int medio = (desde+hasta) / 2;
				bst.insertar( new NumeroConPosicion( medio, anchNodo*medio, Y_PARTIDA ) );
				initRec( bst, anchNodo, desde, medio-1 );
				initRec( bst, anchNodo, medio+1, hasta );
			}
		}

	
	/** Visualiza un árbol esquemáticamente en una ventana gráfica
	 * @param bst	BST a visualizar
	 * @param v	Ventana donde visualizarlo
	 * @param anchoNodo	Anchura de cada nodo en píxels
	 * @param altoNodo	Altura de cada nodo en píxels
	 */
	private static void visualizaBST( BST<NumeroConPosicion> bst, VentanaGrafica v, int anchoNodo, int altoNodo ) {
		v.borra();
		v.repaint();
		visualizaBSTRec( bst.raiz, v, anchoNodo, altoNodo );
		v.repaint();
	}
		private static void visualizaBSTRec( NodoBST<NumeroConPosicion> nodo, VentanaGrafica v, int anchoNodo, int altoNodo ) {
			if (nodo==null) return;
			visualizaBSTRec( nodo.izquierdo, v, anchoNodo, altoNodo );
			visualizaBSTRec( nodo.derecho, v, anchoNodo, altoNodo );
			if (nodo.izquierdo!=null) {
				if (nodo.izquierdo.elemento.y > nodo.elemento.y)
					v.dibujaFlecha( OFFSET_X + nodo.elemento.x + anchoNodo/2, nodo.elemento.y + altoNodo/2, 
						OFFSET_X + nodo.izquierdo.elemento.x + anchoNodo/2, nodo.izquierdo.elemento.y, 
						0.75f, Color.BLUE, 5 );
			}
			if (nodo.derecho!=null) {
				if (nodo.derecho.elemento.y > nodo.elemento.y)
					v.dibujaFlecha( OFFSET_X + nodo.elemento.x + anchoNodo/2, nodo.elemento.y + altoNodo/2, 
						OFFSET_X + nodo.derecho.elemento.x + anchoNodo/2, nodo.derecho.elemento.y, 
						0.75f, Color.BLUE, 5 );
			}
			v.dibujaRect( OFFSET_X+nodo.elemento.x, nodo.elemento.y, anchoNodo, altoNodo, 1.5f, Color.GREEN, Color.WHITE );
			v.dibujaTexto( OFFSET_X+nodo.elemento.x + 5, nodo.elemento.y + ALTO_NODO - 3, nodo.elemento.numero+"", TIPO_LETRA, Color.BLACK );
		}
		
	private static class NumeroConPosicion implements Comparable<NumeroConPosicion> {
		int numero;
		int x;
		int y;
		public NumeroConPosicion(int numero, int x, int y) {
			super();
			this.numero = numero;
			this.x = x;
			this.y = y;
		}
		@Override
		public int compareTo(NumeroConPosicion o) {
			return numero - o.numero;
		}
		@Override
		public String toString() {
			return numero+"";
		}
	}

	
	public static class BST<T extends Comparable<T>> {
		NodoBST<T> raiz;
		
		@SuppressWarnings("unchecked")
		public void insertar( T... nuevos ) {
			for (T nuevo : nuevos) insertarRec( null, raiz, nuevo );
		}
		public void insertar( T nuevo ) {
			insertarRec( null, raiz, nuevo );
		}
			private void insertarRec( NodoBST<T> padre, NodoBST<T> bst, T nuevo ) {
				if (bst==null) {  // Caso base: hay que insertar
					NodoBST<T> nuevoNodo = new NodoBST<T>( nuevo, null, null );
					if (padre==null) // Insertar en raíz
						raiz = nuevoNodo;
					else if (padre.elemento.compareTo(nuevo)<0)
						padre.derecho = nuevoNodo;
					else
						padre.izquierdo = nuevoNodo;
				} else {  // Caso general
					int comp = (bst.elemento.compareTo(nuevo));
					if (comp==0) // Caso base: elemento ya existe -se podría insertar iz o de pero siempre igual. O no insertar si no se permiten repeticiones (es lo que hacemos ahora)
						; // Nada que hacer - retorno
					else if (comp<0) // Insertar a la derecha
						insertarRec( bst, bst.derecho, nuevo );
					else
						insertarRec( bst, bst.izquierdo, nuevo );
				}
			}
			
		/** Busca un elemento en el árbol
		 * @param aBuscar	Elemento a buscar
		 * @return	true si está, false en caso contrario
		 */
		@SuppressWarnings("unused")
		public boolean contains( T aBuscar ) {
			return contains( raiz, aBuscar );
		}
			private boolean contains( NodoBST<T> nodo, T aBuscar ) {
				if (nodo==null) {
					return false;
				} else if (nodo.elemento.compareTo( aBuscar ) == 0) {
					return true;
				} else if (nodo.elemento.compareTo( aBuscar ) < 0) {
					return contains( nodo.derecho, aBuscar ); 
				} else {
					return contains( nodo.izquierdo, aBuscar );
				}
			}
		
		/** Busca y recupera un elemento en el árbol
		 * @param aBuscar	Elemento a buscar
		 * @return	el elemento del árbol si está, null en caso contrario
		 */
		public T get( T aBuscar ) {
			return get( raiz, aBuscar );
		}
			private T get( NodoBST<T> nodo, T aBuscar ) {
				if (nodo==null) {
					return null;
				} else if (nodo.elemento.compareTo( aBuscar ) == 0) {
					return nodo.elemento;
				} else if (nodo.elemento.compareTo( aBuscar ) < 0) {
					return get( nodo.derecho, aBuscar ); 
				} else {
					return get( nodo.izquierdo, aBuscar );
				}
			}
		
		public void recorrerInOrden( Consumer<T> c ) {
			recorrerIOrec( raiz, c );
		}
			private void recorrerIOrec( NodoBST<T> nodo, Consumer<T> c ) {
				if (nodo!=null) {   // Si no caso base
					marca( nodo, true );  // Marca visual de recorrido (solo para ejecutar paso a paso e ir viendo lo que ocurre: no afecta al algoritmo)
					recorrerIOrec( nodo.izquierdo, c );
					c.accept( nodo.elemento );
					recorrerIOrec( nodo.derecho, c );
					marca( nodo, false );  // Acaba marca visual de recorrido
				}
			}
		
		public void recorrerPreOrden( Consumer<T> c ) {
			recorrerPOrec( raiz, c );
		}
			private void recorrerPOrec( NodoBST<T> nodo, Consumer<T> c ) {
				if (nodo!=null) {   // Si no caso base
					c.accept( nodo.elemento );
					recorrerPOrec( nodo.izquierdo, c );
					recorrerPOrec( nodo.derecho, c );
				}
			}
		
		public void recorrerPostOrden( Consumer<T> c ) {
			recorrerPtOrec( raiz, c );
		}
			private void recorrerPtOrec( NodoBST<T> nodo, Consumer<T> c ) {
				if (nodo!=null) {   // Si no caso base
					recorrerPtOrec( nodo.izquierdo, c );
					recorrerPtOrec( nodo.derecho, c );
					c.accept( nodo.elemento );
				}
			}
		
		public void recorrerAnchura( Consumer<T> c ) {
			for (int nivel = 0; nivel<altura(); nivel++) 
				recorrerAncrec( raiz, c, nivel );
		}
			private void recorrerAncrec( NodoBST<T> nodo, Consumer<T> c, int nivel ) {
				if (nodo!=null && nivel>=0) {   // Si no caso base
					recorrerAncrec( nodo.izquierdo, c, nivel-1 );
					if (nivel==0)
						c.accept( nodo.elemento );
					recorrerAncrec( nodo.derecho, c, nivel-1 );
				}
			}
			
		public int altura() {
			return alturaRec( raiz );
		}
			public int alturaRec( NodoBST<T> bst ) {
				if (bst==null)
					return 0;
				else {
					return 1 + Math.max( alturaRec( bst.izquierdo ), alturaRec( bst.derecho ) );
				}
			}
			
		@SuppressWarnings("unused")
		public int size() {
			return sizeRec( raiz );
		}
			private int sizeRec( NodoBST<T> nodo ) {
				if (nodo==null)
					return 0;
				else
					return 1 + sizeRec( nodo.izquierdo ) + sizeRec( nodo.derecho );
			}

			private volatile ArrayList<StringBuffer> lineas;
		@Override
		public String toString() {
			lineas = new ArrayList<>(); lineas.add( new StringBuffer("") );
			toStringRec( raiz, 0 );
			String ret = "";
			for (StringBuffer linea : lineas) if (!linea.toString().isEmpty()) ret += (linea + "\n");
			return ret;
		}
			private void toStringRec( NodoBST<T> nodo, int nivel ) {
				if (nodo!=null) {   // Si no caso base
					if (lineas.size() <= nivel+1) lineas.add( new StringBuffer("") ); 
					toStringRec( nodo.izquierdo, nivel+1 );
					int largoInferior = lineas.get(nivel+1).length();
					toStringRec( nodo.derecho, nivel+1 );
					rellenaEspacios( nivel, largoInferior, lineas.get(nivel+1).length(), nodo.elemento.toString() );
				}
			}
			private void rellenaEspacios( int nivel, int ancho1, int ancho2, String elem ) {
				int faltanEspacios = (ancho1 + ancho2) / 2;
				faltanEspacios = faltanEspacios - lineas.get(nivel).length();
				for (int i=0; i<faltanEspacios-1; i++) lineas.get(nivel).append( " " );
				lineas.get(nivel).append( elem ); lineas.get(nivel).append( " " );
			}
		
		private static BST<Integer>.VentanaArbol v;
		public static void visualizarBSTs() {
			BST<Integer> bst = new BST<>();
			bst.insertar( 6, 4, 9, 2, 5, 7, 10, 1, 3, 8, 11 );
			// Si se quiere sacarlo en consola: System.out.print( bst );
			// Se saca dibujado en ventana
			v = bst.new VentanaArbol();
			v.setAlwaysOnTop(true);
			v.setVisible( true );
			System.out.println( "Altura árbol: " + bst.altura() );
//			System.out.print( "Recorrido árbol inorden = { ");
//			bst.recorrerInOrden( (Integer i) -> { System.out.print( i + " " ); } );
//			System.out.println( "}");
//			System.out.print( "Recorrido árbol preorden = { ");
//				bst.recorrerPreOrden( (Integer i) -> { System.out.print( i + " " ); } );
//			System.out.println( "}");
//			System.out.print( "Recorrido árbol postorden = { ");
//				bst.recorrerPostOrden( (Integer i) -> { System.out.print( i + " " ); } );
//			System.out.println( "}");
//			System.out.print( "Recorrido árbol anchura = { ");
//				bst.recorrerAnchura( (Integer i) -> { System.out.print( i + " " ); } );
//			System.out.println( "}");
			System.out.println();
			System.out.println( "Obsérvese la diferencia entre los dos árboles" );
			bst = new BST<>();
			bst.insertar( 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 );
			// Esto cargaría un árbol aleatorio Random r = new Random(); for (int i=0; i<100; i++) { bst.insertar( r.nextInt(100)); }
			v = bst.new VentanaArbol();
			v.setLocation( 200, 0 );
			v.setAlwaysOnTop(true);
			v.setVisible( true );
			System.out.println( "Altura árbol: " + bst.altura() );
//			System.out.print( "Recorrido árbol inorden = { ");
//			bst.recorrerInOrden( (Integer i) -> { System.out.print( i + " " ); } );
//			System.out.println( "}");
//			System.out.print( "Recorrido árbol preorden = { ");
//				bst.recorrerPreOrden( (Integer i) -> { System.out.print( i + " " ); } );
//			System.out.println( "}");
//			System.out.print( "Recorrido árbol postorden = { ");
//				bst.recorrerPostOrden( (Integer i) -> { System.out.print( i + " " ); } );
//			System.out.println( "}");
//			System.out.print( "Recorrido árbol anchura = { ");
//				bst.recorrerAnchura( (Integer i) -> { System.out.print( i + " " ); } );
//			System.out.println( "}");
		}
		
		private void marca( NodoBST<T> nodo, boolean marca ) {
			nodo.marca = marca;
			v.repaint();
		}
		
		/** Ventana que visualiza el árbol con los valores que tenga en cada momento
		 * @author andoni.eguiluz @ ingenieria.deusto.es
		 */
		@SuppressWarnings("serial")
		public class VentanaArbol extends JFrame {
			private int ANCH_NODO = 60;  // Píxels por nodo
			private int ANCH_REF = 15;  // Píxels por referencia (puntero del nodo)
			private int ALT_NODO = 20;  // Píxels alto por nodo
			private int ALT_NIVEL = 40;  // Píxels entre nivel y nivel del árbol
			private int MIN_LATERAL = 10;  // Píxels mínimos entre nodos 
			private int ALT_TEXTO = 15;  // Píxels alto de texto
			private Font font = new Font( "Arial", Font.PLAIN, 14 );
			VentanaArbol() {
				super( "Visualización de BST" );
				setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
				setSize( 800, 600 );
				add( new JPanel() {
					{
						setBackground( Color.white );
					}
					@Override
					protected void paintComponent(Graphics g) {
						super.paintComponent(g);
						dibujaRec( raiz, (Graphics2D) g, 20, 20, new ArrayList<Integer>(), 0, new Point(-1,-1) );
					}
					private int dibujaRec( NodoBST<T> nodo, Graphics2D g, int xActual, int yActual, ArrayList<Integer> xNiveles, int nivel, Point pDibujado ) {
						if (nodo==null) return xActual; // Caso base
						// Dibuja recursivamente por la izquierda
						xActual = dibujaRec( nodo.izquierdo, g, xActual, yActual + ALT_NIVEL, xNiveles, nivel+1, pDibujado );
						// Calcula x de nodo raíz
						if (xNiveles.size()>nivel && xNiveles.get(nivel)>xActual) xActual = xNiveles.get(nivel);  // Calcula x de dibujado para que no haya solapamiento
						if (nodo.izquierdo!=null && pDibujado.x + ANCH_NODO/2 + MIN_LATERAL/2 > xActual) xActual = pDibujado.x + ANCH_NODO/2 + MIN_LATERAL/2;  // Calcula x de dibujado para que las flechas izquierdas al hijo queden siempre hacia el lado izquierdo
						while (xNiveles.size()<=nivel) xNiveles.add( new Integer(0) );
						if (nivel>0 && xNiveles.get(nivel-1)-ANCH_NODO/2-MIN_LATERAL/2 > xActual) xActual = xNiveles.get(nivel-1)-ANCH_NODO/2-MIN_LATERAL/2;  // Calcula x de dibujado para que las flechas derechas al hijo queden siempre hacia el lado derecho
						int miX = xActual;  // Guarda x donde se dibujará el nodo
						xNiveles.set( nivel, miX + ANCH_NODO + MIN_LATERAL );
						int xDibujadoIzq = pDibujado.x;  // Guarda x del nodo dibujado por la izquierda
						// Dibuja recursivamente por la derecha
						xActual = dibujaRec( nodo.derecho, g, xActual + MIN_LATERAL, yActual + ALT_NIVEL, xNiveles, nivel+1, pDibujado );
						// Dibuja nodo y enlaces
						if (nodo.marca) g.setColor( Color.YELLOW ); else g.setColor( Color.WHITE );  // Fondo del nodo (si está marcado, amarillo)
						g.fillRect(miX, yActual, ANCH_NODO, ALT_NODO );
						g.setColor( Color.BLACK );
						g.drawRect( miX, yActual, ANCH_NODO, ALT_NODO );
						g.drawRect( miX, yActual, ANCH_REF, ALT_NODO );
						g.drawRect( miX + ANCH_NODO - ANCH_REF, yActual, ANCH_REF, ALT_NODO );
						g.setColor( Color.BLUE );
						if (nodo.izquierdo==null) g.drawLine( miX, yActual + ALT_NODO, miX + ANCH_REF, yActual );
						else dibuFlecha( g, miX + ANCH_REF/2, yActual + ALT_NODO/2, xDibujadoIzq + ANCH_NODO/2, pDibujado.y );
						if (nodo.derecho==null) g.drawLine( miX + ANCH_NODO - ANCH_REF, yActual + ALT_NODO, miX + ANCH_NODO, yActual );
						else dibuFlecha( g, miX + ANCH_NODO - ANCH_REF/2, yActual + ALT_NODO/2, pDibujado.x + ANCH_NODO/2, pDibujado.y );
						g.setColor( Color.MAGENTA );
						drawStringCentrado( g, nodo.elemento.toString(), new Rectangle( miX + ANCH_REF, yActual, ANCH_NODO-2*ANCH_REF, ALT_TEXTO ), font );
						// Calcula punto a devolver
						pDibujado.setLocation( miX, yActual );
						return xActual;
					}
					private void dibuFlecha( Graphics2D g, int x1, int y1, int x2, int y2 ) {
						g.drawLine(x1, y1, x2, y2);
						g.fillOval(x1-3, y1-3, 6, 6);
						double ang = Math.atan2( y1-y2, x1-x2 );
						int x3 = (int) Math.round(x2 + 10*Math.cos(ang+0.3));
						int y3 = (int) Math.round(y2 + 10*Math.sin(ang+0.3));
						int x4 = (int) Math.round(x2 + 10*Math.cos(ang-0.3));
						int y4 = (int) Math.round(y2 + 10*Math.sin(ang-0.3));
						g.fillPolygon( new int[]{x2,x3,x4}, new int[]{y2,y3,y4}, 3 );
					}
					private void drawStringCentrado(Graphics g, String text, Rectangle rect, Font font) {
					    FontMetrics metrics = g.getFontMetrics(font); // Mirar las métricas del tipo de letra
					    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2; // Coordenada X centrada para el texto
					    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent() + 4; // Idem y (añadimos ascent y un offset para que no se pegue arriba)
						g.setFont( font );
					    g.drawString(text, x, y);
					}
				});
			}
		}
	}

	private static class NodoBST<T extends Comparable<T>> {
		T elemento;
		NodoBST<T> izquierdo;
		NodoBST<T> derecho;
		boolean marca; // Atributo utilizado para marcar visualmente recorridos
		public NodoBST(T elemento, NodoBST<T> izquierdo, NodoBST<T> derecho) {
			this.elemento = elemento;
			this.izquierdo = izquierdo;
			this.derecho = derecho;
		}
		
	}
	
}

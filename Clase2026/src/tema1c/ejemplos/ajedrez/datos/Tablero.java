package tema1c.ejemplos.ajedrez.datos;

import utils.ventanas.ventanaBitmap.VentanaGrafica;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Representa el tablero de ajedrez.
 * Gestiona la colección de piezas y se encarga de dibujarse a sí mismo.
 */
public class Tablero extends ElementoVisual implements Iterable<Pieza> {

    /** Número de filas y columnas estándar de un tablero de ajedrez. */
    public static final int FILAS    = 8;
    public static final int COLUMNAS = 8;

    // Colección de piezas situadas sobre el tablero
    private ArrayList<Pieza> piezas;

    // Coordenadas de la esquina superior izquierda del tablero (píxeles)
    private int xInicio;
    private int yInicio;

    // -------------------------------------------------------------------------
    // Constructores
    // -------------------------------------------------------------------------

    /**
     * Constructor completo.
     *
     * @param xInicio coordenada x de la esquina superior izquierda (píxeles)
     * @param yInicio coordenada y de la esquina superior izquierda (píxeles)
     * @param ancho   anchura total del tablero en píxeles
     * @param alto    altura total del tablero en píxeles
     * @param ventana ventana gráfica donde se dibujará
     */
    public Tablero(int xInicio, int yInicio, int ancho, int alto, VentanaGrafica ventana) {
        // El centro del tablero es el punto medio de la esquina superior izquierda más las dimensiones
        super(xInicio + ancho / 2, yInicio + alto / 2, ancho, alto, ventana);
        this.xInicio = xInicio;
        this.yInicio = yInicio;
        this.piezas  = new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Getters y setters
    // -------------------------------------------------------------------------

    public ArrayList<Pieza> getPiezas() { return piezas; }

    public int getXInicio() { return xInicio; }
    public void setXInicio(int xInicio) {
        this.xInicio = xInicio;
        setX(xInicio + getAncho() / 2);
    }

    public int getYInicio() { return yInicio; }
    public void setYInicio(int yInicio) {
        this.yInicio = yInicio;
        setY(yInicio + getAlto() / 2);
    }

    // -------------------------------------------------------------------------
    // Gestión de piezas
    // -------------------------------------------------------------------------

    /**
     * Añade una pieza al tablero.
     *
     * @param pieza pieza a añadir (no puede ser null)
     */
    public void addPieza(Pieza pieza) {
        if (pieza != null) {
            piezas.add(pieza);
        }
    }

    /**
     * Elimina una pieza del tablero.
     *
     * @param pieza pieza a eliminar
     * @return true si la pieza estaba en el tablero y se ha eliminado
     */
    public boolean removePieza(Pieza pieza) {
        return piezas.remove(pieza);
    }

    /**
     * Devuelve la pieza que ocupa una casilla dada, o null si está vacía.
     *
     * @param fila    fila de la casilla (0-7)
     * @param columna columna de la casilla (0-7)
     * @return pieza en esa casilla, o null si no hay ninguna
     */
    public Pieza getPiezaEn(int fila, int columna) {
        for (Pieza p : piezas) {
            if (p.getFila() == fila && p.getColumna() == columna) {
                return p;
            }
        }
        return null;
    }

    /**
     * Indica si una casilla está ocupada.
     *
     * @param fila    fila (0-7)
     * @param columna columna (0-7)
     * @return true si hay alguna pieza en esa casilla
     */
    public boolean estaOcupada(int fila, int columna) {
        return getPiezaEn(fila, columna) != null;
    }

    /** Crea piezas de un juego de ajedrez estándar y las coloca en el tablero
     */
    public void crearYColocarPiezas() {
        for (int col = 0; col < 8; col++) {
            addPieza(crearPiezaMayor(col, 0, Color.NEGRA));
            addPieza(crearPiezaMayor(col, 7, Color.BLANCA));
            addPieza(new Peon(1, col, Color.NEGRA, this));
            addPieza(new Peon(6, col, Color.BLANCA, this));
        }
    }

    private Pieza crearPiezaMayor(int col, int fila, Color color) {
        switch (col) {
            case 0: case 7: return new Torre  (fila, col, color, this);
            case 1: case 6: return new Caballo(fila, col, color, this);
            case 2: case 5: return new Alfil  (fila, col, color, this);
            case 3:         return new Dama   (fila, col, color, this);
            case 4:         return new Rey    (fila, col, color, this);
            default: throw new IllegalArgumentException("Columna inesperada: " + col);
        }
    }

    
    
    // -------------------------------------------------------------------------
    // Coordenadas de casilla ↔ píxeles
    // -------------------------------------------------------------------------

    /**
     * Devuelve el tamaño en píxeles de cada casilla (cuadrada).
     */
    public int getTamanoCasilla() {
        return getAncho() / COLUMNAS;   // suponemos tablero cuadrado
    }

    /**
     * Calcula la coordenada x del centro de una casilla.
     *
     * @param columna columna (0-7)
     * @return x en píxeles del centro de esa columna
     */
    public int xCentroCasilla(int columna) {
        int tam = getTamanoCasilla();
        return xInicio + columna * tam + tam / 2;
    }

    /**
     * Calcula la coordenada y del centro de una casilla.
     *
     * @param fila fila (0-7)
     * @return y en píxeles del centro de esa fila
     */
    public int yCentroCasilla(int fila) {
        int tam = getTamanoCasilla();
        return yInicio + fila * tam + tam / 2;
    }

    // -------------------------------------------------------------------------
    // Dibujo
    // -------------------------------------------------------------------------

    /**
     * Dibuja el tablero (cuadrícula) y todas las piezas que contiene.
     */
    @Override
    public void dibujar() {
        VentanaGrafica v = getVentana();
        int tam = getTamanoCasilla();

        // Dibujar las 64 casillas alternando colores
        for (int fila = 0; fila < FILAS; fila++) {
            for (int col = 0; col < COLUMNAS; col++) {
                int px = xInicio + col * tam;
                int py = yInicio + fila * tam;

                // Casilla clara u oscura según paridad
                java.awt.Color colorCasilla = ((fila + col) % 2 == 0) ? java.awt.Color.WHITE : new java.awt.Color(139, 90, 43);
                v.dibujaRect(px, py, tam, tam, 1.0f, java.awt.Color.BLACK, colorCasilla);
            }
        }

        // Dibujar todas las piezas
        for (Pieza p : piezas) {
            p.dibujar();
        }
    }

    // -------------------------------------------------------------------------
    // toString / equals
    // -------------------------------------------------------------------------

    @Override
    public String toString() {
        return "Tablero[xInicio=" + xInicio + ", yInicio=" + yInicio
                + ", ancho=" + getAncho() + ", alto=" + getAlto()
                + ", numPiezas=" + piezas.size() + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        Tablero other = (Tablero) obj;
        return xInicio == other.xInicio && yInicio == other.yInicio;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + xInicio;
        result = 31 * result + yInicio;
        return result;
    }
    
    
    // Interfaz Iterable
    
	@Override
	public Iterator<Pieza> iterator() {
		return new TableroIterator();
	}


	// ---------- CLASE INTERNA ------------
	// Clase local - interna - se asocia a la clase externa 
	// por eso puede acceder a la instancia (this) de tablero --> para accceder a las piezas
	// Podría ser externa, pero en ese caso necesitaría tener también el atributo de piezas 
	// para poder iterar sobre ellas
	
	private class TableroIterator implements Iterator<Pieza> {
		int pos = 0;
		@Override
		public boolean hasNext() {
			return pos<piezas.size();
		}
		@Override
		public Pieza next() {
			Pieza ret = piezas.get(pos);
			pos++;
			return ret;
		}
	}    
    
}
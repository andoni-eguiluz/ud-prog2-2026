package tema1c.ejemplos.ajedrez.datos;

import java.awt.Point; // INTERFACES

/**
 * Clase abstracta que representa una pieza de ajedrez genérica.
 * Cada pieza concreta deberá implementar {@link #moverA(int, int)},
 * {@link #puedeMoverA(int, int)} y {@link #dibujar()}.
 * 
 * ESTADO DELEGADO PARA TIEMPO REAL:
 *  - opacidad (float):      opacidad actual con la que se dibuja la imagen (0..1).
 *                           El bucle principal la actualiza cada fotograma.
 *  - xPixel / yPixel (double): posición continua en píxeles. Permite que el bucle
 *                           principal anime el desplazamiento suave entre casillas.
 *                           Al terminar el movimiento deben coincidir con getX()/getY().
 */
public abstract class Pieza extends ElementoVisual
        implements Seleccionable, Movible
        , Comparable<Pieza>
        { // INTERFACES

    // -------------------------------------------------------------------------
    // Atributos privados
    // -------------------------------------------------------------------------

    private int     fila;          // Fila actual de la pieza en el tablero (0-7)
    private int     columna;       // Columna actual de la pieza en el tablero (0-7)
    private Color   color;         // Color de la pieza (BLANCA o NEGRA)
    private Tablero tablero;       // Tablero al que pertenece la pieza
    private boolean seleccionada;  // true si la pieza está seleccionada por el usuario

    // -------------------------------------------------------------------------
    // Estado delegado para el bucle de tiempo real
    // -------------------------------------------------------------------------

    /** Opacidad actual de la pieza (0.0 = invisible, 1.0 = opaca). */
    private float opacidad = 1.0f;

    /**
     * Posición continua en píxeles del centro de la pieza.
     * Inicialmente coincide con la casilla de partida; el bucle la interpola
     * hacia el destino durante la animación de movimiento.
     */
    private double xPixel;
    private double yPixel;

    
    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    /**
     * Crea una pieza y la posiciona en el tablero.
     * Las coordenadas de píxeles del centro se calculan automáticamente
     * a partir de la fila/columna y el tablero.
     *
     * @param fila    fila inicial (0-7, donde 0 es la fila superior)
     * @param columna columna inicial (0-7)
     * @param color   color de la pieza
     * @param tablero tablero al que pertenece
     */
    public Pieza(int fila, int columna, Color color, Tablero tablero) {
        super(
            tablero.xCentroCasilla(columna),
            tablero.yCentroCasilla(fila),
            tablero.getTamanoCasilla(),
            tablero.getTamanoCasilla(),
            tablero.getVentana()
        );
        this.setFila( fila );
        this.setColumna( columna );
        this.color       = color;
        this.tablero     = tablero;
        this.seleccionada = false;
        this.xPixel = getX();
        this.yPixel = getY();
    }

    // -------------------------------------------------------------------------
    // Getters y setters
    // -------------------------------------------------------------------------

    public int getFila() { return fila; }
    public int getColumna() { return columna; }
    public Color getColor() { return color; }
    public Tablero getTablero() { return tablero; }
    public boolean isSeleccionada() { return seleccionada; }  // mantener compatibilidad
    @Override // INTERFACES
    public boolean isSeleccionado() { return seleccionada; }  // INTERFACES
    
    public void setFila(int fila) {
    	if (fila<0 || fila>7) {
    		System.err.println( "Error en posicionamiento de fila incorrecta: " + fila );
    		return;
    	}
		this.fila = fila;
	}
    public void setColumna(int columna) {
    	if (columna<0 || columna>7) {
    		System.err.println( "Error en posicionamiento de columna incorrecta: " + columna );
    		return;
    	}
		this.columna = columna;
	}

    public void setColor(Color color) { this.color = color; }
    public void setSeleccionada(boolean seleccionada) { this.seleccionada = seleccionada; }

    @Override
    public void setSeleccionado(boolean seleccionado) { this.seleccionada = seleccionado; } // INTERFACES

    /** Devuelve true si el click (px de ventana) cae dentro de la casilla de esta pieza. // INTERFACES
     *  Implementación de Seleccionable. */ // INTERFACES
    @Override // INTERFACES
    public boolean contieneClick(Point click) { // INTERFACES
        int tam = tablero.getTamanoCasilla();
        int px = getX() - tam / 2;
        int py = getY() - tam / 2;
        return click.x >= px && click.x < px + tam
            && click.y >= py && click.y < py + tam;
    }

    // -------------------------------------------------------------------------
    // Getters y setters del estado de tiempo real
    // -------------------------------------------------------------------------

    public float  getOpacidad()             { return opacidad; }
    public void   setOpacidad(float op)     { this.opacidad = Math.max(0f, Math.min(1f, op)); }

    public double getXPixel()               { return xPixel; }
    public double getYPixel()               { return yPixel; }
    public void   setXPixel(double xPixel)  { this.xPixel = xPixel; }
    public void   setYPixel(double yPixel)  { this.yPixel = yPixel; }

    // -------------------------------------------------------------------------
    // Métodos de movimiento (abstractos)
    // -------------------------------------------------------------------------

    /**
     * Mueve la pieza a la casilla indicada si el movimiento es legal.
     * Debe actualizar {@code fila}, {@code columna} y las coordenadas de píxeles.
     *
     * @param fila    fila destino (0-7)
     * @param columna columna destino (0-7)
     */
    public abstract void moverA(int fila, int columna);

    /**
     * Indica si la pieza puede moverse a la casilla indicada según las
     * reglas del ajedrez para este tipo de pieza.
     *
     * @param fila    fila destino (0-7)
     * @param columna columna destino (0-7)
     * @return true si el movimiento es válido
     */
    public abstract boolean puedeMoverA(int fila, int columna);

    // -------------------------------------------------------------------------
    // Método auxiliar protegido: actualizar posición interna
    // -------------------------------------------------------------------------

    /**
     * Actualiza los atributos de fila/columna y las coordenadas en píxeles
     * en función del tablero. Las subclases deben llamar a este método
     * al final de su implementación de {@code moverA}.
     *
     * @param nuevaFila    nueva fila (0-7)
     * @param nuevaColumna nueva columna (0-7)
     */
    protected void actualizarPosicion(int nuevaFila, int nuevaColumna) {
        this.fila    = nuevaFila;
        this.columna = nuevaColumna;
        setX(tablero.xCentroCasilla(nuevaColumna));
        setY(tablero.yCentroCasilla(nuevaFila));
    }

    // -------------------------------------------------------------------------
    // Método de sincronización de píxeles para final de animación
    // -------------------------------------------------------------------------
    public void sincronizarPixeles() {
        setXPixel(getX());
        setYPixel(getY());
    }

    // -------------------------------------------------------------------------
    // toString / equals
    // -------------------------------------------------------------------------

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[fila=" + fila + ", columna=" + columna
                + ", color=" + color + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        Pieza other = (Pieza) obj;
        return fila == other.fila
                && columna == other.columna
                && color == other.color;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + fila;
        result = 31 * result + columna;
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }
    
    @Override
    public int compareTo(Pieza o) {
    	// int comp = fila - o.fila;
    	// if (comp==0) {
    	//     comp = columna - o.columna;
    	// {
    	// return comp;
    	if (fila < o.fila) {
    		return -1;
    	} else if (fila > o.fila) {
    		return +1;
    	} else {
    		if (columna < o.columna) {
    			return -1;
    		} else if (columna < o.columna) {
    			return +1;
    		} else {
    			return 0;
    		}
    	}
    }

}
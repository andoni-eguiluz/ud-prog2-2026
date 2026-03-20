package tema1c.ejemplos.ajedrez.datos;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

/**
 * Representa la Dama (reina) en el tablero de ajedrez.
 *
 * La Dama combina los movimientos de la Torre y el Alfil:
 *  - Se mueve cualquier número de casillas en línea recta (horizontal o vertical).
 *  - Se mueve cualquier número de casillas en diagonal.
 * En ambos casos el camino debe estar libre de otras piezas.
 * Puede capturar una pieza enemiga en la casilla de destino.
 *
 */
public class Dama extends Pieza {

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    /**
     * Crea una Dama y la coloca en el tablero en la posición indicada.
     *
     * @param fila    fila inicial (0-7)
     * @param columna columna inicial (0-7)
     * @param color   color de la pieza (BLANCA o NEGRA)
     * @param tablero tablero al que pertenece
     */
    public Dama(int fila, int columna, Color color, Tablero tablero) {
        super(fila, columna, color, tablero);
        // Inicializar posición continua con el centro de la casilla inicial
    }

    // -------------------------------------------------------------------------
    // Reglas de movimiento
    // -------------------------------------------------------------------------

    /**
     * Comprueba si la Dama puede moverse a la casilla (filaDestino, columnaDestino)
     * siguiendo las reglas del ajedrez.
     *
     * @param filaDestino    fila destino (0-7)
     * @param columnaDestino columna destino (0-7)
     * @return true si el movimiento es legal
     */
    @Override
    public boolean puedeMoverA(int filaDestino, int columnaDestino) {
        int filaOrigen    = getFila();
        int columnaOrigen = getColumna();

        if (filaDestino == filaOrigen && columnaDestino == columnaOrigen) return false;

        if (filaDestino < 0 || filaDestino >= Tablero.FILAS
                || columnaDestino < 0 || columnaDestino >= Tablero.COLUMNAS) return false;

        int dFila    = filaDestino    - filaOrigen;
        int dColumna = columnaDestino - columnaOrigen;

        boolean esRectilineo = (dFila == 0 || dColumna == 0);
        boolean esDiagonal   = (Math.abs(dFila) == Math.abs(dColumna));
        if (!esRectilineo && !esDiagonal) return false;

        int stepFila    = Integer.signum(dFila);
        int stepColumna = Integer.signum(dColumna);
        int f = filaOrigen + stepFila;
        int c = columnaOrigen + stepColumna;

        while (f != filaDestino || c != columnaDestino) {
            if (getTablero().estaOcupada(f, c)) return false;
            f += stepFila;
            c += stepColumna;
        }

        Pieza piezaDestino = getTablero().getPiezaEn(filaDestino, columnaDestino);
        if (piezaDestino != null && piezaDestino.getColor() == getColor()) return false;

        return true;
    }

    /**
     * Registra el destino lógico (fila/columna) en la superclase.
     * La posición continua (xPixel/yPixel) NO se modifica aquí: el bucle de
     * tiempo real la interpolará fotograma a fotograma hacia getX()/getY().
     *
     * @param filaDestino    fila destino (0-7)
     * @param columnaDestino columna destino (0-7)
     */
    @Override
    public void moverA(int filaDestino, int columnaDestino) {
        if (!puedeMoverA(filaDestino, columnaDestino)) {
            System.out.println("Movimiento no válido para la Dama en ("
                    + getFila() + ", " + getColumna() + ") -> ("
                    + filaDestino + ", " + columnaDestino + ")");
            return;
        }

        Pieza capturada = getTablero().getPiezaEn(filaDestino, columnaDestino);
        if (capturada != null) getTablero().removePieza(capturada);

        // Actualiza posición lógica discreta. xPixel/yPixel permanecen en el origen:
        // el bucle animará el deslizamiento hasta getX()/getY().
        actualizarPosicion(filaDestino, columnaDestino);
    }

    // -------------------------------------------------------------------------
    // Dibujo
    // -------------------------------------------------------------------------

    /**
     * Dibuja la Dama en su posición continua (xPixel, yPixel) con su opacidad
     * actual. Ambos valores son actualizados por el bucle de tiempo real cada
     * fotograma, lo que produce animaciones suaves de movimiento y difuminado.
     */
    @Override
    public void dibujar() {
        VentanaGrafica v = getVentana();
        int casilla = getTablero().getTamanoCasilla();
        // Halo de selección amarillo (respeta la opacidad del pulso)
        if (isSeleccionada()) {
            int r = (int)(casilla * 0.35);
            v.dibujaCirculo(getXPixel(), getYPixel(), r + 6, 3.0f,
                    new java.awt.Color(1f, 1f, 0f, getOpacidad()),
                    new java.awt.Color(1f, 1f, 0f, getOpacidad() * 0.6f));
        }
        // Imagen con la opacidad delegada al estado de esta pieza
        // Dibujado con las fichas separadas:
        // String nombreFic = (getColor() == Color.BLANCA)
        //         ? "../img/dama-blanca.png" : "../img/dama-negra.png";
        // v.dibujaImagen(nombreFic, getXPixel(), getYPixel(), casilla, casilla, 1.0, 0.0, getOpacidad());
        // Dibujado con el spritesheet
        String img = "../img/Piezas ajedrez transparentes.png";
        if (getColor() == Color.BLANCA) {
        	v.dibujaParteImagen(img, getXPixel(), getYPixel(), casilla, casilla, 1.0, 0.0, getOpacidad(), 896, 1162, 58, 396 );  // píxeles de posición de figura en el spritesheet
        } else {
        	v.dibujaParteImagen(img, getXPixel(), getYPixel(), casilla, casilla, 1.0, 0.0, getOpacidad(), 896, 1162, 408, 726) ;  // píxeles de posición de figura en el spritesheet
        }

    }

    // -------------------------------------------------------------------------
    // toString
    // -------------------------------------------------------------------------

    @Override
    public String toString() {
        return "Dama[fila=" + getFila() + ", columna=" + getColumna()
                + ", color=" + getColor() + "]";
    }

}
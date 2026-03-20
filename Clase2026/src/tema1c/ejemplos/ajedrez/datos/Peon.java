package tema1c.ejemplos.ajedrez.datos;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

/**
 * Representa un Peón en el tablero de ajedrez.
 *
 * Reglas de movimiento implementadas:
 *  - Avanza una casilla hacia adelante si está libre.
 *  - Desde su fila inicial puede avanzar dos casillas si ambas están libres.
 *  - Captura en diagonal una casilla hacia adelante si hay pieza enemiga.
 *
 * "Adelante" depende del color:
 *  - BLANCA: fila decrece (hacia fila 0).
 *  - NEGRA:  fila crece   (hacia fila 7).
 *
 * NO implementadas: promoción, captura al paso (en passant).
 */
public class Peon extends Pieza {

    private static final int FILA_INICIAL_BLANCA = 6;
    private static final int FILA_INICIAL_NEGRA  = 1;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    public Peon(int fila, int columna, Color color, Tablero tablero) {
        super(fila, columna, color, tablero);
    }

    // -------------------------------------------------------------------------
    // Utilidades privadas
    // -------------------------------------------------------------------------

    /** Dirección de avance: -1 para BLANCA (hacia fila 0), +1 para NEGRA (hacia fila 7). */
    private int dir() {
        return (getColor() == Color.NEGRA) ? 1 : -1;
    }

    private int filaInicial() {
        return (getColor() == Color.BLANCA) ? FILA_INICIAL_BLANCA : FILA_INICIAL_NEGRA;
    }

    // -------------------------------------------------------------------------
    // Reglas de movimiento
    // -------------------------------------------------------------------------

    @Override
    public boolean puedeMoverA(int filaDestino, int columnaDestino) {
        int fo  = getFila();
        int co  = getColumna();
        int dir = dir();

        if (filaDestino < 0 || filaDestino >= Tablero.FILAS
                || columnaDestino < 0 || columnaDestino >= Tablero.COLUMNAS) return false;

        int dF = filaDestino    - fo;
        int dC = columnaDestino - co;

        // Avance simple: una casilla al frente, misma columna, libre
        if (dF == dir && dC == 0)
            return !getTablero().estaOcupada(filaDestino, columnaDestino);

        // Avance doble: solo desde fila inicial, las dos casillas libres
        if (dF == 2 * dir && dC == 0 && fo == filaInicial())
            return !getTablero().estaOcupada(fo + dir, co)
                && !getTablero().estaOcupada(filaDestino, columnaDestino);

        // Captura diagonal: una casilla al frente en diagonal, pieza enemiga en destino
        if (dF == dir && Math.abs(dC) == 1) {
            Pieza objetivo = getTablero().getPiezaEn(filaDestino, columnaDestino);
            return objetivo != null && objetivo.getColor() != getColor();
        }

        return false;
    }

    @Override
    public void moverA(int filaDestino, int columnaDestino) {
        if (!puedeMoverA(filaDestino, columnaDestino)) {
            System.out.println("Movimiento no válido para el Peón en ("
                    + getFila() + ", " + getColumna() + ") -> ("
                    + filaDestino + ", " + columnaDestino + ")");
            return;
        }
        Pieza capturada = getTablero().getPiezaEn(filaDestino, columnaDestino);
        if (capturada != null) getTablero().removePieza(capturada);
        actualizarPosicion(filaDestino, columnaDestino);
    }

    // -------------------------------------------------------------------------
    // Dibujo
    // -------------------------------------------------------------------------

    @Override
    public void dibujar() {
        VentanaGrafica v = getVentana();
        int casilla = getTablero().getTamanoCasilla();
        if (isSeleccionada()) {
            int r = (int)(casilla * 0.35);
            v.dibujaCirculo(getXPixel(), getYPixel(), r + 6, 3.0f,
                    new java.awt.Color(1f, 1f, 0f, getOpacidad()),
                    new java.awt.Color(1f, 1f, 0f, getOpacidad() * 0.6f));
        }
        String img = "../img/Piezas ajedrez transparentes.png";
        if (getColor() == Color.BLANCA) {
        	v.dibujaParteImagen(img, getXPixel(), getYPixel(), casilla, casilla, 1.0, 0.0, getOpacidad(), 37, 303, 58, 396 );  // píxeles de posición de figura en el spritesheet
        } else {
        	v.dibujaParteImagen(img, getXPixel(), getYPixel(), casilla, casilla, 1.0, 0.0, getOpacidad(), 37, 303, 408, 726) ;  // píxeles de posición de figura en el spritesheet
        }
    }

    // -------------------------------------------------------------------------
    // toString
    // -------------------------------------------------------------------------

    @Override
    public String toString() {
        return "Peon[fila=" + getFila() + ", columna=" + getColumna()
                + ", color=" + getColor() + "]";
    }
}
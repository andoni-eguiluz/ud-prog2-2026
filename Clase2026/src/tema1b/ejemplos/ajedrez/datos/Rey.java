package tema1b.ejemplos.ajedrez.datos;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

/**
 * Representa el Rey en el tablero de ajedrez.
 *
 * Reglas de movimiento implementadas:
 *  - Se mueve exactamente una casilla en cualquier dirección
 *    (horizontal, vertical o diagonal).
 *  - No puede moverse a una casilla ocupada por una pieza propia.
 *
 * NO implementados: enroque, comprobación de jaque/jaque mate.
 * (Detectar si el destino queda bajo ataque requiere inspeccionar todas
 * las piezas enemigas; esa lógica corresponde al controlador del juego,
 * no a la pieza aislada.)
 */
public class Rey extends Pieza {

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    public Rey(int fila, int columna, Color color, Tablero tablero) {
        super(fila, columna, color, tablero);
    }

    // -------------------------------------------------------------------------
    // Reglas de movimiento
    // -------------------------------------------------------------------------

    @Override
    public boolean puedeMoverA(int filaDestino, int columnaDestino) {
        if (filaDestino == getFila() && columnaDestino == getColumna()) return false;
        if (filaDestino < 0 || filaDestino >= Tablero.FILAS
                || columnaDestino < 0 || columnaDestino >= Tablero.COLUMNAS) return false;

        int dF = Math.abs(filaDestino    - getFila());
        int dC = Math.abs(columnaDestino - getColumna());

        // El rey se mueve exactamente una casilla en cualquier dirección
        if (dF > 1 || dC > 1) return false;

        // No puede capturar una pieza propia
        Pieza destino = getTablero().getPiezaEn(filaDestino, columnaDestino);
        return destino == null || destino.getColor() != getColor();
    }

    @Override
    public void moverA(int filaDestino, int columnaDestino) {
        if (!puedeMoverA(filaDestino, columnaDestino)) {
            System.out.println("Movimiento no válido para el Rey en ("
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
        	v.dibujaParteImagen(img, getXPixel(), getYPixel(), casilla, casilla, 1.0, 0.0, getOpacidad(), 1110, 1376, 58, 396 );  // píxeles de posición de figura en el spritesheet
        } else {
        	v.dibujaParteImagen(img, getXPixel(), getYPixel(), casilla, casilla, 1.0, 0.0, getOpacidad(), 1110, 1376, 408, 726) ;  // píxeles de posición de figura en el spritesheet
        }
    }

    // -------------------------------------------------------------------------
    // toString
    // -------------------------------------------------------------------------

    @Override
    public String toString() {
        return "Rey[fila=" + getFila() + ", columna=" + getColumna()
                + ", color=" + getColor() + "]";
    }
}
package tema1c.ejemplos.ajedrez.datos;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

/**
 * Representa un Caballo en el tablero de ajedrez.
 *
 * Reglas de movimiento:
 *  - Se mueve en forma de "L": dos casillas en una dirección y una en la
 *    perpendicular (o viceversa), resultando en hasta 8 destinos posibles.
 *  - Es la única pieza que puede SALTAR sobre otras piezas.
 *  - Puede capturar una pieza enemiga en la casilla de destino.
 */
public class Caballo extends Pieza {

    // Los 8 saltos posibles expresados como (deltaFila, deltaColumna)
    private static final int[][] SALTOS = {
        {-2, -1}, {-2, +1},
        {+2, -1}, {+2, +1},
        {-1, -2}, {-1, +2},
        {+1, -2}, {+1, +2},
    };

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    public Caballo(int fila, int columna, Color color, Tablero tablero) {
        super(fila, columna, color, tablero);
    }

    // -------------------------------------------------------------------------
    // Reglas de movimiento
    // -------------------------------------------------------------------------

    @Override
    public boolean puedeMoverA(int filaDestino, int columnaDestino) {
        if (filaDestino < 0 || filaDestino >= Tablero.FILAS
                || columnaDestino < 0 || columnaDestino >= Tablero.COLUMNAS) return false;

        int dF = filaDestino    - getFila();
        int dC = columnaDestino - getColumna();

        // Verificar que el desplazamiento corresponde a uno de los 8 saltos en L
        boolean saltoValido = false;
        for (int[] salto : SALTOS) {
            if (dF == salto[0] && dC == salto[1]) { saltoValido = true; break; }
        }
        if (!saltoValido) return false;

        // El caballo salta: solo importa la casilla de destino
        Pieza destino = getTablero().getPiezaEn(filaDestino, columnaDestino);
        return destino == null || destino.getColor() != getColor();
    }

    @Override
    public void moverA(int filaDestino, int columnaDestino) {
        if (!puedeMoverA(filaDestino, columnaDestino)) {
            System.out.println("Movimiento no válido para el Caballo en ("
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
        	v.dibujaParteImagen(img, getXPixel(), getYPixel(), casilla, casilla, 1.0, 0.0, getOpacidad(), 253, 519, 58, 396 );  // píxeles de posición de figura en el spritesheet
        } else {
        	v.dibujaParteImagen(img, getXPixel(), getYPixel(), casilla, casilla, 1.0, 0.0, getOpacidad(), 253, 519, 408, 722) ;  // píxeles de posición de figura en el spritesheet
        }
    }

    // -------------------------------------------------------------------------
    // toString
    // -------------------------------------------------------------------------

    @Override
    public String toString() {
        return "Caballo[fila=" + getFila() + ", columna=" + getColumna()
                + ", color=" + getColor() + "]";
    }
}
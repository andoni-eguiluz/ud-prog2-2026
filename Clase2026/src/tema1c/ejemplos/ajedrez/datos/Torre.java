package tema1c.ejemplos.ajedrez.datos;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

/**
 * Representa una Torre en el tablero de ajedrez.
 *
 * Reglas de movimiento:
 *  - Se mueve cualquier número de casillas en línea recta (horizontal o vertical).
 *  - El camino debe estar libre de otras piezas.
 *  - Puede capturar una pieza enemiga en la casilla de destino.
 *
 * NO implementado: enroque.
 */
public class Torre extends Pieza {

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    public Torre(int fila, int columna, Color color, Tablero tablero) {
        super(fila, columna, color, tablero);
    }

    // -------------------------------------------------------------------------
    // Reglas de movimiento
    // -------------------------------------------------------------------------

    @Override
    public boolean puedeMoverA(int filaDestino, int columnaDestino) {
        int fo = getFila();
        int co = getColumna();

        if (filaDestino == fo && columnaDestino == co) return false;
        if (filaDestino < 0 || filaDestino >= Tablero.FILAS
                || columnaDestino < 0 || columnaDestino >= Tablero.COLUMNAS) return false;

        int dF = filaDestino    - fo;
        int dC = columnaDestino - co;

        // Solo movimiento rectilíneo (horizontal o vertical, no diagonal)
        if (dF != 0 && dC != 0) return false;

        // Comprobar camino libre
        int stepF = Integer.signum(dF);
        int stepC = Integer.signum(dC);
        int f = fo + stepF;
        int c = co + stepC;
        while (f != filaDestino || c != columnaDestino) {
            if (getTablero().estaOcupada(f, c)) return false;
            f += stepF;
            c += stepC;
        }

        // Destino: libre o pieza enemiga
        Pieza destino = getTablero().getPiezaEn(filaDestino, columnaDestino);
        return destino == null || destino.getColor() != getColor();
    }

    @Override
    public void moverA(int filaDestino, int columnaDestino) {
        if (!puedeMoverA(filaDestino, columnaDestino)) {
            System.out.println("Movimiento no válido para la Torre en ("
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
        	v.dibujaParteImagen(img, getXPixel(), getYPixel(), casilla, casilla, 1.0, 0.0, getOpacidad(), 681, 947, 58, 396 );  // píxeles de posición de figura en el spritesheet
        } else {
        	v.dibujaParteImagen(img, getXPixel(), getYPixel(), casilla, casilla, 1.0, 0.0, getOpacidad(), 681, 947, 408, 726) ;  // píxeles de posición de figura en el spritesheet
        }
    }

    // -------------------------------------------------------------------------
    // toString
    // -------------------------------------------------------------------------

    @Override
    public String toString() {
        return "Torre[fila=" + getFila() + ", columna=" + getColumna()
                + ", color=" + getColor() + "]";
    }
}
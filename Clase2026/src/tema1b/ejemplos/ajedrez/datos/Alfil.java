package tema1b.ejemplos.ajedrez.datos;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

/**
 * Representa un Alfil en el tablero de ajedrez.
 *
 * Reglas de movimiento:
 *  - Se mueve cualquier número de casillas en diagonal.
 *  - El camino debe estar libre de otras piezas.
 *  - Puede capturar una pieza enemiga en la casilla de destino.
 *  - Siempre permanece en casillas del mismo color.
 */
public class Alfil extends Pieza {

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    public Alfil(int fila, int columna, Color color, Tablero tablero) {
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

        // Solo movimiento diagonal (|dF| == |dC|)
        if (Math.abs(dF) != Math.abs(dC)) return false;

        // Comprobar camino libre
        int pasoF = Integer.signum(dF);
        int pasoC = Integer.signum(dC);
        int f = fo + pasoF;
        int c = co + pasoC;
        while (f != filaDestino || c != columnaDestino) {
            if (getTablero().estaOcupada(f, c)) return false;
            f += pasoF;
            c += pasoC;
        }

        // Destino: libre o pieza enemiga
        Pieza destino = getTablero().getPiezaEn(filaDestino, columnaDestino);
        return destino == null || destino.getColor() != getColor();
    }

    @Override
    public void moverA(int filaDestino, int columnaDestino) {
        if (!puedeMoverA(filaDestino, columnaDestino)) {
            System.out.println("Movimiento no válido para el Alfil en ("
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
        	v.dibujaParteImagen(img, getXPixel(), getYPixel(), casilla, casilla, 1.0, 0.0, getOpacidad(), 467, 733, 58, 396 );  // píxeles de posición de figura en el spritesheet
        } else {
        	v.dibujaParteImagen(img, getXPixel(), getYPixel(), casilla, casilla, 1.0, 0.0, getOpacidad(), 467, 733, 408, 726) ;  // píxeles de posición de figura en el spritesheet
        }
    }

    // -------------------------------------------------------------------------
    // toString
    // -------------------------------------------------------------------------

    @Override
    public String toString() {
        return "Alfil[fila=" + getFila() + ", columna=" + getColumna()
                + ", color=" + getColor() + "]";
    }
}
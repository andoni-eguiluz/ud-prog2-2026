package tema1b.ejemplos.ajedrez;

import tema1b.ejemplos.ajedrez.datos.Color;
import utils.ventanas.ventanaBitmap.VentanaGrafica;
import java.awt.Point;
import tema1b.ejemplos.ajedrez.datos.*;

/**
 * Partida de ajedrez interactiva en tiempo real con el tablero completo.
 *
 * ── FUNCIONAMIENTO ───────────────────────────────────────────────────────────
 *  - Se inicializan las 32 piezas en su posición estándar.
 *  - Turno alternado: empiezan las BLANCAS.
 *  - Click sobre una pieza del turno activo → la selecciona (pulso de opacidad).
 *  - Click en casilla válida → la pieza se desliza animada hacia el destino.
 *  - Click sobre la misma pieza seleccionada → cancela la selección.
 *  - Click en casilla inválida → aviso en la barra inferior.
 *  - Mientras una pieza se mueve no se acepta ningún input.
 *
 * ── ESTADOS DEL BUCLE ────────────────────────────────────────────────────────
 *  REPOSO       → Esperando que el jugador del turno activo seleccione una pieza.
 *  SELECCIONADA → Pieza elegida, pulsando. Esperando destino.
 *  MOVIENDO     → Pieza deslizándose. Sin input hasta llegar al destino.
 */
public class PruebaAjedrez {

    // ── Constantes ────────────────────────────────────────────────────────────

    private static final int    TAM_VENTANA     = 640;
    private static final double VEL_PX_SEG      = 400.0;
    private static final double CICLO_PULSO_SEG = 2.0;
    private static final float  OPACIDAD_MIN    = 0.25f;
    private static final long   FPS_OBJETIVO    = 60;
    private static final long   MS_POR_FRAME    = 1000 / FPS_OBJETIVO;

    // ── Estados ───────────────────────────────────────────────────────────────

    private enum Estado { REPOSO, SELECCIONADA, MOVIENDO }

    // ── Campos ────────────────────────────────────────────────────────────────

    private VentanaGrafica ventana;
    private Tablero        tablero;

    private Estado      estado       = Estado.REPOSO;
    private Pieza       piezaActiva  = null;   // pieza seleccionada o en movimiento
    private Color turno              = Color.BLANCA;
    private double      tiempoPulso  = 0.0;

    // ── Punto de entrada ──────────────────────────────────────────────────────

    public static void main(String[] args) {
        new PruebaAjedrez().ejecutar();
    }

    // ── Inicialización ────────────────────────────────────────────────────────

    private void ejecutar() {
        ventana = new VentanaGrafica(TAM_VENTANA, TAM_VENTANA, "Ajedrez");
        ventana.setDibujadoInmediato(false);

        tablero = new Tablero(0, 0, TAM_VENTANA, TAM_VENTANA, ventana);
        colocarPiezas();

        actualizarMensaje();
        bucletiempoReal();

        ventana.acaba();
    }

    /**
     * Coloca las 32 piezas en la posición inicial estándar del ajedrez.
     *
     * Distribución:
     *   Fila 0 → piezas mayores NEGRAS  (Torre Caballo Alfil Reina Rey Alfil Caballo Torre)
     *   Fila 1 → peones NEGROS
     *   Fila 6 → peones BLANCOS
     *   Fila 7 → piezas mayores BLANCAS
     */
    private void colocarPiezas() {
        // ── Piezas mayores ────────────────────────────────────────────────────
        // Columnas: 0=Torre 1=Caballo 2=Alfil 3=Reina 4=Rey 5=Alfil 6=Caballo 7=Torre
        for (int col = 0; col < 8; col++) {
            Pieza pNegra  = crearPiezaMayor(col, 0, Color.NEGRA);
            Pieza pBlanca = crearPiezaMayor(col, 7, Color.BLANCA);
            tablero.addPieza(pNegra);
            tablero.addPieza(pBlanca);
        }
        // ── Peones ────────────────────────────────────────────────────────────
        for (int col = 0; col < 8; col++) {
            tablero.addPieza(new Peon(1, col, Color.NEGRA,  tablero));
            tablero.addPieza(new Peon(6, col, Color.BLANCA, tablero));
        }
    }

    /** Instancia la pieza mayor correspondiente a la columna indicada. */
    private Pieza crearPiezaMayor(int col, int fila, Color color) {
        switch (col) {
            case 0: case 7: return new Torre  (fila, col, color, tablero);
            case 1: case 6: return new Caballo(fila, col, color, tablero);
            case 2: case 5: return new Alfil  (fila, col, color, tablero);
            case 3:         return new Dama   (fila, col, color, tablero);
            case 4:         return new Rey    (fila, col, color, tablero);
            default:        throw new IllegalArgumentException("Columna inesperada: " + col);
        }
    }

    // ── Bucle de tiempo real ──────────────────────────────────────────────────

    private void bucletiempoReal() {
        long tAnterior = System.currentTimeMillis();

        while (!ventana.estaCerrada()) {

            // 1. Delta time
            long   tAhora   = System.currentTimeMillis();
            double deltaSeg = (tAhora - tAnterior) / 1000.0;
            tAnterior = tAhora;

            // 2. Entrada no bloqueante
            Point click = ventana.getRatonClicado();

            // 3. Máquina de estados
            procesarEstado(click, deltaSeg);

            // 4. Animaciones de la pieza activa
            if (piezaActiva != null) actualizarPieza(deltaSeg);

            // 5. Redibujo
            ventana.borra();
            tablero.dibujar();
            ventana.repaint();

            // 6. Limitar FPS
            long sobrante = MS_POR_FRAME - (System.currentTimeMillis() - tAhora);
            if (sobrante > 0) {
                try { Thread.sleep(sobrante); } catch (InterruptedException ignored) {}
            }
        }
    }

    // ── Máquina de estados ────────────────────────────────────────────────────

    private void procesarEstado(Point click, double deltaSeg) {
        switch (estado) {

            // ─────────────────────────────────────────────────────────────────
            case REPOSO:
                if (click == null) break;
                int[] casillaR = tablero.clickACasilla(click);
                Pieza candidata = tablero.getPiezaEn(casillaR[0], casillaR[1]);

                if (candidata != null && candidata.getColor() == turno) {
                    // Seleccionar pieza del turno activo
                    piezaActiva = candidata;
                    piezaActiva.setSeleccionada(true);
                    tiempoPulso = 0.0;
                    estado = Estado.SELECCIONADA;
                    actualizarMensaje();
                }
                break;

            // ─────────────────────────────────────────────────────────────────
            case SELECCIONADA:
                if (click == null) break;
                int[] casillaS = tablero.clickACasilla(click);
                int filS = casillaS[0];
                int colS = casillaS[1];

                if (filS == piezaActiva.getFila() && colS == piezaActiva.getColumna()) {
                    // Click sobre sí misma → cancelar
                    deseleccionar();
                    estado = Estado.REPOSO;

                } else if (piezaActiva.puedeMoverA(filS, colS)) {
                    // Destino válido → mover y animar
                    piezaActiva.moverA(filS, colS);   // actualiza posición lógica
                    piezaActiva.setSeleccionada(false);
                    piezaActiva.setOpacidad(1.0f);
                    estado = Estado.MOVIENDO;

                } else {
                    // Destino inválido: comprobar si se ha hecho click en otra pieza propia
                    Pieza otraPieza = tablero.getPiezaEn(filS, colS);
                    if (otraPieza != null && otraPieza.getColor() == turno) {
                        // Cambiar selección a la nueva pieza
                        deseleccionar();
                        piezaActiva = otraPieza;
                        piezaActiva.setSeleccionada(true);
                        tiempoPulso = 0.0;
                        estado = Estado.SELECCIONADA;
                    } else {
                        ventana.setMensaje("Movimiento no válido. Elige otro destino"
                                + " o click sobre la misma pieza para cancelar.");
                    }
                }
                actualizarMensaje();
                break;

            // ─────────────────────────────────────────────────────────────────
            case MOVIENDO:
                // Sin input mientras la pieza se desplaza
                if (haLlegadoAlDestino()) {
                    // Anclar posición y cambiar turno
                    piezaActiva.setXPixel(piezaActiva.getX());
                    piezaActiva.setYPixel(piezaActiva.getY());
                    piezaActiva = null;
                    turno = (turno == Color.BLANCA) ? Color.NEGRA : Color.BLANCA;
                    estado = Estado.REPOSO;
                    actualizarMensaje();
                }
                break;
        }
    }

    // ── Actualización de animación de la pieza activa ─────────────────────────

    private void actualizarPieza(double deltaSeg) {
        if (estado == Estado.SELECCIONADA) {
            // Pulso de opacidad
            tiempoPulso = (tiempoPulso + deltaSeg) % CICLO_PULSO_SEG;
            double fase = tiempoPulso / CICLO_PULSO_SEG;
            float op;
            if (fase < 0.5) {
                op = 1.0f - (float)(fase * 2.0) * (1.0f - OPACIDAD_MIN);
            } else {
                op = OPACIDAD_MIN + (float)((fase - 0.5) * 2.0) * (1.0f - OPACIDAD_MIN);
            }
            piezaActiva.setOpacidad(op);

        } else if (estado == Estado.MOVIENDO) {
            // Interpolación lineal a velocidad constante
            double destX = piezaActiva.getX();
            double destY = piezaActiva.getY();
            double dx    = destX - piezaActiva.getXPixel();
            double dy    = destY - piezaActiva.getYPixel();
            double dist  = Math.sqrt(dx * dx + dy * dy);
            double avance = VEL_PX_SEG * deltaSeg;

            if (avance >= dist) {
                piezaActiva.setXPixel(destX);
                piezaActiva.setYPixel(destY);
            } else {
                piezaActiva.setXPixel(piezaActiva.getXPixel() + (dx / dist) * avance);
                piezaActiva.setYPixel(piezaActiva.getYPixel() + (dy / dist) * avance);
            }
        }
    }

    // ── Utilidades ────────────────────────────────────────────────────────────

    private void deseleccionar() {
        if (piezaActiva != null) {
            piezaActiva.setSeleccionada(false);
            piezaActiva.setOpacidad(1.0f);
            piezaActiva = null;
        }
    }

    private boolean haLlegadoAlDestino() {
        if (piezaActiva == null) return false;
        double dx = piezaActiva.getX() - piezaActiva.getXPixel();
        double dy = piezaActiva.getY() - piezaActiva.getYPixel();
        return Math.sqrt(dx * dx + dy * dy) < 0.5;
    }

    private void actualizarMensaje() {
        String turnoStr = (turno == Color.BLANCA) ? "BLANCAS" : "NEGRAS";
        switch (estado) {
            case REPOSO:
                ventana.setMensaje("Turno: " + turnoStr + "  —  Click sobre una pieza para seleccionarla.");
                break;
            case SELECCIONADA:
                String tipo = piezaActiva.getClass().getSimpleName();
                ventana.setMensaje("Turno: " + turnoStr + "  —  " + tipo
                        + " seleccionado/a. Click en destino para mover o en la misma pieza para cancelar.");
                break;
            case MOVIENDO:
                ventana.setMensaje("Turno: " + turnoStr + "  —  Moviendo...");
                break;
        }
    }
}
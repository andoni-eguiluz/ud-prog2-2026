package tema1b.ejemplos.ajedrez;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

import java.awt.Point;

import tema1b.ejemplos.ajedrez.datos.Color;
import tema1b.ejemplos.ajedrez.datos.Dama;
import tema1b.ejemplos.ajedrez.datos.Tablero;

/**
 * Programa interactivo de tiempo real para mover una Dama por el tablero.
 *
 * ── BUCLE DE TIEMPO REAL ─────────────────────────────────────────────────────
 * A diferencia del bucle interactivo (que bloquea en esperaAClick), este bucle
 * corre a cada fotograma sin esperar al usuario. En cada iteración:
 *   1. Se calcula el deltaTime (tiempo transcurrido desde el fotograma anterior).
 *   2. Se consulta el estado del ratón (getRatonClicado) sin bloquear.
 *   3. Se actualiza el ESTADO del programa (máquina de estados).
 *   4. Se actualizan los valores de la Dama dependientes del tiempo.
 *   5. Se redibuja la escena.
 *
 * ── ESTADOS DEL BUCLE ────────────────────────────────────────────────────────
 *   REPOSO      → La dama no está seleccionada. Espera click sobre ella.
 *   SELECCIONADA → La dama está seleccionada y pulsa (parpadea). Espera destino.
 *   MOVIENDO    → La dama se desliza en píxeles hacia la casilla destino.
 *                 No se acepta input del usuario durante este estado.
 *
 * ── ESTADO DELEGADO A LA DAMA ────────────────────────────────────────────────
 *   - opacidad:   actualizada aquí cada fotograma. Produce el efecto de pulso.
 *   - xPixel/yPixel: interpoladas aquí a 50 px/s para animar el movimiento.
 */
public class PruebaTiempoRealDama {

    // ── Constantes ────────────────────────────────────────────────────────────

    private static final int    TAM_VENTANA      = 640;
    /** Velocidad de desplazamiento de la dama en píxeles por segundo. */
    private static final double VEL_PX_SEG       = 400.0;
    /** Duración total del ciclo de pulso en segundos (baja + sube = 2 s). */
    private static final double CICLO_PULSO_SEG  = 2.0;
    /** Opacidad mínima alcanzada en el punto más bajo del pulso. */
    private static final float  OPACIDAD_MIN     = 0.25f;
    /** Fotogramas por segundo objetivo (limita el refresco para no saturar la CPU). */
    private static final long   FPS_OBJETIVO     = 60;
    private static final long   MS_POR_FRAME     = 1000 / FPS_OBJETIVO;

    // ── Enumerado de estados del bucle ────────────────────────────────────────

    private enum Estado { REPOSO, SELECCIONADA, MOVIENDO }

    // ── Campos de programa ────────────────────────────────────────────────────

    private VentanaGrafica ventana;
    private Tablero        tablero;
    private Dama           dama;

    /** Estado actual de la máquina de estados del bucle principal. */
    private Estado estado = Estado.REPOSO;

    /** Tiempo acumulado del pulso en segundos (0..CICLO_PULSO_SEG). */
    private double tiempoPulso = 0.0;

    // ── Punto de entrada ─────────────────────────────────────────────────────

    public static void main(String[] args) {
        new PruebaTiempoRealDama().ejecutar();
    }

    // ── Inicialización ────────────────────────────────────────────────────────

    private void ejecutar() {
        ventana = new VentanaGrafica(TAM_VENTANA, TAM_VENTANA, "Dama tiempo real – click para seleccionar y mover");
        ventana.setDibujadoInmediato(false); // Desactivar repintado automático: lo controla el bucle

        tablero = new Tablero(0, 0, TAM_VENTANA, TAM_VENTANA, ventana);
        dama    = new Dama(7, 3, Color.BLANCA, tablero);
        tablero.addPieza(dama);

        actualizarMensaje();
        bucletiempoReal();

        ventana.acaba();
    }

    // ── Bucle de tiempo real ──────────────────────────────────────────────────

    private void bucletiempoReal() {
        long tAnterior = System.currentTimeMillis();

        // ════════════════════════════════════════════════════════════════════
        //  BUCLE PRINCIPAL DE TIEMPO REAL
        //  Corre sin parar hasta que se cierra la ventana.
        //  Cada iteración representa un fotograma.
        // ════════════════════════════════════════════════════════════════════
        while (!ventana.estaCerrada()) {

            // ── 1. Delta time ─────────────────────────────────────────────
            long tAhora   = System.currentTimeMillis();
            double deltaSeg = (tAhora - tAnterior) / 1000.0;
            tAnterior = tAhora;

            // ── 2. Entrada: consulta no bloqueante del ratón ───────────────
            // getRatonClicado() devuelve el último click ocurrido (o null).
            // No espera: si no hay click, el juego sigue actualizándose.
            Point click = ventana.getRatonClicado();

            // ── 3. Actualizar estado según input y lógica ──────────────────
            procesarEstado(click, deltaSeg);

            // ── 4. Actualizar estado delegado en la Dama ───────────────────
            actualizarDama(deltaSeg);

            // ── 5. Redibujar ───────────────────────────────────────────────
            ventana.borra();
            tablero.dibujar();
            ventana.repaint();

            // ── 6. Limitar FPS ─────────────────────────────────────────────
            long tFin    = System.currentTimeMillis();
            long sobrante = MS_POR_FRAME - (tFin - tAhora);
            if (sobrante > 0) {
                try { Thread.sleep(sobrante); } catch (InterruptedException ignored) {}
            }
        }
    }

    // ── Máquina de estados ────────────────────────────────────────────────────

    /**
     * Procesa el input y transiciona entre estados.
     *
     * @param click    click de ratón ocurrido este fotograma (null si ninguno)
     * @param deltaSeg tiempo transcurrido desde el fotograma anterior
     */
    private void procesarEstado(Point click, double deltaSeg) {
        switch (estado) {

            // ─────────────────────────────────────────────────────────────────
            case REPOSO:
                // Espera a que el usuario haga click sobre la Dama
                if (click != null && clickEnCasilla(click, dama.getFila(), dama.getColumna())) {
                    dama.setSeleccionada(true);
                    tiempoPulso = 0.0;
                    estado = Estado.SELECCIONADA;
                    actualizarMensaje();
                }
                break;

            // ─────────────────────────────────────────────────────────────────
            case SELECCIONADA:
                if (click != null) {
                    int[] casilla = tablero.clickACasilla(click);
                    int filClick = casilla[0];
                    int colClick = casilla[1];

                    if (filClick == dama.getFila() && colClick == dama.getColumna()) {
                        // Click sobre sí misma → cancelar selección
                        dama.setSeleccionada(false);
                        dama.setOpacidad(1.0f);
                        estado = Estado.REPOSO;
                    } else if (dama.puedeMoverA(filClick, colClick)) {
                        // Click en casilla válida → iniciar animación de movimiento
                        dama.moverA(filClick, colClick);   // actualiza posición lógica
                        dama.setSeleccionada(false);
                        dama.setOpacidad(1.0f);
                        estado = Estado.MOVIENDO;
                    } else {
                        // Casilla inválida → aviso, permanece seleccionada
                        ventana.setMensaje("Movimiento no válido. Elige otro destino"
                                + " o click sobre la Dama para cancelar.");
                    }
                    actualizarMensaje();
                }
                break;

            // ─────────────────────────────────────────────────────────────────
            case MOVIENDO:
                // El estado de movimiento lo resuelve actualizarDama().
                // Cuando xPixel/yPixel llegan al destino, vuelve a REPOSO.
                // No se procesa ningún input del usuario mientras se mueve.
                if (haLlegadoAlDestino()) {
                    // Ajuste fino: anclar píxeles exactos al centro de casilla
                    dama.setXPixel(dama.getX());
                    dama.setYPixel(dama.getY());
                    estado = Estado.REPOSO;
                    actualizarMensaje();
                }
                break;
        }
    }

    // ── Actualización de la Dama por fotograma ────────────────────────────────

    /**
     * Actualiza cada fotograma el estado delegado de la Dama:
     *   - Pulso de opacidad cuando está seleccionada.
     *   - Interpolación de posición cuando se está moviendo.
     *
     * @param deltaSeg tiempo transcurrido desde el fotograma anterior en segundos
     */
    private void actualizarDama(double deltaSeg) {

        if (estado == Estado.SELECCIONADA) {
            // ── Efecto de pulso de opacidad ───────────────────────────────
            // tiempoPulso avanza de 0 a CICLO_PULSO_SEG y vuelve a 0.
            // Primera mitad del ciclo: baja de 1→MIN; segunda: sube MIN→1.
            tiempoPulso = (tiempoPulso + deltaSeg) % CICLO_PULSO_SEG;
            double fase = tiempoPulso / CICLO_PULSO_SEG;  // 0..1

            float opacidad;
            if (fase < 0.5) {
                // Baja: 1.0 → OPACIDAD_MIN en la primera mitad del ciclo
                opacidad = 1.0f - (float)(fase * 2.0) * (1.0f - OPACIDAD_MIN);
            } else {
                // Sube: OPACIDAD_MIN → 1.0 en la segunda mitad del ciclo
                opacidad = OPACIDAD_MIN + (float)((fase - 0.5) * 2.0) * (1.0f - OPACIDAD_MIN);
            }
            dama.setOpacidad(opacidad);

        } else if (estado == Estado.MOVIENDO) {
            // ── Interpolación de posición a velocidad constante ───────────
            double destX = dama.getX();
            double destY = dama.getY();
            double dx    = destX - dama.getXPixel();
            double dy    = destY - dama.getYPixel();
            double dist  = Math.sqrt(dx * dx + dy * dy);

            double avance = VEL_PX_SEG * deltaSeg;

            if (avance >= dist) {
                // Llegamos al destino en este fotograma
                dama.setXPixel(destX);
                dama.setYPixel(destY);
            } else {
                // Avanzar en la dirección del destino
                double nx = dx / dist;
                double ny = dy / dist;
                dama.setXPixel(dama.getXPixel() + nx * avance);
                dama.setYPixel(dama.getYPixel() + ny * avance);
            }
        }
    }

    // ── Utilidades ────────────────────────────────────────────────────────────

    /** Devuelve true si la posición continua de la dama ya está sobre su casilla destino. */
    private boolean haLlegadoAlDestino() {
        double dx = dama.getX() - dama.getXPixel();
        double dy = dama.getY() - dama.getYPixel();
        return Math.sqrt(dx * dx + dy * dy) < 0.5;
    }

    /** Devuelve true si el click cae dentro de la casilla (fila, col). */
    private boolean clickEnCasilla(Point click, int fila, int col) {
        int[] c = tablero.clickACasilla(click);
        return c[0] == fila && c[1] == col;
    }

    /** Actualiza el mensaje de la barra inferior según el estado actual. */
    private void actualizarMensaje() {
        switch (estado) {
            case REPOSO:
                ventana.setMensaje("Click sobre la Dama para seleccionarla. "
                        + "Posición: (" + dama.getFila() + ", " + dama.getColumna() + ")");
                break;
            case SELECCIONADA:
                ventana.setMensaje("Dama seleccionada. Click en destino para mover,"
                        + " o de nuevo sobre ella para cancelar.");
                break;
            case MOVIENDO:
                ventana.setMensaje("Moviendo hacia ("
                        + dama.getFila() + ", " + dama.getColumna() + ")...");
                break;
        }
    }

}
package tema1c.ejemplos.ajedrez;

import utils.ventanas.ventanaBitmap.VentanaGrafica;
import tema1c.ejemplos.ajedrez.datos.*;
import java.awt.Font;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

// TODO Completar implementación, quedan funcionalidades a medio hacer/probar

/**
 * Partida de ajedrez interactiva en tiempo real con tablero completo,
 * zona lateral con elementos especiales y soporte de interfaces.
 *
 * ── LAYOUT ───────────────────────────────────────────────────────────────────
 *
 *   ┌──────────┬────────────────────────────┐
 *   │  ZONA    │                            │
 *   │ LATERAL  │       TABLERO 640x640      │
 *   │  180px   │                            │
 *   │ [Reloj]  │                            │
 *   │[EscudoB] │                            │
 *   │[EscudoN] │                            │
 *   │  [Mina]  │                            │
 *   └──────────┴────────────────────────────┘
 *
 * ── INTERFACES USADOS ────────────────────────────────────────────────────────
 *
 *   Animable      -> bucle llama actualizar() sobre Reloj y todos los Alfil.
 *   Seleccionable -> bucle detecta clicks sobre Piezas, Mina y Escudos.
 *   Movible       -> distingue si un Seleccionable puede ir al tablero
 *                    (Pieza, Mina) o solo activarse desde lateral (Escudo).
 *
 * ── MECANICAS ESPECIALES ─────────────────────────────────────────────────────
 *
 *   Mina:   el jugador puede, en su turno, colocar la Mina en una casilla
 *           libre. Queda oculta. La siguiente pieza que la pise es eliminada.
 *
 *   Escudo: el jugador puede activar su escudo (uso unico). El turno siguiente
 *           del rival ninguna de sus piezas puede ser capturada.
 */
public class PruebaAjedrezConInterfaces {

    // ── Dimensiones ───────────────────────────────────────────────────────────
    private static final int TAM_TABLERO   = 640;
    private static final int ANCHO_LATERAL = 180;
    private static final int VENTANA_X     = TAM_TABLERO + ANCHO_LATERAL;
    private static final int VENTANA_Y     = TAM_TABLERO;
    private static final int X_TABLERO     = ANCHO_LATERAL;

    // ── Constantes de animacion ───────────────────────────────────────────────
    private static final double VEL_PX_SEG      = 400.0;
    private static final double CICLO_PULSO_SEG = 2.0;
    private static final float  OPACIDAD_MIN    = 0.25f;
    private static final long   FPS_OBJETIVO    = 60;
    private static final long   MS_POR_FRAME    = 1000 / FPS_OBJETIVO;

    // ── Estados ───────────────────────────────────────────────────────────────
    private enum Estado { REPOSO, PIEZA_SELECCIONADA, MINA_SELECCIONADA, MOVIENDO }

    // ── Campos ────────────────────────────────────────────────────────────────
    private VentanaGrafica ventana;
    private Tablero        tablero;

    private Reloj  reloj;          // INTERFACES — implementa Animable
    private Mina   mina;           // INTERFACES — implementa Seleccionable + Movible
    private Escudo escudoBlancas;  // INTERFACES — implementa Seleccionable
    private Escudo escudoNegras;   // INTERFACES — implementa Seleccionable

    /** Todos los elementos que implementan Animable. El bucle los actualiza sin conocer su clase. */ // INTERFACES
    private final List<Animable> animables = new ArrayList<>(); // INTERFACES

    private Estado      estado      = Estado.REPOSO;
    private Pieza       piezaActiva = null;
    private Color turno       = Color.BLANCA;
    private double      tiempoPulso = 0.0;

    // ── Punto de entrada ──────────────────────────────────────────────────────

    public static void main(String[] args) {
        new PruebaAjedrezConInterfaces().ejecutar();
    }

    // ── Inicializacion ────────────────────────────────────────────────────────

    private void ejecutar() {
        ventana = new VentanaGrafica(VENTANA_X, VENTANA_Y, "Ajedrez");
        ventana.setDibujadoInmediato(false);

        tablero = new Tablero(X_TABLERO, 0, TAM_TABLERO, TAM_TABLERO, ventana);
        tablero.crearYColocarPiezas();
        crearElementosLaterales();
        registrarAnimables();  // INTERFACES

        actualizarMensaje();
        bucletiempoReal();
        ventana.acaba();
    }

    private void crearElementosLaterales() {
        int cx = ANCHO_LATERAL / 2;

        // Reloj arriba — INTERFACES: implementa Animable
        reloj = new Reloj(cx, 100, 60, ventana);

        // Escudos — INTERFACES: implementan Seleccionable (pero NO Movible)
        escudoBlancas = new Escudo(cx, 230, Color.BLANCA, ventana);
        escudoNegras  = new Escudo(cx, 320, Color.NEGRA,  ventana);

        // Mina abajo — INTERFACES: implementa Seleccionable + Movible
        mina = new Mina(cx, 470, tablero, ventana);
    }

    /**
     * Registra en animables todos los objetos Animable de la escena.
     * El bucle no necesita saber si son Reloj, Alfil u otra clase. // INTERFACES
     */
    private void registrarAnimables() { // INTERFACES
        animables.add(reloj); // INTERFACES

        // Los Alfil implementan Animable; los descubrimos por polimorfismo // INTERFACES
        for (Pieza p : tablero.getPiezas()) { // INTERFACES
            if (p instanceof Animable) animables.add((Animable) p); // INTERFACES
        } // INTERFACES
    } // INTERFACES

    // ── Bucle principal ───────────────────────────────────────────────────────

    private void bucletiempoReal() {
        long tAnterior = System.currentTimeMillis();

        while (!ventana.estaCerrada()) {
            long   tAhora   = System.currentTimeMillis();
            double deltaSeg = (tAhora - tAnterior) / 1000.0;
            tAnterior = tAhora;

            Point click = ventana.getRatonClicado();

            // Actualizar todos los Animable (Reloj + 4 Alfiles) sin conocer su clase // INTERFACES
            for (Animable a : animables) a.actualizar(deltaSeg); // INTERFACES

            procesarEstado(click, deltaSeg);
            if (piezaActiva != null) actualizarAnimacionPieza(deltaSeg);

            ventana.borra();
            dibujarZonaLateral();
            tablero.dibujar();
            ventana.repaint();

            long sobrante = MS_POR_FRAME - (System.currentTimeMillis() - tAhora);
            if (sobrante > 0) try { Thread.sleep(sobrante); } catch (InterruptedException e) {}
        }
    }

    // ── Maquina de estados ────────────────────────────────────────────────────

    private void procesarEstado(Point click, double deltaSeg) {
        switch (estado) {

            case REPOSO:
                if (click != null) procesarClickEnReposo(click);
                break;

            case PIEZA_SELECCIONADA:
                if (click != null) procesarClickConPiezaSeleccionada(click);
                break;

            case MINA_SELECCIONADA: // INTERFACES
                if (click != null) procesarClickConMinaSeleccionada(click); // INTERFACES
                break; // INTERFACES

            case MOVIENDO:
                if (haLlegadoAlDestino()) {
                    piezaActiva.setXPixel(piezaActiva.getX());
                    piezaActiva.setYPixel(piezaActiva.getY());
                    comprobarMina(piezaActiva); // INTERFACES
                    piezaActiva = null;
                    cambiarTurno();
                    estado = Estado.REPOSO;
                    actualizarMensaje();
                }
                break;
        }
    }

    private void procesarClickEnReposo(Point click) {
        // 1. ¿Click sobre el escudo del turno activo? — INTERFACES: Seleccionable
        Escudo escudoTurno = (turno == Color.BLANCA) ? escudoBlancas : escudoNegras; // INTERFACES
        if (escudoTurno.contieneClick(click)) { // INTERFACES
            if (escudoTurno.activar(turno)) { // INTERFACES
                ventana.setMensaje("Escudo activado: las piezas " + nombreTurno()
                        + " no podran ser capturadas en el proximo turno rival.");
                cambiarTurno();
                actualizarMensaje();
            }
            return;
        }

        // 2. ¿Click sobre la mina? — INTERFACES: Seleccionable
        if (mina.contieneClick(click)) { // INTERFACES
            mina.setSeleccionado(true); // INTERFACES
            estado = Estado.MINA_SELECCIONADA; // INTERFACES
            actualizarMensaje();
            return;
        }

        // 3. ¿Click sobre una pieza del turno activo?
        int[] cas = clickACasilla(click);
        if (cas == null) return;
        Pieza candidata = tablero.getPiezaEn(cas[0], cas[1]);
        if (candidata != null && candidata.getColor() == turno) {
            piezaActiva = candidata;
            piezaActiva.setSeleccionado(true); // INTERFACES: Seleccionable
            tiempoPulso = 0.0;
            estado = Estado.PIEZA_SELECCIONADA;
            actualizarMensaje();
        }
    }

    private void procesarClickConPiezaSeleccionada(Point click) {
        int[] cas = clickACasilla(click);

        if (cas == null) {
            // Click fuera del tablero: cancelar
            deseleccionarPieza();
            estado = Estado.REPOSO;
            actualizarMensaje();
            return;
        }

        int fil = cas[0], col = cas[1];

        if (fil == piezaActiva.getFila() && col == piezaActiva.getColumna()) {
            // Click sobre si misma: cancelar
            deseleccionarPieza();
            estado = Estado.REPOSO;

        } else if (piezaActiva.puedeMoverA(fil, col)) { // INTERFACES: Movible
            // Comprobar escudo antes de capturar — INTERFACES
            Pieza enDestino = tablero.getPiezaEn(fil, col);
            if (enDestino != null && escudoBloqueaCaptura(enDestino)) { // INTERFACES
                ventana.setMensaje("Escudo activo: esa pieza no puede ser capturada ahora.");
                return;
            }
            piezaActiva.moverA(fil, col);          // INTERFACES: Movible
            piezaActiva.setSeleccionado(false);    // INTERFACES: Seleccionable
            piezaActiva.setOpacidad(1.0f);
            estado = Estado.MOVIENDO;

        } else {
            // ¿Otra pieza propia? Cambiar seleccion
            Pieza otra = tablero.getPiezaEn(fil, col);
            if (otra != null && otra.getColor() == turno) {
                deseleccionarPieza();
                piezaActiva = otra;
                piezaActiva.setSeleccionado(true); // INTERFACES: Seleccionable
                tiempoPulso = 0.0;
                estado = Estado.PIEZA_SELECCIONADA;
            } else {
                ventana.setMensaje("Movimiento no valido. Elige otro destino"
                        + " o click sobre la misma pieza para cancelar.");
            }
        }
        actualizarMensaje();
    }

    /** Procesa click mientras la Mina esta seleccionada. // INTERFACES */
    private void procesarClickConMinaSeleccionada(Point click) { // INTERFACES
        int[] cas = clickACasilla(click); // INTERFACES
        if (cas != null && mina.puedeMoverA(cas[0], cas[1])) { // INTERFACES: Movible
            mina.moverA(cas[0], cas[1]); // INTERFACES: Movible — coloca la mina
            cambiarTurno(); // colocar la mina consume el turno
        } else {
            mina.setSeleccionado(false); // INTERFACES: Seleccionable — cancelar
        }
        estado = Estado.REPOSO; // INTERFACES
        actualizarMensaje(); // INTERFACES
    } // INTERFACES

    // ── Logica del escudo ─────────────────────────────────────────────────────

    /** Devuelve true si algun escudo activo protege a la pieza indicada. // INTERFACES */
    private boolean escudoBloqueaCaptura(Pieza pieza) { // INTERFACES
        return escudoBlancas.bloqueaCaptura(pieza) || escudoNegras.bloqueaCaptura(pieza); // INTERFACES
    } // INTERFACES

    /** Comprueba si la pieza que acabo de moverse pisa la mina. // INTERFACES */
    private void comprobarMina(Pieza pieza) { // INTERFACES
        if (mina.comprobarExplosion(pieza)) { // INTERFACES
            ventana.setMensaje("BOOM! La mina elimino a "
                    + pieza.getClass().getSimpleName()); // INTERFACES
        } // INTERFACES
    } // INTERFACES

    // ── Cambio de turno ───────────────────────────────────────────────────────

    private void cambiarTurno() {
        // Al inicio del turno del bando protegido, su escudo se agota // INTERFACES
        Color siguiente = (turno == Color.BLANCA) ? Color.NEGRA : Color.BLANCA;
        if (siguiente == Color.BLANCA && escudoBlancas.isActivo()) escudoBlancas.desactivar(); // INTERFACES
        if (siguiente == Color.NEGRA  && escudoNegras .isActivo()) escudoNegras .desactivar(); // INTERFACES
        turno = siguiente;
    }

    // ── Animacion de la pieza activa ──────────────────────────────────────────

    private void actualizarAnimacionPieza(double deltaSeg) {
        if (estado == Estado.PIEZA_SELECCIONADA) {
            tiempoPulso = (tiempoPulso + deltaSeg) % CICLO_PULSO_SEG;
            double fase = tiempoPulso / CICLO_PULSO_SEG;
            float op = (fase < 0.5)
                    ? 1.0f - (float)(fase * 2.0) * (1.0f - OPACIDAD_MIN)
                    : OPACIDAD_MIN + (float)((fase - 0.5) * 2.0) * (1.0f - OPACIDAD_MIN);
            piezaActiva.setOpacidad(op);

        } else if (estado == Estado.MOVIENDO) {
            double destX = piezaActiva.getX(), destY = piezaActiva.getY();
            double dx = destX - piezaActiva.getXPixel();
            double dy = destY - piezaActiva.getYPixel();
            double dist = Math.sqrt(dx * dx + dy * dy);
            double avance = VEL_PX_SEG * deltaSeg;
            if (avance >= dist) {
                piezaActiva.setXPixel(destX); piezaActiva.setYPixel(destY);
            } else {
                piezaActiva.setXPixel(piezaActiva.getXPixel() + (dx / dist) * avance);
                piezaActiva.setYPixel(piezaActiva.getYPixel() + (dy / dist) * avance);
            }
        }
    }

    // ── Dibujo de la zona lateral ─────────────────────────────────────────────

    private void dibujarZonaLateral() {
        // Fondo
        ventana.dibujaRect(0, 0, ANCHO_LATERAL, VENTANA_Y, 0f,
                new java.awt.Color(40, 40, 50), new java.awt.Color(40, 40, 50));
        // Separador
        ventana.dibujaLinea(ANCHO_LATERAL - 1, 0, ANCHO_LATERAL - 1, VENTANA_Y,
                2.0f, new java.awt.Color(80, 80, 110));
        // Titulos
        ventana.dibujaTexto( 12, 22, "Tiempo partida", new Font("Arial", Font.PLAIN, 24), new java.awt.Color(180, 180, 200) );

        // Elementos — se dibujan ellos mismos
        reloj.dibujar();
        escudoBlancas.dibujar();
        escudoNegras.dibujar();
        mina.dibujar();

        // Indicador de turno
        String turnoStr = nombreTurno();
        java.awt.Color colorTurno = (turno == Color.BLANCA)
                ? new java.awt.Color(255, 255, 210) : new java.awt.Color(180, 180, 255);
        ventana.dibujaTexto( 18, 550, "Turno:", new Font("Arial", Font.PLAIN, 24), new java.awt.Color(160, 160, 180) );
        ventana.dibujaTexto( 18, 588, turnoStr, new Font("Arial", Font.PLAIN, 24), colorTurno );
    }

    // ── Utilidades ────────────────────────────────────────────────────────────

    private void deseleccionarPieza() {
        if (piezaActiva != null) {
            piezaActiva.setSeleccionado(false); // INTERFACES: Seleccionable
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

    /** Convierte click en pixeles a [fila, columna] del tablero, o null si cae fuera. */
    private int[] clickACasilla(Point click) {
        int rx = click.x - X_TABLERO;
        if (rx < 0 || rx >= TAM_TABLERO || click.y < 0 || click.y >= TAM_TABLERO) return null;
        int tam = tablero.getTamanoCasilla();
        return new int[]{
            Math.min(Tablero.FILAS    - 1, click.y / tam),
            Math.min(Tablero.COLUMNAS - 1, rx      / tam)
        };
    }

    private String nombreTurno() {
        return (turno == Color.BLANCA) ? "BLANCAS" : "NEGRAS";
    }

    private void actualizarMensaje() {
        switch (estado) {
            case REPOSO:
                ventana.setMensaje("Turno: " + nombreTurno()
                        + "  —  Selecciona una pieza, la mina o el escudo.");
                break;
            case PIEZA_SELECCIONADA:
                ventana.setMensaje("Turno: " + nombreTurno() + "  —  "
                        + piezaActiva.getClass().getSimpleName()
                        + " seleccionado/a. Click en destino o en la misma pieza para cancelar.");
                break;
            case MINA_SELECCIONADA: // INTERFACES
                ventana.setMensaje("MINA seleccionada — click en casilla libre del tablero para colocarla, fuera para cancelar."); // INTERFACES
                break; // INTERFACES
            case MOVIENDO:
                ventana.setMensaje("Turno: " + nombreTurno() + "  —  Moviendo...");
                break;
        }
    }
}
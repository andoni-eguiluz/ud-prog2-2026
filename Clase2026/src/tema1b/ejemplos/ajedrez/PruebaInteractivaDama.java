package tema1b.ejemplos.ajedrez;

import utils.ventanas.ventanaBitmap.VentanaGrafica;
import java.awt.Point;

import tema1b.ejemplos.ajedrez.datos.Color;
import tema1b.ejemplos.ajedrez.datos.Dama;
import tema1b.ejemplos.ajedrez.datos.Tablero;

/**
 * Programa interactivo para mover una Dama por el tablero con el ratón.
 *
 * Lógica de interacción:
 *   - Un click sobre la casilla donde está la Dama la SELECCIONA (halo amarillo).
 *   - Un click posterior sobre cualquier otra casilla intenta MOVERLA:
 *       · Si el movimiento es válido según las reglas de la Dama, se mueve.
 *       · Si no lo es, se muestra un mensaje de aviso y la dama permanece seleccionada.
 *   - Un click sobre la propia casilla de la Dama mientras está seleccionada la DESELECCIONA.
 *
 * El bucle principal usa esperaAClick() de VentanaGrafica, que bloquea el hilo
 * hasta que el usuario realiza un click real (pulsa y suelta en la misma posición).
 * Esto evita bucles de sondeo activo y hace el diseño puramente reactivo a eventos.
 */
public class PruebaInteractivaDama {

    private static final int TAM_VENTANA = 640;

    public static void main(String[] args) {

        // ── Inicialización ────────────────────────────────────────────────────
        VentanaGrafica ventana = new VentanaGrafica(TAM_VENTANA, TAM_VENTANA,
                "Dama interactiva – click para seleccionar y mover");

        Tablero tablero = new Tablero(0, 0, TAM_VENTANA, TAM_VENTANA, ventana);

        // Dama blanca en la posición inicial estándar (fila 7, columna 3)
        Dama dama = new Dama(7, 3, Color.BLANCA, tablero);
        tablero.addPieza(dama);

        // Dibujo inicial
        dibujar(ventana, tablero, dama, "Click sobre la Dama para seleccionarla.");

        // ── Bucle interactivo ─────────────────────────────────────────────────
        // esperaAClick() bloquea hasta que el usuario hace un click real;
        // no consume CPU mientras espera y devuelve las coordenadas del click.
        while (!ventana.estaCerrada()) {

            Point click = ventana.esperaAClick();
            if (click==null || ventana.estaCerrada()) continue;

            // Convertir coordenadas de píxeles a fila/columna del tablero
            int[] posicionClick = tablero.clickACasilla( click );

            // Validar que el click cae dentro del tablero
            if (posicionClick==null) {
                continue;
            }

            if (!dama.isSeleccionada()) {
                // ── Estado: ninguna pieza seleccionada ────────────────────────
                // Solo reacciona si el click es sobre la propia Dama
                if (posicionClick[0] == dama.getFila() && posicionClick[1] == dama.getColumna()) {
                    dama.setSeleccionada(true);
                    dibujar(ventana, tablero, dama,
                            "Dama seleccionada en (" + dama.getFila() + ", " + dama.getColumna()
                            + "). Click en destino para mover, o de nuevo sobre ella para cancelar.");
                }
                // Click en casilla vacía sin pieza seleccionada → ignorar

            } else {
                // ── Estado: Dama seleccionada ─────────────────────────────────
                if (posicionClick[0] == dama.getFila() && posicionClick[1] == dama.getColumna()) {
                    // Click sobre la propia casilla → deseleccionar
                    dama.setSeleccionada(false);
                    dibujar(ventana, tablero, dama,
                            "Selección cancelada. Click sobre la Dama para seleccionarla.");

                } else {
                    // Click en otra casilla → intentar mover
                    if (dama.puedeMoverA(posicionClick[0], posicionClick[1])) {
                        dama.moverA(posicionClick[0], posicionClick[1]);
                			dama.sincronizarPixeles();   // Hay que hacerlo si el movimiento tiene animación, si no está la animación no hace falta
                        dama.setSeleccionada(false);
                        dibujar(ventana, tablero, dama,
                                "Dama movida a (" + dama.getFila() + ", " + dama.getColumna()
                                + "). Click sobre la Dama para seleccionarla.");
                    } else {
                        // Movimiento inválido: mantener selección y avisar
                        dibujar(ventana, tablero, dama,
                                "Movimiento no válido a (" + posicionClick[0] + ", " + posicionClick[0]
                                + "). Elige otro destino o haz click sobre la Dama para cancelar.");
                    }
                }
            }
        }

        ventana.acaba();
    }

    // ── Método auxiliar de refresco ───────────────────────────────────────────

    /**
     * Borra la ventana, redibuja el tablero con todas sus piezas y
     * actualiza el mensaje de la barra inferior.
     *
     * @param ventana ventana gráfica
     * @param tablero tablero a dibujar
     * @param dama    dama actual (para información de estado)
     * @param mensaje texto informativo para la barra inferior
     */
    private static void dibujar(VentanaGrafica ventana, Tablero tablero,
                                 Dama dama, String mensaje) {
        ventana.borra();
        tablero.dibujar();
        ventana.setMensaje(mensaje);
    }
}
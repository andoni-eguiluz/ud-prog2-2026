package tema1c.ejemplos.ajedrez.datos;

import utils.ventanas.ventanaBitmap.VentanaGrafica;
import java.awt.Font;
import java.awt.Point;

/**
 * Escudo: elemento especial en la zona lateral que un jugador puede activar
 * durante su turno. Cuando está activo, ninguna de sus piezas puede ser
 * capturada durante el turno siguiente del rival.
 *
 * Hereda de ElementoVisual.
 * Implementa Seleccionable: puede clickarse para activarlo. // INTERFACES
 * NO implementa Movible: no se coloca en el tablero,        // INTERFACES
 *   actúa desde su posición fija en la zona lateral.        // INTERFACES
 *
 * Cada bando tiene su propio Escudo. Solo puede usarse una vez por partida.
 *
 * Estados:
 *   - DISPONIBLE: en la zona lateral, seleccionable.
 *   - ACTIVO:     activado este turno; el rival no puede capturar.
 *   - AGOTADO:    ya fue usado; no puede volver a activarse.
 */
public class Escudo extends ElementoVisual implements Seleccionable { // INTERFACES

    // -------------------------------------------------------------------------
    // Estado
    // -------------------------------------------------------------------------

    private boolean seleccionado = false; // INTERFACES
    private boolean activo       = false;
    private boolean agotado      = false;
    private final Color colorBando; // a qué bando protege

    private static final int RADIO = 24;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    /**
     * Crea un escudo en la zona lateral.
     *
     * @param cx         coordenada x del centro en píxeles
     * @param cy         coordenada y del centro en píxeles
     * @param colorBando bando al que protege este escudo
     * @param ventana    ventana gráfica
     */
    public Escudo(int cx, int cy, Color colorBando, VentanaGrafica ventana) {
        super(cx, cy, RADIO * 2, RADIO * 2, ventana);
        this.colorBando = colorBando;
    }

    // -------------------------------------------------------------------------
    // Seleccionable (INTERFACES)
    // -------------------------------------------------------------------------

    @Override // INTERFACES
    public boolean contieneClick(Point click) { // INTERFACES
        if (agotado) return false; // ya no se puede usar // INTERFACES
        int dx = click.x - getX(); // INTERFACES
        int dy = click.y - getY(); // INTERFACES
        return Math.sqrt(dx * dx + dy * dy) <= RADIO; // INTERFACES
    } // INTERFACES

    @Override // INTERFACES
    public void setSeleccionado(boolean s) { this.seleccionado = s; } // INTERFACES

    @Override // INTERFACES
    public boolean isSeleccionado() { return seleccionado; } // INTERFACES

    // -------------------------------------------------------------------------
    // Lógica del escudo
    // -------------------------------------------------------------------------

    /**
     * Activa el escudo para el turno indicado. Solo funciona si está disponible
     * y corresponde al bando del turno actual.
     *
     * @param turnoActual bando que está jugando en este momento
     * @return true si se activó correctamente
     */
    public boolean activar(Color turnoActual) {
        if (agotado || activo) return false;
        if (turnoActual != colorBando) return false;
        activo = true;
        seleccionado = false;
        return true;
    }

    /**
     * Desactiva el escudo al inicio del siguiente turno del bando protegido
     * y lo marca como agotado (uso único).
     */
    public void desactivar() {
        activo  = false;
        agotado = true;
    }

    /** Indica si el escudo está activo (protege este turno). */
    public boolean isActivo()  { return activo; }

    /** Indica si el escudo ya fue usado y no puede volver a activarse. */
    public boolean isAgotado() { return agotado; }

    /** Bando al que protege. */
    public Color getColorBando() { return colorBando; }

    /**
     * Comprueba si una captura debe ser bloqueada por este escudo.
     * Una captura está bloqueada si el escudo está activo y la pieza
     * a capturar es del bando protegido.
     *
     * @param piezaACapturar pieza que el rival intenta capturar
     * @return true si la captura debe bloquearse
     */
    public boolean bloqueaCaptura(Pieza piezaACapturar) {
        return activo && piezaACapturar.getColor() == colorBando;
    }

    // -------------------------------------------------------------------------
    // Dibujo
    // -------------------------------------------------------------------------

    @Override
    public void dibujar() {
        if (agotado) {
            dibujarAgotado();
            return;
        }
        VentanaGrafica v = getVentana();
        int cx = getX();
        int cy = getY();

        // Color del escudo según bando y estado
        java.awt.Color colorRelleno;
        java.awt.Color colorBorde;
        if (activo) {
            colorRelleno = new java.awt.Color(80, 200, 80, 200);   // verde brillante: activo
            colorBorde   = new java.awt.Color(0, 180, 0);
        } else if (seleccionado) {
            colorRelleno = new java.awt.Color(200, 200, 80, 200);  // amarillo: seleccionado
            colorBorde   = new java.awt.Color(180, 180, 0);
        } else if (colorBando == Color.BLANCA) {
            colorRelleno = new java.awt.Color(220, 220, 255, 200);
            colorBorde   = new java.awt.Color(100, 100, 200);
        } else {
            colorRelleno = new java.awt.Color(60, 60, 80, 200);
            colorBorde   = new java.awt.Color(30, 30, 60);
        }

        // Forma de escudo (hexágono aproximado con círculo)
        v.dibujaCirculo(cx, cy, RADIO, 3.0f, colorBorde, colorRelleno);

        // Cruz interior
        v.dibujaLinea(cx, cy - RADIO + 8, cx, cy + RADIO - 8, 3.0f, colorBorde);
        v.dibujaLinea(cx - RADIO + 8, cy, cx + RADIO - 8, cy, 3.0f, colorBorde);

        // Etiqueta
        String etiqueta = activo ? "ACTIVO" : (colorBando == Color.BLANCA ? "ESCUDO B" : "ESCUDO N");
        v.dibujaTexto( cx-62,  cy+RADIO+4, etiqueta, new Font("Arial", Font.PLAIN, 24), colorBorde );
    }

    private void dibujarAgotado() {
        VentanaGrafica v = getVentana();
        int cx = getX();
        int cy = getY();
        v.dibujaCirculo(cx, cy, RADIO, 2.0f,
                new java.awt.Color(150, 150, 150), new java.awt.Color(200, 200, 200, 80));
        v.dibujaTexto( cx-62,  cy+RADIO+4, "AGOTADO", new Font("Arial", Font.PLAIN, 24), java.awt.Color.DARK_GRAY );
    }

}
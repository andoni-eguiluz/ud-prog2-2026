package tema1c.ejemplos.ajedrez.datos;

import utils.ventanas.ventanaBitmap.VentanaGrafica;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;

/**
 * Mina: elemento especial que el jugador puede coger de la zona lateral
 * y depositar en cualquier casilla libre del tablero.
 *
 * Cuando una pieza enemiga entra en la casilla donde hay una mina,
 * la pieza es eliminada del tablero (la mina "explota").
 *
 * Hereda de ElementoVisual.
 * Implementa Seleccionable: puede ser clickada para cogerla. // INTERFACES
 * Implementa Movible: puede colocarse en una casilla del tablero. // INTERFACES
 *
 * A diferencia de las piezas de ajedrez, la Mina no tiene color de bando:
 * afecta a cualquier pieza que pise su casilla.
 *
 * Estado:
 *   - En la zona lateral: visible, seleccionable, no en tablero.
 *   - En el tablero:      activa, oculta (no se dibuja hasta explotar).
 *   - Explotada:          se elimina del tablero.
 */
public class Mina extends ElementoVisual implements Seleccionable, Movible { // INTERFACES

    // -------------------------------------------------------------------------
    // Estado
    // -------------------------------------------------------------------------

    private boolean seleccionado = false; // INTERFACES
    private boolean enTablero    = false; // true cuando ya ha sido colocada
    private boolean explotada    = false; // true tras activarse
    private int     filaTablero  = -1;   // posición lógica en el tablero
    private int     colTablero   = -1;
    private Tablero tablero;

    /** Posición original en la zona lateral (para resetear si se cancela). */
    private final int xOrigen;
    private final int yOrigen;

    // ── Tamaño del icono en la zona lateral
    private static final int RADIO_LATERAL = 22;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    /**
     * Crea una mina en la zona lateral de la ventana.
     *
     * @param cx      coordenada x del centro en la zona lateral (píxeles)
     * @param cy      coordenada y del centro en la zona lateral (píxeles)
     * @param tablero tablero de juego (para verificar casillas libres)
     * @param ventana ventana gráfica
     */
    public Mina(int cx, int cy, Tablero tablero, VentanaGrafica ventana) {
        super(cx, cy, RADIO_LATERAL * 2, RADIO_LATERAL * 2, ventana);
        this.tablero = tablero;
        this.xOrigen = cx;
        this.yOrigen = cy;
    }
    
    public void resetPosicion() {
    	setX( xOrigen );
    	setY( yOrigen );
    }

    // -------------------------------------------------------------------------
    // Seleccionable (INTERFACES)
    // -------------------------------------------------------------------------

    @Override // INTERFACES
    public boolean contieneClick(Point click) { // INTERFACES
        if (enTablero || explotada) return false; // solo seleccionable desde la zona lateral // INTERFACES
        int dx = click.x - getX(); // INTERFACES
        int dy = click.y - getY(); // INTERFACES
        return Math.sqrt(dx * dx + dy * dy) <= RADIO_LATERAL; // INTERFACES
    } // INTERFACES

    @Override // INTERFACES
    public void setSeleccionado(boolean s) { this.seleccionado = s; } // INTERFACES

    @Override // INTERFACES
    public boolean isSeleccionado() { return seleccionado; } // INTERFACES

    // -------------------------------------------------------------------------
    // Movible (INTERFACES)
    // -------------------------------------------------------------------------

    /**
     * La mina puede colocarse en cualquier casilla libre del tablero. // INTERFACES
     * Una vez colocada, no puede volver a moverse. // INTERFACES
     */
    @Override // INTERFACES
    public boolean puedeMoverA(int fila, int columna) { // INTERFACES
        if (enTablero || explotada) return false; // INTERFACES
        if (fila < 0 || fila >= Tablero.FILAS || columna < 0 || columna >= Tablero.COLUMNAS) return false; // INTERFACES
        return !tablero.estaOcupada(fila, columna); // solo casillas libres // INTERFACES
    } // INTERFACES

    /**
     * Coloca la mina en la casilla indicada del tablero. // INTERFACES
     * A partir de este momento queda oculta hasta que explote. // INTERFACES
     */
    @Override // INTERFACES
    public void moverA(int fila, int columna) { // INTERFACES
        if (!puedeMoverA(fila, columna)) return; // INTERFACES
        filaTablero = fila; // INTERFACES
        colTablero  = columna; // INTERFACES
        enTablero   = true; // INTERFACES
        seleccionado = false; // INTERFACES
        // Actualizar posición visual al centro de la casilla
        setX(tablero.xCentroCasilla(columna)); // INTERFACES
        setY(tablero.yCentroCasilla(fila)); // INTERFACES
    } // INTERFACES

    // -------------------------------------------------------------------------
    // Lógica de explosión
    // -------------------------------------------------------------------------

    /**
     * Comprueba si la pieza indicada pisa la mina. Si es así, la elimina
     * del tablero y marca la mina como explotada.
     *
     * @param pieza pieza que acaba de moverse
     * @return true si la mina explotó (la pieza fue eliminada)
     */
    public boolean comprobarExplosion(Pieza pieza) {
        if (!enTablero || explotada) return false;
        if (pieza.getFila() == filaTablero && pieza.getColumna() == colTablero) {
            tablero.removePieza(pieza);
            explotada = true;
            return true;
        }
        return false;
    }

    // -------------------------------------------------------------------------
    // Getters de estado
    // -------------------------------------------------------------------------

    public boolean isEnTablero()  { return enTablero; }
    public boolean isExplotada()  { return explotada; }
    public int     getFilaTablero() { return filaTablero; }
    public int     getColTablero()  { return colTablero; }

    // -------------------------------------------------------------------------
    // Dibujo
    // -------------------------------------------------------------------------

    @Override
    public void dibujar() {
        if (explotada) return;          // ya no existe
        if (enTablero) return;          // oculta hasta que explote

        // Dibujo en la zona lateral
        VentanaGrafica v = getVentana();
        int cx = getX();
        int cy = getY();

        // Cuerpo de la mina (círculo oscuro con detalles)
        Color colorCuerpo = seleccionado
                ? new Color(220, 80, 80)   // rojo al seleccionar
                : new Color(50, 50, 50);    // gris oscuro normal
        v.dibujaCirculo(cx, cy, RADIO_LATERAL, 2.0f, Color.BLACK, colorCuerpo);

        // Mecha (línea hacia arriba-derecha)
        v.dibujaLinea(cx + RADIO_LATERAL - 4, cy - RADIO_LATERAL + 4,
                      cx + RADIO_LATERAL + 6, cy - RADIO_LATERAL - 6,
                      2.5f, new Color(120, 80, 20));

        // Chispa en la mecha
        v.dibujaCirculo(cx + RADIO_LATERAL + 6, cy - RADIO_LATERAL - 6,
                        4, 1.0f, Color.ORANGE, Color.YELLOW);

        // Etiqueta
        v.dibujaTexto( cx-25,  cy+RADIO_LATERAL+28, "MINA", new Font("Arial", Font.PLAIN, 24), Color.DARK_GRAY );
    }
    
}
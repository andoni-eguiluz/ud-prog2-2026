package tema1b.ejemplos.ajedrez.datos;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

/**
 * Clase abstracta base de la jerarquía de elementos visuales.
 * Representa cualquier elemento que puede dibujarse en una VentanaGrafica.
 */
public abstract class ElementoVisual {

    // Coordenadas del centro de la figura (en píxeles)
    private int x;
    private int y;
    // Dimensiones de la figura (en píxeles)
    private int ancho;
    private int alto;
    // Ventana en la que se dibuja el elemento
    private VentanaGrafica ventana;

    // -------------------------------------------------------------------------
    // Constructores
    // -------------------------------------------------------------------------

    /**
     * Constructor completo.
     *
     * @param x       coordenada x del centro (píxeles)
     * @param y       coordenada y del centro (píxeles)
     * @param ancho   anchura en píxeles
     * @param alto    altura en píxeles
     * @param ventana ventana gráfica donde se dibujará el elemento
     */
    public ElementoVisual(int x, int y, int ancho, int alto, VentanaGrafica ventana) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.ventana = ventana;
    }

    // -------------------------------------------------------------------------
    // Getters y setters
    // -------------------------------------------------------------------------

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public int getAncho() { return ancho; }
    public void setAncho(int ancho) { this.ancho = ancho; }

    public int getAlto() { return alto; }
    public void setAlto(int alto) { this.alto = alto; }

    public VentanaGrafica getVentana() { return ventana; }
    public void setVentana(VentanaGrafica ventana) { this.ventana = ventana; }

    // -------------------------------------------------------------------------
    // Métodos comunes
    // -------------------------------------------------------------------------

    /**
     * Representación textual del elemento.
     */
    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[x=" + x + ", y=" + y
                + ", ancho=" + ancho + ", alto=" + alto + "]";
    }

    /**
     * Dos elementos son iguales si tienen la misma posición y dimensiones.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ElementoVisual other = (ElementoVisual) obj;
        return x == other.x && y == other.y
                && ancho == other.ancho && alto == other.alto;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + ancho;
        result = 31 * result + alto;
        return result;
    }

    /**
     * Dibuja el elemento en la ventana gráfica.
     * Cada subclase concreta debe implementar cómo se visualiza.
     */
    public abstract void dibujar();
}
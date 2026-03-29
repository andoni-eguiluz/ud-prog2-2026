package tema1c.ejemplos.ajedrez.datos;

import utils.ventanas.ventanaBitmap.VentanaGrafica;
import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;

/**
 * Reloj analógico que se dibuja en la zona lateral de la ventana.
 *
 * Hereda de ElementoVisual (tiene posición, tamaño y ventana).
 * Implementa Animable: el bucle principal llama a actualizar(deltaSeg)
 * cada fotograma para avanzar el tiempo del reloj.
 *
 * Dibuja:
 *  - Esfera circular con marcas de horas.
 *  - Minutero (aguja larga).
 *  - Segundero (aguja fina y roja).
 *
 * Lleva la cuenta de los segundos totales de partida transcurridos.
 */
public class Reloj extends ElementoVisual implements Animable { // INTERFACES

    // -------------------------------------------------------------------------
    // Estado del reloj
    // -------------------------------------------------------------------------

	/** Segundos totales acumulados desde el inicio de la partida. */
    private double segundosTotales = 0.0;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    /**
     * Crea el reloj centrado en (cx, cy) con el radio indicado.
     *
     * @param cx      coordenada x del centro en píxeles
     * @param cy      coordenada y del centro en píxeles
     * @param radio   radio del reloj en píxeles
     * @param ventana ventana gráfica donde se dibujará
     */
    public Reloj(int cx, int cy, int radio, VentanaGrafica ventana) {
        super(cx, cy, radio * 2, radio * 2, ventana);
    }

    // -------------------------------------------------------------------------
    // Animable: avance del tiempo (INTERFACES)
    // -------------------------------------------------------------------------

    /**
     * Avanza el tiempo interno del reloj en cada fotograma.
     * El bucle principal lo llama automáticamente con el deltaSeg real. // INTERFACES
     */
    @Override // INTERFACES
    public void actualizar(double deltaSeg) { // INTERFACES
        segundosTotales += deltaSeg; // INTERFACES
    } // INTERFACES

    // -------------------------------------------------------------------------
    // Getters de tiempo
    // -------------------------------------------------------------------------

    public int getSegundos() { return (int) segundosTotales % 60; }
    public int getMinutos()  { return (int)(segundosTotales / 60) % 60; }

    // -------------------------------------------------------------------------
    // Dibujo
    // -------------------------------------------------------------------------

    @Override
    public void dibujar() {
        VentanaGrafica v = getVentana();
        int cx    = getX();
        int cy    = getY();
        int radio = getAncho() / 2;

        // Esfera
        v.dibujaCirculo(cx, cy, radio, 3.0f, Color.DARK_GRAY, new Color(245, 245, 220));

        // Marcas de horas (12 puntos)
        for (int h = 0; h < 12; h++) {
            double ang = Math.toRadians(h * 30 - 90);
            int mx = cx + (int)((radio - 6) * Math.cos(ang));
            int my = cy + (int)((radio - 6) * Math.sin(ang));
            v.dibujaCirculo(mx, my, 3, 1.0f, Color.DARK_GRAY, Color.DARK_GRAY);
        }

        // Minutero
        double angMin = Math.toRadians(getMinutos() * 6 + getSegundos() * 0.1 - 90);
        int mxMin = cx + (int)((radio - 12) * Math.cos(angMin));
        int myMin = cy + (int)((radio - 12) * Math.sin(angMin));
        v.dibujaLinea(cx, cy, mxMin, myMin, 3.0f, Color.DARK_GRAY);

        // Segundero
        double angSeg = Math.toRadians(getSegundos() * 6 - 90);
        int mxSeg = cx + (int)((radio - 8) * Math.cos(angSeg));
        int mySeg = cy + (int)((radio - 8) * Math.sin(angSeg));
        v.dibujaLinea(cx, cy, mxSeg, mySeg, 1.5f, Color.RED);

        // Centro
        v.dibujaCirculo(cx, cy, 4, 1.0f, Color.DARK_GRAY, Color.DARK_GRAY);

        // Tiempo en texto bajo el reloj
        String tiempo = String.format("%02d:%02d", getMinutos(), getSegundos());
        v.dibujaTexto( cx-28,  cy+radio+22, tiempo, new Font("Arial", Font.BOLD, 24), Color.WHITE );
    }
}
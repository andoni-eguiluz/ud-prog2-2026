package tema1b.ejemplos.ajedrez;

import tema1b.ejemplos.ajedrez.datos.Color;
import tema1b.ejemplos.ajedrez.datos.Dama;
import tema1b.ejemplos.ajedrez.datos.Tablero;
import utils.ventanas.ventanaBitmap.VentanaGrafica;

/**
 * Clase de demostración: crea un tablero con dos damas y lo dibuja en pantalla.
 */
public class PruebaRapida {

    public static void main(String[] args) {
        VentanaGrafica ventana = new VentanaGrafica(700, 640, "Ajedrez – demo jerarquía");
        
        Tablero tablero = new Tablero( 50, 20, 600, 600, ventana);
        // Crear una Dama blanca en la posición estándar del ajedrez (fila 7, columna 3)
        Dama damaBlanca = new Dama(7, 3, Color.BLANCA, tablero);
        tablero.addPieza(damaBlanca);
        // Crear una Dama negra en la posición estándar (fila 0, columna 3)
        Dama damaNegra = new Dama(0, 3, Color.NEGRA, tablero);
        tablero.addPieza(damaNegra);

        // Dibujar tablero y piezas
        tablero.dibujar();

        // Pequeña demo de movimiento: mover la dama blanca
        ventana.espera(1500);
        System.out.println("Intentando mover Dama blanca a (4, 6): " + damaBlanca.puedeMoverA(4, 3));
        damaBlanca.moverA(4, 6);
        	damaBlanca.sincronizarPixeles();   // Hay que hacerlo si el movimiento tiene animación, si no está la animación no hace falta
        tablero.dibujar();
        ventana.espera(1500);
        System.out.println("Intentando mover Dama blanca a (5, 4): " + damaBlanca.puedeMoverA(4, 7));
        damaBlanca.moverA(5, 4);
    		damaBlanca.sincronizarPixeles();   // Hay que hacerlo si el movimiento tiene animación, si no está la animación no hace falta
        tablero.dibujar();
        ventana.espera(1500);
        System.out.println("Intentando mover Dama blanca a (2, 6): " + damaBlanca.puedeMoverA(4, 7));
        damaBlanca.moverA(2, 6);
    		damaBlanca.sincronizarPixeles();   // Hay que hacerlo si el movimiento tiene animación, si no está la animación no hace falta
        tablero.dibujar();
        ventana.espera(1500);
        ventana.acaba();
    }
}
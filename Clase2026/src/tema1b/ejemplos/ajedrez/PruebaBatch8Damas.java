package tema1b.ejemplos.ajedrez;

import tema1b.ejemplos.ajedrez.datos.Color;
import tema1b.ejemplos.ajedrez.datos.Dama;
import tema1b.ejemplos.ajedrez.datos.Tablero;
import utils.ventanas.ventanaBitmap.VentanaGrafica;

/**
 * Solución al problema de las 8 damas con visualización gráfica.
 *
 * Coloca 8 damas en un tablero (una por fila) probando todas las combinaciones
 * posibles. Cuando encuentra una posición en la que ninguna dama amenaza a
 * las demás, la dibuja y espera 5 segundos antes de continuar.
 * Al finalizar imprime por consola el número total de soluciones encontradas.
 */
public class PruebaBatch8Damas {

    private static final int N              = 8;
    private static final int TAM_VENTANA    = 640;
    private static final int PAUSA_SOLUCION = 800; // ms

    private VentanaGrafica ventana;
    private Tablero        tablero;
    private int            numSoluciones;

    // -------------------------------------------------------------------------
    // Punto de entrada
    // -------------------------------------------------------------------------

    public static void main(String[] args) {
        new PruebaBatch8Damas().ejecutar();
    }

    // -------------------------------------------------------------------------
    // Lógica principal
    // -------------------------------------------------------------------------

    private void ejecutar() {
        ventana  = new VentanaGrafica(TAM_VENTANA, TAM_VENTANA, "8 Damas – búsqueda exhaustiva");
        ventana.setMensaje( "Buscando soluciones!  Pulsa Ctrl para no detenerse en cada solución encontrada" );
        tablero  = new Tablero(0, 0, TAM_VENTANA, TAM_VENTANA, ventana);
        numSoluciones = 0;

        // columnas[fila] = columna donde está la dama de esa fila
        int[] columnas = new int[N];

        // Solución con backtracking recursiva:
        // colocarFila(0, columnas);
        // Solución iterativa (menos elegante pero más directa):
        colocar8Damas( columnas );

        // Mostrar resultado y cerrar
        System.out.println("──────────────────────────────────────");
        System.out.println("Soluciones válidas encontradas: " + numSoluciones);
        System.out.println("──────────────────────────────────────");

        ventana.acaba();
    }
    
    
    private void colocar8Damas(int[] columnas) {
    	for (int c0 = 0; c0 < N; c0++) {
    		columnas[0] = c0;
    		cambiarYDibujarEstado(columnas, 1, false);
    		for (int c1 = 0; c1 < N; c1++) { 
    			if (seAmenazan(0,c0, 1,c1)) continue;  // Evita probar muchas soluciones imposibles
    			columnas[1] = c1;
    			cambiarYDibujarEstado(columnas, 2, false);
    			for (int c2 = 0; c2 < N; c2++) { 
    				if (hayConflicto(columnas, 2, c2)) continue;  // Evita probar muchas soluciones imposibles
    				columnas[2] = c2;
    				cambiarYDibujarEstado(columnas, 3, false);
    				for (int c3 = 0; c3 < N; c3++) { 
    					if (hayConflicto(columnas, 3, c3)) continue;  // Evita probar muchas soluciones imposibles
    					columnas[3] = c3;
    					cambiarYDibujarEstado(columnas, 4, false);
    					for (int c4 = 0; c4 < N; c4++) { 
    						if (hayConflicto(columnas, 4, c4)) continue;  // Evita probar muchas soluciones imposibles
    						columnas[4] = c4;
    						cambiarYDibujarEstado(columnas, 5, false);
    						for (int c5 = 0; c5 < N; c5++) { 
    							if (hayConflicto(columnas, 5, c5)) continue;  // Evita probar muchas soluciones imposibles
    							columnas[5] = c5;
    							cambiarYDibujarEstado(columnas, 6, false);
    							for (int c6 = 0; c6 < N; c6++) { 
    								// if (hayConflicto(columnas, 6, c6)) continue;  // Evita probar muchas soluciones imposibles
    								columnas[6] = c6;
    								cambiarYDibujarEstado(columnas, 7, false);
    								for (int c7 = 0; c7 < N; c7++) { 
    									// if (hayConflicto(columnas, 7, c7)) continue;  // Evita probar muchas
    									columnas[7] = c7;
    									if (hayConflicto(columnas)) {  // Comprueba todas las interacciones
        									cambiarYDibujarEstado(columnas, 8, true);
    										continue;
    									}
    									// Las 8 damas colocadas sin conflictos: solución válida
    									cambiarYDibujarEstado(columnas, 8, true);
    									numSoluciones++;
    									System.out.println("Solución #" + numSoluciones + ": " + descripcion(columnas));
    							        ventana.setMensaje( "Encontradas " + numSoluciones + " soluciones. ¡Buscando más! Pulsa Ctrl para no detenerse en cada solución encontrada" );
    									if (!ventana.isControlPulsado()) {
    										ventana.espera(PAUSA_SOLUCION);
    									}
    								}
    							}
    						}
    					}
    				}
    			}
    		}
    	}
    }

    /**
     * Indica si colocar una dama en (fila, col) entraría en conflicto
     * con alguna de las damas ya colocadas en las filas 0..(fila-1).
     */
    private boolean hayConflicto(int[] columnas, int fila, int col) {
        for (int f = 0; f < fila; f++) {
            if (seAmenazan(f, columnas[f], fila, col)) return true;
        }
        return false;
    }    

    /**
     * Indica si hay conflicto con alguna de las 8 damas colocadas
     */
    private boolean hayConflicto(int[] columnas) {
    	for (int f = 0; f < columnas.length; f++) {
    		if (hayConflicto( columnas, f, columnas[f])) return true;
        }
        return false;
    }    
    
    /** SOLUCIÓN RECURSIVA
     * Coloca recursivamente una dama en cada fila probando todas las columnas.
     *
     * @param fila     fila actual que se está intentando rellenar (0-7)
     * @param columnas array donde columnas[f] indica la columna de la dama en la fila f
     */
    @SuppressWarnings("unused")
	private void colocarFila(int fila, int[] columnas) {
        if (ventana.estaCerrada()) return;

        for (int col = 0; col < N; col++) {
            columnas[fila] = col;

            // Dibujar estado actual (rápido, sin pausa)
            cambiarYDibujarEstado(columnas, fila + 1, true);

            if (fila == N - 1) {
                // Hemos colocado las 8 damas: comprobar si es solución válida
                if (esSolucionValida(columnas)) {
                    numSoluciones++;
                    System.out.println("Solución #" + numSoluciones + ": " + descripcion(columnas));
                    cambiarYDibujarEstado(columnas, N, true);        // redibujar resaltando solución
                    ventana.espera(PAUSA_SOLUCION);
                }
            } else {
                // Poda: continuar solo si la fila actual no genera conflicto
                if (!hayConflictoHastaFila(columnas, fila)) {
                    colocarFila(fila + 1, columnas);
                }
            }

            if (ventana.estaCerrada()) return;
        }
    }

    // -------------------------------------------------------------------------
    // Validación
    // -------------------------------------------------------------------------

    /**
     * Comprueba si la configuración completa de 8 damas es válida:
     * ninguna dama ataca a otra.
     */
    private boolean esSolucionValida(int[] columnas) {
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (seAmenazan(i, columnas[i], j, columnas[j])) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Comprueba si alguna de las damas colocadas hasta la fila {@code hastaFila}
     * (inclusive) entra en conflicto con alguna anterior. Útil para poda.
     */
    private boolean hayConflictoHastaFila(int[] columnas, int hastaFila) {
        for (int i = 0; i < hastaFila; i++) {
            if (seAmenazan(i, columnas[i], hastaFila, columnas[hastaFila])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Indica si dos damas situadas en (f1,c1) y (f2,c2) se amenazan mutuamente.
     * Dos damas se amenazan si están en la misma columna o en la misma diagonal.
     * (Por diseño del algoritmo, nunca están en la misma fila.)
     */
    private boolean seAmenazan(int f1, int c1, int f2, int c2) {
        return c1 == c2                                     // misma columna
            || Math.abs(f1 - f2) == Math.abs(c1 - c2);    // misma diagonal
    }

    // -------------------------------------------------------------------------
    // Visualización
    // -------------------------------------------------------------------------

    /**
     * Borra el tablero y redibuja las {@code numDamas} primeras damas
     * según el array de columnas.
     *
     * @param columnas  array de columnas (una entrada por fila)
     * @param numDamas  cuántas damas se han colocado hasta ahora
     * @param dibujar	si es true dibuja, si es false solo cambia el tablero sin dibujar en ventana
     */
    private void cambiarYDibujarEstado(int[] columnas, int numDamas, boolean dibujar) {
        // Limpiar piezas anteriores y redibujar
        tablero.getPiezas().clear();
        for (int fila = 0; fila < numDamas; fila++) {
            Color colorPieza = (fila % 2 == 0) ? Color.BLANCA : Color.NEGRA;
            Dama dama = new Dama(fila, columnas[fila], colorPieza, tablero);
            tablero.addPieza(dama);
        }
        if (dibujar) {
	        ventana.borra();
	        tablero.dibujar();
        }
    }

    // -------------------------------------------------------------------------
    // Utilidades
    // -------------------------------------------------------------------------

    /** Genera una descripción compacta de la solución para la consola. */
    private String descripcion(int[] columnas) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < N; i++) {
            sb.append("f").append(i).append("→c").append(columnas[i]);
            if (i < N - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

}
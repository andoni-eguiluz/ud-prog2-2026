package tema1c.resueltos.runnerNaves;

import java.util.ArrayList;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public interface Salible {
	/** Comprueba si el objeto se sale completamente por el lado izquierdo de la ventana
	 * @param v	Ventana de comprobación
	 * @return	true si se ha salido completamente por la izquierda, false en caso contrario
	 */
	public boolean seSalePorLaIzquierda( VentanaGrafica v );
	/** Gestiona la lógica de salida del elemento cuando salga por la izquierda
	 * @param lElementos	Lista de elementos (de la cual quitarlo, si procede)
	 */
	void sal( ArrayList<ObjetoEspacial> lElementos );
}

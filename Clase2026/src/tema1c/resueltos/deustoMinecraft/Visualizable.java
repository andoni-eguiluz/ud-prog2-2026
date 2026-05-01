package tema1c.resueltos.deustoMinecraft;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public interface Visualizable {
	/** Dibuja el objeto en la ventana que le corresponde, no lo dibuja si no tiene ventana
	 */
	public void dibujar();
	/** Asocia una ventana gráfica al objeto
	 * @param v	Ventana gráfica en la que dibujarlo a partir de ahora
	 */
	public void setVentana( VentanaGrafica v );
}

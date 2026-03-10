package tema1b.ejemplos.juegoBolas;

import java.awt.Point;
import utils.ventanas.ventanaBitmap.VentanaGrafica;

public abstract class ObjetoJuego /* extends Object */ {
	protected double x;
	protected double y;
	
	// ObjetoJuego() se define automáticamente si no hay otro

	public ObjetoJuego( double x, double y ) {
		this.x = x;
		this.y = y;
	}
	
	/** Devuelve la coordenada x del centro del objeto de juego, con respecto a la pantalla
	 * @return	Coordenada x en píxels
	 */
	public double getX() {
		return x;
	}
	/** Modifica la coordenada x del centro del objeto de juego, con respecto a la pantalla
	 * @param x	Nueva coordenada x
	 */
	public void setX(double x) {
		this.x = x;
	}
	/** Devuelve la coordenada y del centro del objeto de juego, con respecto a la pantalla
	 * @return	Coordenada y en píxels
	 */
	public double getY() {
		return y;
	}
	/** Modifica la coordenada y del centro del objeto de juego, con respecto a la pantalla
	 * @param y	Nueva coordenada y
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	/** Dibuja un objeto de juego
	 * @param v	Ventana en la que dibujar
	 */
	public abstract void dibuja( VentanaGrafica v );
	
	
	/** Comprueba si un punto está dentro o no del objeto de juego
	 * @param p	punto a comprobar
	 * @return	true si está dentro, false si no
	 */
	public abstract boolean contieneA( Point p );

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
}

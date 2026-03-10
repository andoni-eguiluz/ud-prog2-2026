package tema1b.resueltos.ej1b7;

import java.awt.Point;
import java.awt.geom.Point2D;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Clase Figura para herencia de clicker
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public abstract class Figura {

	// NO STATIC
	
	protected double xCentro;
	protected double yCentro;
	protected long tiempoCreacionMs;
	protected double velX;        // Velocidad horizontal de la figura en píxels / segundo 
	protected double velY;        // Velocidad vertical de la figura en píxels / segundo
	
	/** Crea una figura nueva, con velocidad 0
	 * @param xCentro	Coordenada x del centro del círculo, en píxeles (de izquierda a derecha)
	 * @param yCentro	Coordenada y del centro del círculo, en píxeles (de arriba abajo)
	 */
	public Figura(int xCentro, int yCentro) {
		this.xCentro = xCentro;
		this.yCentro = yCentro;
		tiempoCreacionMs = System.currentTimeMillis();
	}
	
	public int getxCentro() {
		return (int) Math.round( xCentro );
	}

	public void setxCentro(int xCentro) {
		this.xCentro = xCentro;
	}

	public int getyCentro() {
		return (int) Math.round( yCentro );
	}

	public void setyCentro(int yCentro) {
		this.yCentro = yCentro;
	}

	/** Devuelve la velocidad x de la figura
	 * @return	Velocidad horizontal, en píxels/segundo
	 */
	public double getVelX() {
		return velX;
	}
	
	/** Modifica la velocidad x de la figura
	 * @param velX	Nueva velocidad horizontal, en píxels/segundo
	 */
	public void setVelX(double velX) {
		this.velX = velX;
	}
	
	/** Devuelve la velocidad y de la figura
	 * @return	Velocidad vertical, en píxels/segundo
	 */
	public double getVelY() {
		return velY;
	}
	
	/** Modifica la velocidad y de la figura
	 * @param velY	Nueva velocidad vertical, en píxels/segundo
	 */
	public void setVelY(double velY) {
		this.velY = velY;
	}
	
	/** Modifica la velocidad de la figura
	 * @param velX	Nueva velocidad x en píxeles/segundo
	 * @param velY	Nueva velocidad y en píxeles/segundo
	 */
	public void setVelXY( double velX, double velY ) {
		setVelX( velX );
		setVelY( velY );
	}
	
	/** Modifica la velocidad de la figura
	 * @param p	Nueva velocidad en forma de punto x,y - ambos en píxeles/segundo
	 */
	public void setVelXY( Point2D p ) {
		setVelXY( p.getX(), p.getY() );
	}
	
	
	/** Devuelve el momento exacto en el que se creó el objeto
	 * @return	Tiempo en milisegundos
	 */
	public long getTiempoCreacionMs() {
		return tiempoCreacionMs;
	}

	/** Convierte la figura en string en formato "(x,y)"
	 */
	public String toString() {
		return String.format( "(%d,%d)", xCentro, yCentro );
	}

	/** Devuelve el tiempo que esta figura lleva de vida desde que se creó hasta este momento
	 * @return	Número de milisegundos transcurridos
	 */
	public long getTiempoVida() {
		return System.currentTimeMillis() - tiempoCreacionMs;
	}

	/** Dibuja la figura
	 * @param v	Ventana en la que dibujarla
	 */
	public abstract void dibujar( VentanaGrafica v );

	/** Informa si la figura contiene un punto de la ventana
	 * @param punto	Punto a consultar
	 * @return	true si ese punto está dentro de la figura
	 */
	public abstract boolean contienePunto( Point punto );
	
	/** Mueve la figura un tiempo indicado
	 * @param milis	Tiempo de movimiento, en milisegundos
	 */
	public void mover( double milis ) {
		xCentro += velX*milis/1000;
		yCentro += velY*milis/1000;
	}
	
	/** Determina si la figura choca con el borde vertical del espacio de dibujado
	 * @param v	Ventana de dibujado
	 * @return	true si la figura toca con el borde inferior o superior, false en caso contrario
	 */
	public abstract boolean chocaBordeVertical( VentanaGrafica v );
	
	/** Determina si la figura choca con el borde horizontal del espacio de dibujado
	 * @param v	Ventana de dibujado
	 * @return	true si la figura toca con el borde izquierdo o derecho, false en caso contrario
	 */
	public abstract boolean chocaBordeHorizontal( VentanaGrafica v );

}

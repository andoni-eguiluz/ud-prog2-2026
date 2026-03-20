package tema1c.ejercicios.acertijo;

import java.awt.Point;
import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Objeto gráfico general de acertijo, clase padre de la jerarquía de objetos que se dibujan en la ventana
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public abstract class ObjetoAcertijo extends Object {
	
	protected int x;  // Coordenada x en ventana
	protected int y;  // Coordenada y en ventana
	protected VentanaGrafica ventana;  // Ventana donde se encuentra el objeto
	
	/** Crea un nuevo objeto
	 * @param x	Posición x (horizontal)
	 * @param y	Posición y (vertical)
	 * @param ventana	Ventana gráfica en la que está ese objeto
	 */
	public ObjetoAcertijo(int x, int y, VentanaGrafica ventana) {
		// super();  // Como si estuviera (Java lo añade si no llamamos a otro super)
		this.x = x;
		this.y = y;
		this.ventana = ventana;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	/** Devuelve el punto de referencia en pantalla del objeto
	 * @return	Punto de referencia (x,y) en pixels
	 */
	public Point getPunto() {
		return new Point( x, y );
	}

	public VentanaGrafica getVentana() {
		return ventana;
	}

	public void setVentana( VentanaGrafica ventana ) {
		this.ventana = ventana;
	}

	/** Dibuja el objeto en su ventana gráfica
	 */
	abstract public void dibujar();
	
	/** Comprueba si el objeto contiene un punto de la ventana
	 * @param punto	Punto x,y a comprobar
	 * @return	true si esa posición está dentro del objeto, false en caso contrario
	 */
	public boolean contienePunto( Point punto ) {
		return contienePunto( punto.x, punto.y );
	}
	
	/** Comprueba si el objeto contiene un punto de la ventana
	 * @param x	Posición x del punto
	 * @param y	Posición y del punto
	 * @return	true si esa posición está dentro del objeto, false en caso contrario
	 */
	abstract public boolean contienePunto( int x, int y );
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ObjetoAcertijo)) return false;
		ObjetoAcertijo o2 = (ObjetoAcertijo) obj;  // Miro este obj como lo que realmente es: un ObjetoAcertijo
		return x==o2.x && y==o2.y && ventana==o2.ventana;
	}
	
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
		
}

package tema1c.ejercicios.acertijo;

import java.awt.Point;

/** Comportamiento de objetos que pueden ser movidos en un espacio de 2D
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public interface Draggable {


	/** Devuelve el punto de referencia en pantalla del objeto
	 * @return	Punto de referencia (x,y) en pixels
	 */
	public Point getPunto();

	/** Cambia la coordenada x,y
	 * @param x	Nueva posición horizontal (x)
	 * @param y	Nueva posición vertical (y)
	 */
	public void mover( int x, int y );
	
	/** Comprueba si el objeto contiene un punto de la ventana
	 * @param punto	Punto x,y a comprobar
	 * @return	true si esa posición está dentro del objeto, false en caso contrario
	 */
	public boolean contienePunto(int x, int y);
	
}

package tema1b.ejemplos.pong;

import java.awt.Color;
import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Clase abstracta Figura
 * Define el comportamiento general de cualquier figura móvil en 2D (posición y velocidad x e y) con color
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public abstract class Figura /*extends Object */ {
	
	/** Distancia por debajo de la cual se entiende que dos figuras están en el mismo punto y son iguales
	 */
	public static final double DISTANCIA_MINIMA_IGUALDAD = 0.0001;
	// Nota: los atributos static no se "heredan" como copia, pero sí se pueden usar en las clases hijas
	
	protected double x;      // Coordenada x de centro o punto de referencia de la figura (en píxels)
	protected double y;      // Coordenada y de centro o punto de referencia de la figura (en píxels)
	protected double velX;     // velocidad x de desplazamiento de la figura (en píxels por segundo)
	protected double velY;     // velocidad y de desplazamiento de la figura (en píxels por segundo)
	protected Color color;   // Color de la figura
	
	/** Crea una figura con x,y,velX,velY inicializadas a cero
	 * @param c	Color de la figura
	 */
	public Figura( Color c ) {
		this.color = c;
	}
	
	/** Crea una figura con velX,velY inicializadas a cero
	 * @param x	Coordenada x de la figura (en píxels)
	 * @param y	Coordenada y de la figura (en píxels)
	 * @param c	Color de la figura
	 */
	public Figura( double x, double y, Color c ) {
		this.x = x;
		this.y = y;
		this.color = c;
	}

	/** Devuelve la coordenada x del centro de la figura
	 * @return	Coordenada x en píxels (0=izquierda de la ventana)
	 */
	public double getX() {
		return x;
	}

	/** Cambia la coordenada del centro de la figura
	 * @param x	Nueva coordenada x en píxels (0=izquierda de la ventana)
	 */
	public void setX(double x) {
		this.x = x;
	}

	/** Devuelve la coordenada y del centro de la figura
	 * @return	Coordenada y en píxels (0=arriba de la ventana)
	 */
	public double getY() {
		return y;
	}

	/** Cambia la coordenada del centro de la figura
	 * @param y	Nueva coordenada y en píxels (0=arriba de la ventana)
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	/** Devuelve la velocidad de desplazamiento de la figura en el eje X
	 * @return	velocidad en píxels por segundo (positivo hacia la derecha, negativo hacia la izquierda, 0 parado)
	 */
	public double getVX() {
		return velX;
	}

	/** Cambia la velocidad de desplazamiento de la figura en el eje X
	 * @param vx	Nueva velocidad en píxels por segundo (positivo hacia la derecha, negativo hacia la izquierda, 0 parado)
	 */
	public void setVX(double vx) {
		this.velX = vx;
	}

	/** Devuelve la velocidad de desplazamiento de la figura en el eje Y
	 * @return	velocidad en píxels por segundo (positivo hacia abajo, negativo hacia arriba, 0 parado)
	 */
	public double getVY() {
		return velY;
	}

	/** Cambia la velocidad de desplazamiento de la figura en el eje Y
	 * @param vy	Nueva velocidad en píxels por segundo (positivo hacia abajo, negativo hacia arriba, 0 parado)
	 */
	public void setVY(double vy) {
		this.velY = vy;
	}

	/** Incrementa o decrementa las coordenadas de la figura
	 * @param incX	Incremento de la coordenada X (negativo si es decremento)
	 * @param incY	Incremento de la coordenada Y (idem)
	 */
	public void incXY( double incX, double incY ) {
		x += incX;
		y += incY;
	}

	/** Devuelve el color de la figura
	 * @return	color de la figura
	 */
	public Color getColor() {
		return color;
	}

	/** Modifica el color de la figura
	 * @param color	color de la figura
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/** Mueve la figura cambiando su posición según su velocidad lineal y el tiempo
	 * @param segs	Tiempo transcurrido
	 */
	public void mueve( double segs ) {
		x = Fisica.calcEspacio( x, velX, segs );
		y = Fisica.calcEspacio( y, velY, segs );
	}
	
	/** Dibuja la figura en una ventana
	 * @param v	Ventana en la que dibujar la figura
	 */
	public abstract void dibuja( VentanaGrafica v );

	/** Comprueba si la figura se sale por la vertical de la ventana
	 * @param v	Ventana de comprobación
	 * @return	true si se está saliendo por arriba o por abajo (tocar exactamente el borde se entiende así), false en caso contrario
	 */
	public abstract boolean seSaleEnVertical( VentanaGrafica v );

	/** Comprueba si la figura se sale por la horizontal de la ventana
	 * @param v	Ventana de comprobación
	 * @return	true si se está saliendo por izquierda o derecha (tocar exactamente el borde se entiende así), false en caso contrario
	 */
	public abstract boolean seSaleEnHorizontal( VentanaGrafica v );

	@Override
	public String toString() {
		return x + " , " + y;
	}
	
}

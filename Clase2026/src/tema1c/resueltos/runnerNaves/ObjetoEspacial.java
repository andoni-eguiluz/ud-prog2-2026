package tema1c.resueltos.runnerNaves;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public abstract class ObjetoEspacial {
	
	protected static boolean DIBUJA_ENVOLVENTE = false;  // Dibuja o no envolventes (círculos) de colisión
	
	protected double x;      // Coordenada x de centro o punto de referencia del objeto espacial
	protected double y;      // Coordenada y de centro o punto de referencia del objeto espacial
	protected double vX;     // velocidad x de desplazamiento del objeto espacial (en píxels por segundo)
	protected double vY;     // velocidad y de desplazamiento del objeto espacial (en píxels por segundo)
	
	public ObjetoEspacial( double x, double y ) {
		this.x = x;
		this.y = y;
	}

	/** Devuelve la coordenada x del centro del objeto espacial
	 * @return	Coordenada x en píxels (0=izquierda de la ventana)
	 */
	public double getX() {
		return x;
	}

	/** Cambia la coordenada del centro del objeto espacial
	 * @param x	Nueva coordenada x en píxels (0=izquierda de la ventana)
	 */
	public void setX(double x) {
		this.x = x;
	}

	/** Devuelve la coordenada y del centro del objeto espacial
	 * @return	Coordenada y en píxels (0=arriba de la ventana)
	 */
	public double getY() {
		return y;
	}

	/** Cambia la coordenada del centro del objeto espacial
	 * @param y	Nueva coordenada y en píxels (0=arriba de la ventana)
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	/** Devuelve la velocidad de desplazamiento del objeto espacial en el eje X
	 * @return	velocidad en píxels por segundo (positivo hacia la derecha, negativo hacia la izquierda, 0 parado)
	 */
	public double getVX() {
		return vX;
	}

	/** Cambia la velocidad de desplazamiento del objeto espacial en el eje X
	 * @param vx	Nueva velocidad en píxels por segundo (positivo hacia la derecha, negativo hacia la izquierda, 0 parado)
	 */
	public void setVX(double vx) {
		this.vX = vx;
	}

	/** Devuelve la velocidad de desplazamiento del objeto espacial en el eje Y
	 * @return	velocidad en píxels por segundo (positivo hacia abajo, negativo hacia arriba, 0 parado)
	 */
	public double getVY() {
		return vY;
	}

	/** Cambia la velocidad de desplazamiento del objeto espacial en el eje Y
	 * @param vy	Nueva velocidad en píxels por segundo (positivo hacia abajo, negativo hacia arriba, 0 parado)
	 */
	public void setVY(double vy) {
		this.vY = vy;
	}

	/** Incrementa o decrementa las coordenadas del objeto espacial
	 * @param incX	Incremento de la coordenada X (negativo si es decremento)
	 * @param incY	Incremento de la coordenada Y (idem)
	 */
	public void incXY( double incX, double incY ) {
		setX( x + incX );  // Mejor así que x += incX porque todo cambio de x pasa por setX en clases hijas
		setY( y + incY );  // Mejor así que y += incY porque todo cambio de y pasa por setY en clases hijas
	}

	/** Mueve el objeto espacial cambiando su posición según su velocidad lineal y el tiempo
	 * @param segs	Tiempo transcurrido
	 */
	public void mueve( double segs ) {
		setX( Fisica.calcEspacio( x, vX, segs ) );  // Mejor set que x = porque todo cambio de x pasa por setX en clases hijas
		setY( Fisica.calcEspacio( y, vY, segs ) );  // Mejor set que y = porque todo cambio de y pasa por setY en clases hijas
	}
	
	/** Dibuja el objeto espacial en una ventana
	 * @param v	Ventana en la que dibujar el objeto espacial
	 */
	public abstract void dibuja( VentanaGrafica v );

	@Override
	public String toString() {
		return x + " , " + y;
	}
	
}

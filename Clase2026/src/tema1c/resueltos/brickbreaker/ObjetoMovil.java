package tema1c.resueltos.brickbreaker;

import java.awt.Color;
import java.awt.geom.Point2D;

/** Clase abstracta de objetos gráficos que pueden moverse con velocidad lineal en la ventana
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public abstract class ObjetoMovil extends ObjetoGrafico {
	protected double antX;           // Coordenada x del centro en el movimiento anterior (método mover)
	protected double antY;           // Coordenada y del centro en el movimiento anterior (método mover)
	protected double velX;           // Velocidad horizontal del objeto en píxels / segundo 
	protected double velY;           // Velocidad vertical del objeto en píxels / segundo
	protected boolean visuVel;       // Visualización de velocidad (flecha visual de indicación de velocidad)
	protected Color colorVelocidad;  // Color de visualización de velocidad

	/** Crea un objeto de animación sin velocidad de borde azul y fondo blanco
	 * @param x	Coordenada x del centro
	 * @param y	Coordenada y del centro
	 */
	public ObjetoMovil( double x, double y ) {
		this( x, y, Color.BLUE, Color.WHITE );
	}

	/** Crea un objeto de animación sin velocidad
	 * @param x	Coordenada x del centro
	 * @param y	Coordenada y del centro
	 * @param colorBorde	Color del borde
	 * @param colorFondo	Color del fondo
	 */
	public ObjetoMovil(double x, double y, Color colorBorde, Color colorFondo) {
		super( x, y, colorBorde, colorFondo );
		colorVelocidad = Color.GREEN;
	}
	
	/** Devuelve la información de visibilidad de velocidad (flecha correspondiente al vector de velocidad)
	 * @return	true si la velocidad es visible, false en caso contrario
	 */
	public boolean isVisuVel() {
		return visuVel;
	}

	/** Modifica la información de visibilidad de velocidad (flecha correspondiente al vector de velocidad)
	 * @param visuVel	true si se quiere hacer la velocidad visible, false en caso contrario
	 */
	public void setVisuVel(boolean visuVel) {
		this.visuVel = visuVel;
	}

	/** Devuelve el color de velocidad
	 * @return	Color del vector de velocidad (flecha correspondiente en ventana)
	 */
	public Color getColorVelocidad() {
		return colorVelocidad;
	}

	/** Modifica el color de velocidad
	 * @param colorVelocidad	Nuevo color del vector
	 */
	public void setColorVelocidad(Color colorVelocidad) {
		this.colorVelocidad = colorVelocidad;
	}

	/** Devuelve la velocidad x
	 * @return	Velocidad horizontal, en píxels/segundo
	 */
	public double getVelX() {
		return velX;
	}
	
	/** Modifica la velocidad x
	 * @param velX	Nueva velocidad horizontal, en píxels/segundo
	 */
	public void setVelX(double velX) {
		this.velX = velX;
	}
	
	/** Devuelve la velocidad y
	 * @return	Velocidad vertical, en píxels/segundo
	 */
	public double getVelY() {
		return velY;
	}
	
	/** Modifica la velocidad y
	 * @param velY	Nueva velocidad vertical, en píxels/segundo
	 */
	public void setVelY(double velY) {
		this.velY = velY;
	}
	
	/** Devuelve el módulo de la velocidad
	 * @return	Módulo de velocidad (raíz cuadrada de la suma de cuadrados de las velocidades ortogonales vx y vy)
	 */
	public double getVelocidad() {
		return Math.sqrt( velX * velX + velY * velY );
	}
	
	/** Modifica la velocidad
	 * @param velX	Nueva velocidad x en píxeles/segundo
	 * @param velY	Nueva velocidad y en píxeles/segundo
	 */
	public void setVelXY( double velX, double velY ) {
		setVelX( velX );
		setVelY( velY );
	}
	
	/** Modifica la velocidad
	 * @param p	Nueva velocidad en forma de punto x,y - ambos en píxeles/segundo
	 */
	public void setVelXY( Point2D p ) {
		setVelXY( p.getX(), p.getY() );
	}
	
	/** Mueve el objeto un tiempo indicado
	 * @param milis	Tiempo de movimiento, en milisegundos
	 */
	public void mover( double milis ) {
		antX = x;
		antY = y;
		x += velX*milis/1000;
		y += velY*milis/1000;
	}
	
	/** Devuelve el avance horizontal del último movimiento (método {@link #mover(double)}
	 * @return	Diferencia entre la posición x actual y la anterior
	 */
	public double getAvanceX() {
		return x - antX;
	}
	
	/** Devuelve el avance vertical del último movimiento (método {@link #mover(double)}
	 * @return	Diferencia entre la posición y actual y la anterior
	 */
	public double getAvanceY() {
		return y - antY;
	}
	
	/** Devuelve el cálculo de energía del objeto
	 * @return	Energía calculada con la fórmula general de energía 0,5 * volumen * velocidad^2
	 */
	public abstract double getEnergia();
	
}

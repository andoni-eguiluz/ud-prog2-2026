package tema1b.ejemplos.bolasYBloques;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public abstract class ObjetoMovil extends Object {
	protected double x;              // Coordenada x del centro del objeto
	protected double y;              // Coordenada y del centro del objeto
	protected double antX;           // Coordenada x del centro en el movimiento anterior (método mover)
	protected double antY;           // Coordenada y del centro en el movimiento anterior (método mover)
	protected double velX;           // Velocidad horizontal del objeto en píxels / segundo 
	protected double velY;           // Velocidad vertical del objeto en píxels / segundo
	protected Color colorBorde;      // Color del borde del objeto
	protected Color colorFondo;      // Color del fondo del objeto
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
		super();
		this.x = x;
		this.y = y;
		this.colorBorde = colorBorde;
		this.colorFondo = colorFondo;
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

	/** Devuelve la coordenada x del centro
	 * @return	Coordenada horizontal
	 */
	public double getX() {
		return x;
	}
	
	/** Modifica la coordenada x del centro
	 * @param x	Nueva coordenada horizontal
	 */
	public void setX(double x) {
		this.x = x;
	}
	
	/** Devuelve la coordenada y del centro
	 * @return	Coordenada vertical
	 */
	public double getY() {
		return y;
	}
	
	/** Modifica la coordenada y del centro
	 * @param y	Nueva coordenada vertical
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	/** Devuelve el rectángulo que engloba el objeto de animación
	 * @return	Coordenadas horizontales y verticales mínimas y máximas en forma de rectángulo
	 */
	public abstract Rectangle2D getRectangulo();
	
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
	
	/** Devuelve el color de borde
	 * @return	Color de borde
	 */
	public Color getColor() {
		return colorBorde;
	}
	
	/** Modifica el color de borde
	 * @param color	Nuevo color de borde
	 */
	public void setColor(Color color) {
		this.colorBorde = color;
	}
	
	/** Devuelve el color de fondo
	 * @return	Color de fondo
	 */
	public Color getColorFondo() {
		return colorFondo;
	}
	
	/** Modifica el color de fondo
	 * @param colorFondo	Nuevo color de fondo
	 */
	public void setColorFondo(Color colorFondo) {
		this.colorFondo = colorFondo;
	}	
	
	/** Dibuja el objeto
	 * @param v	Ventana en la que dibujarlo
	 */
	public abstract void dibujar( VentanaGrafica v );

	/** Borra el objeto, pintándolo en blanco (se borra si el fondo es blanco)
	 * @param v	Ventana en la que borrarlo
	 */
	public abstract void borrar( VentanaGrafica v );

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
	
	/** Determina si el objeto choca con el borde vertical del espacio de dibujado
	 * @param rect	Rectángulo externo de choque
	 * @return	true si el objeto toca con el borde inferior o superior, false en caso contrario
	 */
	public abstract boolean chocaBordeVertical( Rectangle rect );
	
	/** Determina si el objeto choca con el borde vertical del espacio de dibujado
	 * @param v	Ventana de dibujado
	 * @return	true si el objeto toca con el borde inferior o superior, false en caso contrario
	 */
	public boolean chocaBordeVertical( VentanaGrafica v ) {
		return chocaBordeVertical( new Rectangle( v.getAnchura(), v.getAltura() ) );
	}
	
	/** Determina si el objeto choca con el borde horizontal del espacio de dibujado
	 * @param rect	Rectángulo externo de choque
	 * @return	true si el objeto toca con el borde izquierdo o derecho, false en caso contrario
	 */
	public abstract boolean chocaBordeHorizontal( Rectangle rect );
	
	/** Determina si el objeto choca con el borde horizontal del espacio de dibujado
	 * @param v	Ventana de dibujado
	 * @return	true si el objeto toca con el borde izquierdo o derecho, false en caso contrario
	 */
	public boolean chocaBordeHorizontal( VentanaGrafica v ) {
		return chocaBordeHorizontal( new Rectangle( v.getAnchura(), v.getAltura() ) );
	}
	
	/** Comprueba si hay choque entre objetos
	 * @param objeto2	Objeto de animación con el que comprobar choque
	 * @return	true si se tocan este objeto y objeto2, false en caso contrario
	 */
	public abstract boolean chocaCon( ObjetoMovil objeto2 );
	
	/** Informa si el objeto contiene un punto de la ventana
	 * @param punto	Punto a consultar
	 * @return	true si ese punto está dentro de el objeto (incluyendo su borde), false si no lo está
	 */
	public abstract boolean contienePunto( Point punto );

	/** Devuelve el impacto de choque entre dos objetos
	 * @param objeto2	Objeto con el que probar el choque
	 * @return	Devuelve null si no chocan, un vector con forma de punto indicando el ángulo y amplitud del choque sobre el objeto en curso
	 */
	public abstract Polar vectorChoqueConObjeto( ObjetoMovil objeto2 );

	/** Calcula el área del objeto, partiendo de su información de radio
	 * @return	Área del objeto
	 */
	public abstract double getArea();

	/** Calcula el volumen
	 * @return	Volumen del objeto
	 */
	public abstract double getVolumen();
	
	/** Devuelve el cálculo de energía del objeto
	 * @return	Energía calculada con la fórmula general de energía 0,5 * volumen * velocidad^2
	 */
	public abstract double getEnergia();
	
}

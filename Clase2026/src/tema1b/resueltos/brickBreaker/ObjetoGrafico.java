package tema1b.resueltos.brickBreaker;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Clase abstracta de objetos gráficos que pueden moverse con velocidad lineal en la ventana
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public abstract class ObjetoGrafico extends Object {
	protected double x;              // Coordenada x del centro del objeto
	protected double y;              // Coordenada y del centro del objeto
	protected Color colorBorde;      // Color del borde del objeto
	protected Color colorFondo;      // Color del fondo del objeto

	/** Crea un objeto gráfico de borde azul y fondo blanco
	 * @param x	Coordenada x del centro
	 * @param y	Coordenada y del centro
	 */
	public ObjetoGrafico( double x, double y ) {
		this( x, y, Color.BLUE, Color.WHITE );
	}

	/** Crea un objeto gráfico
	 * @param x	Coordenada x del centro
	 * @param y	Coordenada y del centro
	 * @param colorBorde	Color del borde
	 * @param colorFondo	Color del fondo
	 */
	public ObjetoGrafico(double x, double y, Color colorBorde, Color colorFondo) {
		super();
		this.x = x;
		this.y = y;
		this.colorBorde = colorBorde;
		this.colorFondo = colorFondo;
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
	
	/** Devuelve el rectángulo que engloba el objeto gráfico
	 * @return	Coordenadas horizontales y verticales mínimas y máximas en forma de rectángulo
	 */
	public abstract Rectangle2D getRectangulo();
	
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
	
	/** Informa si el objeto contiene un punto de la ventana
	 * @param punto	Punto a consultar
	 * @return	true si ese punto está dentro de el objeto (incluyendo su borde), false si no lo está
	 */
	public abstract boolean contienePunto( Point punto );

	/** Calcula el área del objeto, partiendo de su información de radio
	 * @return	Área del objeto
	 */
	public abstract double getArea();

	/** Calcula el volumen
	 * @return	Volumen del objeto
	 */
	public abstract double getVolumen();
	
}

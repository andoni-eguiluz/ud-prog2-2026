package tema1c.ejemplos.editorGraficos;

import java.awt.Color;
import java.awt.Point;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public abstract class Grafico {

	// Definidos como protegidos para poderse utilizar de forma directa en las clases hijas
	protected double x; //  = 0.0; por omisión (valor 0 para los numéricos, char 0, false para los booleans)
	protected double y;
	protected Color color;  // = null por defecto
	
	/** Crea un nuevo gráfico partiendo de sus coordenadas de referencia
	 * @param x	Coordenada horizontal (en píxels)
	 * @param y	Coordenada vertical (en píxels)
	 */
	public Grafico(double x, double y) {
		// super();
		this.x = x;
		this.y = y;
	}

	/** Devuelve la coordenada x
	 * @return	Coordenada x
	 */
	public double getX() {
		return this.x;
	}
	
	/** Cambia la coordenada x
	 * @param x	Nuevo valor de coordenada x
	 */
	public void setX( double x ) {
		this.x = x;
	}
	
	/** Devuelve la coordenada y
	 * @return	Coordenada y
	 */
	public double getY() {
		return y;
	}

	/** Cambia la coordenada y
	 * @param x	Nuevo valor de coordenada y
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	public void setXY( Point puntoXY ) {
		setX( puntoXY.getX() );
		setY( puntoXY.getY() );
	}

	/** Devuelve el último color en el que se ha dibujado el gráfico
	 * @return	color del último dibujado, null si nunca se ha dibujado
	 */
	public Color getColor() {
		return color;
	}

	/** Modifica el color del gráfico
	 * @param color	nuevo color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/** Convierte el vector a String, en formato (coord.x,coord.y)
	 */
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	/** Dibuja un gráfico en una ventana gráfica, como el gráfico necesita, con grosor 1.0
	 * @param vent	Ventana en la que dibujar el gráfico
	 * @param color	Color de dibujado del gráfico
	 */
	public void dibujar( VentanaGrafica vent, Color color ) {
		dibujar( vent, color, 1.0f );  // Reutiliza el método base sobrecargado
	}

	/** Dibuja un gráfico en una ventana gráfica, como el gráfico necesite
	 * @param vent	Ventana en la que dibujar el gráfico
	 * @param color	Color de dibujado del gráfico
	 * @param grosor	Ancho de la línea que se dibuja
	 */
	public abstract void dibujar( VentanaGrafica vent, Color color, float grosor );
	
	/** Calcula la distancia de un punto al gráfico. Es 0 si toca exactamente el gráfico y puede ser negativa si es interior
	 * @param punto	Punto
	 * @return	Distancia menor entre el punto y el gráfico
	 */
	public abstract double distancia( Vector2D punto );

	/** Comprueba si un punto del plano toca con el gráfico
	 * @param punto	Punto a comprobar
	 * @param margen	Margen de distancia con el que se entiende que hay contacto (por ejemplo 2.0 - 2 o menos píxels de distancia)
	 * @return	true si el punto toca con el gráfico dentro del margen indicado, false en caso contrario
	 */
	public boolean toca( Vector2D punto, double margen ) {
		return distancia( punto ) <= margen;
	}
	
}

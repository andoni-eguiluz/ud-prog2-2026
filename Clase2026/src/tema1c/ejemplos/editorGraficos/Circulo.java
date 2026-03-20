package tema1c.ejemplos.editorGraficos;

import java.awt.Color;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Gráfico de tipo círculo
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public class Circulo extends Grafico implements Movible, RellenableColor {

	protected double radio;
	protected Color colorRelleno; // Color de relleno del círculo
	
	/** Crea un nuevo círculo
	 * @param x	Coordenada x del centro
	 * @param y	Coordenada y del centro
	 * @param radio	Radio
	 * @param color	Color
	 */
	public Circulo( double x, double y, double radio, Color color ) {
		super(x,y);
		this.radio = radio;
		this.color = color;
		// Ojo, que colorRelleno está inicializado a null (tenerlo en cuenta al dibujar)
	}
	
	@Override
	public void dibujar(VentanaGrafica vent, Color color, float grosor) {
		if (colorRelleno == null) {
			vent.dibujaCirculo( x, y, radio, grosor, color );
		} else {
			vent.dibujaCirculo( x, y, radio, grosor, color, colorRelleno );
		}
	}

	@Override
	public double distancia(Vector2D punto) {
		double dist = Math.sqrt( (punto.x-x)*(punto.x-x) + (punto.y-y)*(punto.y-y) );
		return dist - radio;  // Puede ser negativa
	}

	@Override
	public void mover(double x, double y) {
		this.setX( x );
		this.setY( y );
	}

	@Override
	public void setColorRelleno( Color colorRelleno ) {
		this.colorRelleno = colorRelleno;
	}
	
}

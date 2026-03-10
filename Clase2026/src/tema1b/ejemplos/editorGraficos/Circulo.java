package tema1b.ejemplos.editorGraficos;

import java.awt.Color;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Gráfico de tipo círculo
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public class Circulo extends Grafico {

	protected double radio;
	
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
	}
	
	@Override
	public void dibujar(VentanaGrafica vent, Color color, float grosor) {
		vent.dibujaCirculo( x, y, radio, grosor, color );
	}

	@Override
	public double distancia(Vector2D punto) {
		double dist = Math.sqrt( (punto.x-x)*(punto.x-x) + (punto.y-y)*(punto.y-y) );
		return dist - radio;  // Puede ser negativa
	}

	public void mover(double x, double y) {
		this.setX( x );
		this.setY( y );
	}
	
}

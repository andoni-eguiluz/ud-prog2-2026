package tema1b.ejemplos.editorGraficos;

import java.awt.Color;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class Rectangulo extends Grafico {

	protected double ancho;
	protected double alto;
	
	/** Crea un nuevo rectángulo
	 * @param x	Coordenada x del centro
	 * @param y	Coordenada y del centro
	 * @param ancho
	 * @param alto
	 * @param color	Color
	 */
	public Rectangulo( double x, double y, double ancho, double alto, Color color ) {
		super(x,y);
		this.ancho = ancho;
		this.alto = alto;
		this.color = color;
	}
	
	@Override
	public void dibujar(VentanaGrafica vent, Color color, float grosor) {
		vent.dibujaRect( x-ancho/2.0, y-alto/2.0, ancho, alto, grosor, color );
	}

	@Override
	public double distancia(Vector2D punto) {
		if (punto.getX()>=x-ancho/2.0 && punto.getX()<=x+ancho/2.0) {  // Punto en la vertical interior del rectángulo
			if (punto.getY() < y) { // Si está hacia arriba, distancia al lado superior
				return (y-alto/2.0) - punto.getY();
			} else { // Si está hacia abajo, distancia al lado inferior
				return punto.getY() - (y+alto/2.0);
			}
		} else if (punto.getY()>=y-alto/2.0 && punto.getY()<=y+alto/2.0) {  // Punto en la horizontal interior del rectángulo
			if (punto.getX() < x) { // Si está hacia la izquierda, distancia al lado izquierdo
				return (x-ancho/2.0) - punto.getX();
			} else { // Si no, distancia al lado derecho
				return punto.getX() - (x+ancho/2.0);
			}
		} else { // Punto fuera del rectángulo - distancia al vértice más cercano
			double d1 = Math.sqrt( (punto.getX()-(x-ancho/2.0))*(punto.getX()-(x-ancho/2.0)) + (punto.getY()-(y-alto/2.0))*(punto.getY()-(y-alto/2.0)) );
			double d2 = Math.sqrt( (punto.getX()-(x+ancho/2.0))*(punto.getX()-(x+ancho/2.0)) + (punto.getY()-(y-alto/2.0))*(punto.getY()-(y-alto/2.0)) );
			double d3 = Math.sqrt( (punto.getX()-(x-ancho/2.0))*(punto.getX()-(x-ancho/2.0)) + (punto.getY()-(y+alto/2.0))*(punto.getY()-(y+alto/2.0)) );
			double d4 = Math.sqrt( (punto.getX()-(x+ancho/2.0))*(punto.getX()-(x+ancho/2.0)) + (punto.getY()-(y+alto/2.0))*(punto.getY()-(y+alto/2.0)) );
			return Math.min( d1, Math.min( d2, Math.min( d3, d4 ) ) );
		}
	}

	public void mover(double x, double y) {
		this.setX( x );
		this.setY( y );
	}
	
}

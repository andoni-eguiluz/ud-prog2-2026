package tema1c.ejemplos;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class ImagenEscalada extends Imagen {
	// heredax, y, [color,] imagen, zoom
	private int ancho, alto;

	public ImagenEscalada(int xCentro, int yCentro, String imagen, int ancho, int alto) {
		super(xCentro, yCentro, imagen, 0.0);
		this.ancho = ancho;
		this.alto = alto;
		setZoom( 1.0 );
	}
	
	// Constructor indirecto
	// Permite devolver null
	// Permite elegir qué tipo de objeto devolvemos
	public static Imagen crear( int x, int y, String imagen, int ancho, int alto, VentanaGrafica v ) {
		if (ancho<=0 || alto <=0) {
			return null;
		}
		Rectangle r = VentanaGrafica.getTamanyoImagen( imagen );
		if (r.width == ancho && r.height==alto) {
			return new Imagen( x, y, imagen, 1.0 );
		} else {
			return new ImagenEscalada(x, y, imagen, ancho, alto );
		}
	}
	
	@Override
	public void dibujar(VentanaGrafica vent) {
		// vent.dibujaImagen( getImagen(), getX(), getY(), ancho, alto, getZoom(), 0.0, 1.0f );
		// Solucionado error de que las imágenes escaladas no rotaban - getRotacion() también se necesita en el dibujado de la clase hija
		vent.dibujaImagen( getImagen(), getX(), getY(), ancho, alto, getZoom(), getRotacion(), opacidad );
	}
	
	@Override
	public String toString() {
		return "ImagenEscalada " + super.toString();
	}


	// INTERFAZ CLICABLE
	
	@Override
	public boolean contieneCoordenada(int x, int y) {
		Rectangle r = new Rectangle( 0,  0, ancho, alto );  // Rectángulo original
		r.setSize( (int) (r.width*getZoom()), (int) (r.height*getZoom()) );  // Escala
		r.x = this.x-r.width/2;  // Coordenada superior izquierda (antes de rotar)
		r.y = this.y-r.height/2;
		AffineTransform at = AffineTransform.getRotateInstance( getRotacion(), r.getCenterX(), r.getCenterY() );
		Shape rotatedRect = at.createTransformedShape(r);
		boolean estaDentro = rotatedRect.contains( new Point2D.Double(x, y) );
		return estaDentro;
	}
	
}

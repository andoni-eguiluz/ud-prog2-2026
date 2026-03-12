package tema1c.ejemplos;

import java.awt.Rectangle;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class ImagenEscalada extends Imagen {
	// heredax, y, [color,] imagen, zoom
	private int ancho, alto;

	public ImagenEscalada(int xCentro, int yCentro, String imagen, int ancho, int alto) {
		super(xCentro, yCentro, imagen, 0.0);
		this.ancho = ancho;
		this.alto = alto;
	}
	
	// Constructor indirecto
	// Permite devolver null
	// Permite elegir qué tipo de objeto devolvemos
	public static Imagen crear( int x, int y, String imagen, int ancho, int alto, VentanaGrafica v ) {
		if (ancho<=0 || alto <=0) {
			return null;
		}
		Rectangle r = v.getTamanyoImagen( imagen );
		if (r.width == ancho && r.height==alto) {
			return new Imagen( x, y, imagen, 1.0 );
		} else {
			return new ImagenEscalada(x, y, imagen, ancho, alto);
		}
	}
	
	@Override
	public void dibujar(VentanaGrafica vent) {
		vent.dibujaImagen( getImagen(), getX(), getY(), ancho, alto, getZoom(), 0.0, 1.0f );
	}
	
}

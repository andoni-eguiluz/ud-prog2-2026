package tema1b.resueltos.ejClase;

import java.awt.Color;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class Cuadrado extends Figura {

	private int lado;  // lado del cuadrado, en píxeles
	
	/** Crea un cuadrado nuevo
	 * @param ladoEnPixels	Lado del cuadrado en píxeles. Debe ser positivo
	 * @param xCentro	Coordenada x del centro del cuadrado, en píxeles (de izquierda a derecha)
	 * @param yCentro	Coordenada y del centro del cuadrado, en píxeles (de arriba abajo)
	 */
	public Cuadrado(int ladoEnPixels, int xCentro, int yCentro) {
		super( xCentro, yCentro );
		this.lado = ladoEnPixels;
	}
	
	public int getLado() {
		return lado;
	}
	
	public void setLado(int lado) {
		this.lado = lado;
	}

	@Override
	public String toString() {
		return super.toString() + String.format( " - %d", lado );
	}

	/** Dibuja el cuadrado, azul fondo amarillo
	 * @param v	Ventana en la que dibujar el cuadrado
	 */
	public void dibujar( VentanaGrafica v ) {
		v.dibujaRect( xCentro-lado/2, yCentro-lado/2, lado*2, lado*2, 2f, Color.BLUE, Color.YELLOW );
	}

}

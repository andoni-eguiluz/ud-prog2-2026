package tema1b.resueltos.ejClase;

import java.awt.Color;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class Hexagono extends Figura {
	
	private int radio;
	private double giro;
	
	/** Crea un hexágono nuevo
	 * @param xCentro	Coordenada x del centro del hexágono, en píxeles (de izquierda a derecha)
	 * @param yCentro	Coordenada y del centro del hexágono, en píxeles (de arriba abajo)
	 * @param radio	Radio del hexágono en píxeles. Debe ser positivo
	 */
	public Hexagono(int xCentro, int yCentro, int radio) {
		super( xCentro, yCentro );
		this.radio = radio;
	}
	
	public int getRadio() {
		return radio;
	}
	
	public void setRadio(int radio) {
		this.radio = radio;
	}

	public String toString() {
		return String.format( "Hex [%.0f,%.0f] - %d", xCentro, yCentro, radio );
	}

	@Override
	public void dibujar( VentanaGrafica v ) {
		v.dibujaPoligonoRegular( xCentro, yCentro, 6, radio, giro, 2f, Color.green );
		giro += Math.PI/32;
	}

}

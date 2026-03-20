package tema1c.ejercicios.acertijo;

import java.awt.Color;
import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class Pastor extends ObjetoAcertijo implements Draggable {
	
	//================= Parte static
	
	/** Color del círculo */
	public static final Color COLOR_CIRCULO = Color.MAGENTA;
	public static final float GROSOR_CIRCULO = 2.0f;
	/** Tamaño visual del pastor (radio del círculo) */
	public static final int RADIO = 75;
	/** Ruta de la imagen del pastor */
	public static final String IMAGEN = "img/pastor.png";
	
	//================= Parte no static

	int radio;  // Radio en píxels
	
	/** Crea un nuevo pastor
	 * @param x	Coordenada x
	 * @param y	Coordenada y
	 * @param ventana	Ventana del pastor
	 */
	public Pastor(int x, int y, VentanaGrafica ventana ) {
		super(x, y, ventana);
		this.radio = RADIO;
	}
	
	@Override
	public void dibujar() {
		ventana.dibujaCirculo( x, y, radio, GROSOR_CIRCULO, COLOR_CIRCULO );
		ventana.dibujaImagen( IMAGEN, x, y, radio*2, radio*2, 1.0, 0.0, 1.0f );
	}

	@Override
	public void mover( int x, int y ) {
		setX( x );
		setY( y );
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Pastor)) return false;
		Pastor o2 = (Pastor) obj;
		return super.equals(obj) && radio==o2.radio;
	}
	
	@Override
	public String toString() {
		return "Pastor " + super.toString();
	}

	public int getRadio() {
		return radio;
	}

	public void setRadio(int radio) {
		this.radio = radio;
	}
	
	@Override
	public boolean contienePunto(int x, int y) {
		double dist = Math.sqrt( (x-this.x)*(x-this.x) + (y-this.y)*(y-this.y) );
		return dist<=radio;
	}

}

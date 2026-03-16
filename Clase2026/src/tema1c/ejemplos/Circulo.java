package tema1c.ejemplos;

import java.awt.Color;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class Circulo extends Figura implements Coloreable, Movible {
	
	public static final float GROSOR = 1.0f;
	
	private int radio;
//	private Color color;
//	private int x;
//	private int y;
	
	public Circulo(int radio, Color color, int x, int y) {
		super(x, y, color);
		this.radio = radio;
//		this.color = color;
//		this.x = x;
//		this.y = y;
	}

	public int getRadio() {
		return radio;
	}

	public void setRadio(int radio) {
		this.radio = radio;
	}

//	public Color getColor() {
//		return color;
//	}
//
//	public void setColor(Color color) {
//		this.color = color;
//	}
//
//	public int getX() {
//		return x;
//	}
//
//	public void setX(int x) {
//		this.x = x;
//	}
//
//	public int getY() {
//		return y;
//	}
//
//	public void setY(int y) {
//		this.y = y;
//	}
	
	@Override
	public void dibujar(VentanaGrafica v) {
		// v.dibujaCirculo( x, y, radio, GROSOR, color );
		// Cambio de dibujado con interfaz Coloreable
		v.dibujaCirculo( x, y, radio, GROSOR, color, colorFondo );
	}

	@Override
	public String toString() {
		return "Círculo " + super.toString() + " - " + radio;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj==null) {
			return false;
		} else if (obj instanceof Circulo) {
			Circulo circulo2 = (Circulo) obj;
			return x==circulo2.x && y==circulo2.y && radio==circulo2.radio;
		} else {
			return false;
		}
	}

	// INTERFAZ COLOREABLE

	Color colorFondo = Color.WHITE;
	
	@Override
	public Color getColorFondo() {
		return colorFondo;
	}

	@Override
	public void setColorFondo(Color color) {
		this.colorFondo = color;
	}

	// INTERFAZ MOVIBLE
	
	// Los círculos tienen que poderse mover en una velocidad constante (configurable) vertical y horizontal, rebotando en los extremos de la pantalla.
	
	private int velHor = 5;
	private int velVert = 7;
	
	/** Velocidad horizontal en píxeles/movimiento
	 * @return
	 */
	public int getVelHor() {
		return velHor;
	}

	/** Modifica la velocidad horizontal
	 * @param velHor	Píxeles/movimiento
	 */
	public void setVelHor(int velHor) {
		this.velHor = velHor;
	}

	/** Velocidad vertical en píxeles/movimiento
	 * @return
	 */
	public int getVelVert() {
		return velVert;
	}

	/** Modifica la velocidad vertical
	 * @param velHor	Píxeles/movimiento
	 */
	public void setVelVert(int velVert) {
		this.velVert = velVert;
	}

	private VentanaGrafica miVentana = null;  // PARA EL REBOTE HACE FALTA SABER LA VENTANA
	
	public void setVentana(VentanaGrafica miVentana) {
		this.miVentana = miVentana;
	}
	
	public VentanaGrafica getVentana() {
		return miVentana;
	}

	// El tiempo no se usa en el movimiento
	@Override
	public void mover(long tiempoMilis) {
		setX( getX() + velHor );
		setY( getY() + velVert );
		if (miVentana!=null) {
			if (x + radio >= miVentana.getAnchura()) {
				velHor = -velHor;
			} else if (x - radio <= 0) {
				velHor = -velHor;
			} else if (y + radio >= miVentana.getAltura()) {
				velVert = -velVert;
			} else if (y - radio <= 0) {
				velVert = -velVert;
			} 
		}
	}
	
}

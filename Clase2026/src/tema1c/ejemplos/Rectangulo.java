package tema1c.ejemplos;

import java.awt.Color;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class Rectangulo extends Figura implements Coloreable, Movible {
//	private int x;  // Estos atributos se heredan
//	private int y;
//	private Color color;
	private int anchura;
	private int altura;
	private float grosor;
	
	/** Crea un rectángulo ortogonal (alineado con los ejes)
	 * @param x	Coordenada x del centro
	 * @param y	Coordenada y del centro
	 * @param anchura
	 * @param altura
	 * @param color	Color del borde
	 * @param grosor	Grosor del borde, en píxels
	 */
	public Rectangulo(int x, int y, int anchura, int altura, Color color, float grosor) {
		super(x,y,color);
		this.anchura = anchura;
		this.altura = altura;
		this.grosor = grosor;
	}

// Estos métodos se heredan
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

	public int getAnchura() {
		return anchura;
	}

	public void setAnchura(int anchura) {
		this.anchura = anchura;
	}

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

//	public Color getColor() {
//		return color;
//	}
//
//	public void setColor(Color color) {
//		this.color = color;
//	}

	public float getGrosor() {
		return grosor;
	}

	public void setGrosor(float grosor) {
		this.grosor = grosor;
	}
	
	/** Dibuja el rectángulo en la ventana
	 * @param v	Ventana de dibujado. No puede ser null
	 */
	@Override
	public void dibujar( VentanaGrafica v ) {
		// v.dibujaRect( x-anchura/2, y-altura/2, anchura, altura, grosor, color );
		// Cambio de dibujado con interfaz Coloreable
		v.dibujaRect( x-anchura/2, y-altura/2, anchura, altura, grosor, color, colorFondo );
	}
	
	@Override
	public String toString() {
		return "Rectángulo " + super.toString() + " - " + anchura + "," + altura;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj==null) {
			return false;
		} else if (obj instanceof Rectangulo) {
			Rectangulo r2 = (Rectangulo) obj;
			return x==r2.x && y==r2.y && anchura==r2.anchura && altura==r2.altura;
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
	
	// Los rectángulos tienen que poderse mover un píxel en cada nuevo movimiento en una dirección aleatoria
	
	@Override
	public void mover(long tiempoMilis) {
		double aleatorio = Math.random();   // entre 0.0 y 1.0
		if (aleatorio <= 0.25) {
			setX( getX()-1 );
		} else if (aleatorio <= 0.5) {
			setX( getX()+1 );
		} else if (aleatorio <= 0.75) {
			setY( getY()-1 );
		} else {
			setY( getY()+1 );
		}
	}
	
}

package tema1c.ejemplos;

import java.awt.Color;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class Rectangulo extends Figura {
//	private int x;
//	private int y;
	private int anchura;
	private int altura;
//	private Color color;
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
		v.dibujaRect( x-anchura/2, y-altura/2, anchura, altura, grosor, color );
	}
	
	// TODO Pendiente definir toString y equals
	
}

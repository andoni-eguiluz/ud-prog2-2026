package tema1b.ejemplos;

import java.awt.Color;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class Circulo extends Figura {
	
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
		v.dibujaCirculo( x, y, radio, GROSOR, color );
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
	
	
	
}

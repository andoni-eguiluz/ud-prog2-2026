package tema1b.ejemplos;

import java.awt.Color;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public abstract class Figura {
	protected int x;
	protected int y;
	protected Color color;
	
	public Figura(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.color = Color.BLACK;
	}
	public Figura(int x, int y, Color color) {
		super();
		this.x = x;
		this.y = y;
		this.color = color;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
	public abstract void dibujar( VentanaGrafica v);
	
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
}

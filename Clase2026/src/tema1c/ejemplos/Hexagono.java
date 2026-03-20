package tema1c.ejemplos;

import java.awt.Color;
import java.awt.Point;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class Hexagono extends Figura implements Coloreable, Clicable, Movible, Rotable {

	// heredamos x, y, color
	private double radio; // círculo inscribe a hexágono por sus vértices (dist de centro a vértice)

	// Atributos añadidos por comportamientos de interfaces
	private double rotacion;
	private Color colorRelleno;

	public Hexagono(int x, int y, Color color, double radio) {
		super(x, y, color);
		this.radio = radio;
	}

	// Interfaces

	@Override
	public void dibujar(VentanaGrafica v) {
		v.dibujaCirculo( x, y, radio-10, 0f, colorRelleno, colorRelleno );
		v.dibujaPoligonoRegular( x, y, 6, radio, rotacion, 3.5f, color );
	}

	@Override
	public boolean contieneCoordenada(int x, int y) {
		Point p1 = new Point( x, y );
		Point p2 = new Point( this.x, this.y );
		return p1.distance(p2) <= radio;
	}

	@Override
	public void reaccionaAClick() {
		color = new Color( (float) Math.random(), (float) Math.random(), (float) Math.random() );  // Color aleatorio
	}

	@Override
	public void rotar(double anguloRadianes) {
		rotacion += anguloRadianes;
	}

	@Override
	public double getRotacion() {
		return rotacion;
	}

	@Override
	public void mover(long tiempoMilis) {
		x += 1;
	}

	@Override
	public Color getColorFondo() {
		return colorRelleno;
	}

	@Override
	public void setColorFondo(Color color) {
		this.colorRelleno = color;
	}

	
	
}
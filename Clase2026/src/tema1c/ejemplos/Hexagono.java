package tema1c.ejemplos;

import java.awt.Color;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class Hexagono extends Figura implements Coloreable, Clicable, Movible, Rotable {

	// heredamos x, y, color
	private double radio; // círculo inscribe a hexágono por sus vértices (dist de centro a vértice)
	
	public Hexagono(int x, int y, Color color, double radio) {
		super(x, y, color);
		this.radio = radio;
	}

	@Override
	public void rotar(double anguloRadianes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getRotacion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void mover(long tiempoMilis) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contieneCoordenada(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void reaccionaAClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Color getColorFondo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setColorFondo(Color color) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dibujar(VentanaGrafica v) {
		// TODO Auto-generated method stub
		
	}

	
}

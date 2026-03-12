package tema1c.ejemplos;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class Flecha extends Figura {

	// heredo atributos x, y, color
	private int x2, y2;

	public Flecha(int x, int y, int x2, int y2) {
		super(x, y);
		this.x2 = x2;
		this.y2 = y2;
	}

	@Override
	public void dibujar(VentanaGrafica v) {
		v.dibujaFlecha( x, y, x2, y2, 1.0f, color);
	}
	
}

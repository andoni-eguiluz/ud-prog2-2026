package tema1c.ejemplos;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class Flecha extends Figura implements Rotable {

	// heredo atributos x, y, color
	private int x2, y2;
	
	private double rotacion;  // Atributo añadido por implementación de interfaz

	public Flecha(int x, int y, int x2, int y2) {
		super(x, y);
		this.x2 = x2;
		this.y2 = y2;
	}

	@Override
	public void dibujar(VentanaGrafica v) {
		// Cmabio de dibujado por rotación
		double alpha = rotacion;
		double cx = (x + x2) / 2.0;
		double cy = (y + y2) / 2.0;
		double cos = Math.cos(alpha);
		double sin = Math.sin(alpha);
		// punto 1 rotado
		double nx1 = cx + (x - cx) * cos - (y - cy) * sin;
		double ny1 = cy + (x - cx) * sin + (y - cy) * cos;
		// punto 2 rotado
		double nx2 = cx + (x2 - cx) * cos - (y2 - cy) * sin;
		double ny2 = cy + (x2 - cx) * sin + (y2 - cy) * cos;
		// v.dibujaFlecha( x, y, x2, y2, 1.0f, color);
		v.dibujaFlecha( nx1, ny1, nx2, ny2, 1.0f, color);
	}

	@Override
	public void rotar(double anguloRadianes) {
		rotacion += anguloRadianes;
	}

	@Override
	public double getRotacion() {
		return rotacion;
	}
	
	
	
}

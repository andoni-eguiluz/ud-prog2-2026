package tema1b.ejemplos.xsoldier;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class Disparo extends Personaje {

	public Disparo(int x, int y, int vx, int vy, int r, String imagen) {
		super(x, y, vx, vy, r, imagen);
	}
	
	public Disparo(int x, int y) {
		this.x = x;
		this.y = y;
		this.vx = 0;
		this.vy = -1;
		this.r = 5;
		this.imagen = "shot.png";
	}

	@Override
	public String toString() {
		return "Disparo [x=" + x + ", y=" + y + ", vx=" + vx + ", vy=" + vy + ", r=" + r + ", imagen=" + imagen + "]";
	}

	@Override
	public void mover(VentanaGrafica v) {
		this.y += this.vy;
	}

}

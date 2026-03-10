package tema1b.ejemplos.xsoldier;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class Enemigo extends Personaje {
	protected int vida;

	public Enemigo(int x, int y, int vx, int vy, int r, String imagen, int vida) {
		super(x, y, vx, vy, r, imagen);
		this.vida = vida;
	}

	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}

	@Override
	public String toString() {
		return "Enemigo [vida=" + vida + ", x=" + x + ", y=" + y + ", vx=" + vx + ", vy=" + vy + ", r=" + r
				+ ", imagen=" + imagen + "]";
	}
	
	public void mover(VentanaGrafica v) {
		this.x += this.vx;
		this.y += this.vy;
		if (this.x < 0 || this.x > v.getAnchura()) {
			this.vx = - this.vx;
		}
		if (this.y < 0 || this.y > v.getAltura()) {
			this.vy = - this.vy;
		}
	}
}

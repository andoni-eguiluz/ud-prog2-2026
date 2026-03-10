package tema1b.ejemplos.xsoldier;

import java.util.Random;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class Nube extends Personaje {

	// Objeto para crear aleatorios
	private static Random random = new Random();

	public Nube(int x, int y, int vx, int vy, int r) {
		super(x, y, vx, vy, r, "cloud.png");
	}

	/** Crea una nube en coordenadas aleatorias
	 */
	public Nube() {
		this( random.nextInt(400), random.nextInt(300), random.nextInt(11)-5, random.nextInt(3)-1, 0 );
	}

	@Override
	public String toString() {
		return "Nube [x=" + x + ", y=" + y + ", vx=" + vx + ", vy=" + vy + ", r=" + r + "]";
	}
	
	// Mueve con cierta aleatoriedad
	public void mover(VentanaGrafica v) {
		if (random.nextDouble()<0.1) {  // un 20% de las veces...
			vx += (random.nextInt(3) - 1);  // Añade a las velocidades un valor aleatorio entre +/- 1
			vy += (random.nextInt(3) - 1);
		}
		this.x += this.vx;
		this.y += this.vy;
		if (this.x < 0 || this.x > v.getAnchura()) {
			this.vx = - this.vx;
		}
		if (this.y < 0 || this.y > v.getAltura()) {
			this.vy = - this.vy;
		}
	}

	@Override
	public void dibujar(VentanaGrafica v) {
		v.dibujaImagen( "img/"+this.imagen, this.x, this.y, 0.3, 0.0, 0.6f);
	}
	
}

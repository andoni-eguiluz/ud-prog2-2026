package tema1b.ejemplos.xsoldier;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Clase modelo para cualquier personaje móvil del juego
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
abstract public class Personaje {
	protected int x;
	protected int y;
	protected int vx;
	protected int vy;
	protected int r;  // Radio de choque del personaje
	protected String imagen;
	
	/** Crea un personaje de juego
	 * @param x	Coordenada x
	 * @param y	Coordenada y
	 * @param vx	Velocidad horizontal
	 * @param vy	Velocidad vertical
	 * @param r	Radio de choque
	 * @param imagen	Imagen gráfica para el personaje (nombre de fichero jpg o png)
	 */
	public Personaje(int x, int y, int vx, int vy, int r, String imagen) {
		super();
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.r = r;
		this.imagen = imagen;
	}
	
	public Personaje() {
		super();
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

	public int getVx() {
		return vx;
	}

	public void setVx(int vx) {
		this.vx = vx;
	}

	public int getVy() {
		return vy;
	}

	public void setVy(int vy) {
		this.vy = vy;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	@Override
	public String toString() {
		return "Personaje [x=" + x + ", y=" + y + ", vx=" + vx + ", vy=" + vy + ", r=" + r + ", imagen=" + imagen + "]";
	}
	

	public void dibujar(VentanaGrafica v) {
		v.dibujaImagen( "img/"+this.imagen, this.x, this.y, 1.0, 0.0, 1.0f);
	}
	
	abstract public void mover(VentanaGrafica v);
	
	public boolean chocando(Personaje p) {
		double d = Math.sqrt((this.x-p.x)*(this.x-p.x) + (this.y-p.y)*(this.y-p.y));
		return (d < this.r + p.r);
	}
	
}

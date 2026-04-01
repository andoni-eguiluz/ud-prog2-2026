package tema1c.resueltos.runnerNaves;

public class BoundingCircle extends Envolvente {
	protected double x;
	protected double y;
	protected double radio;
	
	/** Crea un bounding circle envolvente con forma de círculo para un objeto que puede colisionar
	 * @param x	Coordenada x del centro del círculo
	 * @param y	Coordenada y del centro del círculo
	 * @param radio	Radio del círculo de choque
	 */
	public BoundingCircle( double x, double y, double radio ) {
		this.x = x;
		this.y = y;
		this.radio = radio;
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getRadio() {
		return radio;
	}

	/** Modifica el radio del bounding circle
	 * @param radio	Nuevo valor de radio en píxels (debe ser positivo)
	 */
	public void setRadio(double radio) {
		this.radio = radio;
	}

	@Override
	public boolean chocaCon(Envolvente envolvente) {
		if (envolvente instanceof BoundingCircle) {
			BoundingCircle bc2 = (BoundingCircle) envolvente;
			double distCentros = Fisica.distancia( x, y, bc2.x, bc2.y );
			return distCentros <= radio + bc2.radio;  // Dos círculos colisionan si la distancia entre los centros es menor que la suma de los radios
		}
		return false;  // Si no es otro bounding circle devuelve false por defecto
		// TODO Implementar otras colisiones si se añaden formas adicionales: por ejemplo un círculo con un rectángulo, con un polígono...
	}
}

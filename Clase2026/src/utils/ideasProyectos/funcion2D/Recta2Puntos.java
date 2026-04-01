package utils.ideasProyectos.funcion2D;

public class Recta2Puntos implements Funcion2D {
	private double x1, y1, x2, y2;
	Recta2Puntos( double x1, double y1, double x2, double y2 ) {
		this.x1 = x1; this.x2 = x2; this.y1 = y1; this.y2 = y2;
	}
	public double f( double x ) {
		return (y2 - y1) / (x2 - x1) * (x - x1) + y1;
	}
}

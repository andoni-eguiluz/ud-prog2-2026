package tema1b.resueltos.runnerNaves;

import java.awt.Point;
import java.awt.geom.Point2D;

public class Fisica {
	
	/** Devuelve la distancia entre dos puntos
	 * @param p1	punto 1
	 * @param p2	punto 2
	 * @return	Distancia entre ambos (positiva)
	 */
	public static double distancia (Point p1, Point p2) {
		return distancia( p1.x, p1.y, p2.x, p2.y );
	}
	
	/** Devuelve la distancia entre dos puntos
	 * @param x1	x del punto 1
	 * @param y1	y del punto 1
	 * @param x2	x del punto 2
	 * @param y2	y del punto 2
	 * @return	Distancia entre ambos (positiva)
	 */
	public static double distancia( double x1, double y1, double x2, double y2 ) {
		return Math.sqrt( (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2) ); 
	}
	
	/** Calcula el movimiento de espacio en función de un desplazamiento lineal sin aceleración
	 * @param espacio	Espacio original (en píxels)
	 * @param velocidad	Velocidad (en píxels por segundo)
	 * @param segs	Segundos transcurridos en el movimimento
	 * @return	Nuevo espacio
	 */
	public static double calcEspacio( double espacio, double velocidad, double segs ) {
		// s2 = s1 + v*t
		return espacio + velocidad * segs;
	}
	
	/** Convierte una coordenada cartesiana en una polar
	 * @param x	Coordenada x
	 * @param y	Coordenada y
	 * @return	Coordenada polar (módulo, argumento) correspondiente. Si el valor es 0,0 devuelve argumento 0.
	 */
	public static Polar cartAPolar( double x, double y ) {
		return new Polar( Math.sqrt( x*x + y*y ), Math.atan2( y, x ) );
	}
	
	/** Clase interna para trabajo con vectores en notación polar en lugar de cartesiana
	 * Permite trabajar con puntos/vectores en modo módulo/argumento
	 * @author andoni.eguiluz @ ingenieria.deusto.es
	 */
	public static class Polar {
		private double modulo;
		private double argumento;
		/** Crea un vector en formato polar
		 * @param modulo	Módulo del vector (positivo)
		 * @param argumento	Ángulo del vector (en radianes)
		 */
		public Polar( double modulo, double argumento ) {
			this.modulo = modulo;
			this.argumento = argumento;
		}
		/** Crea un vector en formato polar desde un punto cartesiano
		 * @param punto	en formato cartesiano
		 */
		public Polar( Point2D punto ) {
			this.modulo = Math.sqrt( punto.getX()*punto.getX() + punto.getY()*punto.getY() );
			this.argumento = Math.atan2( punto.getY(), punto.getX() );
		}
		public double getArgumento() {
			return argumento;
		}
		public double getModulo() {
			return modulo;
		}
		/** Convierte el vector en un punto cartesiano (x,y)
		 * @return	Punto correspondiente al vector polar
		 */
		public Point2D toPoint() {
			Point2D ret = new Point2D.Double( modulo*Math.cos(argumento), modulo*Math.sin(argumento) );
			return ret;
		}
		/** Rota el vector
		 * @param rotacionRad	Ángulo de rotación (en radianes)
		 */
		public void rotar( double rotacionRad ) {
			argumento += rotacionRad;
		}
		/** Cambia el módulo del vector
		 * @param nuevoModulo	Nuevo valor de módulo (debe ser positivo)
		 */
		public void setModulo( double nuevoModulo ) {
			modulo = nuevoModulo;
		}
		@Override
		public String toString() {
			return "|" + modulo + "| " + argumento + " rad";
		}
	}
	
	public static void main(String[] args) {
		double x = 2;
		double y = -2;
		Polar p = cartAPolar( x, y );
		System.out.println( x + "," + y + " = " + p + "  -  " + p.toPoint() );
	}
	
}

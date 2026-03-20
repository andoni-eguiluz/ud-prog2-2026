package tema1c.resueltos.brickbreaker;

import java.awt.geom.Point2D;

/** Clase para trabajo con vectores en notación polar en lugar de cartesiana
 * Permite trabajar con puntos/vectores en modo módulo/argumento
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class Polar {
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
	
	/** Crea un vector en formato polar desde otro polar
	 * @param polar	Valor a copiar
	 */
	public Polar( Polar polar ) {
		this.modulo = polar.modulo;
		this.argumento = polar.argumento;
	}

	/** Crea un vector en formato polar desde un punto cartesiano
	 * @param punto	en formato cartesiano
	 */
	public Polar( Point2D punto ) {
		this.modulo = Math.sqrt( punto.getX()*punto.getX() + punto.getY()*punto.getY() );
		this.argumento = Math.atan2( punto.getY(), punto.getX() );
	}

	/** Crea un vector en formato polar desde un punto cartesiano
	 * @param x	coordenada x cartesiana
	 * @param y	coordenada y cartesiana
	 */
	public static Polar crearPolarDesdeXY( double x, double y ) {
		Polar ret = new Polar( 0, 0 );
		ret.modulo = Math.sqrt( x*x + y*y );
		ret.argumento = Math.atan2( y, x );
		return ret;
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
	
	/** Transforma el punto polar a un nuevo eje de referencia
	 * @param angulo	Ángulo que forma el nuevo eje con respecto al eje OX (en radianes)
	 */
	public void transformaANuevoEje( double angulo ) {
		this.argumento -= angulo;
	}
	
	/** Transforma el punto polar a un nuevo eje de referencia
	 * @param eje	Nuevo eje indicado por un vector representado por el punto desde el origen
	 */
	public void transformaANuevoEje( Point2D eje ) {
		double angulo = Math.atan2( eje.getY(), eje.getX() );
		this.argumento -= angulo;
	}
	
	/** Cambia el módulo del vector
	 * @param nuevoModulo	Nuevo valor de módulo (debe ser positivo)
	 */
	public void setModulo( double nuevoModulo ) {
		modulo = nuevoModulo;
	}
	
	/** Cambia el argumento del vector
	 * @param nuevoModulo	Nuevo valor de argumento (en radianes)
	 */
	public void setArgumento( double nuevoArgumento ) {
		argumento = nuevoArgumento;
	}
	
	@Override
	public String toString() {
		return "|" + modulo + "| " + argumento + " rad";
	}
}

package utils.ideasProyectos.juego2d.datos;
import java.awt.*;
import java.awt.geom.*;

import utils.ideasProyectos.juego2d.utils.Fisica;
import utils.ideasProyectos.juego2d.utils.PolarPoint;
import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Clase que permite crear y gestionar naves
 */
public class Nave extends ObjetoMovil {
	
	private double tamanyo;  // Tamaño en pixels de la nave
	private double giro;     // Ángulo de giro en radianes
	private double modulo;   // Módulo de la velocidad
	protected transient Point2D[] puntos;   // Puntos del triángulo que se dibuja
	
	/** Constructor de nave con datos
	 * @param x	Coordenada x del centro de la nave
	 * @param y	Coordenada y del centro de la nave
	 * @param tamanyo	Tamaño de la nave (en pixels)
	 * @param xDest	Coordenada x del vector de velocidad de la nave
	 * @param yDest	Coordenada y del vector de velocidad de la nave
	 * @param color	Color de la nave
	 */
	public Nave(double x, double y, double tamanyo, double xDest, double yDest, Color color) {
		super( x, y, color );
		this.tamanyo = tamanyo;
		this.velocidadX = xDest-x;
		this.velocidadY = yDest-y;
		giro = Math.atan2( velocidadY, velocidadX );
		calculosGeometria( false );
	}
	
	/** Constructor de nave con datos mínimos, suponiendo color azul y sin velocidad
	 * @param x	Coordenada x del centro de la nave
	 * @param y	Coordenada y del centro de la nave
	 * @param tamanyo	Tamaño de la nave (en pixels)
	 */
	public Nave(double x, double y, double tamanyo) {
		this( x, y, tamanyo, x, y, Color.blue );
	}
	
	/** Pone el giro de la nave en una dirección determinada
	 * @param angulo	Ángulo de giro en radianes
	 */
	public void setGiro( double angulo ) {
		giro = angulo;
		calculosGeometria( false );
	}
	
	public double getGiro() {
		return giro;
	}

	@Override
	public void gira( double angulo ) {
		super.gira( angulo );
		// giro += angulo;   // Lo hace el código de setVelocidad
		calculosGeometria( false );
	}
	
	@Override
	public void acelera( double vel ) {
		setVelocidad( modulo + vel );
	}

	// Métodos redefinidos para tener calculado siempre el giro y los puntos del triángulo en función
	// de la posición y la velocidad
	@Override
	public void setVelocidadX(double vel) {
		super.setVelocidadX(vel);
		calculosGeometria( true );
	}

	@Override
	public void setVelocidadY(double vel) {
		super.setVelocidadY(vel);
		calculosGeometria( true );
	}
	
	@Override
	public void setVelocidad(double vx, double vy) {
		super.setVelocidad(vx, vy);
		calculosGeometria( true );
	}
	
	/** Modifica la velocidad sin cambiar el giro
	 * @param moduloV	Nueva velocidad (positiva; si es negativa, se pone a 0)
	 */
	public void setVelocidad( double moduloV ) {
		if (moduloV<0) moduloV = 0;
		modulo = moduloV;
		PolarPoint pp = new PolarPoint( modulo, giro );
		Point2D pXY = pp.toPoint();
		setVelocidad( pXY.getX(), pXY.getY() );
	}
	
	@Override
	public void setX(double x) {
		super.setX(x);
		calculosGeometria( false );
	}

	@Override
	public void setY(double y) {
		super.setY(y);
		calculosGeometria( false );
	}


	public double getTamanyo() {
		return tamanyo;
	}

	public void setTamanyo(double tamanyo) {
		this.tamanyo = tamanyo;
	}

	/** Calcula el volumen de la nave partiendo de su información de tamaño (altura del cono)
	 * @return	Volumen de la nave suponiendo un cono perfecto
	 */
	@Override
	public double getVolumen() {
		double radio = tamanyo / Math.sqrt(3.0);
		return 1.0/3*Math.PI*radio*radio*tamanyo;
	}
	
	/** Calcula el área de la nave partiendo de su información de tamaño (altura del triángulo)
	 * @return	Area de la nave suponiendo una circunferencia perfecta
	 */
	@Override
	public double getArea() {
		double base = tamanyo / Math.sqrt(3.0) * 2.0;
		return base * tamanyo / 2.0;
	}
	
	/** Dibuja la nave en una ventana, en el color correspondiente de la nave (por defecto, negro)
	 * @param v	Ventana en la que dibujar la nave
	 */
	@Override
	public void dibuja( VentanaGrafica v ) {
		v.dibujaPoligono( 1.5f, color, true, puntos );
		v.dibujaCirculo( puntos[0].getX(), puntos[0].getY(), 2.0, 1.5f, color );
		super.dibuja( v );  // Para dibujar la velocidad si procede
	}
	
		// Métodos auxiliares de geometría de la forma de las naves
	
		private void calculosGeometria( boolean cambiarTambienElGiro ) {
			// Recalcular la geometría polar y si procede, ajustar el giro a la dirección de marcha
			if (!Fisica.igualACero(velocidadX) || !Fisica.igualACero(velocidadY)) {
				if (cambiarTambienElGiro) {
					giro = Math.atan2( velocidadY, velocidadX );
				}
				modulo = Math.sqrt( velocidadX*velocidadX + velocidadY*velocidadY );
			} else {
				modulo = 0.0;
			}
			// Calcular la geometría de la nave en función del giro
			double radio = tamanyo / Math.sqrt(3.0);
			double medio = radio * Math.sqrt(3.0) / 3.0;
			Point2D ret[] = new Point2D[3];
			ret[0] = new Point2D.Double( x, y - tamanyo + medio );
			ret[1] = new Point2D.Double( x + radio, y + medio );
			ret[2] = new Point2D.Double( x - radio, y + medio );
			Point2D centro = new Point2D.Double( x, y );
			rotarPunto( ret[0], centro, giro + Math.PI/2 );
			rotarPunto( ret[1], centro, giro + Math.PI/2 );
			rotarPunto( ret[2], centro, giro + Math.PI/2 );
			puntos = ret;
		}
		
		private void rotarPunto( Point2D puntoARotar, Point2D centro, double angulo ) {
			double x1 = puntoARotar.getX() - centro.getX();
			double y1 = puntoARotar.getY() - centro.getY();
			double x2 = x1 * Math.cos(angulo) - y1 * Math.sin(angulo);
			double y2 = x1 * Math.sin(angulo) + y1 * Math.cos(angulo);
			puntoARotar.setLocation( x2 + centro.getX(), y2 + centro.getY() );		
		}
		
		private double mayorX( Point2D[] punto ) {
			double xExtremo = Double.MIN_VALUE;
			for (Point2D p : punto) if (p.getX()>xExtremo) xExtremo = p.getX();
			return xExtremo;
		}

		private double menorX( Point2D[] punto ) {
			double xExtremo = Double.MAX_VALUE;
			for (Point2D p : punto) if (p.getX()<xExtremo) xExtremo = p.getX();
			return xExtremo;
		}

		private double mayorY( Point2D[] punto ) {
			double yExtremo = Double.MIN_VALUE;
			for (Point2D p : punto) if (p.getY()>yExtremo) yExtremo = p.getY();
			return yExtremo;
		}

		private double menorY( Point2D[] punto ) {
			double yExtremo = Double.MAX_VALUE;
			for (Point2D p : punto) if (p.getY()<yExtremo) yExtremo = p.getY();
			return yExtremo;
		}
		
	
	/** Borra la nave en una ventana
	 * @param v	Ventana en la que borrar la nave
	 */
	@Override
	public void borra( VentanaGrafica v ) {
		v.borraPoligono( 1.5f, true, puntos );
	}

	/** Ajusta la nave al borde vertical, si se ha "pasado" del borde.
	 * @param v	Ventana de la que ajustar al suelo
	 * @param dibujar	true si se quiere dibujar la nave, false en caso contrario
	 */
	@Override
	public void corrigeChoqueVertical( VentanaGrafica v, boolean dibujar ) {
		// Simplificado... se podría calcular con mejor precisión para que cambiara la velocidad
		double mayorY = mayorY( puntos );
		double menorY = menorY( puntos );
		if (menorY<0) {  // Se sale por arriba
			if (dibujar) borra( v );
			setY( y - menorY + 1 );
			if (dibujar) dibuja( v );
		} else if (mayorY>v.getAltura()) {  // Se sale por abajo
			if (dibujar) borra( v );
			setY( y - mayorY + v.getAltura() - 1 );
			if (dibujar) dibuja( v );
		}
	}
	
	/** Ajusta la nave al lateral
	 * @param v	Ventana de la que ajustar al lateral
	 * @param dibujar	true si se quiere dibujar la nave, false en caso contrario
	 */
	@Override
	public void corrigeChoqueLateral( VentanaGrafica v, boolean dibujar ) {
		// Simplificado... se podría calcular con mejor precisión para que cambiara la velocidad
		double mayorX = mayorX( puntos );
		double menorX = menorX( puntos );
		double tiempo = -1;
		if (menorX<0) {  // Se sale por izquierda
			tiempo = 1.0 - Math.abs( menorX / (x - antX));
		} else if (mayorX>v.getAnchura()) {  // Se sale por derecha
			tiempo = 1.0 - Math.abs( (mayorX-v.getAnchura()) / (x - antX));
		}
		if (tiempo>=0) {
			if (dibujar) borra( v );
			setX( antX + (x-antX) * tiempo );
			setY( antY + (y-antY) * tiempo );
			if (dibujar) dibuja( v );
		}
	}
	
	/** Detecta el choque de la nave con los bordes de la ventana
	 * @param v	Ventana con la que probar el choque
	 * @return	Devuelve un número formado por la suma de: 0 si no choca, 1 si choca con la izquierda, 2 con la derecha, 4 arriba, 8 abajo.
	 */
	@Override
	public int chocaConBorde( VentanaGrafica v ) {
		double mayorX = mayorX( puntos );
		double menorX = menorX( puntos );
		double mayorY = mayorY( puntos );
		double menorY = menorY( puntos );
		int ret = 0;
		if (menorX<=0) ret += 1;
		if (mayorX>=v.getAnchura()) ret += 2;
		if (menorY<=0) ret += 4;
		if (mayorY>=v.getAltura()) ret += 8;
		return ret;
	}
	
	/** Detecta el choque de la nave con otro objeto
	 * @param nave2	nave con la que probar el choque
	 * @return	Devuelve null si no chocan, un vector con forma de punto indicando el ángulo y amplitud del choque sobre la nave en curso
	 */
	@Override
	public Point2D chocaConObjeto( ObjetoMovil objeto2 ) {
		if (objeto2 instanceof Nave) {
			Point2D[] puntos2 = ((Nave)objeto2).puntos;
			Path2D.Double shape1 = new Path2D.Double(); shape1.moveTo( puntos[0].getX(), puntos[0].getY() );
				shape1.lineTo( puntos[1].getX(), puntos[1].getY() );
				shape1.lineTo( puntos[2].getX(), puntos[2].getY() );
				shape1.closePath();
			Area area1 = new Area(shape1);
			Path2D.Double shape2 = new Path2D.Double(); shape2.moveTo( puntos2[0].getX(), puntos2[0].getY() );
				shape2.lineTo( puntos2[1].getX(), puntos2[1].getY() );
				shape2.lineTo( puntos2[2].getX(), puntos2[2].getY() );
				shape2.closePath();
			Area area2 = new Area(shape2);
			area1.intersect(area2);
			if (area1.isEmpty()) 
				return null;
			else {
				Rectangle r = area1.getBounds();
				Point2D p = new Point.Double( r.getWidth(), r.getHeight() );
				return p;
			}
		} else {
			return null;
		}
	}
	
	/** Comprueba si la nave incluye a un punto dado
	 * @param punto	Punto a chequear
	 * @return	true si el punto está dentro de la nave, false en caso contrario
	 */
	@Override
	public boolean contieneA( Point2D punto ) {
		double radio = tamanyo / Math.sqrt(3.0);
		double dist = punto.distance( x, y );
		return dist <= radio;
	}

	@Override
	public boolean isBota() {
		return true;
	}
	
	@Override
	public boolean isFijo() {
		return false;
	}
	
	@Override
	public String toString() {
		return String.format( "nave %1s (%2$7.2f,%3$7.2f) Vel.=(%4$6.3f,%5$6.3f)", nombre, x, y, velocidadX, velocidadY );
	}
	
}

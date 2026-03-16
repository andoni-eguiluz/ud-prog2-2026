package tema1c.ejemplos;

import java.awt.geom.Line2D;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class Flecha extends Figura implements Rotable, Movible, Clicable {

	// heredo atributos x, y, color
	private int x2, y2;
	
	// private double rotacion;  // Atributo añadido por implementación de interfaz - en este caso lo calculamos con los puntos

	public Flecha(int x, int y, int x2, int y2) {
		super(x, y);   // Interfaz Movible: Polimórficamente llama a setX y setY (cambiando también las coordenadas reales)
		setX2(x2);
		setY2(y2);
	}

	@Override
	public void dibujar(VentanaGrafica v) {
		v.dibujaFlecha( x1Real, y1Real, x2Real, y2Real, 1.0f, color);
	}
	
	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
		x2Real = x2;  // Por interfaz Movible
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
		y2Real = y2;  // Por interfaz Movible
	}
	
	@Override
	public void setX(int x) {
		super.setX(x);
		x1Real = x;  // Por interfaz Movible
	}

	@Override
	public void setY(int y) {
		super.setY(y);
		y1Real = y;  // Por interfaz Movible
	}
	
	@Override
	public void rotar(double anguloRadianes) {
		// Recálculo de coordenadas tras rotación
		double alpha = anguloRadianes;
		
		double cx = (x1Real + x2Real) / 2.0;
		double cy = (y1Real + y2Real) / 2.0;
		double cosA = Math.cos(alpha);
		double sinA = Math.sin(alpha);
		// Punto 1 relativo al centro
		double dx1 = x1Real - cx;
		double dy1 = y1Real - cy;
		// Punto 2 relativo al centro
		double dx2 = x2Real - cx;
		double dy2 = y2Real - cy;
		// Rotación del punto 1
		double nx1 = cx + dx1 * cosA - dy1 * sinA;
		double ny1 = cy + dx1 * sinA + dy1 * cosA;
		// Rotación del punto 2
		double nx2 = cx + dx2 * cosA - dy2 * sinA;
		double ny2 = cy + dx2 * sinA + dy2 * cosA;
		
		// Actualizar coordenadas
		x1Real = nx1;
		x2Real = nx2;
		y1Real = ny1;
		y2Real = ny2;
		// Actualización de coordenadas enteras
		x = (int) Math.round(x1Real);
		x2 = (int) Math.round(x2Real);
		y = (int) Math.round(y1Real);
		y2 = (int) Math.round(y2Real);
	}

	@Override
	public double getRotacion() {
		double anguloFlecha = Math.atan2( y2Real-y1Real, x2Real-x1Real );
		return anguloFlecha;
	}
	
	// INTERFAZ MOVIBLE
	
	// Las flechas tienen que poderse mover en la dirección en la que apuntan, a una velocidad constante de 10 píxeles por segundo.
	private double x1Real, y1Real, x2Real, y2Real;  // Necesarias para movimiento fino (con enteros se trunca el movimiento perdiendo precisión)
	private static final double VELOCIDAD = 250.0;  // píxeles por segundo 
	@Override
	public void mover(long tiempoMilis) {
		double anguloFlecha = Math.atan2( y2Real-y1Real, x2Real-x1Real );
		double movX = VELOCIDAD * Math.cos( anguloFlecha ) * tiempoMilis / 1000;
		double movY = VELOCIDAD * Math.sin( anguloFlecha ) * tiempoMilis / 1000;
		x1Real += movX;
		x2Real += movX;
		y1Real += movY;
		y2Real += movY;
		// Actualización de coordenadas enteras
		x = (int) Math.round(x1Real);
		x2 = (int) Math.round(x2Real);
		y = (int) Math.round(y1Real);
		y2 = (int) Math.round(y2Real);
	}


	// INTERFAZ CLICABLE
	
	private static final int DISTANCIA_PIXELS_CLICK = 4;
	
	@Override
	public boolean contieneCoordenada(int x, int y) {
		Line2D linea = new Line2D.Double( x1Real, y1Real, x2Real, y2Real );
		double dist = linea.ptSegDist( x, y );
		return dist <= DISTANCIA_PIXELS_CLICK;
	}
		
	// 	Las flechas deben implementar ese comportamiento para que al ser clicadas inviertan su rotación (dirección contraria)	

	@Override
	public void reaccionaAClick() {
		rotar( Math.PI );
	}
	
}

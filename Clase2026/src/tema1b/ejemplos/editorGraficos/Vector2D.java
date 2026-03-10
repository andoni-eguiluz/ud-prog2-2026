package tema1b.ejemplos.editorGraficos;

import java.awt.Color;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Clase para crear objetos de tipo vector en 2 dimensiones
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public class Vector2D extends Grafico {

	//--------------------------------
	// STATIC
	//--------------------------------
	public static final Vector2D VECTOR0 = new Vector2D();
	
	//--------------------------------
	// NO STATIC
	//--------------------------------
	

	// Este es el constructor implícito, el que hace Java si no hacemos constructor. En este caso también lo programamos
	/** Construye un nuevo vector 0,0
	 */
	public Vector2D() {
		super( 0.0, 0.0 );
		// Constructor por defecto x y = 0
	}
	
	/** Construye un vector en coords cartesianas
	 * @param x	Coord x
	 * @param y	Coord y
	 */
	public Vector2D( double x, double y ) {
		super( x, y );
	}
	
	// Constructor indirecto 
	// ¿Debe ser static? Sí
	/** Construye un vector en coords polares
	 * @param modulo	Longitud del vector
	 * @param argumento	Ángulo del vector con respecto al eje OX positivo
	 * @return	Devuelve el vector creado
	 */
	public static Vector2D crearVectorPolar( double modulo, double argumento ) {
		Vector2D ret = new Vector2D();
		ret.setX( modulo * Math.cos(argumento) );
		ret.setY( modulo * Math.sin(argumento) );
		return ret;
	}
	
	// Suma
	/** Modifica el vector sumándole otro
	 * @param vSumando	Vector a sumar al vector en curso
	 */
	public void sumarA( Vector2D vSumando ) {
		this.x += vSumando.x;
		this.y += vSumando.y;
	}
	
	/** Suma el vector en curso con otro y devuelve el resultado
	 * @param vSumando	Segundo vector a sumar
	 * @return	Nuevo vector con valor de la suma de ambos
	 */
	public Vector2D sumar( Vector2D vSumando ) {
		Vector2D res = new Vector2D( this.x, this.y );
		res.sumarA( vSumando );
		return res;
	}
	
	
	/** Calcula y devuelve el módulo del vector (longitud)
	 * @return	Devuelve el módulo del vector calculado por pitágoras
	 */
	public double getModulo() {
		return Math.sqrt( x*x + y*y );
	}
	
	/** Calcula y devuelve el argumento del vector (ángulo sobre el OX positivo hacia el OY positivo)
	 * @return	Ángulo de argumento del vector en radianes
	 */
	public double getArgumento() {
		return Math.atan2(y, x);
	}
	
	/** Escala un vector
	 * @param escala	Escala a aplicar, siendo 1.0 el 100%, menores que 1 más pequeños (0.5 la mitad), mayores que 1 más grandes (2.0 el doble)
	 */
	public void escala( double escala ) {
		x *= escala;
		y *= escala;
	}
	

	@Override
	/** Dibuja un vector en una ventana gráfica, como una flecha desde el origen hasta sus coordenadas
	 * @param vent	Ventana en la que dibujar el vector
	 * @param color	Color de dibujado del vector
	 * @param grosor	Ancho de la línea que se dibuja
	 */
	public void dibujar( VentanaGrafica vent, Color color, float grosor ) {
		vent.dibujaFlecha(0.0, 0.0, this.getX(), this.getY(), grosor, color, 12 );
		this.color = color;
	}
	
	// Métodos de picking
	
	/** Calcula la distancia de un punto al vector
	 * @param punto	Punto
	 * @return	Distancia menor entre el punto y el vector (segmento)
	 */
	public double distancia( Vector2D punto ) {
		// Opción 1: Usar funciones ya existentes en la API de Java
		java.awt.geom.Line2D.Double segmento = new java.awt.geom.Line2D.Double( VECTOR0.getPoint(), getPoint() );
		double dist = segmento.ptSegDist( punto.getPoint() );
		// Opción 2: Calcularlo directamente. Por ejemplo cálculo en base a operaciones con vectores:
		return dist;
	}
	
	/** Devuelve el punto correspondiente a un vector
	 * @return	Punto x,y de ese vector
	 */
	public java.awt.geom.Point2D getPoint() {
		return new java.awt.geom.Point2D.Double( x, y );
	}
		
}

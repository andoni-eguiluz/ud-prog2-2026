package tema1b.ejemplos.editorGraficos;

import java.awt.Color;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class VectorConOrigen2D extends Vector2D {
	protected double xOrigen;
	protected double yOrigen;

	// Constructores
	
	/** Construye un nuevo vector
	 * @param xOrigen	Coordenada x de origen de vector
	 * @param yOrigen	Coordenada y de origen de vector
	 * @param xFinal	Coordenada x de final de vector
	 * @param yFinal	Coordenada y de final de vector
	 */
	public VectorConOrigen2D( double xOrigen, double yOrigen, double xFinal, double yFinal ) {
		super( xFinal, yFinal );
		this.xOrigen = xOrigen;
		this.yOrigen = yOrigen;
	}
	
	/** Construye un nuevo vector
	 * @param xOrigen	Coordenada x de origen de vector
	 * @param yOrigen	Coordenada y de origen de vector
	 * @param xFinal	Coordenada x de final de vector
	 * @param yFinal	Coordenada y de final de vector
	 * @param color
	 */
	public VectorConOrigen2D( double xOrigen, double yOrigen, double xFinal, double yFinal, Color color ) {
		super( xFinal, yFinal );
		this.xOrigen = xOrigen;
		this.yOrigen = yOrigen;
		this.color = color;
	}
	
	// Métodos get y set. Los que se heredan no hace falta redefinirlos
	
	/** Devuelve la coordenada de origen del vector
	 * @return	Coordenada x de origen
	 */
	public double getxOrigen() {
		return xOrigen;
	}

	/** Modifica la coordenada de origen del vector
	 * @param xOrigen	Nueva coordenada x
	 */
	public void setxOrigen(double xOrigen) {
		this.xOrigen = xOrigen;
	}

	/** Devuelve la coordenada de origen del vector
	 * @return	Coordenada y de origen
	 */
	public double getyOrigen() {
		return yOrigen;
	}

	/** Modifica la coordenada de origen del vector
	 * @param yOrigen	Nueva coordenada y
	 */
	public void setyOrigen(double yOrigen) {
		this.yOrigen = yOrigen;
	}
	
	// Redefinición del método de dibujado
	
	@Override
	/** Dibuja un vector en una ventana gráfica, como una flecha
	 * @param vent	Ventana en la que dibujar el vector
	 * @param color	Color de dibujado del vector
	 * @param grosor	Ancho de la línea que se dibuja
	 */
	public void dibujar( VentanaGrafica vent, Color color, float grosor ) {
		vent.dibujaFlecha( xOrigen, yOrigen, x, y, grosor, color, 12 );
		this.color = color;
	}

	// La distancia necesita dos puntos ya que el origen no es ahora 0,0
	@Override
	public double distancia(Vector2D punto) {
		// java.awt.geom.Line2D.Double segmento = new java.awt.geom.Line2D.Double( VECTOR0.getPoint(), getPoint() );
		// double dist = segmento.ptSegDist( punto.getPoint() );
		java.awt.geom.Line2D.Double segmento = new java.awt.geom.Line2D.Double( 
			new java.awt.geom.Point2D.Double( xOrigen, yOrigen )
			, getPoint() );
		double dist = segmento.ptSegDist( punto.getPoint() );
		return dist;
	}

	// Obsérvese que NO HACE FALTA cambiar el método toca, que a su vez utiliza el método distancia polimórfico
	
	// Redefinición de métodos que se calculan distinto
	
	@Override
	public double getModulo() {
		return Math.sqrt( (x-xOrigen)*(x-xOrigen) + (y-yOrigen)*(y-yOrigen) );
	}
	
	@Override
	public double getArgumento() {
		return Math.atan2(y-yOrigen, x-xOrigen);
	}
	
	@Override
	public void escala(double escala) {
		x = xOrigen + (x-xOrigen)*escala;
		y = yOrigen + (y-yOrigen)*escala;
	}
	
	@Override
	public String toString() {
		return "(" + xOrigen + "," + yOrigen + ") -> " + super.toString();
	}
	
	// Constructor indirecto estático - NO SE HEREDA luego hay que hacer uno completamente nuevo

	/** Construye un vector en coords polares
	 * @param xOrigen	Coordenada x de origen de vector
	 * @param yOrigen	Coordenada y de origen de vector
	 * @param modulo	Longitud del vector
	 * @param argumento	Ángulo del vector con respecto al eje OX positivo
	 * @return	Devuelve el vector creado
	 */
	public static VectorConOrigen2D crearVectorPolar( double xOrigen, double yOrigen, double modulo, double argumento ) {
		VectorConOrigen2D ret = new VectorConOrigen2D( xOrigen, yOrigen, 
			xOrigen + modulo * Math.cos(argumento), yOrigen + modulo * Math.sin(argumento) );
		return ret;
	}
	
	public void mover(double x, double y) {
		double difX = x - this.xOrigen;
		double difY = y - this.yOrigen;
		xOrigen = x;
		yOrigen = y;
		setX( this.x + difX );
		setY( this.y + difY );
	}
	
}

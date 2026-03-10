package tema1b.resueltos.brickBreaker;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class Bola extends ObjetoMovil {
	// Parte static
	private static final Random random = new Random();  // Generador de aleatorios (1 para todas las bolas)
	private static final Color[] colores = { Color.YELLOW, Color.RED, Color.GREEN, Color.BLUE, Color.CYAN, Color.MAGENTA, Color.BLACK };  // Array de colores aleatorios de la bola
	// Otra manera de inicializar los estáticos (a veces no se puede inicializar en la misma línea de la declaración, o hay que hacer más cosas):
	//	static {
	//		random = new Random(); 
	//		colores = { Color.YELLOW, Color.RED, Color.GREEN, Color.BLUE, Color.CYAN, Color.MAGENTA, Color.BLACK };  // Array de colores aleatorios de la bola
	//	}
	
	// Parte no static
	
	protected double radio;      // Píxels de radio de la bola
	
	/** Crea una bola nueva sin movimiento (con velocidad 0)
	 * @param x	Coordenada x del centro
	 * @param y	Coordenada x del centro
	 * @param radio	Píxels de radio
	 * @param color	Color de borde
	 * @param colorFondo	Color de fondo
	 */
	public Bola(double x, double y, double radio, Color color, Color colorFondo) {
		this( x, y, radio, 0.0, 0.0, color, colorFondo );
	}
	
	/** Crea una nueva bola de radio 10, borde azul y fondo blanco
	 * @param xCentro	coordenada x del centro
	 * @param yCentro	coordenada y del centro
	 */
	public Bola(int xCentro, int yCentro) {
		this( xCentro, yCentro, 10, Color.BLUE, new Color(255,255,255) );
	}

	/** Crea una nueva bola en las coordenadas 0,0 de radio 10, borde azul y fondo blanco
	 */
	public Bola() {
		this( 0, 0, 10, Color.BLUE, new Color(255,255,255) );
	}
	
	/** Crea una bola nueva
	 * @param x	Coordenada x del centro
	 * @param y	Coordenada x del centro
	 * @param radio	Píxels de radio
	 * @param velX	Velocidad horizontal en píxels/segundo (positivo hacia la derecha, negativo hacia la izquierda)
	 * @param velY	Velocidad vertical en píxels/segundo (positivo hacia la derecha, negativo hacia la izquierda)
	 * @param color	Color de borde
	 * @param colorFondo	Color de fondo
	 */
	public Bola(double x, double y, double radio, double velX, double velY, Color color, Color colorFondo) {
		super( x, y, color, colorFondo );
		this.radio = radio;
		this.velX = velX;
		this.velY = velY;
	}
	
	/** Crea una bola aleatoria 
	 * Toda la bola dentro de la ventana recibida
	 * Radio entre 10 y 30 pixels
	 * Velocidad x,y entre 100 y 300 pixels / seg, positiva o negativa
	 * Color de borde y fondo aleatorios entre los colores amarillo, rojo, verde, azul, cyan, magenta y negro
	 * @param v	Ventana de referencia para la creación (se toma su tamaño)
	 */
	public Bola(VentanaGrafica v) {
		super( 0, 0 );
		radio = random.nextInt(21) + 10;
		x = random.nextInt( (int) Math.round(v.getAnchura()-radio-radio) ) + radio;
		y = random.nextInt( (int) Math.round(v.getAltura()-radio-radio) ) + radio;
		velX = random.nextInt(201) + 100;
		velY = random.nextInt(201) + 100;
		if (random.nextBoolean() ) {
			velX = -velX;
		}
		if (random.nextBoolean()) {
			velY = -velY;
		}
		colorBorde = colores[ random.nextInt(colores.length) ];
		colorFondo = colores[ random.nextInt(colores.length) ];
	}

	/** Devuelve el radio de la bola
	 * @return	Radio en píxels
	 */
	public double getRadio() {
		return radio;
	}
	
	/** Modifica el radio de la bola
	 * @param radio	Nuevo radio en píxels
	 */
	public void setRadio(double radio) {
		this.radio = radio;
	}

	@Override
	public Rectangle2D getRectangulo() {
		return new java.awt.geom.Rectangle2D.Double( x-radio, y-radio, radio*2, radio*2 );
	}
	
	@Override
	public void dibujar( VentanaGrafica v ) {
		v.dibujaCirculo( x, y, radio, 2.0f, colorBorde, colorFondo );
		if (visuVel) {
			v.dibujaFlecha( x, y, x+velX, y+velY, 2.0f, colorVelocidad, 10 );
		}
	}
	
	@Override
	public void borrar( VentanaGrafica v ) {
		v.dibujaCirculo( x, y, radio, 2.0f, Color.WHITE, Color.WHITE );
		if (visuVel) {
			v.dibujaFlecha( x, y, x+velX, y+velY, 2.0f, Color.WHITE, 10 );
		}
	}
	
	@Override
	public boolean chocaBordeVertical( Rectangle rect ) {
		return y-radio <= 0 || y+radio >= rect.height;
	}
	
	@Override
	public boolean chocaBordeHorizontal( Rectangle rect ) {
		return x-radio <= 0 || x+radio >= rect.width;
	}
	
	@Override
	public boolean esChocable() {
		return true;
	}
	
	@Override
	public boolean chocaCon( ObjetoMovil objeto2 ) {
		if (objeto2 instanceof Bola) {
			Bola bola2 = (Bola) objeto2;
			double distCentros = Math.sqrt( (x-bola2.x)*(x-bola2.x) + (y-bola2.y)*(y-bola2.y) );
			return (radio + bola2.radio) >= distCentros;
		} else {
			Bloque bloque = (Bloque) objeto2;
			return bloque.chocaCon( this );  // Codificado en la clase Bloque
		}
	}
	
	@Override
	public boolean contienePunto( Point punto ) {
		double distCentroAPunto = Math.sqrt( (x-punto.x)*(x-punto.x) + (y-punto.y)*(y-punto.y) );
		return distCentroAPunto <= radio;
	}

	@Override
	public Polar vectorChoqueConObjeto( ObjetoMovil objeto2 ) {
		if (objeto2 instanceof Bloque) {
			Polar ret = ((Bloque)objeto2).vectorChoqueConObjeto(this);
			if (ret!=null) {
				ret.setArgumento( ret.getArgumento() + Math.PI );  // Invierte el vector
			}
			return ret;
		} else if (objeto2 instanceof Bola) {
			Polar ret = Polar.crearPolarDesdeXY( objeto2.x-x, objeto2.y-y );
			double moduloChoque = radio + ((Bola)objeto2).radio - ret.getModulo();
			if (moduloChoque < 0) return null;  // No hay choque
			ret.setModulo( moduloChoque );
			return ret;
		} else {
			return null;
		}
	}	
	
	@Override
	public double getArea() {
		return Math.PI*radio*radio;
	}

	@Override
	// Volumen de la pelota suponiendo una esfera perfecta
	public double getVolumen() {
		return 4.0/3*Math.PI*radio*radio*radio;
	}
	
	@Override
	public double getEnergia() {
		return 0.5 * getVolumen() * getVelocidad()*getVelocidad();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Bola)) {
			return false;
		}
		Bola bola2 = (Bola) obj;
		return x==bola2.x&& y==bola2.y && radio==bola2.radio;
	}

	@Override
	public String toString() {
		return String.format( "[Bola (%.2f,%.2f) r=%.1f v=(%.4f,%.4f)]", x, y, radio, velX, velY );
	}

}

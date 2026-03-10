package tema1b.resueltos.brickBreaker;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class Bloque extends ObjetoMovil {
	// Parte static
	protected static final Random random = new Random();  // Generador de aleatorios (1 para todos los bloques)
	protected static final Color[] colores = { Color.YELLOW, Color.RED, Color.GREEN, Color.BLUE, Color.CYAN, Color.MAGENTA, Color.BLACK };  // Array de colores aleatorios del bloque
	
	// Parte no static
	
	protected double ancho;      // Píxels de ancho del bloque
	protected double alto;       // Píxels de alto del bloque
	
	/** Crea un bloque nuevo sin movimiento (con velocidad 0)
	 * @param x	Coordenada x del centro
	 * @param y	Coordenada x del centro
	 * @param ancho	Píxels de ancho
	 * @param alto	Píxels de alto
	 * @param color	Color de borde
	 * @param colorFondo	Color de fondo
	 */
	public Bloque(double x, double y, double ancho, double alto, Color color, Color colorFondo) {
		this( x, y, ancho, alto, 0.0, 0.0, color, colorFondo );
	}
	
	/** Crea un bloque nuevo de ancho y alto 10, borde azul y fondo blanco
	 * @param xCentro	coordenada x del centro
	 * @param yCentro	coordenada y del centro
	 */
	public Bloque(int xCentro, int yCentro) {
		this( xCentro, yCentro, 10, 10, Color.BLUE, new Color(255,255,255) );
	}

	/** Crea un bloque nuevo en las coordenadas 0,0 de ancho y alto 10, borde azul y fondo blanco
	 */
	public Bloque() {
		this( 0, 0, 10, 10, Color.BLUE, new Color(255,255,255) );
	}
	
	/** Crea un bloque nuevo
	 * @param x	Coordenada x del centro
	 * @param y	Coordenada x del centro
	 * @param ancho	Píxels de ancho
	 * @param alto	Píxels de alto
	 * @param velX	Velocidad horizontal en píxels/segundo (positivo hacia la derecha, negativo hacia la izquierda)
	 * @param velY	Velocidad vertical en píxels/segundo (positivo hacia la derecha, negativo hacia la izquierda)
	 * @param color	Color de borde
	 * @param colorFondo	Color de fondo
	 */
	public Bloque(double x, double y, double ancho, double alto, double velX, double velY, Color color, Color colorFondo) {
		super( x, y, color, colorFondo );
		this.ancho = ancho;
		this.alto = alto;
		this.velX = velX;
		this.velY = velY;
	}
	
	/** Crea un bloque aleatorio
	 * Todo el bloque dentro de la ventana recibida
	 * Ancho y alto entre 10 y 50 pixels
	 * Velocidad x,y entre 100 y 300 pixels / seg, positiva o negativa
	 * Color de borde y fondo aleatorios entre los colores amarillo, rojo, verde, azul, cyan, magenta y negro
	 * @param v	Ventana de referencia para la creación (se toma su tamaño)
	 */
	public Bloque(VentanaGrafica v) {
		super( 0, 0 );
		ancho = random.nextInt(41) + 10;
		alto = random.nextInt(41) + 10;
		x = random.nextInt( (int) Math.round(v.getAnchura()-ancho-ancho) ) + ancho;
		y = random.nextInt( (int) Math.round(v.getAltura()-alto-alto) ) + alto;
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

	/** Devuelve la anchura del bloque
	 * @return	Ancho en píxels
	 */
	public double getAncho() {
		return ancho;
	}
	
	/** Modifica la anchura del bloque
	 * @param ancho	Nuevo ancho en píxels
	 */
	public void setAncho(double ancho) {
		this.ancho = ancho;
	}
	
	/** Devuelve la altura del bloque
	 * @return	Alto en píxels
	 */
	public double getAlto() {
		return alto;
	}
	
	/** Modifica la altura del bloque
	 * @param alto	Nuevo alto en píxels
	 */
	public void setAlto(double alto) {
		this.alto = alto;
	}
	
	@Override
	public Rectangle2D getRectangulo() {
		return new java.awt.geom.Rectangle2D.Double( x-ancho/2, y-alto/2, ancho, alto );
	}
	
	@Override
	public void dibujar( VentanaGrafica v ) {
		v.dibujaRect( x-ancho/2, y-alto/2, ancho, alto, 2.0f, colorBorde, colorFondo );
		if (visuVel) {
			v.dibujaFlecha( x, y, x+velX, y+velY, 2.0f, colorVelocidad, 10 );
		}
	}
	
	@Override
	public void borrar( VentanaGrafica v ) {
		v.dibujaRect( x-ancho/2, y-alto/2, ancho, alto, 2.0f, Color.WHITE, Color.WHITE );
		if (visuVel) {
			v.dibujaFlecha( x, y, x+velX, y+velY, 2.0f, Color.WHITE, 10 );
		}
	}
	
	@Override
	public boolean chocaBordeVertical( Rectangle rect ) {
		return y-alto/2 <= 0 || y+alto/2 >= rect.height;
	}
	
	@Override
	public boolean chocaBordeHorizontal( Rectangle rect ) {
		return x-ancho/2 <= 0 || x+ancho/2 >= rect.width;
	}

	@Override
	public boolean esChocable() {
		return true;
	}
	
	@Override
	public boolean chocaCon( ObjetoMovil objeto2 ) {
		if (objeto2 instanceof Bloque) {
			Bloque bloque2 = (Bloque) objeto2;
			double distCentrosX = Math.abs( x-bloque2.x );
			double distCentrosY = Math.abs( y-bloque2.y );
			return distCentrosX <= ancho/2 + bloque2.ancho/2 && distCentrosY <= alto/2 + bloque2.alto/2;
		} else {
			Bola bola = (Bola) objeto2;
			return vectorChoqueConObjeto(bola)!=null;
		}
	}
	
	@Override
	public boolean contienePunto( Point punto ) {
		double distX = Math.abs( x-punto.x );
		double distY = Math.abs( y-punto.y );
		return distX <= ancho/2 && distY <= alto/2;
	}
	
	@Override
	public Polar vectorChoqueConObjeto( ObjetoMovil objeto2 ) {
		if (objeto2 instanceof Bola) {
			Bola bola = (Bola) objeto2;
			double distCentrosX = x-bola.getX();
			double distCentrosY = y-bola.getY();
			// Choque horizontal con bloque
			if (bola.getY() >= y-alto/2 &&
				bola.getY() <= y+alto/2) {  // La y de la bola está dentro del bloque...
				if (bola.getX()-bola.getRadio()<=x+ancho/2 &&
					bola.getX()+bola.getRadio()>=x-ancho/2 ) {  // ...y la x lateral de la bola está dentro del bloque
					if (distCentrosX >= 0) {
						return Polar.crearPolarDesdeXY( (x-ancho/2) - (bola.getX()+bola.getRadio()), 0 );
					} else {
						return Polar.crearPolarDesdeXY( (x+ancho/2) - (bola.getX()-bola.getRadio()), 0 );
					}
				}
			}
			// Choque vertical con bloque
			if (bola.getX() >= x-ancho/2 &&
				bola.getX() <= x+ancho/2) {  // La x de la bola está dentro del bloque...
				if (bola.getY()-bola.getRadio()<=y+alto/2 &&
					bola.getY()+bola.getRadio()>=y-alto/2 ) {  // ...y la y borde de la bola está dentro del bloque: choque
					if (distCentrosY >= 0) {
						return Polar.crearPolarDesdeXY( 0, (y-alto/2) - (bola.getY()+bola.getRadio()) );
					} else {
						return Polar.crearPolarDesdeXY( 0, (y+alto/2) - (bola.getY()-bola.getRadio()) );
					}
				}
			}
			// Coche con esquina: calculamos las distancias del centro de la bola a las cuatro esquinas
			double dist1 = Fisica.distancia( bola.getX(), bola.getY(), x+ancho/2, y+alto/2 );
			double dist2 = Fisica.distancia( bola.getX(), bola.getY(), x+ancho/2, y-alto/2 );
			double dist3 = Fisica.distancia( bola.getX(), bola.getY(), x-ancho/2, y+alto/2 );
			double dist4 = Fisica.distancia( bola.getX(), bola.getY(), x-ancho/2, y-alto/2 );
			if (dist1<=bola.getRadio() || dist2<=bola.getRadio() || dist3<=bola.getRadio() || dist4<=bola.getRadio()) { // Si alguna de esas distancias es inferior al radio, hay choque
				if (dist1<=bola.getRadio()) {
					return Polar.crearPolarDesdeXY( (x+ancho/2) - (bola.getX()-bola.getRadio()), (y+alto/2) - (bola.getY()-bola.getRadio()) );
				} else if (dist2<=bola.getRadio()) {
					return Polar.crearPolarDesdeXY( (x+ancho/2) - (bola.getX()-bola.getRadio()), (y-alto/2) - (bola.getY()+bola.getRadio()) );
				} else if (dist3<=bola.getRadio()) {
					return Polar.crearPolarDesdeXY( (x-ancho/2) - (bola.getX()+bola.getRadio()), (y+alto/2) - (bola.getY()-bola.getRadio()) );
				} else {
					return Polar.crearPolarDesdeXY( (x-ancho/2) - (bola.getX()+bola.getRadio()), (y-alto/2) - (bola.getY()+bola.getRadio()) );
				}
			}
			return null;  // Si no hay ninguno de los tres choques, es que no chocan
		} else {  // Bloque
			Bloque bloque2 = (Bloque) objeto2;
			if (this.chocaCon(bloque2)) {  // Chocan - calcular el vector de choque
				// Calculamos esquinas
				double xIzquierdaBloque1 = x - ancho/2;
				double xDerechaBloque1 = x + ancho/2;
				double yArribaBloque1 = y - alto/2;
				double yAbajoBloque1 = y + alto/2;
				double xIzquierdaBloque2 = bloque2.x - bloque2.ancho/2;
				double xDerechaBloque2 = bloque2.x + bloque2.ancho/2;
				double yArribaBloque2 = bloque2.y - bloque2.alto/2;
				double yAbajoBloque2 = bloque2.y + bloque2.alto/2;
				// Calculamos todas las opciones de posición relativa entre el bloque 1 y el 2
				if (xIzquierdaBloque2 < xIzquierdaBloque1) {  // Segundo bloque empieza a la izquierda
					if (xDerechaBloque2<=xDerechaBloque1) {  // Segundo bloque acaba dentro
						if (yAbajoBloque2 < yAbajoBloque1 && yArribaBloque2 > yArribaBloque1) {  // Segundo bloque choca dentro a la izquierda del primero
							return Polar.crearPolarDesdeXY( xIzquierdaBloque1-xDerechaBloque2, 0.0 );
						} else if (yAbajoBloque2 < yAbajoBloque1) {  // Segundo bloque choca por arriba a la izquierda del primero
							return Polar.crearPolarDesdeXY( xIzquierdaBloque1-xDerechaBloque2, yArribaBloque1-yAbajoBloque2 );
						} else if (yArribaBloque2 > yArribaBloque1) {  // Segundo bloque choca por abajo a la izquierda del primero
							return Polar.crearPolarDesdeXY( xIzquierdaBloque1-xDerechaBloque2, yAbajoBloque1-yArribaBloque2 );
						} else { // Segundo bloque engloba por la izquierda al primero
							return Polar.crearPolarDesdeXY( xIzquierdaBloque1-xDerechaBloque2, 0.0 );
						}
					} else { // Segundo bloque engloba en horizontal al primero
						if (yAbajoBloque2 < yAbajoBloque1 && yArribaBloque2 > yArribaBloque1) {  // Segundo bloque choca dentro en vertical del primero
							return Polar.crearPolarDesdeXY( xDerechaBloque1-xIzquierdaBloque1, yAbajoBloque2-yArribaBloque2 );
						} else if (yAbajoBloque2 < yAbajoBloque1) {  // Segundo bloque choca por arriba del primero
							return Polar.crearPolarDesdeXY( 0, yArribaBloque1-yAbajoBloque2 );
						} else if (yArribaBloque2 > yArribaBloque1) {  // Segundo bloque choca por abajo del primero
							return Polar.crearPolarDesdeXY( 0, yAbajoBloque1-yArribaBloque2 );
						} else { // Segundo bloque engloba por ambos lados al primero
							return Polar.crearPolarDesdeXY( -(xDerechaBloque1-xIzquierdaBloque1), -(yAbajoBloque1-yArribaBloque1) );
						}
					}
				} else if (xDerechaBloque2 <= xDerechaBloque1) { // Segundo bloque contenido en horizontal dentro del primero
					if (yAbajoBloque2 < yAbajoBloque1 && yArribaBloque2 > yArribaBloque1) {  // Segundo bloque choca dentro del primero
						return Polar.crearPolarDesdeXY( xDerechaBloque2-xIzquierdaBloque2, yAbajoBloque2-yArribaBloque2 );
					} else if (yAbajoBloque2 < yAbajoBloque1) {  // Segundo bloque choca por arriba dentro del primero
						return Polar.crearPolarDesdeXY( 0, yArribaBloque1-yAbajoBloque2 );
					} else if (yArribaBloque2 > yArribaBloque1) {  // Segundo bloque choca por abajo dentro del primero
						return Polar.crearPolarDesdeXY( 0, yAbajoBloque1-yArribaBloque2 );
					} else { // Segundo bloque engloba en vertical al primero
						return Polar.crearPolarDesdeXY( xDerechaBloque2-xIzquierdaBloque2, yAbajoBloque1-yArribaBloque1 );
					}
				} else { // Segundo bloque empieza dentro y acaba a la derecha del primero
					if (yAbajoBloque2 < yAbajoBloque1 && yArribaBloque2 > yArribaBloque1) {  // Segundo bloque choca dentro a la derecha del primero
						return Polar.crearPolarDesdeXY( xDerechaBloque1-xIzquierdaBloque2, 0.0 );
					} else if (yAbajoBloque2 < yAbajoBloque1) {  // Segundo bloque choca por arriba a la derecha del primero
						return Polar.crearPolarDesdeXY( xDerechaBloque1-xIzquierdaBloque2, yArribaBloque1-yAbajoBloque2 );
					} else if (yArribaBloque2 > yArribaBloque1) {  // Segundo bloque choca por abajo a la derecha del primero
						return Polar.crearPolarDesdeXY( xDerechaBloque1-xIzquierdaBloque2, yAbajoBloque1-yArribaBloque2 );
					} else { // Segundo bloque engloba por la derecha al primero
						return Polar.crearPolarDesdeXY( xDerechaBloque1-xIzquierdaBloque2, 0.0 );
					}
				}
			} else {  // No chocan
				return null;
			}
//			Otra manera diferente
//			if (chocaCon(bloque2)) {  // Chocan
//				double difX = bloque2.x - x;
//				double difY = bloque2.y - y;
//				boolean solapeHoriz = (x-ancho/2 >= bloque2.x-bloque2.ancho/2 && x+ancho/2 <= bloque2.x+bloque2.ancho/2) ||
//						(x-ancho/2 <= bloque2.x-bloque2.ancho/2 && x+ancho/2 >= bloque2.x+bloque2.ancho/2);  // Se solapan en toda su anchura
//				boolean solapeVert = (y-alto/2 >= bloque2.y-bloque2.alto/2 && y+alto/2 <= bloque2.y+bloque2.alto/2) ||
//						(y-alto/2 <= bloque2.y-bloque2.alto/2 && y+alto/2 >= bloque2.y+bloque2.alto/2);  // Se solapan en toda su anchura
//				if (solapeHoriz) {  // Choque vertical
//					double moduloChoque = alto/2+bloque2.alto/2 - Math.abs(difY);
//					if (difY<0) moduloChoque = -moduloChoque;
//					return Polar.crearPolarDesdeXY( 0, moduloChoque );
//				} else if (solapeVert) {  // Choque horizontal
//					double moduloChoque = ancho/2+bloque2.ancho/2 - Math.abs(difX);
//					if (difX<0) moduloChoque = -moduloChoque;
//					return Polar.crearPolarDesdeXY( moduloChoque, 0 );
//				} else {
//					double moduloChoqueX = ancho/2+bloque2.ancho/2 - Math.abs(difX);
//					if (difX<0) moduloChoqueX = -moduloChoqueX;
//					double moduloChoqueY = alto/2+bloque2.alto/2 - Math.abs(difY);
//					if (difY<0) moduloChoqueY = -moduloChoqueY;
//					return Polar.crearPolarDesdeXY( moduloChoqueX, moduloChoqueY );
//				}
//			} else {  // No chocan
//				return null;
//			}
		}
	}

	@Override
	public double getArea() {
		return ancho*alto;
	}

	@Override
	public double getVolumen() {
		return ancho*alto*Math.min(ancho, alto);
	}

	@Override
	public double getEnergia() {
		return 0.5 * getVolumen() * getVelocidad()*getVelocidad();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Bloque)) {
			return false;
		}
		Bloque bloque2 = (Bloque) obj;
		return x==bloque2.x&& y==bloque2.y && ancho==bloque2.ancho && alto==bloque2.alto;
	}

	@Override
	public String toString() {
		return "[Bloque (" + x + "," + y + ") t=" + ancho + "," + alto + "]";
	}

}

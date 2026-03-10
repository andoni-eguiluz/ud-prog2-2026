package tema1b.resueltos.pong;
import java.awt.Color;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Clase que permite crear y gestionar rectángulos (siempre paralelos a los ejes) y dibujarlos en una ventana gráfica
 */
public class Rectangulo extends Figura {
	
	/** Color por defecto de los rectángulos nuevos
	 */
	public static Color COLOR_POR_DEFECTO = Color.green;  // 
	
	/**  Método de prueba
	 * @param args	No utilizado
	 */
	public static void main(String[] args) {
		Rectangulo r1 = new Rectangulo( 0, 0,  0,  0 , Color.black );
		// Prueba de cómo el equals funciona si se le pasa un objeto de otro tipo
		if (r1.equals("hola")) {
			System.out.println( "Eres un string 'hola'" );
		} else {
			System.out.println( "No eres un string 'hola'. Qué eres?   Soy un " + r1.getClass().getName() );
		}
		System.out.println( "Realmente soy " + r1 + " de clase " + r1.getClass().getName() );
	}
	
	// =================================================
	// PARTE DE OBJETO (NO STATIC)
	// =================================================
	
	private double tamX;   // Anchura de rectángulo
	private double tamY;   // Altura de rectángulo

	/** Crea un nuevo rectángulo de radio 0, coordenada 0,0, color azul
	 */
	public Rectangulo() {
		// super();  // Ojo que lo pone por defecto Java si no lo ponemos nosotros
		super( COLOR_POR_DEFECTO );
	}
	
	/** Crea un nuevo rectángulo
	 * @param anc	Píxels de anchura del rectángulo (debe ser mayor que 0)
	 * @param alt	Píxels de altura del rectángulo (debe ser mayor que 0)
	 * @param x	Coordenada x del centro del rectángulo (en píxels)
	 * @param y	Coordenada y del centro del rectángulo (en píxels)
	 * @param color	Color del rectángulo
	 */
	public Rectangulo( double anc, double alt, double x, double y, Color color ) {
		super( x, y, color ); // Este constructor del padre es a quien delegamos la inicialización de los atributos heredados: this.x = x; this.y = y; this.color = color;
		this.tamX = anc;
		this.tamY = alt;
	}
	
	/** Crea un nuevo rectángulo, copiando los datos de otro existente
	 * @param rectangulo	Rectángulo ya creado, del que copiar los datos
	 */
	public Rectangulo( Rectangulo rectangulo ) {
		this( rectangulo.tamX, rectangulo.tamY, rectangulo.x, rectangulo.y, rectangulo.color );
		velX = rectangulo.velX;
		velY = rectangulo.velY;
	}
	
	// Método clone() es otra manera de copiar un objeto a otro duplicando sus atributos (no toda clase soporta el clone())
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new Rectangulo(this);  // Llama al constructor de copia que tenemos arriba
	}
	
	/** Devuelve la anchura del rectángulo
	 * @return	Anchura en píxels
	 */
	public double getAnchura() {
		return tamX;
	}

	/** Cambia la anchura del rectángulo. Debe ser mayor que cero
	 * @param anc	Nueva anchura del rectángulo en píxels (debe ser mayor que cero)
	 */
	public void setAnchura(double anc) {
		this.tamX = anc;
	}

	/** Devuelve la altura del rectángulo
	 * @return	Altura en píxels
	 */
	public double getAltura() {
		return tamY;
	}

	/** Cambia la altura del rectángulo. Debe ser mayor que cero
	 * @param alt	Nueva altura del rectángulo en píxels (debe ser mayor que cero)
	 */
	public void setAltura(double alt) {
		this.tamY = alt;
	}

	/** Dibuja el rectángulo en una ventana
	 * @param v	Ventana en la que dibujar el rectángulo
	 */
	@Override
	public void dibuja( VentanaGrafica v ) {
		v.dibujaRect( x-tamX/2, y-tamY/2, tamX, tamY, 2f, color, color );
	}
	
	/** Comprueba si el rectángulo se sale por la vertical de la ventana
	 * @param v	Ventana de comprobación
	 * @return	true si se está saliendo por arriba o por abajo (tocar exactamente el borde se entiende así), false en caso contrario
	 */
	@Override
	public boolean seSaleEnVertical( VentanaGrafica v ) {
		return y-tamY/2<=0 || y+tamY/2>=v.getAltura();
	}

	/** Comprueba si el rectángulo se sale por la horizontal de la ventana
	 * @param v	Ventana de comprobación
	 * @return	true si se está saliendo por izquierda o derecha (tocar exactamente el borde se entiende así), false en caso contrario
	 */
	@Override
	public boolean seSaleEnHorizontal( VentanaGrafica v ) {
		return x-tamX/2<=0 || x+tamX/2>=v.getAnchura();
	}

	@Override
	public String toString() {
		return x + "," + y + " (" + tamX + "," + tamY + ")";
	}
	
	/** Comprueba si el rectángulo es igual a otro objeto dado. Se entiende que dos rectángulos son iguales o muy cercanas  - {@link tema3.pong.Figura#DISTANCIA_MINIMA_IGUALDAD}
	 * y sus tamaños (altura y anchura) también lo son
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Rectangulo) {
			Rectangulo r2 = (Rectangulo) obj; // Nota: Cast de obj a círculo2 (ver capítulo de herencia)
			return Math.abs(r2.x-x)<=DISTANCIA_MINIMA_IGUALDAD && Math.abs(r2.y-y)<=DISTANCIA_MINIMA_IGUALDAD 
					&& Math.abs(r2.tamX-tamX)<=DISTANCIA_MINIMA_IGUALDAD && Math.abs(r2.tamY-tamY)<=DISTANCIA_MINIMA_IGUALDAD; // Devuelve true o false dependiendo de las coordenadas y de los tamaños de los rectángulos this y r2
		} else {
			return false;
		}
	}

}

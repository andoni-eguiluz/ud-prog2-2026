package tema1b.resueltos.pong;

import java.awt.Color;
import utils.ventanas.ventanaBitmap.VentanaGrafica;

/** Clase que permite crear y gestionar círculos, dibujados como balones (gráfico y dibujarlos en una ventana gráfica
 */
public class Circulo extends Figura {
	
	/** Color por defecto de los círculos nuevos (azul)
	 */
	public static final Color COLOR_POR_DEFECTO = Color.blue;  // 

	/** Método de prueba de la clase Círculo - crea un círculo y lo saca a consola y lo visualiza en una ventana gráfica pequeña
	 * @param args	No utilizado
	 */
	public static void main(String[] args) {
		// Nota: Circulo c2 = new Circulo( Color.red );  no es posible, no se hereda el constructor Figura(color) (ningún constructor se hereda)
		Circulo c1 = new Circulo( 50, 100, 150, Color.yellow );
		System.out.println( c1 );
		VentanaGrafica v = new VentanaGrafica( 400, 300, "Prueba círculo" );
		c1.dibuja( v );
	}
	
	// =================================================
	// PARTE DE OBJETO (NO STATIC)
	// =================================================
	
	private double radio;  // Radio de círculo (en píxels)
	private double rot = 0.0; // rotación del círculo (para hacer animación)

	/** Crea un nuevo círculo de radio 0, coordenada 0,0, color {@link #COLOR_POR_DEFECTO}
	 */
	public Circulo() {
		// super();  // Nota: Ojo que lo pone por defecto Java si no lo ponemos nosotros
		super( COLOR_POR_DEFECTO );
	}
	
	/** Crea un nuevo círculo
	 * @param radio	Píxels de radio del círculo (debe ser mayor que 0)
	 * @param x	Coordenada x del centro del círculo (en píxels)
	 * @param y	Coordenada y del centro del círculo (en píxels)
	 * @param color	Color del círculo
	 */
	public Circulo( double radio, double x, double y, Color color ) {
		super( x, y, color ); // Nota: Este constructor del padre es a quien delegamos la inicialización de los atributos heredados: this.x = x; this.y = y; this.color = color;
		this.radio = radio;
	}
	
	/** Crea un nuevo círculo copiando los datos de otro existente
	 * @param circulo	Círculo ya creado, del que copiar los datos
	 */
	public Circulo( Circulo circulo ) {
		this( circulo.radio, circulo.x, circulo.y, circulo.color );
		velX = circulo.velX;
		velY = circulo.velY;
	}
	
	// Método clone() es otra manera de copiar un objeto a otro duplicando sus atributos (no toda clase soporta el clone())
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new Circulo(this);  // Llama al constructor de copia que tenemos arriba
	}
	
	/** Devuelve el radio del círculo
	 * @return	Radio en píxels
	 */
	public double getRadio() {
		return radio;
	}

	/** Cambia el radio del círculo. Debe ser mayor que cero
	 * @param radio	Nuevo radio del círculo (debe ser mayor que cero)
	 */
	public void setRadio(double radio) {
		this.radio = radio;
	}

	/** Devuelve la rotación del círculo
	 * @return	Rotación en radianes
	 */
	public double getRotacion() {
		return rot;
	}

	/** Cambia la rotación del círculo
	 * @param rotacion	Nueva rotación del círculo, en radianes
	 */
	public void setRotacion(double rotacion) {
		this.rot = rotacion;
	}

	/** Rota el círculo (balón). Se visualizará con la nueva rotación en el próximo dibujado  {@link #dibuja(VentanaGrafica)}
	 * @param rotacion	Radianes de rotación adicionales a la rotación que ya tenga
	 */
	public void rota( double rotacion ) {
		rot += rotacion;
	}
	
	/** Dibuja el círculo (con gráfico de balón) en una ventana
	 * @param v	Ventana en la que dibujar el círculo
	 */
	public void dibuja( VentanaGrafica v ) {
		v.dibujaCirculo( x, y, radio, 2f, color ); // Dibuja el círculo debajo
		v.dibujaImagen( "balon.png", x, y, 2*radio/50, rot, 1.0f );  // Y el balón encima (si no existiera el gráfico, se vería solo el círculo)
	}
	
	/** Comprueba si el círculo se sale por la vertical de la ventana
	 * @param v	Ventana de comprobación
	 * @return	true si se está saliendo por arriba o por abajo (tocar exactamente el borde se entiende así), false en caso contrario
	 */
	@Override
	public boolean seSaleEnVertical( VentanaGrafica v ) {
		return y-radio<=0 || y+radio>=v.getAltura();
	}

	/** Comprueba si el círculo se sale por la horizontal de la ventana
	 * @param v	Ventana de comprobación
	 * @return	true si se está saliendo por izquierda o derecha (tocar exactamente el borde se entiende así), false en caso contrario
	 */
	@Override
	public boolean seSaleEnHorizontal( VentanaGrafica v ) {
		return x-radio<=0 || x+radio>=v.getAnchura();
	}

	/** Comprueba si el círculo se sale por la vertical de la ventana
	 * @param v	Ventana de comprobación
	 * @return	+1 si se está saliendo por arriba, -1 si se sale por abajo, 0 si no se sale
	 */
	public int salidaVertical( VentanaGrafica v ) {
		if (y-radio<=0) return +1;
		else if (y+radio>=v.getAltura()) return -1;
		else return 0;
	}

	/** Comprueba si el círculo se sale por la horizontal de la ventana
	 * @param v	Ventana de comprobación
	 * @return	+1 si se está saliendo por izquierda, -1 si se sale por derecha, 0 si no se sale
	 */
	public int salidaHorizontal( VentanaGrafica v ) {
		if (x-radio<=0) return +1;
		else if (x+radio>=v.getAnchura()) return -1;
		else return 0;
	}

	// Nota: Los comentarios de métodos override no son imprescindibles (están en la clase original y se "heredan" en javadoc
	
	@Override
	public String toString() {
		return super.toString() + " (" + radio + ")";
	}
	
	// Nota: Aunque sí se pueden indicar si el método aporta algo diferencial como aquí
	
	/** Comprueba si el círculo es igual a otro objeto dado. Se entiende que dos círculos son iguales
	 * cuando las coordenadas de sus centros (redondeadas a enteros) son muy cercanas (distancia x e y inferior a ) - {@link tema3.pong.Figura#DISTANCIA_MINIMA_IGUALDAD}
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Circulo)) {  // Si el objeto no es un círculo, no son iguales
			return false;
		}
		Circulo c2 = (Circulo) obj;  // Nota: Cast de obj a círculo2 (ver capítulo de herencia)
		return Math.abs(c2.x-x)<=DISTANCIA_MINIMA_IGUALDAD && Math.abs(c2.y-y)<=DISTANCIA_MINIMA_IGUALDAD; // Devuelve true o false dependiendo de las coordenadas de los círculos this y c2
	}
	
}
